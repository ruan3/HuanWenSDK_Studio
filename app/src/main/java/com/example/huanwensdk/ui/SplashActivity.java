package com.example.huanwensdk.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.hipermisson.HiPermission;
import com.example.hipermisson.PermissionCallback;
import com.example.hipermisson.PermissionItem;
import com.example.huanwensdk.R;
import com.example.huanwensdk.mvp.contract.initcontract;
import com.example.huanwensdk.mvp.presenter.InitPresenter;
import com.example.huanwensdk.utils.Constant;
import com.example.huanwensdk.utils.HWControl;
import com.example.huanwensdk.utils.LogUtils;
import com.example.huanwensdk.utils.ResLoader;
import com.example.huanwensdk.utils.sp.HWConfigSharedPreferences;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.xutils.common.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends AppCompatActivity implements initcontract.initView{

	Context context ;
	ImageView iv_logo;

	initcontract.initPresenter presenter;

	private  IWXAPI weChatApi;;

	private static final int MY_PERMISSION_REQUEST_CODE = 10000;

	private static final int REQUEST_CODE_SAVE_FILE_PERMISSION = 1;


	private String[] defaultPermission = { "android.permission.READ_PHONE_STATE", "android.permission.WRITE_EXTERNAL_STORAGE" };

	initcontract.initListener initListener;

	boolean isactivation;

	initcontract.initView view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		context = this;
		view = this;
		RequestPermission();
		isactivation = HWConfigSharedPreferences.getInstance(context).Activation();
//		HWControl.getInstance().setContext(context);
		weChatApi = WXAPIFactory.createWXAPI(context, Constant.WX_APP_ID, false);
		weChatApi.registerApp(Constant.WX_APP_ID);
	}
	private void RequestPermission() {
		List<PermissionItem> permissionItems = new ArrayList<PermissionItem>();
		permissionItems.add(new PermissionItem(Manifest.permission.READ_PHONE_STATE, "PhoneSTATE", ResLoader.getId(context, "permission_ic_phone")));
		permissionItems.add(new PermissionItem(Manifest.permission.WRITE_EXTERNAL_STORAGE,"读写权限",ResLoader.getId(context,"permission_ic_storage")));
		HiPermission.create(SplashActivity.this)
				.permissions(permissionItems)
				.style(R.style.PermissionDefaultGreenStyle)
				.checkMutiPermission(new PermissionCallback() {
					@Override
					public void onClose() {
						//关闭
						LogUtils.e("权限申请关闭");
					}

					@Override
					public void onFinish() {
						LogUtils.e("权限申请完成");
						requestWindowFeature(Window.FEATURE_NO_TITLE);
						getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
						initListener = HWControl.getInstance().getInitListener();
						presenter = new InitPresenter();
						presenter.init(view);//开始初始化
						new Handler().postDelayed(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								finish();
							}
						}, 2000);
					}

					@Override
					public void onDeny(String permission, int position) {
						LogUtils.e("权限申请拒绝");
					}

					@Override
					public void onGuarantee(String permission, int position) {
						LogUtils.e("权限申请获取");
						initListener = HWControl.getInstance().getInitListener();
						presenter = new InitPresenter();
						presenter.init(view);//开始初始化
						new Handler().postDelayed(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								finish();
							}
						}, 2000);
					}
				});

		/*if (EffortlessPermissions.hasPermissions(this, defaultPermission)) {
			// We've got the permission.
			saveFileWithPermission();
			LogUtils.e("hasPermissions");
		} else if (EffortlessPermissions.somePermissionPermanentlyDenied(this,
				defaultPermission)) {
			// Some permission is permanently denied so we cannot request them normally.
			OpenAppDetailsDialogFragment.show(
					R.string.save_file_permission_permanently_denied_message,
					R.string.open_settings, this);
			LogUtils.e("somePermissionPermanentlyDenied");
		} else  {
			// Request the permissions.
			EffortlessPermissions.requestPermissions(this,
					R.string.save_file_permission_request_message,
					REQUEST_CODE_SAVE_FILE_PERMISSION, defaultPermission);
			LogUtils.e("requestPermissions");
		}*/

	}

	@SuppressLint("NewApi") private void requestPermission(){

		boolean isAllGranted = checkPermissionAllGranted(defaultPermission);

		if(isAllGranted){

			if(initListener==null){
				initListener = HWControl.getInstance().getInitListener();
			}
			initListener.success();//返回初始化成功

		}else{
			requestPermissions(defaultPermission, MY_PERMISSION_REQUEST_CODE);
		}

	}

	/**
	 * 检查是否拥有指定的所有权限
	 */
	@SuppressLint("NewApi") private boolean checkPermissionAllGranted(String[] permissions) {
		for (String permission : permissions) {
			if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
				// 只要有一个权限没有被授予, 则直接返回 false
				return false;
			}
		}
		return true;
	}

	/**
	 * 根据接口返回处理
	 */
	@Override
	public void getOrientationResult(int code, int orientation) {
		// TODO Auto-generated method stub
		setContentView(ResLoader.getLayout(context, "activity_splash"));
//		ResLoader.getLayout(context, "activity_splash");
		iv_logo = (ImageView) findViewById(ResLoader.getId(context, "iv_logo"));
		LogUtil.e("code---->"+code);
		LogUtil.e("orientation----->"+orientation);
		if(code == 0){
			//成功配置相关
			setRequestedOrientation(orientation);
			switch (orientation) {
				case -1:
					//未指定，此為默認值，由Android系統自己選擇適當的方向
					iv_logo.setBackground((ResLoader.getDrawable(context, "fg_logo_portrait")));
					break;
				case 0:
					//横屏显示
					iv_logo.setBackground((ResLoader.getDrawable(context, "fg_logo_landscape")));
					break;
				case 1:
					//竖屏
					iv_logo.setBackground((ResLoader.getDrawable(context, "fg_logo_portrait")));
					break;
				case 6:
					//横屏显示可翻转
					iv_logo.setBackground((ResLoader.getDrawable(context, "fg_logo_landscape")));
					break;
				case 7:
					//竖屏显示可翻转
					iv_logo.setBackground((ResLoader.getDrawable(context, "fg_logo_portrait")));
					break;
				default:
					break;
			}
			if(isactivation){
				if (Build.VERSION.SDK_INT > 22) {

					requestPermission();
				}else{
					if(initListener == null){
						initListener = HWControl.getInstance().getInitListener();
					}

					initListener.success();
				}
			}
		}else{
			if(initListener == null){
				initListener = HWControl.getInstance().getInitListener();
			}
			initListener.fail("");
			//横屏显示可翻转
			iv_logo.setBackgroundResource(R.drawable.fg_logo_portrait);
		}

	}

	/**
	 * 动态申请权限回调
	 */
	@TargetApi(23) @Override
	public void onRequestPermissionsResult(int requestCode,
										   String[] permissions, int[] grantResults) {
		// TODO Auto-generated method stub
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);

		if (requestCode == MY_PERMISSION_REQUEST_CODE) {
			boolean isAllGranted = true;

			// 判断是否所有的权限都已经授予了
			for (int grant : grantResults) {
				if (grant != PackageManager.PERMISSION_GRANTED) {
					isAllGranted = false;
					break;
				}
			}

			if (isAllGranted) {
				// 如果所有的权限都授予了, 则执行备份代码
				Log.e("Com", "初始化成功回调");
				if(initListener == null){
					initListener = HWControl.getInstance().getInitListener();
				}
				initListener.success();//返回成功
			} else {
				// 弹出对话框告诉用户需要权限的原因, 并引导用户去应用权限管理中手动打开权限按钮
				Log.e("Com", "初始化失败回调");
				if(initListener == null){
					initListener = HWControl.getInstance().getInitListener();
				}
				initListener.fail("权限获取失败");//返回失败
//                openAppDetails();
			}
		}
	}


	/**
	 * 打开 APP 的详情设置
	 */
	private void openAppDetails() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("备份通讯录需要访问 “通讯录” 和 “外部存储器”，请到 “应用信息 -> 权限” 中授予！");
		builder.setPositiveButton("去手动授权", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent();
				intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
				intent.addCategory(Intent.CATEGORY_DEFAULT);
				intent.setData(Uri.parse("package:" + getPackageName()));
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
				intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
				startActivity(intent);
			}
		});
		builder.setNegativeButton("取消", null);
		builder.show();
	}

	@Override
	public void fail(String result) {
		// TODO Auto-generated method stub
		LogUtil.e("激活失败--->"+result);
		if(result.equals("请勿重复激活")){

			HWConfigSharedPreferences.getInstance(context).setActivation(true);
			if(initListener == null){
				initListener = HWControl.getInstance().getInitListener();
			}
			initListener.success();
		}else{
			if(initListener == null){
				initListener = HWControl.getInstance().getInitListener();
			}
			initListener.fail(result);//返回失败
		}
	}

	@Override
	public void activationSuccess() {
		// TODO Auto-generated method stub
		HWConfigSharedPreferences.getInstance(context).setActivation(true);
		if (Build.VERSION.SDK_INT > 22) {

			requestPermission();
		}else{
			if(initListener == null){
				initListener = HWControl.getInstance().getInitListener();
			}
			initListener.success();
		}
	}

}
