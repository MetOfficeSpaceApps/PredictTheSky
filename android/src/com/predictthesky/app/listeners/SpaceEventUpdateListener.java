package com.predictthesky.app.listeners;

import com.predictthesky.app.SpaceEvent;

public interface SpaceEventUpdateListener
{
    public void onEventUpdated(SpaceEvent event);

    public void onFinishedUpdate();

    public void onStartedUpdate();
}
