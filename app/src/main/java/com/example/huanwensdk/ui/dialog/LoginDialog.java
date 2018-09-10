package com.example.huanwensdk.ui.dialog;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.example.huanwensdk.utils.HWControl;
import com.example.huanwensdk.utils.LogUtils;
import com.example.huanwensdk.utils.ResLoader;
import com.example.huanwensdk.utils.sp.HWConfigSharedPreferences;

/**
 * 
 * @Title: LoginDialog.java
 * @Package ui.dialog
 * @Description: 登录对话框
 * @author: Android_ruan
 * @date: 2018-3-23 上午11:26:47
 * @version V1.0
 */
public class LoginDialog extends DialogBase{

	TextView tv_private_policy;//隐私政策
	TextView tv_login;// 登录按钮
	TextView tv_register;// 注册按钮
	TextView tv_try_play;//试玩账号
	Context context;
	boolean isShowAuthorLogin ;//判断是否显示第三方登录
	
	private LoginDialog() {
		// TODO Auto-generated constructor stub
		initView();
	}
	
	private static class LoginDialogHepler{
		
		private static LoginDialog loginDialog = new LoginDialog();
		
	}
	
	public static LoginDialog getInstance(){
		
		return LoginDialogHepler.loginDialog;
		
	}

	/**
	 * 初始化view
	 */
	public void initView() {
//		super.initView(R.layout.dialog_login);
		context = HWControl.getInstance().getContext();
		super.initView(ResLoader.getLayout(context, "dialog_login"));
		
//		tv_private_policy = (TextView) dialog.findViewById(R.id.tv_private_policy);
		tv_private_policy = (TextView) dialog.findViewById(ResLoader.getId(context, "tv_private_policy"));
		
		LogUtils.e("tv_private_policy---->"+tv_private_policy);
		tv_private_policy.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); // 下划线
//		tv_login = (TextView) dialog.findViewById(R.id.tv_login);
		tv_login = (TextView) dialog.findViewById(ResLoader.getId(context, "tv_login"));
		
//		tv_register = (TextView) dialog.findViewById(R.id.tv_register);
		tv_register = (TextView) dialog.findViewById(ResLoader.getId(context, "tv_register"));
		
//		tv_try_play = (TextView) dialog.findViewById(R.id.tv_try_play);
		tv_try_play = (TextView) dialog.findViewById(ResLoader.getId(context, "tv_try_play"));
		
		dialog.setCanceledOnTouchOutside(false);
		dialog.setCancelable(false);
		
		//显示隐私政策
		tv_private_policy.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				ProtocolDialog.getInstance().show();
				ThirdPartyLogin.getInstance().show();
			}
		});

		isShowAuthorLogin = HWConfigSharedPreferences.getInstance(context).isShowAuthorLogin();
		LogUtils.e("isShowAuthorLogin--->"+isShowAuthorLogin);
		if(isShowAuthorLogin){
			//获取在激活接口里的是否现在第三方登录状态
			tv_private_policy.setVisibility(View.VISIBLE);
		}else{
			tv_private_policy.setVisibility(View.GONE);
		}
		
		//试玩登录
		tv_try_play.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				LoginTrialDialog.getInstance().show();
			}
		});
		
		//注册
		tv_register.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				RegisterDialog.getInstance().show();
			}
		});
		
		//登录
		tv_login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MemberLoginDialog.getInstance().show();
			}
		});
		
	}
	
	/**
	 * 显示view
	 */
	public void show(){
		
		if(dialog.isShowing()){
			dialog.dismiss();
		}
		isShowAuthorLogin = HWConfigSharedPreferences.getInstance(context).isShowAuthorLogin();
		LogUtils.e("isShowAuthorLogin--->"+isShowAuthorLogin);
		if(isShowAuthorLogin){
			//获取在激活接口里的是否现在第三方登录状态
			tv_private_policy.setVisibility(View.VISIBLE);
		}else{
			tv_private_policy.setVisibility(View.VISIBLE);
		}
		dialog.show();
		
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		if(dialog.isShowing()){
			dialog.dismiss();
		}
	}

}
