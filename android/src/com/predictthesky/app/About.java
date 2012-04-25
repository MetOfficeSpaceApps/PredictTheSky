package com.predictthesky.app;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;

public class About extends ListActivity {

    InfoListAdapter iad;
    List<InfoListAdapter.InfoByte> about_items = new ArrayList<InfoListAdapter.InfoByte>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.about);

	iad = new InfoListAdapter(getBaseContext(),
		R.layout.about_multiline, about_items);
	setListAdapter(iad);
	
	iad.add(iad.new InfoByte("Version", "0.8.6"));
	iad.add(iad.new InfoByte("License", "MIT"));
    }
}