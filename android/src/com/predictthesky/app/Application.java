package com.predictthesky.app;

import java.util.ArrayList;

import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ViewFlipper;

public class Application extends FragmentActivity
{

    Animation        slide_in_left, slide_in_right;
    Animation        slide_out_left, slide_out_right;
    ArrayList<Event> events;
    Event            nextClearEvent;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flipper);

        slide_in_left = AnimationUtils.loadAnimation(Application.this, R.anim.slide_in_from_left);
        slide_in_right = AnimationUtils.loadAnimation(Application.this, R.anim.slide_in_from_right);
        slide_out_left = AnimationUtils.loadAnimation(Application.this, R.anim.slide_out_to_left);
        slide_out_right = AnimationUtils.loadAnimation(Application.this, R.anim.slide_out_to_right);

        Location loc = new Location(LocationManager.NETWORK_PROVIDER);
        loc.setLatitude(50.749079);
        loc.setLongitude(-1.890025);

        Updater updater = new Updater();
        updater.update(this, events, false);
    }

    public void slideRight()
    {
        ViewFlipper flipper = (ViewFlipper) findViewById(R.id.flipper);
        flipper.setOutAnimation(slide_out_left);
        flipper.setInAnimation(slide_in_right);
        flipper.showNext();
    }

    public void slideLeft()
    {
        ViewFlipper flipper = (ViewFlipper) findViewById(R.id.flipper);
        flipper.setOutAnimation(slide_out_right);
        flipper.setInAnimation(slide_in_left);
        flipper.showPrevious();
    }

}
