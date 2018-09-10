package com.example.huanwensdk.ui.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huanwensdk.mvp.contract.ForgetPasswordContract;
import com.example.huanwensdk.mvp.contract.listener.LoginListener;
import com.example.huanwensdk.mvp.presenter.ForgetPasswordPresenter;
import com.example.huanwensdk.utils.HWControl;
import com.example.huanwensdk.utils.LogUtils;
import com.example.huanwensdk.utils.MyCountDownTimer;
import com.example.huanwensdk.utils.ResLoader;
import com.example.huanwensdk.utils.Validator;

public class ForgetPasswordDialog extends DialogBase implements ForgetPasswordContract.ForgetPassWordView{

	EditText et_email;
	EditText et_code;
	Button btn_code_get;
	Button btn_next;
	Button btn_close;
	TextView tv_title;
	
	Context context;
	
	ForgetPasswordContract.ForgetPasswordPresenter presenter;
	ForgetPasswordContract.ForgetPassWordView forgetView;
	
	String email;
	String code;
	
	LoginListener loginListener;
	
	MyCountDownTimer myCountDownTimer;
	
	LinearLayout loading;
	
	private ForgetPasswordDialog(){
		
//		initView(R.layout.dialog_forget_password);
		initView(ResLoader.getLayout(context, "dialog_forget_password"));

	}
	
	private static class ForgetPasswordDialogHepler{
		
		private static ForgetPasswordDialog forgetPasswordDialog = new ForgetPasswordDialog();
		
	}
	
	public static ForgetPasswordDialog getInstance(){
		
		return ForgetPasswordDialogHepler.forgetPasswordDialog;
		
	}
	
	@Override
	public void initView(int ContentId) {
		// TODO Auto-generated method stub
		super.initView(ContentId);
		
		context = HWControl.getInstance().getContext();
		presenter = new ForgetPasswordPresenter();
		forgetView = this;
		loginListener = HWControl.getInstance().getLoginListener();
		
//		et_email = (EditText) dialog.findViewById(R.id.et_email);
		et_email = (EditText) dialog.findViewById(ResLoader.getId(context, "et_email"));
		
//		et_code = (EditText) dialog.findViewById(R.id.et_code);
		et_code = (EditText) dialog.findViewById(ResLoader.getId(context, "et_code"));
		
//		btn_code_get = (Button) dialog.findViewById(R.id.btn_code_get);
		btn_code_get = (Button) dialog.findViewById(ResLoader.getId(context, "btn_code_get"));
		
//		btn_next = (Button) dialog.findViewById(R.id.btn_next);
		btn_next = (Button) dialog.findViewById(ResLoader.getId(context, "btn_next"));
		
//		btn_close = (Button) dialog.findViewById(R.id.btn_close);
		btn_close = (Button) dialog.findViewById(ResLoader.getId(context, "btn_close"));
		
//		tv_title = (TextView) dialog.findViewById(R.id.tv_title);
		tv_title = (TextView) dialog.findViewById(ResLoader.getId(context, "tv_title"));
		
//		loading = (LinearLayout) dialog.findViewById(R.id.loading);
		loading = (LinearLayout) dialog.findViewById(ResLoader.getId(context, "loading"));
		
		btn_close.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				close();
			}
		});
		myCountDownTimer = new MyCountDownTimer(60000,1000,btn_code_get);
		
		//获取验证码
		btn_code_get.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(isEmpty()){
					loginListener.fail(401, ResLoader.getString(context, "string_forget_email_isnull"));
					Toast.makeText(context, ResLoader.getString(context, "string_forget_email_isnull"), Toast.LENGTH_SHORT).show();
				}else{
					if(!Validator.isEmail(email)){
						Toast.makeText(context, "请输入正确邮箱", Toast.LENGTH_SHORT).show();
					}else{
						myCountDownTimer.start();
						presenter.sendEmail(email, forgetView);
					}
				}
				
			}
		});
		
		//进入下一步
		btn_next.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				if(isCodeEmailEmpty()){
					
					//提示输入验证码和邮箱
					Toast.makeText(context, ResLoader.getString(context, "string_forget_email_code_allnull"), Toast.LENGTH_LONG).show();
				}else{
					
					//开始走邮箱流程
					presenter.validEmail(email, code, forgetView);
					loading.setVisibility(View.VISIBLE);
					
				}
				
			}
		});
		
		
		tv_title.setText(ResLoader.getString(context, "string_forget"));
		
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
		if(dialog.isShowing()){
			dialog.dismiss();
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
	
	public boolean isCodeEmailEmpty(){
		
		email = et_email.getText().toString().trim();
		code = et_code.getText().toString().trim();
		
		if(TextUtils.isEmpty(email)||TextUtils.isEmpty(code)){
			return true;
		}
		return false;
		
	}
	
	//检查邮箱的空
	public boolean isEmpty(){
		email = et_email.getText().toString().trim();
		if(TextUtils.isEmpty(email)){
			return true;
		}
		return false;
	}

	@Override
	public void sendEmailResult(String msg,String sms) {
		// TODO Auto-generated method stub
		
		Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
		if(!TextUtils.isEmpty(sms)){
			
			et_code.setText(sms);
			code = sms;
			
		}
	}

	@Override
	public void vaildEmailSuccess(String msg) {
		// TODO Auto-generated method stub
		loading.setVisibility(View.GONE);
		ResetPasswordDialog.getInstance().show();
		LogUtils.e("email--->"+email+" code--->"+code);
		ResetPasswordDialog.getInstance().setCodeandEmail(email, code);
		
	}

	@Override
	public void vaildEmailFail(String msg) {
		// TODO Auto-generated method stub
		loading.setVisibility(View.GONE);
		Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
		
	}

}
