#ifndef MYLOCATION_H_
#define MYLOCATION_H_

#include <AMUI/Screen.h>
#include <AMLocation/LocationBus/LocationBus.h>
using namespace AMUI;
using namespace AMLocation;
#include "../Utilities/UI.h"

class MyLocation : public Screen, public LocationListener
{
	public:
		MyLocation();
		virtual ~MyLocation();

		/* LocationListener */
		void locationReceived(MALocation& location);

	protected:
		StackLayout* mLayout;
		Label* mDescription;

	private:
};


#endif /* MYLOCATION_H_ */
