package com.predictthesky.app.fragments.settings;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.predictthesky.app.R;

public class LocationSettingsFragment extends PreferenceFragment
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.locationsettings);
    }
}
