package com.predictthesky.app;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class InfoListAdapter extends ArrayAdapter<Object> {

    public class InfoByte {
	public InfoByte(String titleText, String subText) {
	    major = titleText;
	    minor = subText;
	}

	public String major;
	public String minor;
    }

    LayoutInflater inflater;
    List<InfoByte> listData;

    public InfoListAdapter(Context context, int textViewResourceId,
	    List<InfoByte> objects) {
	super(context, textViewResourceId);
	listData = objects;
	inflater = (LayoutInflater) context
		.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

	View row = inflater.inflate(R.layout.about_multiline, parent, false);

	TextView MajorText = (TextView) row
		.findViewById(R.id.majorText);
	TextView MinorText = (TextView) row
		.findViewById(R.id.minorText);

	MajorText.setText(listData.get(position).major);
	MinorText.setText(listData.get(position).minor);

	return row;
    }

    @Override
    public void add(Object object) {
	listData.add((InfoByte) object);
	super.add(object);
    }

}
