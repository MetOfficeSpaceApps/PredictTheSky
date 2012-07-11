package com.predictthesky.app;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;

import com.predictthesky.app.fragments.EventDetailsFragment;
import com.predictthesky.app.fragments.NextClearEventFrameFragment;
import com.predictthesky.app.fragments.UpcomingEventListFragment;
import com.predictthesky.app.tasks.GetLocationTask;

public class HomeActivity extends FragmentActivity
{
    public NextClearEventFrameFragment nextClearEventFragment;
    public UpcomingEventListFragment   eventListFragment;
    public EventDetailsFragment        detailsFragment;

    private boolean                    isRestoring = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        setTheme(R.style.PredictTheSky_Dark);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.home);

        FragmentManager supportFragmentManager = getSupportFragmentManager();

        eventListFragment = (UpcomingEventListFragment) supportFragmentManager
                .findFragmentById(R.id.fragment_upcomingeventlist);

        nextClearEventFragment = (NextClearEventFrameFragment) supportFragmentManager
                .findFragmentById(R.id.fragment_nextcleareventframe);

        detailsFragment = (EventDetailsFragment) supportFragmentManager.findFragmentById(R.id.fragment_eventdetails);
        if (detailsFragment != null)
        { // Tablet Layout
            App.runningOnTablet = true;
        }

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setTitle(getString(R.string.activity_home_title));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.actionbar_updatelocation:

                new GetLocationTask(this).execute();

                return true;

            case R.id.actionbar_updatedata:

                nextClearEventFragment.update(true);
                eventListFragment.update(true);

                return true;

            case R.id.actionbar_overflow_settings:

                startActivity(new Intent(this, SettingsActivity.class));

                return true;

            default:

                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
        isRestoring = true;
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        if (!isRestoring)
        {
            nextClearEventFragment.update(false);
            eventListFragment.update(false);
        }
    }
}
