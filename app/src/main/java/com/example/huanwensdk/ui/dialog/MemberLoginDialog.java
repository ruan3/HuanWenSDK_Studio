package com.example.huanwensdk.ui.dialog;

import android.content.Context;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huanwensdk.mvp.contract.MemberLoginContract;
import com.example.huanwensdk.mvp.contract.listener.LoginListener;
import com.example.huanwensdk.mvp.presenter.MemberLoginPresenter;
import com.example.huanwensdk.utils.HWControl;
import com.example.huanwensdk.utils.LogUtils;
import com.example.huanwensdk.utils.ResLoader;
import com.example.huanwensdk.utils.Validator;

/**
 * 
 * @Title:  MemberLoginDialog.java   
 * @Package com.example.huanwensdk.ui.dialog   
 * @Description:    会员登录功能   
 * @author: Android_ruan     
 * @date:   2018-5-29 上午9:54:18   
 * @version V1.0
 */
public class MemberLoginDialog extends DialogBase implements MemberLoginContract.MemberLoginView{

	EditText et_user;
	EditText et_psd;
	ImageButton btn_clear;
	
	CheckBox cb_allow_policy;
	
	Button btn_login;
	TextView tv_forget_psd;
	TextView tv_find_back;
	Button btn_close;
	
	private String user;
	private String pwd;
	private boolean isAllowPolicy = false;
	
	LoginListener loginListener;
	Context context;
	
	boolean isPress = true;
	
	MemberLoginContract.MemberLoginPresenter presenter;
	MemberLoginContract.MemberLoginView loginView;
	
	LinearLayout loading;
	
	private MemberLoginDialog(){
		
		initView();
		
	}
	
	private static class MemberLoginDialogHepler{
		
		private static MemberLoginDialog memberLoginDialog = new MemberLoginDialog();
		
	}
	
	public static MemberLoginDialog getInstance(){
		return MemberLoginDialogHepler.memberLoginDialog;
	}
	
	public void initView(){
//		super.initView(R.layout.dialog_login_input);
		super.initView(ResLoader.getLayout(context, "dialog_login_input"));
		
		loginListener = HWControl.getInstance().getLoginListener();//得到返回给SDK的listener
		context = HWControl.getInstance().getContext();
		presenter = new MemberLoginPresenter();
		loginView = this;
		
//		et_user = (EditText) dialog.findViewById(R.id.et_user);
		et_user = (EditText) dialog.findViewById(ResLoader.getId(context, "et_user"));
		
//		et_psd = (EditText) dialog.findViewById(R.id.et_psd);
		et_psd = (EditText) dialog.findViewById(ResLoader.getId(context, "et_psd"));
		
//		btn_clear = (ImageButton) dialog.findViewById(R.id.btn_clear);
		btn_clear = (ImageButton) dialog.findViewById(ResLoader.getId(context, "btn_clear"));
		
//		cb_allow_policy = (CheckBox) dialog.findViewById(R.id.cb_allow_policy);
		cb_allow_policy = (CheckBox) dialog.findViewById(ResLoader.getId(context, "cb_allow_policy"));
		
//		btn_login = (Button) dialog.findViewById(R.id.btn_login);
		btn_login = (Button) dialog.findViewById(ResLoader.getId(context, "btn_login"));
		
//		tv_forget_psd = (TextView) dialog.findViewById(R.id.tv_forget_psd);
		tv_forget_psd = (TextView) dialog.findViewById(ResLoader.getId(context, "tv_forget_psd"));
		
//		tv_find_back = (TextView) dialog.findViewById(R.id.tv_find_back);
		tv_find_back = (TextView) dialog.findViewById(ResLoader.getId(context, "tv_find_back"));
		
//		btn_close = (Button) dialog.findViewById(R.id.btn_close);
		btn_close = (Button) dialog.findViewById(ResLoader.getId(context, "btn_close"));
		
//		loading = (LinearLayout) dialog.findViewById(R.id.loading);
		loading = (LinearLayout) dialog.findViewById(ResLoader.getId(context, "loading"));
		
		//关闭创空
		btn_close.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				close();
			}
		});
//		//勾选服务条例
//		cb_allow_policy.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//			
//			@Override
//			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//				// TODO Auto-generated method stub
//				
//				isAllowPolicy = isChecked;
//				
//			}
//		});
		
		//显示密码
		btn_clear.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				pwd = et_psd.getText().toString();
				if (isPress) {// 根据点击选择可看密码显示或隐藏
					et_psd
							.setInputType(android.text.InputType.TYPE_CLASS_TEXT
									| android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
					et_psd.setText(pwd);
//					btn_clear.setImageResource(R.drawable.fg_clear_n);
					btn_clear.setImageDrawable(ResLoader.getDrawable(context, "fg_clear_n"));
					isPress = false;
				} else {
					et_psd
							.setInputType(android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
					et_psd.setText(pwd);
					isPress = true;
//					btn_clear.setImageResource(R.drawable.fg_clear_p);
					btn_clear.setImageDrawable(ResLoader.getDrawable(context, "fg_clear_p"));
				}
				// 下面两行代码实现: 输入框光标一直在输入文本后面
				Editable etable = et_psd.getText();
				Selection.setSelection(etable, etable.length());
			}
		});
		
		//登录
		btn_login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				LogUtils.e("isEmpty---->"+isEmpty());
				if(isEmpty()){
					//提示用户输入用户与密码
					loginListener.fail(401, ResLoader.getString(context, "string_login_empty"));
					Toast.makeText(context, ResLoader.getString(context, "string_login_empty"), Toast.LENGTH_SHORT).show();
				}else{
					
//					if(isAllowPolicy){
						//勾选同意服务条款后
					if(!Validator.isEmail(user)){
						Toast.makeText(context, "请输入正确邮箱", Toast.LENGTH_SHORT).show();
					}else{
						presenter.login(user, pwd, loginView);
						loading.setVisibility(View.VISIBLE);
					}
//					}else{
//						//提示勾选服务条款
//						loginListener.fail(401, ResLoader.getString(context, "string_login_allow"));
//					}
					
				}
				
			}
		});
		
		//忘记密码
		tv_forget_psd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ForgetPasswordDialog.getInstance().show();
			}
		});
		
		//试玩账号找回
		tv_find_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				RetrieveGuestAccountDialog.getInstance().show();
				
			}
		});
		
	}
	
	@Override
	public void initView(int ContentId) {
		// TODO Auto-generated method stub
//		super.initView(R.layout.dialog_login_input);
		super.initView(ResLoader.getLayout(context, "dialog_login_input"));
		
		loginListener = HWControl.getInstance().getLoginListener();//得到返回给SDK的listener
		context = HWControl.getInstance().getContext();
		presenter = new MemberLoginPresenter();
		loginView = this;
		
//		et_user = (EditText) dialog.findViewById(R.id.et_user);
		et_user = (EditText) dialog.findViewById(ResLoader.getId(context, "et_user"));
		
//		et_psd = (EditText) dialog.findViewById(R.id.et_psd);
		et_psd = (EditText) dialog.findViewById(ResLoader.getId(context, "et_psd"));
		
//		btn_clear = (ImageButton) dialog.findViewById(R.id.btn_clear);
		btn_clear = (ImageButton) dialog.findViewById(ResLoader.getId(context, "btn_clear"));
		
//		cb_allow_policy = (CheckBox) dialog.findViewById(R.id.cb_allow_policy);
		cb_allow_policy = (CheckBox) dialog.findViewById(ResLoader.getId(context, "cb_allow_policy"));
		
//		btn_login = (Button) dialog.findViewById(R.id.btn_login);
		btn_login = (Button) dialog.findViewById(ResLoader.getId(context, "btn_login"));
		
//		tv_forget_psd = (TextView) dialog.findViewById(R.id.tv_forget_psd);
		tv_forget_psd = (TextView) dialog.findViewById(ResLoader.getId(context, "tv_forget_psd"));
		
//		tv_find_back = (TextView) dialog.findViewById(R.id.tv_find_back);
		tv_find_back = (TextView) dialog.findViewById(ResLoader.getId(context, "tv_find_back"));
		
//		btn_close = (Button) dialog.findViewById(R.id.btn_close);
		btn_close = (Button) dialog.findViewById(ResLoader.getId(context, "btn_close"));
		
		//关闭创空
		btn_close.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				close();
			}
		});
		//勾选服务条例
		cb_allow_policy.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				
				isAllowPolicy = isChecked;
				
			}
		});
		
		//显示密码
		btn_clear.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				pwd = et_psd.getText().toString();
				if (isPress) {// 根据点击选择可看密码显示或隐藏
					et_psd
							.setInputType(android.text.InputType.TYPE_CLASS_TEXT
									| android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
					et_psd.setText(pwd);
//					btn_clear.setImageResource(R.drawable.fg_clear_n);
					btn_clear.setImageDrawable(ResLoader.getDrawable(context, "fg_clear_n"));
					isPress = false;
				} else {
					et_psd
							.setInputType(android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
					et_psd.setText(pwd);
					isPress = true;
//					btn_clear.setImageResource(R.drawable.fg_clear_p);
					btn_clear.setImageDrawable(ResLoader.getDrawable(context, "fg_clear_p"));
				}
				// 下面两行代码实现: 输入框光标一直在输入文本后面
				Editable etable = et_psd.getText();
				Selection.setSelection(etable, etable.length());
			}
		});
		
//		//登录
//		btn_login.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				LogUtils.e("isEmpty---->"+isEmpty());
//				if(isEmpty()){
//					//提示用户输入用户与密码
//					loginListener.fail(401, ResLoader.getString(context, "string_login_empty"));
//				}else{
//					
//					if(isAllowPolicy){
//						//勾选同意服务条款后
//						presenter.login(user, pwd, loginView);
//					}else{
//						//提示勾选服务条款
//						loginListener.fail(401, ResLoader.getString(context, "string_login_allow"));
//					}
//					
//				}
//				
//			}
//		});
		
		//忘记密码
		tv_forget_psd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ForgetPasswordDialog.getInstance().show();
			}
		});
		
		//试玩账号找回
		tv_find_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				RetrieveGuestAccountDialog.getInstance().show();
				
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

	@Override
	public void close() {
		// TODO Auto-generated method stub
		if(dialog.isShowing()){
			dialog.dismiss();
		}
	}

	private boolean isEmpty(){
		
		user = et_user.getText().toString().trim();
		pwd = et_psd.getText().toString().trim();
		LogUtils.e("user--->"+user + "  "+"pwd---->"+pwd);
		if(TextUtils.isEmpty(user) || TextUtils.isEmpty(pwd)){
			return true;
		}
		return false;
		
	}

	@Override
	public void loginSuccess() {
		// TODO Auto-generated method stub
		loading.setVisibility(View.GONE);
		close();
	}

	@Override
	public void loginFail() {
		// TODO Auto-generated method stub
		LogUtils.e("登录错误");
		loading.setVisibility(View.GONE);
//		close();
	}
	
}
