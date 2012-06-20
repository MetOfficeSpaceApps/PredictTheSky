package com.predictthesky.app.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.predictthesky.app.R;

public class HelpFragment extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View fragmentUI = inflater.inflate(R.layout.help_layout, container, false);

        // Allow legal content to scroll
        ((TextView) fragmentUI.findViewById(R.id.help_legal_content_view))
                .setMovementMethod(new ScrollingMovementMethod());

        return fragmentUI;
    }
}
