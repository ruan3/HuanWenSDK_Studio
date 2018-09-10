package com.example.huanwensdk.ui.dialog;

import android.app.Dialog;
import android.widget.TextView;

import com.example.huanwensdk.R;
import com.example.huanwensdk.base.BaseDialog;
import com.example.huanwensdk.bean.user.HWInfoUser;
import com.example.huanwensdk.utils.HWControl;
import com.example.huanwensdk.utils.HWUtils;
import com.example.huanwensdk.utils.LogUtils;
import com.example.huanwensdk.utils.ResLoader;

/**
 * 
 * @Title: GuestFindDialog.java
 * @Package ui.dialog
 * @Description: 保存游客登录截图
 * @author: Android_ruan
 * @date: 2018-3-28 上午10:45:30
 * @version V1.0
 */
public class GuestFindDialog {

	BaseDialog dialog;
	TextView tv_game_name;
	TextView tv_uid;
	TextView tv_reply_code;

	private GuestFindDialog() {

		initView();
		LogUtils.e("执行GuestFindDialog初始化方法");

	}

	private static class GuestFindDialogHepler {

		private static GuestFindDialog guestDialog = new GuestFindDialog();

	}

	public static GuestFindDialog getInstance() {

		return GuestFindDialogHepler.guestDialog;

	}
	
	public Dialog getDialog(){
		return this.dialog;
	}

	public void initView() {

		// 设置SDK语言
		HWUtils.setLanguage(HWControl.getInstance().getContext(), HWControl
				.getInstance().getContext().getResources());

		dialog = new BaseDialog(HWControl.getInstance().getContext(),
				R.style.dialog);
		dialog.setContentView(R.layout.dialog_save_login_trial_02);

		tv_game_name = (TextView) dialog.findViewById(R.id.tv_game_name);
		tv_reply_code = (TextView) dialog.findViewById(R.id.tv_reply_code);
		tv_uid = (TextView) dialog.findViewById(R.id.tv_uid);

	}

	public void initData(HWInfoUser user) {

		tv_game_name.setText(ResLoader.getString(HWControl.getInstance()
				.getContext(), "game_name"));
		tv_reply_code.setText(user.getShowId());
		tv_uid.setText(user.getUserid());
		if(dialog.isShowing()){
			
			HWUtils.getScreen();
		}

	}

	public void show() {
		LogUtils.e("执行GuestFindDialog---show()方法");
		if (dialog.isShowing()) {

			dialog.dismiss();

		}
		dialog.show();

	}

}
