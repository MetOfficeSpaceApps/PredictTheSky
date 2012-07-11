package com.predictthesky.app;

import java.io.Serializable;

public class SpaceEvent implements Serializable
{
    private static final long     serialVersionUID = -550791526669241097L;
    public final String           title;
    public final SpaceEventMarker eventStart;
    public final SpaceEventMarker eventFinish;
    public final String           weather;
    public final int              weathericon;

    public SpaceEvent(String title, SpaceEventMarker eventStart, SpaceEventMarker eventFinish, String weather,
            int weatherIcon)
    {
        this.title = title;
        this.eventStart = eventStart;
        this.eventFinish = eventFinish;
        this.weather = weather;
        weathericon = weatherIcon;
    }
}
