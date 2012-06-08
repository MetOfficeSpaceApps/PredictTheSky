package com.predictthesky.app.fragments;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.predictthesky.app.Event;
import com.predictthesky.app.R;

public class NextEventList extends ListFragment
{

    ArrayList<Event> eventData;

    public NextEventList(ArrayList<Event> data)
    {
        eventData = data;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {

        ArrayAdapter<Event> adapter = new Adapter(getActivity(), eventData);
        setListAdapter(adapter);

        super.onCreate(savedInstanceState);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id)
    {
        super.onListItemClick(l, v, position, id);

        Event event = (Event) l.getAdapter().getItem(position);

        // Open in details view
        Toast.makeText(getActivity(), event.title, Toast.LENGTH_SHORT).show();
    }

    private class Adapter extends ArrayAdapter<Event>
    {

        public Adapter(Context context, ArrayList<Event> data)
        {
            super(context, R.layout.nextevent_info, data);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            View eventView = convertView;

            if (eventView == null)
            {
                LayoutInflater inflater = getLayoutInflater(null);
                eventView = inflater.inflate(R.layout.nextevent_info, parent);
            }

            Event e = getItem(position); // Event to add to the list

            ((TextView) eventView.findViewById(R.id.nextEventInfo_eventTitle)).setText(e.title);
            ((TextView) eventView.findViewById(R.id.nextEventInfo_eventWeather)).setText(e.weather);
            ((TextView) eventView.findViewById(R.id.nextEventInfo_eventTime)).setText(e.time);
            ((ImageView) eventView.findViewById(R.id.nextEventInfo_eventWeatherIcon)).setImageResource(e.weatherIcon);

            return eventView;
        }
    }

}
