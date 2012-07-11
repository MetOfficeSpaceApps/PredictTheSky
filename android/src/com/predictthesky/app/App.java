package com.predictthesky.app;

import android.app.Application;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

// Static, Application-wide settings and objects
public class App extends Application
{
    public static boolean runningOnTablet = false;

    public static Animation quick_fade_out, quick_fade_in;
    public static Animation elegant_fade_out, elegant_fade_in;

    @Override
    public void onCreate()
    {
        quick_fade_in = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        quick_fade_out = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        elegant_fade_in = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
        elegant_fade_out = AnimationUtils.loadAnimation(this, android.R.anim.fade_out);

        super.onCreate();
    }
}
