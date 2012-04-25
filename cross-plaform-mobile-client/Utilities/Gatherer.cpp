#include "Gatherer.h"
#include "../std.h"
#include <AMUtil/snprintf.h>

Gatherer::Gatherer()
{}

Gatherer::~Gatherer()
{}

void Gatherer::locationReceived(MALocation& location)
{
	LOG("Got a new location: %f %f", location.lat, location.lon);

	/* Create a new download request to get the data */
	mLastLocation = location;
	update();
}

void Gatherer::update()
{
	requestData(mLastLocation.lat, mLastLocation.lon);
}

void Gatherer::requestData(double lat, double lon)
{
	/* Create a DownloadRequest to get the XML from the server */
	/* The url is in std.h */
	DownloadRequest* req = DownloadManager::createRequest(this);

	char buffer[400];
	snprintf(buffer, 400, DATA_URL, lat, lon);
	req->setUrl(buffer);

	DownloadManager::enqueue(req);
}

void Gatherer::downloadError(DownloadRequest* request, int errorCode)
{
}

void Gatherer::downloadFinished(DownloadRequest* request)
{
	if(request == NULL || request->getData() == NULL) return;

	/* Get the XML from the request */
	int len = maGetDataSize(request->getData());
	char buffer[len+1];
	maReadData(request->getData(), buffer, 0, len);

	/* Convert to XmlDocument */
	XmlReader xr(XML_FORMAT);
	XmlDocument* data = xr.parseXML(buffer);

	/* Clean up resources */
	maDestroyPlaceholder(request->getData());
	delete request;

	/* Process the XML */
	processEventData(data);

	delete data;
}

void Gatherer::processEventData(XmlDocument* data)
{
	Vector<XmlNode*> events = data->rootNode->selectNodes("event");

	if(events.size() == 0) /* no data */ return;

	/* Tell the screens that they are about to get new data, and how much */
	Vector_each(GathererListener*, itr, mListeners)
		(*itr)->prepareForNewData(events.size());

	XmlNode* eventNode = NULL;
	Vector_each(XmlNode*, en, events)
	{
		eventNode = *en;
		String name = eventNode->selectSingleNode("title")->getInnerText();
		String weather = eventNode->selectSingleNode("weather/simple")->getInnerText();
		DateTime start = DateTime(eventNode->selectSingleNode("start/time")->getInnerText());
		DateTime end = DateTime(eventNode->selectSingleNode("end/time")->getInnerText());

		Vector_each(GathererListener*, itr, mListeners)
		{
			(*itr)->eventReceived(name, weather, start, end);
		}
	}
}

void Gatherer::addGathererListener(GathererListener* listener)
{
	mListeners.add(listener);
}

void Gatherer::removeGathererListener(GathererListener* listener)
{
	Vector_each(GathererListener*, itr, mListeners)
	{
		if(*itr == listener)
		{
			mListeners.remove(itr);
			break;
		}
	}
}
