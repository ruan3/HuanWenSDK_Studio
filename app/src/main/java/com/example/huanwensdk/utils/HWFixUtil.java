package com.example.huanwensdk.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

public class HWFixUtil {

	 public static int getBottomStatusHeight(Context paramContext, int paramInt)
	  {
	    int i = getDpi(paramContext) - paramInt;
	    if (i > 0)
	      return i;
	    return 0;
	  }

	  public static int getDpi(Context paramContext)
	  {
	    Display localDisplay = ((WindowManager)paramContext.getSystemService( Context.WINDOW_SERVICE)).getDefaultDisplay();
	    DisplayMetrics localDisplayMetrics = new DisplayMetrics();
	    try
	    {
	      Class.forName("android.view.Display").getMethod("getRealMetrics", new Class[] { DisplayMetrics.class }).invoke(localDisplay, new Object[] { localDisplayMetrics });
	      int i = localDisplayMetrics.heightPixels;
	      return i;
	    }
	    catch (Exception localException)
	    {
	      localException.printStackTrace();
	    }
	    return 0;
	  }

	  public static boolean isTablet(Context paramContext)
	  {
	    return (0xF & paramContext.getResources().getConfiguration().screenLayout) >= 3;
	  }
	
}
