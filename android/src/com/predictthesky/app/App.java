package com.predictthesky.app;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

// Static, Application-wide settings
public class App
{
    // Preferred resources to use for visuals in the application
    public static int                        currentTheme;
    public static int                        transitionInAnimation, transitionOutAnimation;

    // ---
    // Extra data for occasional use
    public static final Map<String, float[]> locationList;
    static
    // TODO: Convert to .txt data and dynamically import
    {
        Map<String, float[]> map = new HashMap<String, float[]>();
        map.put("Aberdeen", new float[] { 57.0f, 2.0f });
        map.put("Adelaide", new float[] { 34.0f, 138.0f });
        map.put("Algiers", new float[] { 36.0f, 3.0f });
        map.put("Amsterdam", new float[] { 52.0f, 4.0f });
        map.put("Ankara", new float[] { 39.0f, 32.0f });
        map.put("Asunción", new float[] { 25.0f, 57.0f });
        map.put("Athens", new float[] { 37.0f, 23.0f });
        map.put("Auckland", new float[] { -36.0f, 174.0f });
        map.put("Bangkok", new float[] { -13.0f, 100.0f });
        map.put("Barcelona", new float[] { 41.0f, 2.0f });
        map.put("Beijing", new float[] { 39.0f, 116.0f });
        locationList = Collections.unmodifiableMap(map);
    }

}
