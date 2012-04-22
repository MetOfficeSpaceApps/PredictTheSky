#include "UI.h"
#include <AMUtil/ScreenSize.h>
#include "../std.h"
#include "MAHeaders.h"
using namespace AMUtil;

Label* createLabel(const String& label)
{
	Label* l = new Label(0, 0, 10, 10, NULL);
	l->setCaption(label);

#if USE_NATIVE_UI
	l->setAutoSizeY(true);
	l->setAutoSizeX(true);
#else
	l->setAllowResize(Widget::ALLOW_RESIZE_X_ONLY);
	l->setAutoSizeY(true);
	l->setAutoSizeX(false);
#endif
	l->setMultiLine(true);
	l->setDrawBackground(false);

	return l;
}

Label* createTitle(const String& label)
{
	Label* l = createLabel(label);
	l->setStyle(StyleWarehouse::find(STYLE_TITLE));

	return l;
}

Image* createImage(MAHandle image)
{
	Image* i = new Image(0, 0, 10, 10, NULL, true, true, image);
	return i;
}

StackLayout* createVerticalLayout()
{
	StackLayout* sl = new StackLayout(0, 0, 100, 100, NULL, StackLayout::SL_VERTICAL, StackLayout::SL_STRETCH);
	sl->setStyle(StyleWarehouse::find(STYLE_LAYOUT));
	return sl;
}

RelativeLayout* createRelativeLayout()
{
	RelativeLayout* rl = new RelativeLayout(0, 0, 10, 10, NULL);
	rl->setAllowResize(Widget::ALLOW_RESIZE_BOTH);
	return rl;
}

ListBox* createListView()
{
	ListBox* lb = new ListBox(0, 0, 10, 10, NULL, ListBox::LBO_VERTICAL, ListBox::LBA_LINEAR, false);
	lb->setAllowResize(Widget::ALLOW_RESIZE_BOTH);
	lb->setDrawBackground(false);
	return lb;
}

ListViewItem* createListViewItem(const String& caption)
{
	ListViewItem* lvi = new ListViewItem(0, 0, ScreenSize::width() - 20, 50, NULL);
	lvi->setStyle(StyleWarehouse::find(STYLE_LVI));
	lvi->setAllowResize(Widget::ALLOW_RESIZE_BOTH);
	lvi->setText(caption);
	lvi->setPaddingLeft(5);
	lvi->setAccessory(ListViewItem::LVI_ACCESSORY_HAS_DETAILS, ICON_ARROW);

	return lvi;
}
