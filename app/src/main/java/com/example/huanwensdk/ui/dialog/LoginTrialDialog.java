package com.example.huanwensdk.ui.dialog;

import android.app.Dialog;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.huanwensdk.mvp.contract.InitLoginTrial;
import com.example.huanwensdk.mvp.presenter.LoginTrialPresenter;
import com.example.huanwensdk.utils.HWControl;
import com.example.huanwensdk.utils.ResLoader;

/**
 * 
 * @Title: LoginTrialDialog.java
 * @Package ui.dialog
 * @Description: 游客登录的提示
 * @author: Android_ruan
 * @date: 2018-3-26 下午2:48:55
 * @version V1.0
 */
public class LoginTrialDialog extends DialogBase implements InitLoginTrial.LoginTrialView{

	TextView tv_login_trial_tips;
	TextView tv_title;
	Button btn_close;

	Button btn_confirm;
	
	LinearLayout loading;
	
	InitLoginTrial.LoginTrialPresenter presenter;

	private LoginTrialDialog() {

		initView();

	}

	private static class LoginTrialDailogHelper {

		private static LoginTrialDialog loginTrialDialog = new LoginTrialDialog();

	}

	public static LoginTrialDialog getInstance() {
		return LoginTrialDailogHelper.loginTrialDialog;
	}

	private void initView() {
//		super.initView(R.layout.dialog_login_trial);
		super.initView(ResLoader.getLayout(context, "dialog_login_trial"));
		// 初始化该有的东西
//		tv_title = (TextView) dialog.findViewById(R.id.tv_title);
		tv_title = (TextView) dialog.findViewById(ResLoader.getId(context, "tv_title"));
		
//		btn_close = (Button) dialog.findViewById(R.id.btn_close);
		btn_close = (Button) dialog.findViewById(ResLoader.getId(context, "btn_close"));
		
//		tv_login_trial_tips = (TextView) dialog
//				.findViewById(R.id.tv_login_trial_tips);
		tv_login_trial_tips = (TextView) dialog
				.findViewById(ResLoader.getId(context, "tv_login_trial_tips"));
		
//		btn_confirm = (Button) dialog.findViewById(R.id.btn_confirm);
		btn_confirm = (Button) dialog.findViewById(ResLoader.getId(context, "btn_confirm"));
		
//		loading = (LinearLayout) dialog.findViewById(R.id.loading);
		loading = (LinearLayout) dialog.findViewById(ResLoader.getId(context, "loading"));
		
		Log.e("Com",
				"测试标题---->"
						+ ResLoader.getString(HWControl.getInstance()
								.getContext(), "string_try_play"));
		tv_title.setText(ResLoader.getString(HWControl.getInstance()
				.getContext(), "hw_login_tour_dialog_title"));// 设置标题
		btn_confirm.setText(ResLoader.getString(HWControl.getInstance()
				.getContext(), "hw_confirm"));
		tv_login_trial_tips.setText(ResLoader.getString(HWControl.getInstance()
				.getContext(), "hw_login_tour_dialog_message"));// 提示将用游客登录
		btn_close.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (dialog.isShowing()) {
					dialog.dismiss();
				}

			}
		});
		
		btn_confirm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				initData();
			}
		});

		dialog.setCancelable(false);
		
	}
	
	void initData(){
		presenter = new LoginTrialPresenter();
		presenter.loginTrial(this);
		loading.setVisibility(View.VISIBLE);
		tv_login_trial_tips.setVisibility(View.GONE);
		
	}
	
	public Dialog getDialog(){
		
		return this.dialog;
		
	}
	
	public void show() {

		if (dialog.isShowing()) {
			dialog.dismiss();
		}
		dialog.show();

	}
	
	@Override
	public void callbackLogin() {
		// TODO Auto-generated method stub
		loading.setVisibility(View.GONE);
		tv_login_trial_tips.setVisibility(View.VISIBLE);
		//获取到返回接口，关闭对话框
		if(dialog.isShowing()){
			dialog.dismiss();
		}
		LoginDialog.getInstance().close();
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
		if(dialog.isShowing()){
			dialog.dismiss();
		}
		
	}

	

}
