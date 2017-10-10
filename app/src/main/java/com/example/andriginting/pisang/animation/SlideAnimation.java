package com.example.andriginting.pisang.animation;

import android.content.Context;
import android.view.View;
import android.view.animation.AnimationUtils;

import com.example.andriginting.pisang.R;

/**
 * Created by Andri Ginting on 10/10/2017.
 */

public class SlideAnimation {

    public static void slideInFromLeft(Context context, View v){
        runSimpleAnimation(context,v, R.anim.slide_from_left);
    }
    public static void slideInFromRight(Context context,View v){
        runSimpleAnimation(context,v,R.anim.slide_from_right);
    }

    public static void slideOutToLeft(Context context,View v){
        runSimpleAnimation(context,v,R.anim.slide_left);
    }
    public static void slideOutToRight(Context context,View v){
        runSimpleAnimation(context,v,R.anim.slide_right);
    }

    private static void runSimpleAnimation(Context context, View v, int animationID) {
     v.startAnimation(AnimationUtils.loadAnimation(context,animationID));
    }
}
