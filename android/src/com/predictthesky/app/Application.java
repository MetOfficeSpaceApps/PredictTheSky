package com.predictthesky.app;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class Application extends FragmentActivity
{

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

}
