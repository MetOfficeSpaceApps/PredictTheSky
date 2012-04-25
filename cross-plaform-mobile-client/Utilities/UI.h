
#ifndef UI_H_
#define UI_H_

#include <AMUI/Label.h>
#include <AMUI/Image.h>
#include <AMUI/StackLayout.h>
#include <AMUI/RelativeLayout.h>
#include <AMUI/ListBox.h>
#include <AMUI/ListViewItem.h>
using namespace AMUI;

Label* createLabel(const String& caption);
Label* createTitle(const String& caption);
Image* createImage(MAHandle image);
StackLayout* createVerticalLayout();
RelativeLayout* createRelativeLayout();
ListBox* createListView();
ListViewItem* createListViewItem(const String& caption);

#endif /* UI_H_ */
