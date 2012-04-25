package com.predictthesky.app;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NextEventAdapter extends ArrayAdapter<Object> {

    public class EventInfo {
	public EventInfo(int weatherIconResourceId, String eventTypeText,
		String eventTimeText, String eventWeatherText) {
	    iconResource = weatherIconResourceId;
	    type = eventTypeText;
	    time = eventTimeText;
	    weather = eventWeatherText;
	    loading = false;
	}

	public EventInfo() {
	    loading = true;
	}

	public boolean loading;
	public int iconResource;
	public String type;
	public String time;
	public String weather;
    }

    LayoutInflater inflater;
    List<EventInfo> listData;

    public NextEventAdapter(Context context, int textViewResourceId,
	    List<EventInfo> objects) {
	super(context, textViewResourceId);
	listData = objects;
	inflater = (LayoutInflater) context
		.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

	EventInfo ei = listData.get(position);
	View row = null;

	if (!ei.loading) {
	    row = inflater.inflate(R.layout.next_event_info, parent, false);

	    ImageView wIcon = (ImageView) row
		    .findViewById(R.id.eventInfoWeatherIcon);
	    TextView tEvent = (TextView) row
		    .findViewById(R.id.eventInfoTypeText);
	    TextView tTime = (TextView) row
		    .findViewById(R.id.eventInfoTimeText);
	    TextView tWeather = (TextView) row
		    .findViewById(R.id.eventInfoWeatherText);

	    wIcon.setImageResource(ei.iconResource);
	    tEvent.setText(ei.type);
	    tTime.setText(ei.time);
	    tWeather.setText(ei.weather);
	} else {
	    row = inflater.inflate(R.layout.loading_anim, parent, false);
	    Animation rotanim = AnimationUtils.loadAnimation(getContext(), R.anim.spin);
	    ImageView sprite = (ImageView) row.findViewById(R.id.loadingSprite);
	    sprite.startAnimation(rotanim);
	}

	return row;
    }

    @Override
    public void add(Object object) {
	listData.add((EventInfo) object);
	super.add(object);
    }

}
