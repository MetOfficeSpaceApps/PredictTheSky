package com.predictthesky.app;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

public class InitActivity extends Activity
{
    public void citySelected(String checkedItemKey)
    {
        float[] location = App.locationList.get(checkedItemKey);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        prefs.edit().putBoolean("isLocationCached", true).putFloat("Latitude", location[0])
                .putFloat("Longitude", location[1]);

        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
        overridePendingTransition(App.transitionInAnimation, App.transitionOutAnimation);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // Set theme to application-wide theme
        setTheme(App.currentTheme);

        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        /*
         * End Location section =============================================
         */

        if (!PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).contains("appTheme"))
        {
            PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit()
                    .putString("appTheme", "theme_holo_dark");
        }
    }

    void updateLoadingSecondaryStatus(String status)
    {
        secondaryLoadingStatus.fadeTo(status);
    }

    void updateLoadingStatus(String status)
    {
        loadingStatus.fadeTo(status);
    }

}
