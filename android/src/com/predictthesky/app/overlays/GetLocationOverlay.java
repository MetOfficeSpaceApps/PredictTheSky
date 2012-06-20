package com.predictthesky.app.overlays;

import android.view.LayoutInflater;
import android.view.View;

import com.predictthesky.app.R;
import com.predictthesky.app.views.FadingText;

public class GetLocationOverlay extends Overlay
{
    public FadingText primaryStatus;
    public FadingText secondaryStatus;

    @Override
    protected View getContentView(LayoutInflater inflater)
    {
        View overlay = inflater.inflate(R.layout.loading_layout, null, false);

        primaryStatus = (FadingText) overlay.findViewById(R.id.loading_status);
        secondaryStatus = (FadingText) overlay.findViewById(R.id.loading_secondarystatus);

        return overlay;
    }

}
