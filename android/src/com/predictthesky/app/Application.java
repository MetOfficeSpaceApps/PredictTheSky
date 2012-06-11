package com.predictthesky.app;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ViewFlipper;

import com.predictthesky.app.fragments.NextEventList.onListItemClickListener;

public class Application extends FragmentActivity implements onListItemClickListener
{

    ViewFlipper flipper;
    Animation   slide_out_right, slide_out_left;
    Animation   slide_in_right, slide_in_left;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flipper);

        flipper = (ViewFlipper) findViewById(R.id.appViewFlipper);

        slide_out_right = AnimationUtils.loadAnimation(this, R.anim.slide_out_right);
        slide_out_left = AnimationUtils.loadAnimation(this, R.anim.slide_out_left);
        slide_in_right = AnimationUtils.loadAnimation(this, R.anim.slide_in_right);
        slide_in_left = AnimationUtils.loadAnimation(this, R.anim.slide_in_left);
    }

    @Override
    public void onListItemClick()
    {
        // Show details of clicked item
        flipper.setOutAnimation(slide_out_left);
        flipper.setInAnimation(slide_in_right);
        flipper.showNext();
    }

    @Override
    public void onBackPressed()
    {
        if (flipper.getDisplayedChild() == 1)
        {
            flipper.setOutAnimation(slide_out_right);
            flipper.setInAnimation(slide_in_left);
            flipper.showPrevious();
        }
        else
        {
            super.onBackPressed(); // End activity
        }
    }

}
