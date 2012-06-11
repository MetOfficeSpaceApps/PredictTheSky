package com.predictthesky.app.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.predictthesky.app.Event;
import com.predictthesky.app.R;
import com.predictthesky.app.Updater;

public class NextEventList extends ListFragment
{

    Adapter                 mAdapter;
    onListItemClickListener clickListener;

    @Override
    public void onAttach(Activity activity)
    {
        mAdapter = new Adapter(activity);

        try
        {
            clickListener = (onListItemClickListener) activity;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(activity.toString() + " must implement onListItemClickListener");
        }

        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        getListView().setLayoutAnimation(
                new LayoutAnimationController(AnimationUtils.loadAnimation(getActivity(), R.anim.add_list_item)));

        setListAdapter(mAdapter);
        (new Updater()).update(getActivity(), mAdapter, false);
    }

    private class Adapter extends ArrayAdapter<Event>
    {

        public Adapter(Context context)
        {
            super(context, R.layout.nexteventlist_row);
            setNotifyOnChange(false);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            View row = convertView;
            Event e = null;

            if (row == null)
            {
                LayoutInflater inflater = ((Activity) getActivity()).getLayoutInflater();
                row = inflater.inflate(R.layout.nexteventlist_row, parent, false);

                e = getItem(position);

                row.setTag(e);
            }
            else
            {
                e = (Event) row.getTag();
            }

            ((TextView) row.findViewById(R.id.nextEventList_Row_TitleText)).setText(e.title);
            ((TextView) row.findViewById(R.id.nextEventList_Row_TimeText)).setText(e.time);
            ((TextView) row.findViewById(R.id.nextEventList_Row_WeatherText)).setText("Cloudy");
            ((ImageView) row.findViewById(R.id.nextEventList_Row_WeatherIcon)).setImageResource(R.drawable.cloudy);

            return row;
        }
    }

    public interface onListItemClickListener
    {

        public void onListItemClick();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id)
    {
        clickListener.onListItemClick();
    }

}
