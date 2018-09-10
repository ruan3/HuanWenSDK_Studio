package com.example.huanwensdk.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.huanwensdk.R;
import com.example.huanwensdk.base.BaseDialog;
import com.example.huanwensdk.ui.dialog.LoginDialog;
import com.example.huanwensdk.ui.dialog.PayDialog;

/**
 *
 *
 * @ClassName: DialogUtils
 * @Description: TODO(管理所有对话框)
 * @author ruan
 * @date 2017-6-13 下午1:50:00
 *
 */
public class DialogUtils {

	static boolean isShowPwd = true;

	BaseDialog dialog_tips;

	BaseDialog dialog;

	String appid;
	String jsonMap = "";

	private static final int MY_PERMISSION_REQUEST_CODE = 10000;
	private static String[] defaultPermission = { "android.permission.READ_PHONE_STATE", "android.permission.WRITE_EXTERNAL_STORAGE" };

	private static DialogUtils dialogUtils = new DialogUtils();

	public static DialogUtils getInstance() {
		return dialogUtils;
	}

	/**
	 *
	 * @Description: TODO(启动登录对话框)
	 * @param @param context 上下文
	 * @param @param leftListener
	 * @param @param rightListener 设定文件
	 * @return （返回类型）
	 * @throws
	 */
	public void showLogin(final Context context) {

		HWControl.getInstance().setContext(context);
		LoginDialog.getInstance().show();

	}

	public void showPay(final Context context) {

		HWControl.getInstance().setContext(context);
		PayDialog.getInstance().show();

	}

	boolean isPress = false;

	/**
	 * 正式输入数据登录
	 *
	 * @param context
	 */
	public void showLoginDetail(final Context context) {

		EditText et_psd;
		final ImageButton btn_clear;

		if (dialog == null) {

			dialog = new BaseDialog(context, R.style.dialog);
		}

		dialog.setContentView(R.layout.dialog_login_input);
		et_psd = (EditText) dialog.findViewById(R.id.et_psd);
		btn_clear = (ImageButton) dialog.findViewById(R.id.btn_clear);

		btn_clear.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (isPress) {

					btn_clear.setImageResource(R.drawable.fg_clear_p);
					isPress = false;
				} else {
					btn_clear.setImageResource(R.drawable.fg_clear_n);
					isPress = true;
				}

			}
		});

		dialog.setCanceledOnTouchOutside(false);
		dialog.show();
	}

	boolean isRegisterPress = false;
	boolean isComfirmPress = false;

	/**
	 * 显示注册账号
	 *
	 * @param context
	 */
	public void showRegister(Context context) {

		final ImageButton btn_comfirm_clear;
		final ImageButton btn_register_clear;

		if (dialog == null) {

			dialog = new BaseDialog(context, R.style.dialog);
		}

		dialog.setContentView(R.layout.dialog_register);
		btn_comfirm_clear = (ImageButton) dialog
				.findViewById(R.id.btn_comfirm_clear);
		btn_register_clear = (ImageButton) dialog
				.findViewById(R.id.btn_register_clear);

		// 再次输入密码
		btn_comfirm_clear.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (isComfirmPress) {// 根据点击选择可看密码显示或隐藏

					btn_comfirm_clear.setImageResource(R.drawable.fg_clear_p);
					isComfirmPress = false;
				} else {
					btn_comfirm_clear.setImageResource(R.drawable.fg_clear_n);
					isComfirmPress = true;
				}

			}
		});
		// 再次输入密码
		btn_register_clear.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (isRegisterPress) {// 根据点击选择可看密码显示或隐藏

					btn_register_clear.setImageResource(R.drawable.fg_clear_p);
					isRegisterPress = false;
				} else {
					btn_register_clear.setImageResource(R.drawable.fg_clear_n);
					isRegisterPress = true;
				}

			}
		});

		dialog.setCanceledOnTouchOutside(false);
		dialog.show();

	}

	/**
	 * 会员中心对话框
	 *
	 * @param context
	 */
	public void showMemberCneter(Context context) {

		if (dialog == null) {

			dialog = new BaseDialog(context, R.style.dialog);
		}

		dialog.setContentView(R.layout.dialog_user_center);

		dialog.setCanceledOnTouchOutside(false);
		dialog.show();
	}

	/**
	 * 获取手机IMEI号
	 */
	public static String getIMEI(Context context) {
		TelephonyManager telephonyManager = (TelephonyManager) context
				.getSystemService(context.TELEPHONY_SERVICE);
		if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
		}else{
			ActivityCompat.requestPermissions((Activity) context,defaultPermission,MY_PERMISSION_REQUEST_CODE);
		}

		String imei = telephonyManager.getDeviceId();
		return imei;
	}

	/**
	 * 获取手机IMSI号
	 */
	public static String getIMSI(Context context) {
		TelephonyManager mTelephonyMgr = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
		}else{
			ActivityCompat.requestPermissions((Activity) context,defaultPermission,MY_PERMISSION_REQUEST_CODE);
		}
		String imsi = mTelephonyMgr.getSubscriberId();

		return imsi;
	}

	/**
	 * 获取手机ICCId
	 */
	public static String getICCID(Context context) {
		TelephonyManager mTelephonyMgr = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
		}else{
			ActivityCompat.requestPermissions((Activity) context,defaultPermission,MY_PERMISSION_REQUEST_CODE);
		}
		String imei = mTelephonyMgr.getSimSerialNumber(); // 取出ICCID
		return imei;
	}

	/**
	 * 获取手机mac地址
	 */
	/**
	 * 获取手机mac地址<br/>
	 * 错误返回12个0
	 */
	public static String getMacAddress(Context context) {
		// 获取mac地址：
		String macAddress = "000000000000";
		try {
			WifiManager wifiMgr = (WifiManager) context
					.getSystemService(Context.WIFI_SERVICE);
			WifiInfo info = (null == wifiMgr ? null : wifiMgr
					.getConnectionInfo());
			if (null != info) {
				if (!TextUtils.isEmpty(info.getMacAddress()))
					macAddress = info.getMacAddress().replace(":", "");
				else
					return macAddress;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return macAddress;
		}
		return macAddress;
	}

	/**
	 * 获取屏幕分辨率
	 */
	public static Point getSizeNew(Context ctx) {
		WindowManager wm = (WindowManager) ctx
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics dm = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(dm);
		Point size = new Point();
		size.x = dm.widthPixels;
		size.y = dm.heightPixels;
		return size;
	}
}
