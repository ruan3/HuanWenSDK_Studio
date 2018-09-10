package com.example.huanwensdk.ui.dialog;

import android.content.Context;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.huanwensdk.mvp.contract.LoginBindEmailContract;
import com.example.huanwensdk.mvp.presenter.LoginBindPresenter;
import com.example.huanwensdk.utils.HWControl;
import com.example.huanwensdk.utils.LogUtils;
import com.example.huanwensdk.utils.ResLoader;
/**
 * 登录邮箱绑定
 * @author Administrator
 *
 */
public class LoginBindEmailDialog extends DialogBase implements LoginBindEmailContract.LoginBIndEmailView{


	EditText et_user;
	EditText et_psd;
	EditText et_comfirm_psd;
	Button btn_register;
	Button btn_back;
	ImageButton btn_register_clear;
	ImageButton btn_comfirm_clear;

	String user;
	String psd;
	String pwdConfrim;

	String showid;
	String RandPwd;

	boolean isRegisterPress = true;
	boolean isComfirmPress = true;

	LoginBindEmailContract.LoginBindEmailPresenter presenter;
	LoginBindEmailContract.LoginBIndEmailView trialBIndEmailView;
	
	Context context;
	
	LinearLayout loading;
	
	private LoginBindEmailDialog() {
//		initView(R.layout.dialog_trial_binding_email);
		initView(ResLoader.getLayout(context, "dialog_trial_binding_email"));
	}

	private static class TrialBindingEmailDialogHepler {

		private static LoginBindEmailDialog trialBindingEmailDialog = new LoginBindEmailDialog();

	}

	public static LoginBindEmailDialog getInstance() {
		return TrialBindingEmailDialogHepler.trialBindingEmailDialog;
	}

	@Override
	public void initView(int ContentId) {
		// TODO Auto-generated method stub
		super.initView(ContentId);
		
		context = HWControl.getInstance().getContext();
		presenter = new LoginBindPresenter();
		trialBIndEmailView = this;
		
//		et_user = (EditText) dialog.findViewById(R.id.et_user);// 用户名，邮箱名
		et_user = (EditText) dialog.findViewById(ResLoader.getId(context, "et_user"));// 用户名，邮箱名
		
//		et_psd = (EditText) dialog.findViewById(R.id.et_psd);// 密码
		et_psd = (EditText) dialog.findViewById(ResLoader.getId(context, "et_psd"));// 密码
		
//		et_comfirm_psd = (EditText) dialog.findViewById(R.id.et_comfirm_psd);// 确认密码
		et_comfirm_psd = (EditText) dialog.findViewById(ResLoader.getId(context, "et_comfirm_psd"));// 确认密码
		
//		btn_register_clear = (ImageButton) dialog
//				.findViewById(R.id.btn_register_clear);// 显示密码
		btn_register_clear = (ImageButton) dialog
				.findViewById(ResLoader.getId(context, "btn_register_clear"));// 显示密码
		
//		btn_comfirm_clear = (ImageButton) dialog
//				.findViewById(R.id.btn_comfirm_clear);// 显示确认密码
		btn_comfirm_clear = (ImageButton) dialog
				.findViewById(ResLoader.getId(context, "btn_comfirm_clear"));// 显示确认密码
		
//		btn_back = (Button) dialog.findViewById(R.id.btn_back);// 关闭对话框
		btn_back = (Button) dialog.findViewById(ResLoader.getId(context, "btn_back"));// 关闭对话框
		
//		btn_register = (Button) dialog.findViewById(R.id.btn_bind);
		btn_register = (Button) dialog.findViewById(ResLoader.getId(context, "btn_bind"));
		
//		loading = (LinearLayout) dialog.findViewById(R.id.loading);
		loading = (LinearLayout) dialog.findViewById(ResLoader.getId(context, "loading"));

		btn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				close();
			}
		});
		// 注册
		btn_register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (isEmpty()) {
					// 如果为空，提示输入字段
					
					Toast.makeText(context, ResLoader.getString(context, "string_register_no_same"), Toast.LENGTH_LONG).show();
					
				} else {
					if (psd.equals(pwdConfrim)) {
						// 开始走绑定流程
						LogUtils.e("走没走");
						presenter.loginBindingEmail(psd, user, trialBIndEmailView);
						loading.setVisibility(View.VISIBLE);
						
					}else{
						//提示密码和确认密码不一样
						Toast.makeText(context, ResLoader.getString(context, "string_register_no_same"), Toast.LENGTH_LONG).show();
					}

				}

			}
		});

		// 再次输入密码
		btn_comfirm_clear.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				pwdConfrim = et_comfirm_psd.getText().toString();
				if (isComfirmPress) {// 根据点击选择可看密码显示或隐藏
					et_comfirm_psd
							.setInputType(android.text.InputType.TYPE_CLASS_TEXT
									| android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
					et_comfirm_psd.setText(pwdConfrim);
//					btn_comfirm_clear.setImageResource(R.drawable.fg_clear_n);
					btn_comfirm_clear.setImageDrawable((ResLoader.getDrawable(context, "fg_clear_n")));
					isComfirmPress = false;
				} else {
					et_comfirm_psd
							.setInputType(android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
					et_comfirm_psd.setText(pwdConfrim);
					isComfirmPress = true;
//					btn_comfirm_clear.setImageResource(R.drawable.fg_clear_p);
					btn_comfirm_clear.setImageDrawable((ResLoader.getDrawable(context, "fg_clear_p")));
				}
				// 下面两行代码实现: 输入框光标一直在输入文本后面
				Editable etable = et_comfirm_psd.getText();
				Selection.setSelection(etable, etable.length());
			}
		});
		// 输入密码
		btn_register_clear.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				psd = et_psd.getText().toString();

				if (isRegisterPress) {// 根据点击选择可看密码显示或隐藏
					et_psd.setInputType(android.text.InputType.TYPE_CLASS_TEXT
							| android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
					et_psd.setText(psd);
//					btn_register_clear.setImageResource(R.drawable.fg_clear_n);
					btn_register_clear.setImageDrawable((ResLoader.getDrawable(context, "fg_clear_n")));
					isRegisterPress = false;
				} else {
					et_psd.setInputType(android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
					et_psd.setText(psd);
//					btn_register_clear.setImageResource(R.drawable.fg_clear_p);
					btn_register_clear.setImageDrawable((ResLoader.getDrawable(context, "fg_clear_p")));
					isRegisterPress = true;
				}
				Editable etable = et_psd.getText();
				Selection.setSelection(etable, etable.length());
			}
		});

		dialog.setCancelable(false);

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

		if (dialog.isShowing()) {
			dialog.dismiss();
		}
		dialog.show();
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

		if (dialog.isShowing()) {
			dialog.dismiss();
		}

	}

	private boolean isEmpty() {

		user = et_user.getText().toString();
		psd = et_psd.getText().toString();
		pwdConfrim = et_comfirm_psd.getText().toString();

		if (TextUtils.isEmpty(user) || TextUtils.isEmpty(psd)
				|| TextUtils.isEmpty(pwdConfrim)) {
			return true;
		}

		return false;

	}
	
	@Override
	public void loginBindEmailSuccess(String msg) {
		// TODO Auto-generated method stub
		loading.setVisibility(View.GONE);
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void loginBindEmailFail(String msg) {
		// TODO Auto-generated method stub
		loading.setVisibility(View.GONE);
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}
	
}
