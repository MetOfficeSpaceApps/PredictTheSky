Readme.txt

This project is for use with the Space Apps Challenge 'Predict the Sky' 

It is written in C++, and should be built with MoSync 3.0 (www.mosync.com), which will compile 
the application for J2ME, Android, Windows Phone, iOS, Symbian, Meego and Blackberry.  For iOS, the project
builds an XCode C++ project for building on OSX.  For WP7, the project builds a C# project for building in
Visual Studio. On Android, the project will recompile to native ARM code at runtime (< 1 second).  J2ME output
is statically linked bytecode.  Symbian projects have a runtime interpreter.

It also requires the AppMancer libraries (appmancer.com).

Both MoSync and AppMancer are open source projects released on GPL.

The application has screens for showing now/next, and details of each item.

The application will get the users location by GPS or CellID, and use Google's API for converting
a cell id into Lat/Lon pair.

There is a class called 'Gatherer' which will retreive new data from the server, but this isn't wired up yet.
It may just need the correct URL entering, but it is untested.

If you are using a smartphone platform (Windows Phone 7, iOS or Android), then the details screen will open the
links to Wikipedia etc in the app.  Other platforms will launch the native browser.

Author: 
Samuel Pickard
AppMancer
@rival
sjp@appmancer.com