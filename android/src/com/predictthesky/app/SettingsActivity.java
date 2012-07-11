package com.predictthesky.app;

import java.util.List;

import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceActivity;

public class SettingsActivity extends PreferenceActivity
{

    @Override
    public void onBuildHeaders(List<Header> target)
    {
        super.onBuildHeaders(target);

        loadHeadersFromResource(R.xml.settings_headers, target);
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) addPreferencesFromResource(R.xml.settings);
    }

}
