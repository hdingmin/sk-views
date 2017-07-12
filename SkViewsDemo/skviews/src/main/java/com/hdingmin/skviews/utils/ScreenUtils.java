package com.hdingmin.skviews.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.WindowManager;

/**
 * Created by sKy on 2016/10/31.
 */
public class ScreenUtils {
    private ScreenUtils() {
        throw new AssertionError();
    }

    public  static  int dp2px(Context context,float dpValue)
    {
        return  (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dpValue,context.getResources().getDisplayMetrics());
    }
    public  static int sp2px(Context context,float spVlaue)
    {
        return  (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,spVlaue,context.getResources().getDisplayMetrics());
    }

    public  static float getScreenDpiWidth(Context context)
    {
        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return  dm.widthPixels/dm.density;
    }
    public static float getScreenDpiHeight(Context context)
    {
        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return  dm.heightPixels/dm.density;
    }
    public  static  int getScreenPxWidth(Context context)
    {
        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return  dm.widthPixels;
    }
    public  static  int getScreenPxHeight(Context context)
    {
        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return  dm.heightPixels;
    }
}
