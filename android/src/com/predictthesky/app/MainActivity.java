package com.predictthesky.app;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.predictthesky.app.fragments.HelpFragment;
import com.predictthesky.app.fragments.HomeFragment;
import com.predictthesky.app.fragments.SettingsFragment;
import com.predictthesky.app.overlays.GetLocationOverlay;

public class MainActivity extends Activity
{
    private Fragment currentFragment;

    void navigateToFragment(Fragment fragment, String title)
    {
        // Get required local references
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Remove old fragment
        if (currentFragment != null) fragmentTransaction.remove(currentFragment);

        // Add the fragment to the screen, thus 'navigating' to it. (Tag with generic class tag)
        fragmentTransaction.add(android.R.id.content, fragment, fragment.toString());

        // Commit the actions
        fragmentTransaction.commit();

        // Get the activities ActionBar
        ActionBar actionBar = getActionBar();

        // Set the ActionBar title
        actionBar.setTitle(title);

        // Don't show Icon (Use fragment title as 'Up')
        actionBar.setDisplayShowHomeEnabled(false);

        // Set the 'Home Button' functionality depending on what fragment is showing
        if (title.equalsIgnoreCase(getString(R.string.fragment_home_title)))
            actionBar.setDisplayHomeAsUpEnabled(false);
        else
            actionBar.setDisplayHomeAsUpEnabled(true);

        currentFragment = fragment;
    }

    @Override
    public void onBackPressed()
    {
        if (!((String) getActionBar().getTitle()).equalsIgnoreCase(getString(R.string.fragment_home_title)))
        { // Return to Home Screen
            navigateToFragment(new HomeFragment(), getString(R.string.fragment_home_title));
        }
        else
        { // Already on Home, close application
            super.onBackPressed();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // Set Application-wide theme
        setTheme(App.currentTheme);

        super.onCreate(savedInstanceState);

        SharedPreferences data = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if (data.getBoolean(getString(R.string.preferencekey_do_theme_refresh), false))
        {
            // Refresh complete flip switch back
            data.edit().putBoolean(getString(R.string.preferencekey_do_theme_refresh), false).commit();

            // Load settings fragment
            navigateToFragment(new SettingsFragment(), getString(R.string.fragment_settings_title));
        }
        else
        {
            // Load Home fragment by default
            navigateToFragment(new HomeFragment(), getString(R.string.fragment_home_title));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:

                if (!((String) getActionBar().getTitle()).equalsIgnoreCase(getString(R.string.fragment_home_title)))
                    navigateToFragment(new HomeFragment(), getString(R.string.fragment_home_title));

                return true;

            case R.id.actionbar_updatelocation:

                relocateUser();

                return true;

            case R.id.actionbar_overflow_settings:

                if (!((String) getActionBar().getTitle()).equalsIgnoreCase(getString(R.string.fragment_settings_title)))
                    navigateToFragment(new SettingsFragment(), getString(R.string.fragment_settings_title));

                return true;

            case R.id.actionbar_overflow_help:

                if (!((String) getActionBar().getTitle()).equalsIgnoreCase(getString(R.string.fragment_help_title)))
                    navigateToFragment(new HelpFragment(), getString(R.string.fragment_help_title));

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    void relocateUser()
    {
        GetLocationOverlay overlay = new GetLocationOverlay(new Runnable() {
            @Override
            public void run()
            {
                // TODO Auto-generated method stub

            }
        });

        Runnable overlayAction;
    }
}
