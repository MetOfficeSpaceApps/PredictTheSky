#include "Detail.h"
#include "MAHeaders.h"
#include "MoreInfo.h"

Detail::Detail()
{
	setTitle("Detail");
	mLayout = createVerticalLayout();
	mMainImage = createImage(IMAGE_ISS);
	mMainImage->setAutoSizeX(false);
	mMainImage->setAutoSizeY(true);
	mMainImage->setAllowResize(Widget::ALLOW_RESIZE_X_ONLY);

	mLayout->add(mMainImage);

	mDesc = createTitle("ISS\nTonight - 22:00\nClear skies");
	mLayout->add(mDesc);

	mOptions = createListView();
	mOptions->setAllowResize(Widget::ALLOW_RESIZE_BOTH);
	mOptions->add(createItem(ICON_SKYMAP, "Sky Map"));
	mOptions->add(createItem(ICON_WIKIPEDIA, "Wikipedia"));
	mOptions->add(createItem(ICON_METOFFICE, "Met Office"));
	mOptions->addListBoxListener(this);
	mLayout->add(mOptions);

	mOptions->setSelectedIndex(0);
	setMain(mLayout);
}

Detail::~Detail()
{
	delete mLayout;
}

void Detail::itemSelected(ListBox *sender, Widget *selectedWidget, Widget *unselectedWidget)
{
	switch(mOptions->getSelectedIndex())
	{
		case 0:
			break;
		case 1:
			showUrl("http://en.wikipedia.org/wiki/International_Space_Station");
			break;
		case 2:
			showUrl("http://www.metoffice.gov.uk/");
			break;
	}
}

void Detail::showUrl(const String& url)
{
#if USE_NATIVE_UI
	MoreInfo* mi = (MoreInfo*)ScreenWarehouse::find(SCREEN_MOREINFO);
	if(mi != NULL)
	{
		mi->setUrl(url);
		STACKSCREEN->show(mi);
	}
#else
	maPlatformRequest(url.c_str());
#endif
}


ListViewItem* Detail::createItem(MAHandle eventImage, const String& caption)
{
	ListViewItem* lvi = createListViewItem(caption);
	lvi->setIcon(eventImage);

	return lvi;
}

void Detail::keyPressEvent(int keyCode, int nativeCode)
{
	switch(keyCode)
	{
		case MAK_UP:
		case MAK_2:
		case MAK_LEFT:
		case MAK_4:
			mOptions->selectPreviousItem(false);
			break;
		case MAK_DOWN:
		case MAK_8:
		case MAK_RIGHT:
		case MAK_6:
			mOptions->selectNextItem(false);
			break;
		case MAK_FIRE:
		case MAK_5:
			itemSelected(mOptions, NULL, NULL);
			break;
		case MAK_SOFTLEFT:
			STACKSCREEN->back();
			break;
	}
}
