#include "MoreInfo.h"
#include "../std.h"

MoreInfo::MoreInfo()
{
	mWebView = new WebView(0, 0, ScreenSize::width(), ScreenSize::height(), NULL);
	mWebView->enableScrollBars(WebView::SCROLLBARS_BOTH, true);
	mWebView->enableZoom(true);
	mWebView->openURL("http://spaceappschallenge.org");
	mWebView->setAllowResize(Widget::ALLOW_RESIZE_BOTH);
	setMain(mWebView);
}

MoreInfo::~MoreInfo()
{
	delete mWebView;
}

void MoreInfo::setUrl(const String& url)
{
	mWebView->openURL(url);
}
