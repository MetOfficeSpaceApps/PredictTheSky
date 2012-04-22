#ifndef HOME_H_
#define HOME_H_

#include <AMUI/Screen.h>
#include "../Utilities/UI.h"
#include "../std.h"
#include <AMLocation/LocationBus/LocationBus.h>
using namespace AMLocation;

class Home : public Screen, public LocationListener, public ListBoxListener
{
	public:
		Home();
		virtual ~Home();

		/* Location Listener */
		void locationReceived(MALocation& location);

		/* ListBoxListener */
        void itemSelected(ListBox *sender, Widget *selectedWidget, Widget *unselectedWidget);
        void blocked(ListBox *sender, int direction) {}

		void keyPressEvent(int keyCode, int nativeCode);

	protected:
		StackLayout* mLayout;
		RelativeLayout* mMainItemLayout;
		Image* mMainItemImage;
		Label* mMainItemDesc;
		ListBox* mComingNextItems;

		ListViewItem* createItem(MAHandle eventImage, const String& caption);

	private:
};

#endif /* HOME_H_ */
