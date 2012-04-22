
#ifndef HORIZON_H_
#define HORIZON_H_

#include <AMUI/Widget.h>
using namespace AMUI;

class Horizon : public Widget
{
	public:
		Horizon(int x, int y, int width, int height, Widget* parent = NULL);
		Horizon(const Horizon& h);
		virtual ~Horizon();

		/* Widget Requirements */
		void drawWidget();
		Widget* clone();
		const char* getType() const { return "Horizon"; }
		const char* getText() const { return ""; }

	protected:
		void plotCircle();
		void plotClouds();
		void plotElements();
};


#endif /* HORIZON_H_ */
