package com.predictthesky.app.tasks;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Xml;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.predictthesky.app.R;
import com.predictthesky.app.SpaceEvent;
import com.predictthesky.app.SpaceEventMarker;
import com.predictthesky.app.listeners.SpaceEventUpdateListener;

public class GetEventsTask extends AsyncTask<Void, Void, Void>
{
    private class PredictTheSkyXmlParser
    {
        private static final String TAG_EVENT = "event";
        private static final String TAG_TITLE = "title";
        private static final String TAG_START = "start";
        private static final String TAG_END   = "end";
        private static final String TAG_TIME  = "time";
        private static final String TAG_ALT   = "alt";
        private static final String TAG_AZ    = "az";

        private final String        ns        = null;

        public void parse(InputStream in) throws XmlPullParserException, IOException
        {
            try
            {
                XmlPullParser parser = Xml.newPullParser();
                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                parser.setInput(in, null);
                parser.nextTag();

                readFeed(parser);
            }
            finally
            {
                in.close();
            }
        }

        private SpaceEvent readEntry(XmlPullParser parser) throws XmlPullParserException, IOException
        {
            parser.require(XmlPullParser.START_TAG, ns, TAG_EVENT);

            String title = null;
            SpaceEventMarker eventStart = null;
            SpaceEventMarker eventFinish = null;
            String weather = null;

            while (parser.next() != XmlPullParser.END_TAG)
            {
                if (parser.getEventType() != XmlPullParser.START_TAG)
                {
                    continue;
                }

                String name = parser.getName();

                if (name.equalsIgnoreCase(TAG_TITLE))
                {
                    title = readText(parser);
                }
                else if (name.equalsIgnoreCase(TAG_START))
                {
                    eventStart = readEntryMarker(parser);
                }
                else if (name.equalsIgnoreCase(TAG_END))
                {
                    eventFinish = readEntryMarker(parser);
                }
            }
            return new SpaceEvent(title, eventStart, eventFinish, weather, R.drawable.ic_weather_clear_sunny_dark);
        }

        private SpaceEventMarker readEntryMarker(XmlPullParser parser) throws XmlPullParserException, IOException
        {
            String time = null;
            int altitude = -1;
            String azimuth = null;

            while (parser.next() != XmlPullParser.END_TAG)
            {
                if (parser.getEventType() != XmlPullParser.START_TAG)
                {
                    continue;
                }

                String name = parser.getName();

                if (name.equalsIgnoreCase(TAG_TIME))
                {
                    time = readText(parser);
                }
                else if (name.equalsIgnoreCase(TAG_ALT))
                {
                    altitude = Integer.decode(readText(parser));
                }
                else if (name.equalsIgnoreCase(TAG_AZ))
                {
                    azimuth = readText(parser);
                }
            }
            return new SpaceEventMarker(time, altitude, azimuth);
        }

        private void readFeed(final XmlPullParser parser) throws XmlPullParserException, IOException
        {
            while (parser.next() != XmlPullParser.END_DOCUMENT)
            {
                if (parser.getEventType() != XmlPullParser.START_TAG)
                {
                    continue;
                }

                String name = parser.getName();

                if (name.equalsIgnoreCase(TAG_EVENT))
                {
                    final SpaceEvent event = readEntry(parser);

                    ((Activity) context).runOnUiThread(new Runnable() {

                        @Override
                        public void run()
                        {
                            listener.onEventUpdated(event);
                        }
                    });
                }
            }
        }

        private String readText(XmlPullParser parser) throws XmlPullParserException, IOException
        {
            String result = "";
            if (parser.next() == XmlPullParser.TEXT)
            {
                result = parser.getText();
                parser.nextTag();
            }
            return result;
        }
    }

    private Context                  context;

    private PredictTheSkyXmlParser   predictTheSkyXmlParser;
    private Resources                appResources;

    private SpaceEventUpdateListener listener;

    private boolean                  isGettingNextClear;
    private boolean                  forceRemoteUpdate;

    public GetEventsTask(Context context, SpaceEventUpdateListener listener)
    {
        this(context, listener, false, false);
    }

    public GetEventsTask(Context context, SpaceEventUpdateListener listener, boolean getNextClear)
    {
        this(context, listener, getNextClear, false);
    }

    public GetEventsTask(Context context, SpaceEventUpdateListener listener, boolean getNextClear,
            boolean forceCacheUpdate)
    {
        if (context != null)
        {
            this.context = context;

            appResources = context.getResources();

            if (listener != null) this.listener = listener;

            isGettingNextClear = getNextClear;
            forceRemoteUpdate = forceCacheUpdate;

            predictTheSkyXmlParser = new PredictTheSkyXmlParser();
        }
        else
        {
            throw new IllegalArgumentException();
        }
    }

    @Override
    protected Void doInBackground(Void... params)
    {
        FileInputStream cache = null;
        try
        {
            cache = context.openFileInput("PredictTheSky.cache");
        }
        catch (FileNotFoundException e)
        {
            forceRemoteUpdate = true;
        }

        try
        {
            if (forceRemoteUpdate)
            {
                updateRemoteXML();
                cache = context.openFileInput("PredictTheSky.cache");
            }

            if (cache != null) predictTheSkyXmlParser.parse(cache);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (XmlPullParserException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void result)
    {
        ((Activity) context).runOnUiThread(new Runnable() {

            @Override
            public void run()
            {
                listener.onFinishedUpdate();
            }
        });
        super.onPostExecute(result);
    }

    @Override
    protected void onPreExecute()
    {
        ((Activity) context).runOnUiThread(new Runnable() {

            @Override
            public void run()
            {
                listener.onStartedUpdate();
            }
        });
        super.onPreExecute();
    }

    private void updateRemoteXML()
    {
        try
        {
            ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = connMgr.getActiveNetworkInfo();

            if (netInfo != null && netInfo.isConnected())
            {
                SharedPreferences data = PreferenceManager.getDefaultSharedPreferences(context);

                String opts = "";
                opts += "?";

                // Encode latitude
                String remoteLatitudeArgument = appResources.getString(R.string.remote_arguments_latitude);
                String defaultLatitudePrefKey = appResources.getString(R.string.preferencekey_defaultlatitude);
                String latitudePrefKey = appResources.getString(R.string.preferencekey_latitude);
                float defaultLatitude = data.getFloat(defaultLatitudePrefKey, 91f);
                float latitude = data.getFloat(latitudePrefKey, defaultLatitude);
                if (latitude <= 90 && latitude >= -90)
                {
                    opts += URLEncoder.encode(remoteLatitudeArgument, "UTF-8");
                    opts += "=";
                    opts += URLEncoder.encode(String.valueOf(latitude), "UTF-8");
                }

                opts += "&";

                // Encode longitude
                String remoteLongitudeArgument = appResources.getString(R.string.remote_arguments_longitude);
                String defaultLongitudePrefKey = appResources.getString(R.string.preferencekey_defaultlongitude);
                String longitudePrefKey = appResources.getString(R.string.preferencekey_longitude);
                float defaultLongitude = data.getFloat(defaultLongitudePrefKey, 91f);
                float longitude = data.getFloat(longitudePrefKey, defaultLongitude);
                if (longitude <= 90 && longitude >= -90)
                {
                    opts += URLEncoder.encode(remoteLongitudeArgument, "UTF-8");
                    opts += "=";
                    opts += URLEncoder.encode(String.valueOf(longitude), "UTF-8");
                }

                if (isGettingNextClear)
                {
                    opts += "&";
                    opts += URLEncoder.encode("nextClear", "UTF-8");
                    opts += "=";
                    opts += URLEncoder.encode("true", "UTF-8");
                }

                URL request = new URL("http://www.adamretter.org.uk/spaceapps/space.xql" + opts);

                HttpURLConnection connection = (HttpURLConnection) request.openConnection();
                connection.setReadTimeout(10000); // milliseconds
                connection.setConnectTimeout(15000);
                connection.setRequestMethod("GET");
                connection.setDoInput(true);

                connection.connect();
                InputStream is = connection.getInputStream();

                FileOutputStream cacheStream = context.openFileOutput("PredictTheSky.cache", Context.MODE_PRIVATE);

                int read = 0;
                byte[] streamBytes = new byte[1024];

                while ((read = is.read(streamBytes)) != -1)
                    cacheStream.write(streamBytes, 0, read);

                is.close();
                cacheStream.flush();
                cacheStream.close();
            }
            else
            {
                Toast.makeText(context, "NO NETWORK CONNECTION", Toast.LENGTH_SHORT).show();
            }
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
