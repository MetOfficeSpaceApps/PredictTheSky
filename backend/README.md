PredictTheSky - Back End code
=============================

This is written in XQuery 3.0 and run's atop the eXist-db Native XML Database. You will need eXist-db trunk or 2.0 preview. http://www.exist-db.org.

The code here consists of a database backup which can be imported into eXist-db.You also need to enable the datetime XQuery extension module for eXist-db, see conf.xml in your eXist-db install folder. After starting eXist-db you can restore the backup with the Java Admin Client. After restoring, visit the URL http://localhost:8080/exist/rest/db/spaceapps/space.xql

The parameters are provided in the QueryString of the URL. The parameters that you can use are:

* lat
> The latitude of the location to check, e.g. 50.7218

* lng
> The longitude of the location to check, e.g. -3.5336

* format
> The format to return the data from the API in, valid values are 'json' or 'xml'.Default is XML if the parameter is omitted.

> * jsonp
>> If the format parameter is set to 'json', you may also wish to set the json parameter to a function name, in this way the JSON output will be wrapped as JSON-P and you may invoke the named function from your own javascript.

* nextClear
> The nextClear parameter may be set to 'true' or 'false'. If set to true then only the next Clear Sky Event will be returned. The default is false.

Example
-------
The following URL would retrieve the next clear sky event in Exeter, with the results in JSON-P format:
http://www.adamretter.org.uk/spaceapps/space.xql?lat=50.7218&lng=-3.5336&format=json&jsonp=getNextClearEvent&nextClear=true

The URL for a test service is available here - http://www.adamretter.org.uk/spaceapps/space.xql

Adam Retter <adam.retter@googlemail.com> / @adamretter
