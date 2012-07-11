package com.predictthesky.app.tasks;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.widget.Toast;

import com.predictthesky.app.R;

public class GetLocationTask extends AsyncTask<Void, Void, Void> implements LocationListener
{
    private Context         context;
    private LocationManager locationManager;

    private boolean         isRunning;
    private boolean         gpsEnabled;
    private boolean         networkEnabled;

    public GetLocationTask(Context context)
    {
        if (context != null)
        {
            this.context = context;

            locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

            gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            networkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        }
    }

    @Override
    protected Void doInBackground(Void... params)
    {
        SharedPreferences data = PreferenceManager.getDefaultSharedPreferences(context);

        isRunning = true;

        final boolean useGPS = data.getBoolean(context.getString(R.string.preferencekey_usegps), false);

        if (useGPS)
        {
            requestGPSLocation();
        }
        else
        {
            requestNetworkLocation();
        }

        return null;
    }

    @Override
    public void onLocationChanged(Location location)
    {
        SharedPreferences data = PreferenceManager.getDefaultSharedPreferences(context);

        data.edit().putFloat(context.getString(R.string.preferencekey_latitude), (float) location.getLatitude())
                .putFloat(context.getString(R.string.preferencekey_longitude), (float) location.getLongitude())
                .commit();

        locationManager.removeUpdates(this);

        isRunning = false;

        Toast.makeText(context, "Location updated", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderDisabled(String provider)
    {

    }

    @Override
    public void onProviderEnabled(String provider)
    {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras)
    {

    }

    private void requestGPSLocation()
    {
        if (gpsEnabled)
        {
            ((Activity) context).runOnUiThread(new Runnable() {

                @Override
                public void run()
                {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, GetLocationTask.this);

                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run()
                        {
                            if (isRunning)
                            { // i.e. still waiting on GPS after 5s timeout
                                locationManager.removeUpdates(GetLocationTask.this);
                                requestNetworkLocation();
                            }
                        }
                    }, 5000); // 5s
                }
            });
        }
        else
        {
            requestNetworkLocation();
        }
    }

    private void requestNetworkLocation()
    {
        if (networkEnabled)
        {
            ((Activity) context).runOnUiThread(new Runnable() {

                @Override
                public void run()
                {
                    locationManager
                            .requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, GetLocationTask.this);
                }
            });
        }
        else
        {
            showNetworkError();
        }
    }

    private void showNetworkError()
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setMessage("Please enable location services to update your location")
                .setPositiveButton("Settings", new OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        switch (which)
                        {
                            case DialogInterface.BUTTON_POSITIVE:

                                Intent locationSettingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                ((Activity) context).startActivity(locationSettingsIntent);
                        }
                    }
                }).show();
    }
}
