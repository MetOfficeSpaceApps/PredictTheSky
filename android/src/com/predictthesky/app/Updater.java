package com.predictthesky.app;

import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.os.AsyncTask;
import android.util.Log;

import com.predictthesky.app.NextEventAdapter.EventInfo;

public class Updater extends AsyncTask<String, Void, Void> {

    public boolean isLoadingNextClear = false;
    public ArrayList<EventInfo> event_data;
    Application app;
    
    public Updater(Application a)
    {
	app = a;
    }

    @Override
    protected Void doInBackground(String... params) {

	event_data = new ArrayList<EventInfo>();
	if(params[2].equalsIgnoreCase("TRUE")) isLoadingNextClear = true;

	try {
	    String data = URLEncoder.encode("lat", "UTF-8") + "="
		    + URLEncoder.encode(params[0], "UTF-8");
	    data += "&" + URLEncoder.encode("lng", "UTF-8") + "="
		    + URLEncoder.encode(params[1], "UTF-8");
	    data += "&" + URLEncoder.encode("nextClear", "UTF-8") + "="
		    + URLEncoder.encode(params[2], "UTF-8");

	    if (params[2].equalsIgnoreCase("TRUE"))
		isLoadingNextClear = true;
	    else
		isLoadingNextClear = false;

	    OutputStreamWriter wr = null;
	    URLConnection conn = null;
	    URL url = new URL(
		    "http://www.adamretter.org.uk/spaceapps/space.xql?");

	    try {
		conn = url.openConnection();
		conn.setDoOutput(true);
		wr = new OutputStreamWriter(conn.getOutputStream());
	    } catch (UnknownHostException unk) {
		Log.e("APPLICATION", "Unknown Host: " + url.toString());
	    }
	    wr.write(data);
	    wr.flush();

	    SAXParserFactory factory = SAXParserFactory.newInstance();
	    SAXParser sp = factory.newSAXParser();

	    DefaultHandler handler = new DefaultHandler() {

		EventInfo i = Application.nextListAdapter.new EventInfo();
		boolean titleTag = false;
		boolean simpleWeatherTag = false;
		boolean tempTag = false;
		boolean timeStartTag = false;
		boolean timeStopTag = false;
		boolean timeTag = false;
		Date startDate = null;

		@Override
		public void startElement(String uri, String localName,
			String qName, Attributes attributes)
			throws SAXException {

		    if (localName.equalsIgnoreCase("TITLE")) {
			titleTag = true;
		    }
		    if (localName.equalsIgnoreCase("DESCRIPTION")) {
			simpleWeatherTag = true;
		    }
		    if (localName.equalsIgnoreCase("TEMPERATURE")) {
			tempTag = true;
		    }
		    if (localName.equalsIgnoreCase("START")) {
			timeStartTag = true;
		    }
		    if (localName.equalsIgnoreCase("END")) {
			timeStopTag = true;
		    }
		    if (localName.equalsIgnoreCase("TIME")) {
			timeTag = true;
		    }

		}

		public void endElement(String uri, String localName,
			String qName) throws SAXException {

		    if (localName.equalsIgnoreCase("EVENT")) {
			Log.d("APPLICATION", "i = \"" + i.type + "\", \""
				+ i.weather + "\"");

    			event_data.add(i);
		    }

		}

		public void characters(char ch[], int start, int length)
			throws SAXException {

		    if (titleTag) {
			i.type = new String(ch, start, length);
			titleTag = false;
		    }
		    if (simpleWeatherTag) {
			i.weather = new String(ch, start, length);
			simpleWeatherTag = false;
		    }
		    if (tempTag) {
			i.weather += (" (" + new String(ch, start, length) + "\u00B0C)");
			tempTag = false;
		    }
		    if (timeTag)
		    {
			if(timeStartTag)
			{
			    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-ddTHH:mm:ss");
			    sdf.setTimeZone(TimeZone.getDefault());
			    try {
				startDate = sdf.parse(new String(ch, start, length));
			    } catch (ParseException e) {
				e.printStackTrace();
			    }
			}
			else if (timeStopTag)
			{
			    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-ddTHH:mm:ss");
			    sdf.setTimeZone(TimeZone.getDefault());
			    try {
				i.time = startDate.toLocaleString() + " to " + sdf.parse(new String(ch, start, length)).toLocaleString();
			    } catch (ParseException e) {
				e.printStackTrace();
			    }
			}
		    }
		}
	    };

	    try {
		sp.parse(conn.getInputStream(), handler);
	    } catch (Exception e) {
		e.printStackTrace();
	    }

	    wr.close();
	} catch (Exception e) {
	    e.printStackTrace();
	}

	return null;
    }

    @Override
    protected void onPostExecute(Void result) {

	if (this.isLoadingNextClear) {
	    app.setNextEventInfo(event_data.get(0));
	} else {
	    Application.nextListAdapter.listData = event_data;
	    Application.nextListAdapter.notifyDataSetChanged();
	}

	super.onPostExecute(result);
    }
}
