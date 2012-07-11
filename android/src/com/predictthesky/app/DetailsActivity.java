package com.predictthesky.app;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;

import com.predictthesky.app.fragments.EventDetailsFragment;
import com.predictthesky.app.tasks.GetLocationTask;

public class DetailsActivity extends FragmentActivity
{
    private EventDetailsFragment detailsFragment;

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        setTheme(R.style.PredictTheSky_Dark);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.details);

        FragmentManager supportFragmentManager = getSupportFragmentManager();

        detailsFragment = (EventDetailsFragment) supportFragmentManager.findFragmentById(R.id.fragment_eventdetails);

        Intent i = getIntent();
        detailsFragment.update((SpaceEvent) i.getSerializableExtra("eventDetails"));

        // ActionBar ---
        ActionBar actionBar = getActionBar();

        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setTitle(getString(R.string.activity_details_title));
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:

                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                return true;

            case R.id.actionbar_updatelocation:

                new GetLocationTask(this).execute();

                return true;

            case R.id.actionbar_overflow_settings:

                startActivity(new Intent(this, SettingsActivity.class));

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
