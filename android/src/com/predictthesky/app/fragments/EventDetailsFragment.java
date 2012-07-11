package com.predictthesky.app.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.predictthesky.app.R;
import com.predictthesky.app.SpaceEvent;

public class EventDetailsFragment extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_eventdetails, container);
    }

    public void update(SpaceEvent event)
    {

    }
}
