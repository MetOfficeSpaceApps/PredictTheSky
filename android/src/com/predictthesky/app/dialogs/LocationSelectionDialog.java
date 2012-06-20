package com.predictthesky.app.dialogs;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.predictthesky.app.App;
import com.predictthesky.app.InitActivity;

public class LocationSelectionDialog extends DialogFragment
{
    private class SelectionDialogListener implements DialogInterface.OnClickListener
    {
        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            switch (which)
            {
                case DialogInterface.BUTTON_POSITIVE:
                    ((InitActivity) getActivity()).citySelected((String) dialogList.getItemAtPosition(dialogList
                            .getCheckedItemPosition()));
                    dismiss();
                    break;
            }
        }
    }

    SelectionDialogListener listener;
    ListView                dialogList;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        int style = DialogFragment.STYLE_NORMAL;
        int theme = android.R.style.Theme_Holo_Dialog;

        setStyle(style, theme);

        listener = new SelectionDialogListener();
        dialogList = new ListView(getActivity());

        dialogList.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.select_dialog_singlechoice,
                new ArrayList<String>(App.locationList.keySet())));
        dialogList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        AlertDialog dialog = builder.setView(dialogList).setPositiveButton(android.R.string.ok, listener).create();

        return dialog;
    }
}
