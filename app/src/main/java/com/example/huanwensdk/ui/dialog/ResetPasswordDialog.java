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

import com.example.huanwensdk.mvp.contract.ResetPasswordContract;
import com.example.huanwensdk.mvp.presenter.ResetPasswordPresenter;
import com.example.huanwensdk.utils.HWControl;
import com.example.huanwensdk.utils.LogUtils;
import com.example.huanwensdk.utils.ResLoader;

/**
 * 
 * @Title:  ResetPasswordDialog.java   
 * @Package ui.dialog   
 * @Description:    忘记密码里面的重置密码  
 * @author: Android_ruan     
 * @date:   2018-4-3 下午4:32:48   
 * @version V1.0
 */
public class ResetPasswordDialog extends DialogBase implements ResetPasswordContract.ResetPasswordView{

	EditText et_psd;
	ImageButton btn_register_clear;
	EditText et_comfirm_psd;
	ImageButton btn_comfirm_clear;
	Button btn_reset;
	Button btn_close;
	
	String pwd;
	String comfirm_pwd;
	String code;
	String email;
	
	Context context;
	
	ResetPasswordContract.ResetPasswordPresenter presenter;
	ResetPasswordContract.ResetPasswordView resetPasswordView;
	
	boolean isRegisterPress = true;
	boolean isComfirmPress = true;
	
	LinearLayout loading;
	
	private ResetPasswordDialog(){
	
//		initView(R.layout.dialog_change_password);
		initView(ResLoader.getLayout(context, "dialog_change_password"));
		
	}
	
	private static class ResetPasswordDialogHepler{
		
		private static ResetPasswordDialog resetPasswordDialog = new ResetPasswordDialog();
		
	}
	
	public static ResetPasswordDialog getInstance(){
		
		return ResetPasswordDialogHepler.resetPasswordDialog;
		
	}
	
	@Override
	public void initView(int ContentId) {
		// TODO Auto-generated method stub
		super.initView(ContentId);
		
		context = HWControl.getInstance().getContext();
		presenter = new ResetPasswordPresenter();
		resetPasswordView = this;
		
//		et_psd = (EditText) dialog.findViewById(R.id.et_psd);
		et_psd = (EditText) dialog.findViewById(ResLoader.getId(context, "et_psd"));
		
//		et_comfirm_psd = (EditText) dialog.findViewById(R.id.et_comfirm_psd);
		et_comfirm_psd = (EditText) dialog.findViewById(ResLoader.getId(context, "et_comfirm_psd"));
		
//		btn_comfirm_clear = (ImageButton) dialog.findViewById(R.id.btn_comfirm_clear);
		btn_comfirm_clear = (ImageButton) dialog.findViewById(ResLoader.getId(context, "btn_comfirm_clear"));
		
//		btn_register_clear = (ImageButton) dialog.findViewById(R.id.btn_register_clear);
		btn_register_clear = (ImageButton) dialog.findViewById(ResLoader.getId(context, "btn_register_clear"));
		
//		btn_reset = (Button) dialog.findViewById(R.id.btn_reset);
		btn_reset = (Button) dialog.findViewById(ResLoader.getId(context, "btn_reset"));
		
//		btn_close = (Button) dialog.findViewById(R.id.btn_back);
		btn_close = (Button) dialog.findViewById(ResLoader.getId(context, "btn_back"));
		
//		loading = (LinearLayout) dialog.findViewById(R.id.loading);
		loading = (LinearLayout) dialog.findViewById(ResLoader.getId(context, "loading"));
		
		btn_reset.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if(isEmpty()){
					
					//提示用户输入密码和确认密码
					Toast.makeText(context, ResLoader.getString(context, "string_forget_pwd_null"), Toast.LENGTH_LONG).show();
				}else{
					
					if(isSame()){
						LogUtils.e("pwd--->"+pwd+" email--->"+email+" code---->"+code);
						//开始走重设流程
						presenter.reset(pwd, email, code, resetPasswordView);
						loading.setVisibility(View.VISIBLE);
					}else{
						
						//提示两次密码输入不一样
						//提示用户输入密码和确认密码
						Toast.makeText(context, ResLoader.getString(context, "string_register_no_same"), Toast.LENGTH_LONG).show();
					}
					
				}
				
			}
		});
		//关闭对话框
		btn_close.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				close();
			}
		});
		
		// 再次输入密码
				btn_comfirm_clear.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						comfirm_pwd = et_comfirm_psd.getText().toString();
						if (isComfirmPress) {// 根据点击选择可看密码显示或隐藏
							et_comfirm_psd
									.setInputType(android.text.InputType.TYPE_CLASS_TEXT
											| android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
							et_comfirm_psd.setText(comfirm_pwd);
//							btn_comfirm_clear.setImageResource(R.drawable.fg_clear_n);
							btn_comfirm_clear.setImageDrawable(ResLoader.getDrawable(context, "fg_clear_n"));
							isComfirmPress = false;
						} else {
							et_comfirm_psd
									.setInputType(android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
							et_comfirm_psd.setText(comfirm_pwd);
							isComfirmPress = true;
//							btn_comfirm_clear.setImageResource(R.drawable.fg_clear_p);
							btn_comfirm_clear.setImageDrawable(ResLoader.getDrawable(context, "fg_clear_p"));
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
						pwd = et_psd.getText().toString();

						if (isRegisterPress) {// 根据点击选择可看密码显示或隐藏
							et_psd.setInputType(android.text.InputType.TYPE_CLASS_TEXT
									| android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
							et_psd.setText(pwd);
//							btn_register_clear.setImageResource(R.drawable.fg_clear_n);
							btn_register_clear.setImageDrawable(ResLoader.getDrawable(context, "fg_clear_n"));
							isRegisterPress = false;
						} else {
							et_psd.setInputType(android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
							et_psd.setText(pwd);
//							btn_register_clear.setImageResource(R.drawable.fg_clear_p);
							btn_register_clear.setImageDrawable(ResLoader.getDrawable(context, "fg_clear_p"));
							isRegisterPress = true;
						}
						Editable etable = et_psd.getText();
						Selection.setSelection(etable, etable.length());
					}
				});
		
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		if(dialog.isShowing()){
			dialog.dismiss();
		}
		dialog.show();
	}

	public boolean isEmpty(){
		
		pwd = et_psd.getText().toString().trim();
		comfirm_pwd = et_comfirm_psd.getText().toString().trim();
		
		if(TextUtils.isEmpty(pwd)||TextUtils.isEmpty(comfirm_pwd)){
			
			return true;
			
		}

			return false;
	}
	
	public boolean isSame(){
		
		pwd = et_psd.getText().toString().trim();
		comfirm_pwd = et_comfirm_psd.getText().toString().trim();
		
		if(pwd.equals(comfirm_pwd)){
			return true;
		}
		return false;
			
		
	}
	
	public void setCodeandEmail(String email,String code){
		
		this.code = code;
		this.email = email;
		
	}
	
	@Override
	public void close() {
		// TODO Auto-generated method stub
		if(dialog.isShowing()){
			dialog.dismiss();
		}
	}

	/**
	 * 重置密码成功
	 */
	@Override
	public void resetSuccess(String msg) {
		// TODO Auto-generated method stub
		loading.setVisibility(View.GONE);
		Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
		close();//关闭当前对话
		ForgetPasswordDialog.getInstance().close();//关闭获取验证码对话框
		MemberLoginDialog.getInstance().show();//打开登录对话框
	}

	/**
	 * 充值密码失败
	 */
	@Override
	public void resetFail(String msg) {
		// TODO Auto-generated method stub
		loading.setVisibility(View.GONE);
		Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
	}

}
