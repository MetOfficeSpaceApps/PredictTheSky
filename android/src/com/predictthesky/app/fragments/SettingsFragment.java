package com.predictthesky.app.fragments;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import com.predictthesky.app.App;
import com.predictthesky.app.MainActivity;
import com.predictthesky.app.R;

public class SettingsFragment extends PreferenceFragment implements OnSharedPreferenceChangeListener
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.settings);

        PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext())
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key)
    {
        if (isAdded())
        {
            if (key.equalsIgnoreCase(getString(R.string.preferencekey_currenttheme)))
            {
                // Get the new theme
                String theme = sharedPreferences.getString(key, getString(R.string.preference_theme_dark));

                // Set the system-wide theme
                if (theme.equalsIgnoreCase(getString(R.string.preference_theme_light)))
                    App.currentTheme = R.style.PredictTheSky_Light;
                else
                    App.currentTheme = R.style.PredictTheSky_Dark;

                // Flip preference switch to allow Activity to return to our settings fragment
                sharedPreferences.edit().putBoolean(getString(R.string.preferencekey_do_theme_refresh), true).commit();

                // Restart Activity to refresh theme
                Activity activity = getActivity();
                activity.startActivity(new Intent(activity.getApplicationContext(), MainActivity.class));
                activity.finish();
                activity.overridePendingTransition(App.transitionInAnimation, App.transitionOutAnimation);
            }
        }
    }
}
