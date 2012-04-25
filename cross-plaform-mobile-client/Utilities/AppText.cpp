#include "AppText.h"
#include <AMXml/XmlReader.h>
#include "../std.h"

XmlDocument* AppText::sLanguage = NULL;
String AppText::sEmpty = "";

AppText::AppText()
{}

AppText::~AppText()
{
	if(sLanguage != NULL)
		delete sLanguage;
}

/*static*/ void AppText::loadLanguage(MAHandle lang)
{
	XmlReader xr;
	if(sLanguage != NULL) delete sLanguage;

	char buffer[maGetDataSize(lang) + 1];
	maReadData(lang, buffer, 0, maGetDataSize(lang));
	String xml(buffer);
	xr.setEncoding(XML_FORMAT);

	sLanguage = xr.parseXML(xml);
}

/*static*/ const String& AppText::getText(const String& key)
{
	if(sLanguage == NULL) return sEmpty;
	XmlNode* n = sLanguage->rootNode->selectSingleNode(key);
	if(n == NULL)
		return sEmpty;
	else
		return n->getInnerText();
}
