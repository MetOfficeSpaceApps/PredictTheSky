package com.predictthesky.app.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.predictthesky.app.Event;
import com.predictthesky.app.R;
import com.predictthesky.app.Updater;

public class NextEventList extends ListFragment
{

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        Adapter adapter = new Adapter(getActivity());
        setListAdapter(adapter);

        getListView().setLayoutAnimation(
                new LayoutAnimationController(AnimationUtils.loadAnimation(getActivity(), R.anim.add_list_item)));

        Updater updater = new Updater();
        updater.update(getActivity(), adapter, false);

        super.onActivityCreated(savedInstanceState);
    }

    private class Adapter extends ArrayAdapter<Event>
    {

        public Adapter(Context context)
        {
            super(context, R.layout.nextevent_info);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            View row = convertView;
            Event e = null;

            if (row == null)
            {
                LayoutInflater inflater = ((Activity) getActivity()).getLayoutInflater();
                row = inflater.inflate(R.layout.nextevent_info, parent, false);

                e = getItem(position);

                row.setTag(e);
            }
            else
            {
                e = (Event) row.getTag();
            }

            ((TextView) row.findViewById(R.id.nextEventInfo_eventTitle)).setText(e.title);
            ((TextView) row.findViewById(R.id.nextEventInfo_eventTime)).setText(e.time);
            ((TextView) row.findViewById(R.id.nextEventInfo_eventWeather)).setText(e.weather);
            ((ImageView) row.findViewById(R.id.nextEventInfo_eventWeatherIcon)).setImageResource(e.weatherIcon);

            return row;
        }
    }

}
