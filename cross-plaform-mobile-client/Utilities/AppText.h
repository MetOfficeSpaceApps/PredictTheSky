
#ifndef APPTEXT_H_
#define APPTEXT_H_

#include <AMXml/XmlDocument.h>
using namespace AMXml;

class AppText
{
	public:
		AppText();
		~AppText();

		static void loadLanguage(MAHandle lang);
		static const String& getText(const String& key);

	protected:
		static XmlDocument* sLanguage;
		static String sEmpty;
};


#endif /* APPTEXT_H_ */
