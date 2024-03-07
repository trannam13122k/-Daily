package com.example.daily.effect;

import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.viewpager2.widget.ViewPager2;

public class DepthPageTransformer implements ViewPager2.PageTransformer {
    private static final float MIN_SCALE = 0.75f;

    public void transformPage(View view, float position) {
        int pageWidth = view.getWidth();

        if (position < -1) {
            view.setAlpha(0f);
        } else if (position <= 0) {
            view.setAlpha(1f);
            view.setTranslationX(0f);
            view.setTranslationZ(0f);
            view.setScaleX(1f);
            view.setScaleY(1f);

        } else if (position <= 1) {
            view.setAlpha(1 - position);
            view.setTranslationX(pageWidth * -position);
            view.setTranslationZ(-1f);

            float scaleFactor = MIN_SCALE
                    + (1 - MIN_SCALE) * (1 - Math.abs(position));
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);
        } else {
            view.setAlpha(0f);
        }
    }
}