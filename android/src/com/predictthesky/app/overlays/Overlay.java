package com.predictthesky.app.overlays;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import com.predictthesky.app.R;

public abstract class Overlay extends DialogFragment
{
    private class OverlayTask extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... params)
        {
            if (associatedTask != null) associatedTask.run();
            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {
            dismiss();
            super.onPostExecute(result);
        }
    }

    private final Runnable associatedTask;

    protected Overlay()
    {
        this(null);
    }

    protected Overlay(Runnable task)
    {
        associatedTask = task;
    }

    /**
     * Inflates the View to be used in the overlay
     * 
     * @param inflater
     *            - The LayoutInflater to inflate the view
     * @return The View to be overlaid on the application
     */
    protected abstract View getContentView(LayoutInflater inflater);

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        Dialog overlayLayout = new Dialog(getActivity(), R.style.PredictTheSky_Overlay);
        overlayLayout.requestWindowFeature(Window.FEATURE_NO_TITLE);

        overlayLayout.setContentView(getContentView(getActivity().getLayoutInflater()));

        if (associatedTask != null) setCancelable(false);

        return overlayLayout;
    }

    public void show()
    {
        new OverlayTask().execute();
        super.show(getFragmentManager(), toString());
    }
}
