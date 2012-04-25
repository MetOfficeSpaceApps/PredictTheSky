#ifndef APPLOADER_H_
#define APPLOADER_H_

class AppLoader
{
	public:
		static void load();

	protected:
		static void loadFonts();
		static void loadSkins();
		static void loadStyles();
		static void loadScreens();
		static void loadLocation();
};


#endif /* APPLOADER_H_ */
