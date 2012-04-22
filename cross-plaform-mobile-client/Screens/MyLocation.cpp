#include "MyLocation.h"
#include "../std.h"
#include "../Utilities/UI.h"

MyLocation::MyLocation()
{
	setTitle("My Location");
	mLayout = createVerticalLayout();
	mDescription = createLabel("Location goes here");
	setMain(mLayout);
}

MyLocation::~MyLocation()
{
	delete mLayout;
}

void MyLocation::locationReceived(MALocation& location)
{

}
