/**
 * Connects to the API and gets new data
 */
#ifndef GATHERER_H_
#define GATHERER_H_

#include <AMDownload/DownloadManager.h>
using namespace AMDownload;

#include <AMLocation/LocationBus/LocationBus.h>
#include <AMLocation/Location.h>
using namespace AMLocation;

#include <AMUtil/DateTime.h>
using namespace AMUtil;

#include <AMXml/XmlDocument.h>
#include <AMXml/XmlReader.h>
using namespace AMXml;

class GathererListener
{
	public:
		/**
		 * This will be called before new data is added. It gives the receiver
		 * notification to clear the UI if necessary
		 */
		virtual void prepareForNewData(int count) {}
		virtual void eventReceived(const String& name, const String& weather, DateTime& start, DateTime& end) {}
};

class Gatherer : public DownloadRequestListener, public LocationListener
{
	public:
		Gatherer();
		virtual ~Gatherer();

		void update();

		/* LocationListener */
		void locationReceived(MALocation& location);

		/* DownloadRequestListener */
		void downloadError(DownloadRequest* request, int errorCode);

		/* Downloaded XML will be in the request object here */
		void downloadFinished(DownloadRequest* request);

		void addGathererListener(GathererListener* listener);
		void removeGathererListener(GathererListener* listener);

	protected:
		Vector<GathererListener*> mListeners;
		MALocation mLastLocation;

		void requestData(double lat, double lon);
		void processEventData(XmlDocument* data);

	private:
};


#endif /* GATHERER_H_ */
