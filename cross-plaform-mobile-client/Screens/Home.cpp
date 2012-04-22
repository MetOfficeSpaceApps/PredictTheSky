#include "Home.h"
#include "MAHeaders.h"

Home::Home()
{
	setTitle("Now and Next");
	mLayout = createVerticalLayout();
	mLayout->add(createTitle("Now & Next"));
//	int mainItemHeight = (int)(ScreenSize::height() / 3);

	mMainItemImage = createImage(IMAGE_ISS);
	mMainItemImage->setAutoSizeX(false);
	mMainItemImage->setAutoSizeY(true);
	//mMainItemImage->setScaleImage(true);
	mMainItemImage->setAllowResize(Widget::ALLOW_RESIZE_X_ONLY);

	mLayout->add(mMainItemImage);

	mMainItemDesc = createLabel("ISS visible, 20:10 - 20:20");
	mMainItemDesc->setFont(FontWarehouse::find(FONT_SMALL));
	mLayout->add(mMainItemDesc);

	mLayout->add(createTitle("Coming Up"));

	mComingNextItems = createListView();
	mComingNextItems->setAllowResize(Widget::ALLOW_RESIZE_BOTH);
	mComingNextItems->add(createItem(ICON_ISS, "ISS\nToday 20:00"));
	mComingNextItems->add(createItem(ICON_ISS, "Hubble\nToday 10:00"));
	mComingNextItems->add(createItem(ICON_ISS, "Meteors\nThur 20:00"));
	mComingNextItems->add(createItem(ICON_ISS, "ISS\nSat 20:00"));
	mLayout->add(mComingNextItems);

	mComingNextItems->setSelectedIndex(0);
	mComingNextItems->addListBoxListener(this);
	setMain(mLayout);
}

Home::~Home()
{
	delete mLayout;
}

ListViewItem* Home::createItem(MAHandle eventImage, const String& caption)
{
	ListViewItem* lvi = createListViewItem(caption);
	lvi->setIcon(eventImage);

	return lvi;
}

void Home::locationReceived(MALocation& location)
{
	LOG("Location received %f %f", location.lat, location.lon);
}

void Home::keyPressEvent(int keyCode, int nativeCode)
{
	switch(keyCode)
	{
		case MAK_UP:
		case MAK_2:
		case MAK_LEFT:
		case MAK_4:
			mComingNextItems->selectPreviousItem(false);
			break;
		case MAK_DOWN:
		case MAK_8:
		case MAK_RIGHT:
		case MAK_6:
			mComingNextItems->selectNextItem(false);
			break;
		case MAK_5:
		case MAK_FIRE:
			itemSelected(mComingNextItems, NULL, NULL);
			break;
	}
}

void Home::itemSelected(ListBox *sender, Widget *selectedWidget, Widget *unselectedWidget)
{
	STACKSCREEN->show(ScreenWarehouse::find(SCREEN_DETAIL));
}
