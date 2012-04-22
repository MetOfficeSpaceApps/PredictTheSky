package com.predictthesky.app;

import android.app.Activity;
import android.os.Bundle;
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
	  
	  ImageView backdrop = (ImageView) findViewById(R.id.imageView1);
	  
	  backdrop.setOnClickListener(new OnClickListener() {
	        public void onClick(View v) {
	            Toast.makeText(v.getContext(), // <- Line changed
	                    "Load Detail View",
	                    Toast.LENGTH_SHORT).show();
	        }
	    });
	  
	  ListView modeList = (ListView) findViewById(R.id.nextEventsList);
	  String[] stringArray = new String[] { "Meteor Shower", "Aurora" };
	  ArrayAdapter<String> modeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, stringArray);
	  modeList.setAdapter(modeAdapter);
	}

}
