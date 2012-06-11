package com.predictthesky.app;

import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class Updater
{

    public void update(Context ctx, ArrayAdapter<Event> adapter, Boolean getNextClearEvent)
    {
        (new Task(adapter, getNextClearEvent, ctx)).execute();
    }

    private class Task extends AsyncTask<Void, Event, Void>
    {

        ArrayAdapter<Event> output;
        Boolean             loadNextClear;
        Resources           mRes;

        public Task(ArrayAdapter<Event> adapter, Boolean nextClear, Context ctx)
        {
            output = adapter;
            loadNextClear = nextClear;
            mRes = ctx.getResources();
        }

        @Override
        protected Void doInBackground(Void... params)
        {
            try
            {
                /**
                 * PredictTheSky API:
                 * 
                 * lat - The latitude of the location to check, e.g. 50.7218
                 * 
                 * lng - The longitude of the location to check, e.g. -3.5336
                 * 
                 * format - The format to return the data from the API in, valid values are 'json' or 'xml'.Default is
                 * XML if the parameter is omitted.
                 * 
                 * (jsonp - If the format parameter is set to 'json', you may also wish to set the json parameter to a
                 * function name, in this way the JSON output will be wrapped as JSON-P and you may invoke the named
                 * function from your own javascript.)
                 * 
                 * nextClear - The nextClear parameter may be set to 'true' or 'false'. If set to true then only the
                 * next Clear Sky Event will be returned. The default is false.
                 */
                String data = URLEncoder.encode("lat", "UTF-8") + "=" + URLEncoder.encode("50", "UTF-8");
                data += "&" + URLEncoder.encode("lng", "UTF-8") + "=" + URLEncoder.encode("-3", "UTF-8");
                data += "&" + URLEncoder.encode("format", "UTF-8") + "=" + URLEncoder.encode("xml", "UTF-8");
                data += "&" + URLEncoder.encode("nextClear", "UTF-8") + "="; // true/false
                if (loadNextClear)
                    data += URLEncoder.encode("true", "UTF-8");
                else
                    data += URLEncoder.encode("false", "UTF-8");

                URL api = new URL(mRes.getString(R.string.internal_remote_url));
                URLConnection conn = api.openConnection();

                conn.setDoInput(true);
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(data);
                wr.flush();

                SAXParserFactory factory = SAXParserFactory.newInstance();
                factory.setNamespaceAware(true);
                SAXParser parser = factory.newSAXParser();

                SAXReaderRules rules = new SAXReaderRules();

                parser.parse(conn.getInputStream(), rules);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            return null;
        }

        // Class inside a class inside a class... CLASS-CEPTION.
        class SAXReaderRules extends DefaultHandler
        {

            Event   eventToBuild;

            // Switches
            Boolean title      = false;
            Boolean eventStart = false;
            Boolean time       = false;
            Boolean alt        = false;
            Boolean az         = false;

            public SAXReaderRules()
            {
                eventToBuild = new Event();
            }

            @Override
            public void startElement(String uri, String localName, String qName, Attributes attributes)
                    throws SAXException
            {
                if (qName.equalsIgnoreCase("event"))
                { // New event definition reached!

                }
                else if (qName.equalsIgnoreCase("title"))
                { // Event title definition
                    title = true;
                }
                else if (qName.equalsIgnoreCase("start"))
                {
                    eventStart = true;
                }
                else if (qName.equalsIgnoreCase("time"))
                {
                    time = true;
                }
            }

            @Override
            public void characters(char[] ch, int start, int length) throws SAXException
            {
                if (title) // Check what switch is active
                {
                    eventToBuild.title = new String(ch, start, length);
                    title = false; // flip switch (click!)
                }
                else if (time)
                {
                    if (eventStart)
                    { // Event start time
                        eventToBuild.time = new String(ch, start, length);
                        time = false;
                    }
                }
                else if (alt)
                {
                    if (eventStart)
                    {

                    }

                }
                else if (az)
                {
                    if (eventStart)
                    {

                    }
                }
            }

            @Override
            public void endElement(String uri, String localName, String qName) throws SAXException
            {
                if (qName.equalsIgnoreCase("event"))
                { // Event definition finished. Package it all up and ship it off ASAP.
                    publishProgress(eventToBuild);
                    eventToBuild = new Event(); // Refresh
                }
                else if (qName.equalsIgnoreCase("start"))
                { // Close start definition
                    eventStart = false;
                }
            }
        }

        @Override
        protected void onProgressUpdate(Event... values)
        {
            output.add(values[0]);
        }

        @Override
        protected void onPostExecute(Void result)
        {
            output.notifyDataSetChanged();
        }
    }

}
