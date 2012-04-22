#include <MAUtil/Moblet.h>
#include <conprint.h>
using namespace MAUtil;
#include "AppLoader.h"
#include "std.h"

class MyMoblet : public Moblet
{
	public:
		MyMoblet()
		{
			Widget::setUseNativeUI(USE_NATIVE_UI);
			AppLoader::load();
			StackScreen* ss = (StackScreen*)ScreenWarehouse::find("stackscreen");
			if(ss != NULL)
			{
			ss->show();
				ss->show(ScreenWarehouse::find(SCREEN_HOME));
			}
/*			TabScreen* tabs = (TabScreen*)ScreenWarehouse::find(SCREEN_TABSCREEN);
			if(tabs != NULL)
				tabs->show();*/
		}
};

/**
 * Entry point of the program. The MAMain function
 * needs to be declared as extern "C".
 */
extern "C" int MAMain()
{
	Moblet::run(new MyMoblet());
	return 0;
}
