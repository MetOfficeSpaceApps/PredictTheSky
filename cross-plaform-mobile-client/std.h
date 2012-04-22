#ifndef STD_H_
#define STD_H_

#include <AMUI/Font.h>
#include <AMUI/Style.h>
#include <AMUI/StackScreen.h>
using namespace AMUI;

#include <AMWarehouse/FontWarehouse.h>
#include <AMWarehouse/ImageWarehouse.h>
#include <AMWarehouse/SkinWarehouse.h>
#include <AMWarehouse/StyleWarehouse.h>
#include <AMWarehouse/ScreenWarehouse.h>

#include <AMUtil/ScreenSize.h>
using namespace AMUtil;

#include <conprint.h>
#define LOG lprintfln

#define STACKSCREEN ((StackScreen*)ScreenWarehouse::find("stackscreen"))

/* Data Source URL */
/* First %f is lat %f is lon  - format this url as appropriate */
#define DATA_URL "http://example.com/%f/%f"

/* Font sizes */
#define FONT_SIZE_TITLE 40
#define FONT_SIZE_SMALL 32

/* Switches */
#define USE_NATIVE_UI true
#define XML_FORMAT XmlReader::UTF8
#define USE_EMULATED_LOCATION true

/* Resources */
#define FONT_TITLE "title"
#define FONT_STANDARD "standard"
#define FONT_SMALL "small"

#define STYLE_TITLE "title"
#define STYLE_LAYOUT "layout"
#define STYLE_LVI "lvi"

#define SKIN_LISTVIEWITEM "lvi"

/* Screens */
#define SCREEN_TABSCREEN "tabs"
#define SCREEN_STACKSCREEN "stackscreen"
#define SCREEN_HOME "home"
#define SCREEN_DETAIL "detail"
#define SCREEN_MOREINFO "moreinfo"


/* AppText */

#endif /* STD_H_ */
