package com.predictthesky.app;

import android.app.Activity;
import android.app.AlertDialog;
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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	AlertDialog.Builder alert;
	LocationManager geoloc;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.main);
	  
	  setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	  
	  ImageView backdrop = (ImageView) findViewById(R.id.mainBackdrop);
	  
	  backdrop.setOnClickListener(new OnClickListener() {
	        public void onClick(View v) {
	        	startActivity(new Intent(getApplicationContext(), DetailsActivity.class));
	        }
	    });
	  
	  ListView modeList = (ListView) findViewById(R.id.nextEventsList);
	  String[] stringArray = new String[] { "Meteor Shower", "Aurora" };
	  ArrayAdapter<String> modeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, stringArray);
	  modeList.setAdapter(modeAdapter);
	  
	  
	  geoloc = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	  
	  Location loc = geoloc.getLastKnownLocation(geoloc.getBestProvider(new Criteria(), true));
	  
	  alert = new AlertDialog.Builder(this);
	  
	  final CharSequence[] items = {"Exeter", "London", "..."};
	  
	  alert.setItems(items, new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int item) {
		        Toast.makeText(getApplicationContext(), items[item], Toast.LENGTH_SHORT).show();
		    }
		});
	  
	  EditText et = new EditText(this);
	  et.setText(String.valueOf(loc.getLatitude()) + ", " + String.valueOf(loc.getLongitude()));
	  alert.setView(et);
	  
	  alert.create();
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
	        	startActivity(new Intent(this,AboutActivity.class));
	            return true;
	        case R.id.menu_locate:
	        	alert.show();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
}
