PredictTheSky - Back End code
=============================

This is written in XQuery and run's atop the eXist-db Native XML Database. You will need eXist-db trunk or 2.0 preview. http://www.exist-db.org.

The code here consists of a database backup which can be imported into eXist-db.You also need to enable the datetime XQuery extension module for eXist-db, see conf.xml in your eXist-db install folder. After starting eXist-db you can restore the backup with the Java Admin Client. After restoring, visit the URL http://localhost:8080/exist/rest/db/spaceapps/space.xql

The parameters are ?lat=xyz&lng=abc&format=fmt where these are your latitude, longitude and format is either 'json' or 'xml'.
If you want 'jsonp' like output you can use the 'json' format and append the parameter jsonp=myFunctionName. e.g. ?lat=50.7218&lng=-3.5336&format=json&jsonp=getEventsData

The URL for a test service is available here - http://www.adamretter.org.uk/spaceapps/space.xql
