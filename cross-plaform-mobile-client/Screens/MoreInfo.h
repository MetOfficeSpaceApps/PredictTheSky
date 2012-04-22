#ifndef MOREINFO_H_
#define MOREINFO_H_

#include <AMUI/Screen.h>
#include <AMUI/WebView.h>
#include "../Utilities/UI.h"
using namespace AMUI;

class MoreInfo : public Screen
{
	public:
		MoreInfo();
		virtual ~MoreInfo();

		void setUrl(const String& url);

	protected:
		WebView* mWebView;

	private:
};


#endif /* MOREINFO_H_ */
