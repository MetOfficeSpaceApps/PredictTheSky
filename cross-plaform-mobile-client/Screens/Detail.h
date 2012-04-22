#ifndef DETAIL_H_
#define DETAIL_H_

#include <AMUI/Screen.h>
#include "../Utilities/UI.h"
#include "../std.h"
using namespace AMUI;

class Detail : public Screen, public ListBoxListener
{
	public:
		Detail();
		virtual ~Detail();

		/* ListBoxListener */
        void itemSelected(ListBox *sender, Widget *selectedWidget, Widget *unselectedWidget);
        void blocked(ListBox *sender, int direction) {}

		void keyPressEvent(int keyCode, int nativeCode);

	protected:
		StackLayout* mLayout;
		Image* mMainImage;
		Label* mDesc;
		ListBox* mOptions;

		void showUrl(const String& url);

		ListViewItem* createItem(MAHandle eventImage, const String& caption);

};

#endif /* DETAIL_H_ */
