package com.predictthesky.app;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.predictthesky.app.NextEventAdapter.EventInfo;

public class Application extends Activity {

    public static NextEventAdapter nextListAdapter;
    Updater u;

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.main);
	//
	// addLoadingAnimation(R.id.nextClearEventZone);

	nextListAdapter = new NextEventAdapter(this, R.layout.next_event_info,
		new ArrayList<NextEventAdapter.EventInfo>());
	((ListView) findViewById(R.id.nextEventList))
		.setAdapter(nextListAdapter);

	u = new Updater(this);
	u.execute("5", "-3", "true");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	MenuInflater inflater = getMenuInflater();
	inflater.inflate(R.menu.main_options, menu);
	return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
	switch (item.getItemId()) {
	case R.id.menuAbout:
	    openAbout();
	    return true;
	case R.id.menuSettings:
	    openSettings();
	default:
	    return super.onOptionsItemSelected(item);
	}
    }

    public void openAbout() {
	Intent aboutIntent = new Intent(this, About.class);
	startActivityForResult(aboutIntent, 0);
    }

    public void openSettings() {

    }

    // public void addLoadingAnimation(int parentLayoutId) {
    // ViewGroup parent = (ViewGroup) findViewById(parentLayoutId);
    // LayoutInflater inflater = this.getLayoutInflater();
    // inflater.inflate(R.layout.loading_anim, parent);
    // Animation rotanim = AnimationUtils.loadAnimation(this, R.anim.spin);
    // ImageView sprite = (ImageView) parent.findViewById(
    // R.layout.loading_anim).findViewById(R.id.loadingSprite);
    // sprite.startAnimation(rotanim);
    // }
    //
    // public void removeLoadingAnimation(int parentLayoutId) {
    // ViewGroup parent = (ViewGroup) findViewById(parentLayoutId);
    // parent.removeView(parent.findViewById(R.layout.loading_anim));
    // }

    public void setNextEventInfo(EventInfo info) {
	// removeLoadingAnimation(R.id.nextClearEventZone);
	((TextView) findViewById(R.id.nextClearEventOverlayText))
		.setText(info.type + "\n" + info.time + "\n" + info.weather);
	Animation fadeInAnimation = AnimationUtils.loadAnimation(
		getApplicationContext(), android.R.anim.fade_in);
	if (info.type.equalsIgnoreCase("International Space Station")) {
	    ((ImageView) findViewById(R.id.eventBackdropImage))
		    .setBackgroundResource(R.drawable.iss);
	    ((ImageView) findViewById(R.id.eventBackdropImage))
		    .startAnimation(fadeInAnimation);
	    Log.d("APPLICATION", "Set Backdrop Image to ISS");
	}
    }

}
