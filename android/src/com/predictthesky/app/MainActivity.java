package com.predictthesky.app;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.main);
	  
	  setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	  
	  ImageView backdrop = (ImageView) findViewById(R.id.imageView1);
	  
	  backdrop.setOnClickListener(new OnClickListener() {
	        public void onClick(View v) {
	            Toast.makeText(v.getContext(),
	                    "Load Detail View",
	                    Toast.LENGTH_SHORT).show();
	        }
	    });
	  
	  ListView modeList = (ListView) findViewById(R.id.nextEventsList);
	  String[] stringArray = new String[] { "Meteor Shower", "Aurora" };
	  ArrayAdapter<String> modeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, stringArray);
	  modeList.setAdapter(modeAdapter);
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
	            
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
}
