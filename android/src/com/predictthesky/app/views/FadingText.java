package com.predictthesky.app.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class FadingText extends TextView implements AnimationListener
{
    Animation fade_in, fade_out;
    String    textToSet;

    public FadingText(Context context)
    {
        super(context);
        loadAnimations(context);
    }

    public FadingText(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        loadAnimations(context);
    }

    public FadingText(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        loadAnimations(context);
    }

    public void fadeTo(String text)
    {
        textToSet = text;
        if (getText() != null)
        {
            startAnimation(fade_out);
        }
        else
        {
            onAnimationEnd(fade_out);
        }
    }

    void loadAnimations(Context ctx)
    {
        fade_in = AnimationUtils.loadAnimation(ctx, android.R.anim.fade_in);
        fade_out = AnimationUtils.loadAnimation(ctx, android.R.anim.fade_out);
        fade_out.setAnimationListener(this);
    }

    @Override
    public void onAnimationEnd(Animation animation)
    {
        setText(textToSet);
        startAnimation(fade_in);
    }

    @Override
    public void onAnimationRepeat(Animation animation)
    {
    }

    @Override
    public void onAnimationStart(Animation animation)
    {
    }

}
