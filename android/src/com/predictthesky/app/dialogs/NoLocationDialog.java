package com.predictthesky.app.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

public class NoLocationDialog extends DialogFragment
{
    private class NoLocationDialogListener implements DialogInterface.OnClickListener
    {
        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            switch (which)
            {
                case DialogInterface.BUTTON_POSITIVE:
                    Intent locationOptions = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(locationOptions);
                    break;
                case DialogInterface.BUTTON_NEUTRAL:
                    new LocationSelectionDialog().show(getFragmentManager(), null);
                    dismiss();
                    break;
            }
        }
    }

    NoLocationDialogListener listener;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        int style = DialogFragment.STYLE_NORMAL;
        int theme = android.R.style.Theme_Holo_Dialog;

        setStyle(style, theme);

        listener = new NoLocationDialogListener();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        AlertDialog dialog = builder.setMessage("A location is required for the app to function.")
                .setPositiveButton("Settings", listener).setNeutralButton("Pick from a list", listener).create();

        return dialog;
    }
}
