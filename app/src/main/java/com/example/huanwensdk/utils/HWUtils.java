package com.example.huanwensdk.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import com.example.huanwensdk.ui.dialog.LoginTrialDialog;
import com.example.huanwensdk.utils.sp.HWConfigSharedPreferences;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * 
 * @Title: HWUtils.java
 * @Package utils
 * @Description: SDK工具类
 * @author: Android_ruan
 * @date: 2018-3-21 上午11:35:03
 * @version V1.0
 */
public class HWUtils {

	private static final int MY_PERMISSION_REQUEST_CODE = 10000;
	private static String[] defaultPermission = { "android.permission.READ_PHONE_STATE", "android.permission.WRITE_EXTERNAL_STORAGE" };
	/**
	 * 设置SDK语言
	 * 
	 * @param context
	 * @param resources
	 */
	public static void setLanguage(Context context, Resources resources) {
		String language = HWConfigSharedPreferences.getInstance(context)
				.getLanguage();
		LogUtils.e("获取的语言---->"+language);
		if (language == null) {
			String[] languages = Locale.getDefault().toString().split("_");
			if (languages.length > 1) {
				language = languages[0] + "_" + languages[1];
				if (language.equalsIgnoreCase("zh_HK")) {
					language = "zh_TW";
				}
				if ((!language.equalsIgnoreCase("zh_CN"))
						&& (!language.equalsIgnoreCase("zh_TW"))
						&& (!language.equalsIgnoreCase("en_US"))
						&& (!language.equalsIgnoreCase("ko_KR"))
						&& (!language.equalsIgnoreCase("th_TH"))
						&& (!language.equalsIgnoreCase("id_ID"))) {
					language = "en_US";
				}
			} else {
				language = languages[0];
				if (language.equalsIgnoreCase("en_US".split("_")[0]))
					language = "en_US";
				else if (language.equalsIgnoreCase("ko_KR".split("_")[0]))
					language = "ko_KR";
				else if (language.equalsIgnoreCase("th_TH".split("_")[0]))
					language = "th_TH";
				else if (language.equalsIgnoreCase("id_ID".split("_")[0]))
					language = "id_ID";
				else if (language.equalsIgnoreCase("zh_CN".split("_")[0]))
					language = "zh_CN";
				else {
					language = "en_US";
				}
			}
		}

		String[] languages = language.split("_");
		Locale local;
		if (languages.length > 1)
			local = new Locale(languages[0], languages[1]);
		else {
			local = new Locale(languages[0]);
		}

		Configuration config = resources.getConfiguration();
		DisplayMetrics dm = resources.getDisplayMetrics();
		config.locale = local;
		resources.updateConfiguration(config, dm);
	}

	@SuppressLint("NewApi")
	public static Map<String, String> getAndroidEmulatorData(
			Context paramContext) {
		HashMap localHashMap = new HashMap();
		String str1 = Build.SERIAL;
		if (ActivityCompat.checkSelfPermission(paramContext, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
		}else{
			ActivityCompat.requestPermissions((Activity) paramContext,defaultPermission,MY_PERMISSION_REQUEST_CODE);
		}
		TelephonyManager telephonyManager = (TelephonyManager) paramContext
				.getSystemService(paramContext.TELEPHONY_SERVICE);
		String str2 = telephonyManager.getDeviceId();
		String str3 = telephonyManager.getNetworkOperatorName();
		String str4 = (Build.FINGERPRINT + " , " + Build.DEVICE + " , "
				+ Build.MODEL + " , " + Build.BRAND + " , " + Build.PRODUCT
				+ " , " + Build.MANUFACTURER + " , " + Build.HARDWARE)
				.toLowerCase();
		if (str1 == null)
			str1 = "";
		localHashMap.put("androidSerial", str1);
		if (str2 == null)
			str2 = "";
		localHashMap.put("androidIMEI", str2);
		if (str3 == null)
			str3 = "";
		localHashMap.put("androidNetworkOperator", str3);
		localHashMap.put("androidBuildDetails", str4);
		return localHashMap;
	}

	public static String formatTime(String paramString) {
		return formatTime(paramString, "yyyy-MM-dd HH:mm");
	}

	public static String formatTime(String paramString1, String paramString2) {
		return new SimpleDateFormat(paramString2).format(new Date(Long.valueOf(
				paramString1).longValue()));
	}

	public static String getTimestamp() {
		return String.valueOf(System.currentTimeMillis());
	}

	/**
	 * 获取手机机器码
	 * 
	 * @return
	 */
	public static String getDeviceId(Context context) {

		TelephonyManager telephonyManager = (TelephonyManager) context
				.getSystemService(context.TELEPHONY_SERVICE);
		if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
		}else{
			ActivityCompat.requestPermissions((Activity) context,defaultPermission,MY_PERMISSION_REQUEST_CODE);
		}
		return telephonyManager.getDeviceId();

	}
	
	@SuppressLint("NewApi") public static void getScreen(){
		View dView = ((Activity) HWControl.getInstance().getContext()).getWindow().getDecorView();
		dView.setDrawingCacheEnabled(true);
		dView.buildDrawingCache();
		Bitmap bitmap1 = Bitmap.createBitmap(dView.getDrawingCache());
		//如果需要同时保存打开的dialog的截图，可以这么做，如果不需要，上面的bitmap就是当前activity的截图了。
	    View dialogView = LoginTrialDialog.getInstance().getDialog().getWindow().getDecorView();
	    LogUtils.e("dialogView---->"+dialogView);
	    int location2[] = new int[2];
	    dialogView.getLocationOnScreen(location2);
	    dialogView.setDrawingCacheEnabled(true);
	    dialogView.buildDrawingCache();
	    int location[] = new int[2];
	    dView.getLocationOnScreen(location);
	    if (Build.VERSION.SDK_INT >= 11) {  
	    	dialogView.measure(View.MeasureSpec.makeMeasureSpec(dialogView.getWidth(), View.MeasureSpec.EXACTLY),  
	                View.MeasureSpec.makeMeasureSpec(dialogView.getHeight(), View.MeasureSpec.EXACTLY));  
	    	dialogView.layout((int) dialogView.getX(), (int) dialogView.getY(), (int) dialogView.getX() + dialogView.getMeasuredWidth(), (int) dialogView.getY() + dialogView.getMeasuredHeight());  
	    } else {  
	    	dialogView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),  
	                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));  
	    	dialogView.layout(0, 0, dialogView.getMeasuredWidth(), dialogView.getMeasuredHeight());  
	    }
	    LogUtils.e("dialogView.getWidth---->"+dialogView.getWidth());
	    LogUtils.e("dialogView.getHeight---->"+dialogView.getHeight());
	    Bitmap bitmap = Bitmap.createBitmap(dialogView.getDrawingCache(), 0, 0, dialogView.getMeasuredWidth(), dialogView.getMeasuredHeight());
//	    Bitmap bitmap = Bitmap.createBitmap(dialogView.getDrawingCache());
	    LogUtils.e("bitmapHeight---->"+bitmap.getHeight());
	    LogUtils.e("bitmapWidth--->"+bitmap.getWidth());
	    Canvas canvas = new Canvas(bitmap);
	    canvas.drawBitmap(bitmap, location2[0] - location[0], location2[1] - location[1], new Paint());
	    dialogView.destroyDrawingCache();
	    dView.destroyDrawingCache();
		if (bitmap != null) {
		  try {
		    // 获取内置SD卡路径
		    String sdCardPath = Environment.getExternalStorageDirectory().getPath();
		    // 图片文件路径
		    String filePath = Constant.GAME_FILE_PATH + "screenshot2.png";
		    File file = new File(filePath);
		    FileOutputStream os = new FileOutputStream(file);
		    bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
		    os.flush();
		    os.close();
		    LogUtils.e("存储完成");
		  } catch (Exception e) {
		  }
		}
	}
	
	/** 
	 * 返回当前程序版本名 
	 */  
	public static String getAppVersionName(Context context) {  
	    String versionName = "";  
	    try {  
	        // ---get the package info---  
	        PackageManager pm = context.getPackageManager();  
	        PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);  
	        versionName = pi.versionName;  
	        if (versionName == null || versionName.length() <= 0) {  
	            return "";  
	        }  
	    } catch (Exception e) {  
	        Log.e("VersionInfo", "Exception", e);  
	    }  
	    return versionName;  
	}  
}
