package com.predictthesky.app;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

	AlertDialog.Builder alert;
	LocationManager geoloc;
	Location loc;
	public static final int DIALOG_SWITCH_LOCATION = 0;
	
	Document doc;

	ArrayList<EventInfo> event_list;
	EventViewerAdapter event_list_adapter;
	private static final String remote_url = "http://www.adamretter.org.uk/spaceapps/space.xql";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		// Location

		geoloc = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		loc = geoloc.getLastKnownLocation(geoloc.getBestProvider(
				new Criteria(), true));

		// Main Event

		ImageView backdrop = (ImageView) findViewById(R.id.mainBackdrop);

		backdrop.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(),
						DetailsActivity.class));
			}
		});

		// Next Events Listing TODO: load from API

		ListView nextEventList = (ListView) findViewById(R.id.nextEventsList);
		event_list = new ArrayList<EventInfo>();
		event_list_adapter = new EventViewerAdapter(this, R.layout.list_item,
				event_list);
		nextEventList.setAdapter(event_list_adapter);

		addEventToList(new EventInfo(R.drawable.ic_launcher, "Time and Date",
				"Meteor Shower", "Cloudy"));
		addEventToList(new EventInfo(R.drawable.ic_launcher, "Time and Date",
				"ISS Passover", "Patchy Cloud"));
		addEventToList(new EventInfo(R.drawable.ic_launcher, "Time and Date",
				"Meteor Shower", "Cloudy"));

		getData();
		
		
	}

	public void addEventToList(EventInfo i) {
		event_list.add(i);
		event_list_adapter.notifyDataSetChanged();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_about:
			startActivity(new Intent(this, AboutActivity.class));
			return true;
		case R.id.menu_locate:
			showDialog(DIALOG_SWITCH_LOCATION);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		Dialog dialog;
		switch (id) {
		case DIALOG_SWITCH_LOCATION:
			alert = new AlertDialog.Builder(this);

			final CharSequence[] items = { "Exeter", "London", "..." };

			EditText et = new EditText(this);
			et.setText(String.valueOf(loc.getLatitude()) + ", "
					+ String.valueOf(loc.getLongitude()));
			alert.setView(et);

			alert.setItems(items, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int item) {
					Toast.makeText(getApplicationContext(), items[item],
							Toast.LENGTH_SHORT).show();
				}
			});

			dialog = alert.create();
			break;
		default:
			dialog = null;
		}
		return dialog;
	}

	public void getData() {
		
		String data = null;
		try {
			data = URLEncoder.encode("lat", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(loc.getLatitude()), "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    try {
			data += "&" + URLEncoder.encode("lng", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(loc.getLongitude()), "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	    // Send data
	    URL url = null;
		try {
			url = new URL(remote_url);
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    URLConnection conn = null;
		try {
			conn = url.openConnection();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    conn.setDoOutput(true);
	    OutputStreamWriter wr = null;
		try {
			wr = new OutputStreamWriter(conn.getOutputStream());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    try {
			wr.write(data);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    try {
			wr.flush();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		DocumentBuilder db = null;
		try {
			db = dbf.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		try {
			doc = db.parse(conn.getInputStream());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
