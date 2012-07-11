package com.predictthesky.app.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.predictthesky.app.R;
import com.predictthesky.app.SpaceEvent;

public class UpcomingEventListAdapter extends ArrayAdapter<SpaceEvent>
{
    class EventRowLayout
    {
        ImageView weatherIcon;
        TextView  titleText;
        TextView  timeText;
        TextView  weatherText;
    }

    public UpcomingEventListAdapter(Context context, int textViewResourceId)
    {
        super(context, textViewResourceId);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        EventRowLayout layout = null;
        SpaceEvent rowData = getItem(position);

        if (convertView == null)
        {
            LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
            convertView = inflater.inflate(R.layout.fragment_upcomingeventlist_row, parent, false);

            layout = new EventRowLayout();

            layout.weatherIcon = (ImageView) convertView.findViewById(R.id.eventlistrow_weathericon);

            layout.titleText = (TextView) convertView.findViewById(R.id.eventlistrow_title);
            layout.timeText = (TextView) convertView.findViewById(R.id.eventlistrow_time);
            layout.weatherText = (TextView) convertView.findViewById(R.id.eventlistrow_weather);

            convertView.startAnimation(AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_in));

            convertView.setTag(layout);
        }
        else
        {
            layout = (EventRowLayout) convertView.getTag();
        }

        layout.weatherIcon.setImageResource(rowData.weathericon);
        layout.titleText.setText(rowData.title);
        layout.timeText.setText(rowData.eventStart.time);
        layout.weatherText.setText(rowData.weather);

        return convertView;
    }
}
