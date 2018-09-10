package com.example.huanwensdk.utils;

import android.content.Context;
import android.util.DisplayMetrics;

import com.example.huanwensdk.utils.sp.HWViewConfigSP;
import com.google.gson.Gson;

public class HWFixView {
	public static HWFixView instance;
	  private int height;
	  private HWViewConfig viewConfig;
	  private int width;

	  public static HWFixView getInstance()
	  {
	    if (instance == null)
	      instance = new HWFixView();
	    return instance;
	  }

	  public int getBottomStatusHeight(Context paramContext)
	  {
	    return getViewConfig(paramContext).getBottomStatusHeight();
	  }

	  public int getHeight(Context paramContext)
	  {
	    return getViewConfig(paramContext).getHeight();
	  }

	  public boolean getStandardPx(Context paramContext)
	  {
	    int i = getWidth(paramContext);
	    int j = getHeight(paramContext);
	    if (i < j)
	      if (1020.0F / getViewConfig(paramContext).getDesignWidth() * i != 1020.0F / getViewConfig(paramContext).getDesignHeight() * j);
	    while (1020.0F / getViewConfig(paramContext).getDesignHeight() * i == 1020.0F / getViewConfig(paramContext).getDesignWidth() * j);
	    return false;
	  }

	  public HWViewConfig getViewConfig(Context paramContext)
	  {
		  Gson gson = new Gson();
	    if (this.viewConfig == null)
	    	LogUtils.e("hwViewConfigSp---->"+new HWViewConfigSP(paramContext).getVIEWCONFIG());
	      this.viewConfig = ((HWViewConfig)gson.fromJson(new HWViewConfigSP(paramContext).getVIEWCONFIG(), HWViewConfig.class));
	    return this.viewConfig;
	  }

	  public int getWidth(Context paramContext)
	  {
	    return getViewConfig(paramContext).getWidth();
	  }

	  public void getWidthAndHeight(Context paramContext)
	  {
	    new DisplayMetrics();
	    DisplayMetrics localDisplayMetrics = paramContext.getResources().getDisplayMetrics();
	    this.width = localDisplayMetrics.widthPixels;
	    this.height = localDisplayMetrics.heightPixels;
	  }

	  public void init(Context paramContext, HWViewConfig paramFGViewConfig)
	  {
	    getInstance().getWidthAndHeight(paramContext);
	    boolean bool = getInstance().isScreenChange(paramContext);
	    this.viewConfig = paramFGViewConfig;
	    paramFGViewConfig.setWidth(this.width);
	    paramFGViewConfig.setHeight(this.height);
	    paramFGViewConfig.setScreenOrientation(bool);
	    paramFGViewConfig.setStandardPx(getStandardPx(paramContext));
	    paramFGViewConfig.setBottomStatusHeight(HWFixUtil.getBottomStatusHeight(paramContext, this.height));
	    paramFGViewConfig.setPad(HWFixUtil.isTablet(paramContext));
	    Gson gson = new Gson();
	    LogUtils.e("fixView - paramFGViewConfig---->"+paramFGViewConfig.toString());
	    new HWViewConfigSP(paramContext).setViewConfig(gson.toJson(paramFGViewConfig).toString());
	  }

	  public boolean isScreenChange(Context paramContext)
	  {
	    int i = paramContext.getResources().getConfiguration().orientation;
	    if (i == 1);
	    do
	      return true;
	    while (i != 2);
	  }

	  public boolean isScreenOrientation(Context paramContext)
	  {
	    return getViewConfig(paramContext).isScreenOrientation();
	  }

	  public boolean isStandardPx(Context paramContext)
	  {
	    return getViewConfig(paramContext).isStandardPx();
	  }
}
