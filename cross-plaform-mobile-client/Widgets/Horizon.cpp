#include "Horizon.h"
#include <MAUtil/Graphics.h>
#include "../std.h"
#include <mastdlib.h>
#include <madmath.h>
using namespace MAUtil;

Horizon::Horizon(int x, int y, int width, int height, Widget* parent) : Widget(x, y, width, height, parent)
{}

Horizon::Horizon(const Horizon& h) : Widget(h)
{}

Horizon::~Horizon()
{}

Widget* Horizon::clone()
{
	Horizon* h = new Horizon(*this);
	h->cloneChildren(this);

	return h;
}

void Horizon::drawWidget()
{
	plotCircle();
}

void Horizon::plotCircle()
{
	int r = paddedBounds.height > paddedBounds.width ? paddedBounds.width >> 1 : paddedBounds.height >> 1;
	int ox = paddedBounds.width >> 1;
	int oy = paddedBounds.height >> 1;
	int lx = ox + r;
	int ly = oy;
	int nx = 0;
	int ny = 0;
	Gfx_setColor(255, 255, 255);
	double PI = 3.14;

	for(int i = 0; i <= 360; i++)
	{
		//LOG("Iteration %d", i);
		//LOG("From %d, %d", lx, ly);
		nx = (int)(ox + (r * cos(i * PI / 180)));
		ny = (int)(oy + (r * sin(i * PI / 180)));
		//LOG("To %d, %d", nx, ny);
		Gfx_line(lx, ly, nx, ny);
		lx = nx;
		ly = ny;
	}
}

void Horizon::plotClouds()
{}

void Horizon::plotElements()
{}
