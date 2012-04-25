#include "AppLoader.h"
#include "std.h"
#include "MAHeaders.h"
#include <AMLocation/Location.h>
#include <AMLocation/LocationBus/LocationBus.h>
#include <AMLocation/LocationProviders/CellIDProvider.h>
#include <AMLocation/LocationProviders/GPSProvider.h>
#include <AMLocation/CellIDDatabases/GoogleCellIDDatabase.h>
using namespace AMLocation;

#include <AMUI/Engine.h>
#include <AMUI/Colours.h>
#include <AMUI/StackScreen.h>
#include <AMUI/TabScreen.h>
using namespace AMUI;

#include "Screens/Home.h"
#include "Screens/Detail.h"
#include "Screens/MoreInfo.h"

/*static*/ void AppLoader::load()
{
	loadFonts();
	loadSkins();
	loadStyles();
	loadScreens();
	loadLocation();
}

/*static*/ void AppLoader::loadFonts()
{
	FontWarehouse::add(FONT_STANDARD, new Font(STANDARD));
	FontWarehouse::add(FONT_SMALL, new Font(SMALL));

	Engine::getSingleton().setDefaultFont(FontWarehouse::find(FONT_STANDARD));
}

/*static*/ void AppLoader::loadSkins()
{
	//SkinWarehouse::add(SKIN_LISTVIEWITEM, new WidgetSkin(SKIN_LVI_SEL, SKIN_LVI, 15, 85, 15, 85, false, false));
}

/*static*/ void AppLoader::loadStyles()
{
	Style* screenTitle = new Style();
	screenTitle->setFont(FontWarehouse::find(FONT_STANDARD));
	screenTitle->setPadding(2, 2, 2, 2);
	screenTitle->setBackground(WHITE, false, NULL, WHITE);
	screenTitle->setFixedSize(Style::ALLOW_RESIZE_X_ONLY);
	screenTitle->setNativeFontSize(FONT_SIZE_TITLE);
	StyleWarehouse::add(STYLE_TITLE, screenTitle);

	Style* menuOption = new Style();
#if !USE_NATIVE_UI
	menuOption->setFont(FontWarehouse::find(FONT_SMALL));
	menuOption->setBackground(0xF3F3F3, true, NULL, 0xE5E5E5);
#else
	menuOption->setNativeFontSize(FONT_SIZE_SMALL);
#endif
	StyleWarehouse::add(STYLE_LVI, menuOption);

	Style* mainLayout = new Style();
#if !USE_NATIVE_UI
	mainLayout->setBackground(0xF6F6F6, true, NULL, 0xF6F6F6);
#endif
	mainLayout->setPadding(10, 10, 10, 10);
	StyleWarehouse::add(STYLE_LAYOUT, mainLayout);
}

/*static*/ void AppLoader::loadScreens()
{
	TabScreen* tabs = new TabScreen("Predict the Sky", false);
#if !USE_NATIVE_UI
	tabs->setLayoutStyle(StyleWarehouse::find(STYLE_LAYOUT));
	tabs->setListItemStyle(StyleWarehouse::find(STYLE_LVI));
#endif
	ScreenWarehouse::add(SCREEN_TABSCREEN, tabs);

	StackScreen* ss = new StackScreen();
	ScreenWarehouse::add(SCREEN_STACKSCREEN, (Screen*)ss);

	ScreenWarehouse::add(SCREEN_HOME, new Home());
	ScreenWarehouse::add(SCREEN_DETAIL, new Detail());
	ScreenWarehouse::add(SCREEN_MOREINFO, new MoreInfo());

}

/*static*/ void AppLoader::loadLocation()
{
	CellIDProvider* cellid = new CellIDProvider();
	cellid->setUseEmulatedLocation(USE_EMULATED_LOCATION);
	cellid->setCellIDDatabase(new GoogleCellIDDatabase());
	LocationBus::getBus()->addLocationProvider(cellid);

	GPSProvider* gps = new GPSProvider();
	gps->setUseEmulatedLocation(USE_EMULATED_LOCATION);
	LocationBus::getBus()->addLocationProvider(gps);


}
