PredictTheSky - Back End code
=============================

This is written in XQuery and run's atop the eXist-db Native XML Database. You will need eXist-db trunk or 2.0 preview. http://www.exist-db.org.

The code here consists of a database backup which can be imported into eXist-db. After restoring, visit the URL http://localhost:8080/exist/rest/db/spaceapps/space.xql

The parameters are ?lat=xyz&lng=abc where these are your latitude and longitude.
