package com.predictthesky.app.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import com.predictthesky.app.App;
import com.predictthesky.app.DetailsActivity;
import com.predictthesky.app.HomeActivity;
import com.predictthesky.app.R;
import com.predictthesky.app.SpaceEvent;
import com.predictthesky.app.adapters.UpcomingEventListAdapter;
import com.predictthesky.app.listeners.SpaceEventUpdateListener;
import com.predictthesky.app.tasks.GetEventsTask;

public class UpcomingEventListFragment extends ListFragment implements SpaceEventUpdateListener
{
    private UpcomingEventListAdapter adapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        if (adapter == null)
            adapter = new UpcomingEventListAdapter(getActivity(), R.layout.fragment_upcomingeventlist_row);
        setListAdapter(adapter);
    }

    @Override
    public void onEventUpdated(final SpaceEvent event)
    {
        adapter.add(event);
    }

    @Override
    public void onFinishedUpdate()
    {

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id)
    {
        if (App.runningOnTablet)
        {
            ((HomeActivity) getActivity()).detailsFragment.update((SpaceEvent) getListAdapter().getItem(position));
        }
        else
        {
            Intent detailsIntent = new Intent(getActivity(), DetailsActivity.class);
            detailsIntent.putExtra("eventDetails", (SpaceEvent) getListAdapter().getItem(position));
            startActivity(detailsIntent);
            getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }
    }

    @Override
    public void onStartedUpdate()
    {
        adapter.clear();
    }

    public void update(boolean forceCacheUpdate)
    {
        if (forceCacheUpdate)
            new GetEventsTask(getActivity(), this, false, true).execute();
        else
            new GetEventsTask(getActivity(), this, false, false).execute();
    }
}
