package com.predictthesky.app;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PredictTheSkyApplication extends Application
{

    @Override
    public void onCreate()
    {
        // Get application preference data
        SharedPreferences data = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        // Load the theme preference (else use dark theme as default)
        String themeToSet = data.getString(getString(R.string.preferencekey_currenttheme),
                getString(R.string.preference_theme_dark));

        // Set the preferred theme for later use by Activities
        if (themeToSet.equalsIgnoreCase(getString(R.string.preference_theme_dark)))
        {
            App.currentTheme = R.style.PredictTheSky_Dark;
        }
        else if (themeToSet.equalsIgnoreCase(getString(R.string.preference_theme_light)))
        {
            App.currentTheme = R.style.PredictTheSky_Light;
        }

        // Begin the application
        super.onCreate();
    }

}
