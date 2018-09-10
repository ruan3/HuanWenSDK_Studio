package com.example.huanwensdk.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.graphics.Movie;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
/**
 * 
 * @Title: ResLoader.java
 * @Package utils
 * @Description: 加载资源
 * @author: Android_ruan
 * @date: 2018-3-21 上午10:12:36
 * @version V1.0
 */
public class ResLoader {

	public static Object loadRes(ResType type, Context context, int id) {
		if ((context == null) || (id < 1))
			return null;
		switch (type) {
		case Animation:
			return getAnimation(context, id);
		case Boolean:
			return Boolean.valueOf(getBoolean(context, id));
		case Color:
			return Integer.valueOf(getColor(context, id));
		case ColorStateList:
			return getColorStateList(context, id);
		case Dimension:
			return Float.valueOf(getDimension(context, id));
		case DimensionPixelOffset:
			return Integer.valueOf(getDimensionPixelOffset(context, id));
		case DimensionPixelSize:
			return Integer.valueOf(getDimensionPixelSize(context, id));
		case Drawable:
			return getDrawable(context, id);
		case IntArray:
			return Integer.valueOf(getInteger(context, id));
		case Integer:
			return getIntArray(context, id);
		case Movie:
			return getMovie(context, id);
		case String:
			return getString(context, id);
		case StringArray:
			return getStringArray(context, id);
		case Text:
			return getText(context, id);
		case TextArray:
			return getTextArray(context, id);
		case Xml:
			return getXml(context, id);
		}

		return null;
	}

	public static Animation getAnimation(Context context, int id) {
		if(context==null){
			context = HWControl.getInstance().getContext();
		}
		return AnimationUtils.loadAnimation(context, id);
	}

	public static boolean getBoolean(Context context, int id) {
		if(context==null){
			context = HWControl.getInstance().getContext();
		}
		return context.getResources().getBoolean(id);
	}

	public static boolean getBoolean(Context context, String name) {
		if(context==null){
			context = HWControl.getInstance().getContext();
		}
		int id = context.getResources().getIdentifier(name, "boolean",
				context.getPackageName());
		return context.getResources().getBoolean(id);
	}

	public static int getColor(Context context, int id) {
		if(context==null){
			context = HWControl.getInstance().getContext();
		}
		return context.getResources().getColor(id);
	}

	public static int getColor(Context context, String name) {
		if(context==null){
			context = HWControl.getInstance().getContext();
		}
		int id = context.getResources().getIdentifier(name, "color",
				context.getPackageName());
		return context.getResources().getColor(id);
	}

	public static ColorStateList getColorStateList(Context context, int id) {
		if(context==null){
			context = HWControl.getInstance().getContext();
		}
		return context.getResources().getColorStateList(id);
	}

	public static ColorStateList getColorStateList(Context context, String name) {
		if(context==null){
			context = HWControl.getInstance().getContext();
		}
		int id = context.getResources().getIdentifier(name, "colorstatelist",
				context.getPackageName());
		return context.getResources().getColorStateList(id);
	}

	public static float getDimension(Context context, int id) {
		if(context==null){
			context = HWControl.getInstance().getContext();
		}
		return context.getResources().getDimension(id);
	}

	public static float getDimension(Context context, String name) {
		if(context==null){
			context = HWControl.getInstance().getContext();
		}
		int id = context.getResources().getIdentifier(name, "dimension",
				context.getPackageName());
		return context.getResources().getDimension(id);
	}

	public static int getDimensionPixelOffset(Context context, int id) {
		if(context==null){
			context = HWControl.getInstance().getContext();
		}
		return context.getResources().getDimensionPixelOffset(id);
	}

	public static int getDimensionPixelOffset(Context context, String name) {
		if(context==null){
			context = HWControl.getInstance().getContext();
		}
		int id = context.getResources().getIdentifier(name,
				"dimensionpixeloffset", context.getPackageName());
		return context.getResources().getDimensionPixelOffset(id);
	}

	public static int getDimensionPixelSize(Context context, int id) {
		if(context==null){
			context = HWControl.getInstance().getContext();
		}
		return context.getResources().getDimensionPixelSize(id);
	}

	public static Drawable getDrawable(Context context, int id) {
		if(context==null){
			context = HWControl.getInstance().getContext();
		}
		return context.getResources().getDrawable(id);
	}

	public static Drawable getDrawable(Context context, String name) {
		if(context==null){
			context = HWControl.getInstance().getContext();
		}
		int id = context.getResources().getIdentifier(name, "drawable",
				context.getPackageName());
		return context.getResources().getDrawable(id);
	}

	@SuppressLint("NewApi") public static Drawable getDrawable(Context paramContext,
			String paramString, int paramInt1, int paramInt2) {
		Drawable localDrawable = paramContext.getDrawable(
				paramContext.getResources().getIdentifier(paramString,
						"drawable",
						"com."));
		localDrawable.setBounds(0, 0,
				(int) HWFixHelper.getFixHeight(paramContext, paramInt1),
				(int) HWFixHelper.getFixHeight(paramContext, paramInt2));
		return localDrawable;
	}

	public static int getInteger(Context context, int id) {
		if(context==null){
			context = HWControl.getInstance().getContext();
		}
		return context.getResources().getInteger(id);
	}

	public static int getInteger(Context context, String name) {
		if(context==null){
			context = HWControl.getInstance().getContext();
		}
		int id = context.getResources().getIdentifier(name, "integer",
				context.getPackageName());
		return context.getResources().getInteger(id);
	}

	public static int[] getIntArray(Context context, int id) {
		if(context==null){
			context = HWControl.getInstance().getContext();
		}
		return context.getResources().getIntArray(id);
	}

	public static int[] getIntArray(Context context, String name) {
		if(context==null){
			context = HWControl.getInstance().getContext();
		}
		int id = context.getResources().getIdentifier(name, "intarray",
				context.getPackageName());
		return context.getResources().getIntArray(id);
	}

	public static Movie getMovie(Context context, int id) {
		if(context==null){
			context = HWControl.getInstance().getContext();
		}
		return context.getResources().getMovie(id);
	}

	public static String getString(Context context, int id) {
		if(context==null){
			context = HWControl.getInstance().getContext();
		}
		return context.getResources().getString(id);
	}

	public static String getString(Context context, String name) {
		if(context==null){
			context = HWControl.getInstance().getContext();
		}
		int id = context.getResources().getIdentifier(name, "string",
				context.getPackageName());
		if (id == 0) {
			Log.e("Comt", "找不到string的id");
			return "";
		}
		return context.getResources().getString(id);
	}

	public static int getId(Context context, String type, String name) {
		if(context==null){
			context = HWControl.getInstance().getContext();
		}
		int id = context.getResources().getIdentifier(name, type,
				context.getPackageName());
		return id;
	}

	public static String[] getStringArray(Context context, int id) {
		if(context==null){
			context = HWControl.getInstance().getContext();
		}
		return context.getResources().getStringArray(id);
	}

	public static String[] getStringArray(Context context, String name) {
		if(context==null){
			context = HWControl.getInstance().getContext();
		}
		int id = context.getResources().getIdentifier(name, "array",
				context.getPackageName());
		return context.getResources().getStringArray(id);
	}

	public static CharSequence getText(Context context, int id) {
		if(context==null){
			context = HWControl.getInstance().getContext();
		}
		return context.getResources().getText(id);
	}

	public static CharSequence[] getTextArray(Context context, int id) {
		if(context==null){
			context = HWControl.getInstance().getContext();
		}
		return context.getResources().getTextArray(id);
	}

	public static XmlResourceParser getXml(Context context, int id) {
		if(context==null){
			context = HWControl.getInstance().getContext();
		}
		return context.getResources().getXml(id);
	}

	public static int getId(Context context, String name) {
		if(context==null){
			context = HWControl.getInstance().getContext();
		}
		return context.getResources().getIdentifier(name, "id",
				context.getPackageName());
	}

	public static int getLayout(Context context, String name) {
		if(context==null){
			context = HWControl.getInstance().getContext();
		}
		return context.getResources().getIdentifier(name, "layout",
				context.getPackageName());
	}

	public static int getTheme(Context context, String name) {
		if(context==null){
			context = HWControl.getInstance().getContext();
		}
		return context.getResources().getIdentifier(name, "style",
				context.getPackageName());
	}

	public static int getRaw(Context context, String name) {
		if(context==null){
			context = HWControl.getInstance().getContext();
		}
		int id = context.getResources().getIdentifier(name, "raw",
				context.getPackageName());
		return id;
	}

	public static <T extends View> View getView(Context context, int viewId) {
		if(context==null){
			context = HWControl.getInstance().getContext();
		}
		return ((Activity) context).findViewById(viewId);
	}

	public static <T extends View> View getView(Context context, View view,
			int viewId) {
		if(context==null){
			context = HWControl.getInstance().getContext();
		}
		return view.findViewById(viewId);
	}

	public static <T extends View> View getView(Context context, String viewId) {
		if(context==null){
			context = HWControl.getInstance().getContext();
		}
		return ((Activity) context).findViewById(getId(context, viewId));
	}

	public static <T extends View> View getView(Window window, int viewId) {
		return window.findViewById(viewId);
	}

	public static <T extends View> View getView(Window window, String viewId) {
		return window.findViewById(getId(window.getContext(), viewId));
	}

	public static <T extends View> View getView(Context context, View view,
			String viewId) {
		return view.findViewById(getId(context, viewId));
	}

	public static <T extends View> View getView(Context context, View view,
			String viewId, View.OnClickListener clickListener) {
		View v = view.findViewById(getId(context, viewId));
		v.setOnClickListener(clickListener);

		return v;
	}

	public static <T extends View> View getView(Context context, String viewId,
			View.OnClickListener clickListener) {
		View view = ((Activity) context).findViewById(getId(context, viewId));
		view.setOnClickListener(clickListener);

		return view;
	}

	public static int getStyle(Context context, String name) {
		if(context==null){
			context = HWControl.getInstance().getContext();
		}
		return context.getResources().getIdentifier(name, "style",
				context.getPackageName());
	}

}
