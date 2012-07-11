package com.predictthesky.app.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.predictthesky.app.R;
import com.predictthesky.app.SpaceEvent;
import com.predictthesky.app.listeners.SpaceEventUpdateListener;
import com.predictthesky.app.tasks.GetEventsTask;

public class NextClearEventFrameFragment extends Fragment implements SpaceEventUpdateListener
{
    private ImageView eventBackdrop;

    private TextView  eventTitle;
    private TextView  eventTime;
    private TextView  eventWeather;
    private ImageView eventWeatherIcon;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View frame = inflater.inflate(R.layout.fragment_nextcleareventframe, container);

        eventBackdrop = (ImageView) frame.findViewById(R.id.nextcleareventframe_backdrop);

        eventTitle = (TextView) frame.findViewById(R.id.eventlistrow_title);
        eventTime = (TextView) frame.findViewById(R.id.eventlistrow_time);
        eventWeather = (TextView) frame.findViewById(R.id.eventlistrow_weather);
        eventWeatherIcon = (ImageView) frame.findViewById(R.id.eventlistrow_weathericon);

        return frame;
    }

    @Override
    public void onEventUpdated(SpaceEvent event)
    {
        eventTitle.setText(event.title);
        eventTime.setText(event.eventStart.time);
        eventWeather.setText(event.weather);
        eventWeatherIcon.setImageResource(event.weathericon);

        if (event.title.equalsIgnoreCase("International Space Station"))
        {
            eventBackdrop.setImageResource(R.drawable.iss);
        }
    }

    @Override
    public void onFinishedUpdate()
    {
        getView().setVisibility(View.VISIBLE);
    }

    @Override
    public void onStartedUpdate()
    {
        getView().setVisibility(View.INVISIBLE);
    }

    public void update(boolean forceCacheUpdate)
    {
        if (forceCacheUpdate)
            new GetEventsTask(getActivity(), this, true, true).execute();
        else
            new GetEventsTask(getActivity(), this, true, false).execute();
    }
}
