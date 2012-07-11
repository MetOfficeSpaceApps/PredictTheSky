package com.predictthesky.app;

import java.io.Serializable;

public class SpaceEventMarker implements Serializable
{
    private static final long serialVersionUID = 2582617019331747184L;
    public final String       time;
    public final int          altitude;
    public final String       azimuth;

    public SpaceEventMarker(String time, int altitude, String azimuth)
    {
        this.time = time;
        this.altitude = altitude;
        this.azimuth = azimuth;
    }
}
