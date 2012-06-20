package com.predictthesky.app.views;

import android.content.Context;
import android.preference.ListPreference;
import android.util.AttributeSet;

import com.predictthesky.app.R;

public class ThemePreference extends ListPreference
{

    public ThemePreference(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        setWidgetLayoutResource(R.layout.themepreference_layout);
    }

}
