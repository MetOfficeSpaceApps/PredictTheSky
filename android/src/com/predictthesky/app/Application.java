package com.predictthesky.app;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ListView;

public class Application extends Activity {
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.main);
	
	//addLoadingAnimation(R.id.nextClearEventZone);
	
	ImageView i = (ImageView) findViewById(R.id.eventBackdropImage);
	i.setBackgroundResource(R.drawable.aurora_photo);
	
	NextEventAdapter nextListAdapter = new NextEventAdapter(this, R.layout.next_event_info, new ArrayList<NextEventAdapter.EventInfo>());
	((ListView) findViewById(R.id.nextEventList)).setAdapter(nextListAdapter);
	
	nextListAdapter.add(nextListAdapter.new EventInfo(R.drawable.cloudy, "Meteor Shower", "8.00pm - 8.15pm", "Cloudy"));
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	MenuInflater inflater = getMenuInflater();
	inflater.inflate(R.menu.main_options, menu);
	return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
        case R.id.menuAbout:
            openAbout();
            return true;
        case R.id.menuSettings:
            openSettings();
        default:
            return super.onOptionsItemSelected(item);
        }
    }
    
    public void openAbout()
    {
	Intent aboutIntent = new Intent(this, About.class);
	startActivityForResult(aboutIntent, 0);
    }
    
    public void openSettings()
    {
	
    }
    
    public void addLoadingAnimation(int parentLayoutId)
    {
	ViewGroup parent = (ViewGroup) findViewById(parentLayoutId);
	LayoutInflater inflater = getLayoutInflater();
	View v = inflater.inflate(R.layout.loading_anim, parent);
	Animation rotanim = AnimationUtils.loadAnimation(this, R.anim.spin);
	ImageView sprite = (ImageView) v.findViewById(R.id.loadingSprite);
	sprite.startAnimation(rotanim);
    }

}
