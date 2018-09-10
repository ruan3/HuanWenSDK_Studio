package com.example.huanwensdk.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.R;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;

/**
 * 
 * @Title: HWBitmap.java
 * @Package utils
 * @Description: 用来保存截图
 * @author: Android_ruan
 * @date: 2018-3-27 下午5:28:23
 * @version V1.0
 */
public class HWBitmap {

	public static Bitmap getBitmap(Context paramContext, String paramString) {
		BitmapFactory.Options localOptions = new BitmapFactory.Options();
		return BitmapFactory.decodeResource(paramContext.getResources(),
				ResLoader.getId(paramContext, "drawable", paramString),
				localOptions);
	}

	public static int getFontHeight(Paint paramPaint) {
		Paint.FontMetrics localFontMetrics = paramPaint.getFontMetrics();
		return 2 + (int) Math.ceil(localFontMetrics.descent
				- localFontMetrics.top);
	}

	public static float getFontlength(Paint paramPaint, String paramString) {
		return paramPaint.measureText(paramString);
	}

	public static Bitmap getTrialBitmap(Context paramContext) {
		Bitmap localBitmap1 = ((BitmapDrawable) ResLoader.getDrawable(
				paramContext, "fg_login_type_trial", 60, 60)).getBitmap();
		int i = localBitmap1.getWidth();
		int j = localBitmap1.getHeight();
		Bitmap localBitmap2 = Bitmap
				.createBitmap(i, j, Bitmap.Config.ARGB_8888);
		Canvas localCanvas = new Canvas(localBitmap2);
		localCanvas.drawBitmap(localBitmap1, 0.0F, 0.0F, null);
		Paint localPaint = new Paint();
		localPaint.setColor(-1);
		localPaint.setAntiAlias(true);
		localPaint.setFilterBitmap(true);
		int k;
		if (HWFixView.getInstance().isStandardPx(paramContext))
			k = 25;
		int i1;
		for (int n = 27;;) {
			localPaint.setTextSize(HWFixHelper.getFixWidth(paramContext, 25));
			String str = ResLoader.getString(paramContext,
					"fg_login_trial_text");
			localCanvas.drawText(
					str,
					(i - getFontlength(localPaint, str)) / 2.0F,
					(j - getFontHeight(localPaint)) / 2
							+ HWFixHelper.getFixWidth(paramContext, n),
					localPaint);
			localCanvas.save(Canvas.ALL_SAVE_FLAG);
			localCanvas.restore();
			return localBitmap2;
		}
	}

	public static void saveTrialAccountBitmap(Context paramContext,
			String paramString1, String paramString2) {
		Bitmap localBitmap1 = getBitmap(paramContext, "fg_guest_find_account");
		int i = localBitmap1.getWidth();
		int j = localBitmap1.getHeight();
		Bitmap localBitmap2 = Bitmap
				.createBitmap(i, j, Bitmap.Config.ARGB_8888);
		Canvas localCanvas = new Canvas(localBitmap2);
		localCanvas.drawBitmap(localBitmap1, 0.0F, 0.0F, null);
		Paint localPaint = new Paint();
		localPaint.setColor(ResLoader.getColor(paramContext, "fg_find_text"));
		localPaint.setAntiAlias(true);
		localPaint.setFilterBitmap(true);
		localPaint.setTextSize(HWFixHelper.getFixWidth(paramContext, 42.0F));
		localPaint.setTextAlign(Paint.Align.LEFT);
		localCanvas.drawText(paramString1,
				(i - getFontlength(localPaint, paramString1)) / 2.0F,
				j * 762 / 1920, localPaint);
		localCanvas.drawText(paramString2,
				(i - getFontlength(localPaint, paramString2)) / 2.0F,
				j * 1132 / 1920, localPaint);
		localCanvas.save(Canvas.ALL_SAVE_FLAG);
		localCanvas.restore();
		svaeBitmap(paramContext, localBitmap2, "fg_guest_find_account" + "_"
				+ paramString1);
	}

	public static void saveTrialUIDBitmap(Context paramContext,
			String paramString1, String paramString2, String paramString3) {
		/**
		 * 我们先画要保存的图
		 */
		Bitmap localBitmap1 = getBitmap(paramContext, "fg_guest_find_uid");//背景图片
		int width = localBitmap1.getWidth();
		int height = localBitmap1.getHeight();
		Bitmap localBitmap2 = Bitmap.createBitmap(width, height,
				Bitmap.Config.ARGB_8888);
		Canvas localCanvas = new Canvas(localBitmap2);//绘制背景
		localCanvas.drawBitmap(localBitmap1, 0.0F, 0.0F, null);
		Paint localPaint = new Paint();//获取画笔
		localPaint.setColor(ResLoader.getColor(paramContext, "fg_find_text"));//设置画笔颜色
		localPaint.setAntiAlias(true);
		localPaint.setFilterBitmap(true);
		localPaint.setTextSize(20);
		localCanvas.drawText(paramString3,
				(width - getFontlength(localPaint, paramString3)) / 2.0F,
				height * 665 / 1920, localPaint);//根据位置 绘制相应的账号密码等
		localCanvas.drawText(paramString1,
				(width - getFontlength(localPaint, paramString1)) / 2.0F,
				height * 980 / 1920, localPaint);
		localCanvas.drawText(paramString2,
				(width - getFontlength(localPaint, paramString2)) / 2.0F,
				height * 1280 / 1920, localPaint);
		localCanvas.save(Canvas.ALL_SAVE_FLAG);
		localCanvas.restore();
		svaeBitmap(paramContext, localBitmap2, "fg_guest_find_uid");
	}

	public static Bitmap scaleBitmap(Bitmap paramBitmap, double paramDouble1,
			double paramDouble2) {
		float f1 = paramBitmap.getWidth();
		float f2 = paramBitmap.getHeight();
		Matrix localMatrix = new Matrix();
		localMatrix.postScale((float) paramDouble1 / f1, (float) paramDouble2
				/ f2);
		return Bitmap.createBitmap(paramBitmap, 0, 0, (int) f1, (int) f2,
				localMatrix, true);
	}

	public static void svaeBitmap(Context paramContext, Bitmap paramBitmap,
			String paramString) {
		/**
		 * 保存图片到本地
		 */
		String str1 = Constant.GAME_FILE_PATH + paramContext.getPackageName();
		LogUtils.e("保存图片路径名str1----->" + str1);
		String str2 = str1 + File.separator + paramString + ".jpg";
		LogUtils.e("保存图片路径名str2----->" + str2);
		File localFile1 = new File(str1);
		File localFile2 = new File(str2);
		if (!localFile1.exists()) {
			localFile1.mkdirs();
		}
		try {
			if (localFile2.exists()) {
				localFile2.createNewFile();
			}
			LogUtils.e("这里执行了---->"+localFile2.getPath());
			FileOutputStream localFileOutputStream1 = new FileOutputStream(localFile2);
			paramBitmap.compress(Bitmap.CompressFormat.JPEG, 100,
					localFileOutputStream1);
			Intent localIntent = new Intent(
					"android.intent.action.MEDIA_SCANNER_SCAN_FILE");
			localIntent.setData(Uri.fromFile(localFile2));
			paramContext.sendBroadcast(localIntent);
		} catch (FileNotFoundException e) {

			LogUtils.e("fileNotFoudExceptrin---->"+e.toString());
			
		} catch (IOException ex) {
			LogUtils.e("IOException---->"+ex.toString());
		}
	}

}
