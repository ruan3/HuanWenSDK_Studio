package com.example.huanwensdk.ui.dialog;

import java.util.Arrays;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huanwensdk.R;
import com.example.huanwensdk.bean.user.HWInfoUser;
import com.example.huanwensdk.mvp.contract.FacebookContract;
import com.example.huanwensdk.mvp.contract.InitLoginTrial;
import com.example.huanwensdk.mvp.contract.ThirdPartyLoginContract;
import com.example.huanwensdk.mvp.contract.listener.LoginListener;
import com.example.huanwensdk.mvp.contract.listener.WeChatLoginListener;
import com.example.huanwensdk.mvp.presenter.FacebookPresenter;
import com.example.huanwensdk.mvp.presenter.ThirdPartyLoginPresenter;
import com.example.huanwensdk.utils.HWControl;
import com.example.huanwensdk.utils.LogUtils;
import com.example.huanwensdk.utils.ResLoader;
import com.example.huanwensdk.utils.sp.HWConfigSharedPreferences;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

/**
 * 
 * @Title:  ThirdPartyLogin.java   
 * @Package com.example.huanwensdk.ui.dialog   
 * @Description:    第三方登录
 * @author: Android_ruan     
 * @date:   2018-5-15 下午4:05:53   
 * @version V1.0
 */
public class ThirdPartyLogin extends DialogBase implements ThirdPartyLoginContract.ThirdLoginView,WeChatLoginListener,InitLoginTrial.LoginTrialView,FacebookContract.FaceBookView{

	TextView tv_wechat_login;
	TextView tv_qq_login;
	LoginButton tv_facebook_login;
	
	ThirdPartyLoginContract.Presenter presenter;
	ThirdPartyLoginContract.ThirdLoginView loginView;
	
//	AccessTokenTracker accessTokenTracker;
    private CallbackManager mCallbackManager;
    
    private static final String EMAIL = "email";
    private static final String USER_POSTS = "user_posts";
    private static final String AUTH_TYPE = "rerequest";
    
    LoginListener loginListener;
    
    FacebookContract.FaceBookPresenter facePresenter;
    FacebookContract.FaceBookView faceBookView;
	
	private ThirdPartyLogin(){
//		initView(R.layout.dialog_third_party_login);
		initView(ResLoader.getLayout(context, "dialog_third_party_login"));
	}
	
	private static class ThirdPartyLoginHelper{
		
		private static ThirdPartyLogin thirdPartyLogin = new ThirdPartyLogin();
		
	}
	
	public static ThirdPartyLogin getInstance(){
		
		return ThirdPartyLoginHelper.thirdPartyLogin;
		
	}
	
	@Override
	public void initView(int ContentId) {
		// TODO Auto-generated method stub
		super.initView(ContentId);
		HWControl.getInstance().setWeChatLoginListener(this);
//		tv_wechat_login = (TextView) dialog.findViewById(R.id.tv_wechat_login);
		tv_wechat_login = (TextView) dialog.findViewById(ResLoader.getId(context, "tv_wechat_login"));
		
//		tv_qq_login = (TextView) dialog.findViewById(R.id.tv_qq_login);
		tv_qq_login = (TextView) dialog.findViewById(ResLoader.getId(context, "tv_qq_login"));
		
//		tv_facebook_login = (TextView) dialog.findViewById(R.id.tv_facebook_login);
		tv_facebook_login = (LoginButton) dialog.findViewById(ResLoader.getId(context, "tv_facebook_login"));
		
		initData();
	}
	
	private void initData(){
		presenter = new ThirdPartyLoginPresenter();
		facePresenter = new FacebookPresenter();
		faceBookView = this;
		loginView = this;
		tv_wechat_login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
					
				//进行微信登录流程
				presenter.wechatLogin(loginView);
			}
		});
		String country = HWConfigSharedPreferences.getInstance(context).getCountry();
		LogUtils.e("country---->"+country);
		if(country.equals("CN")){
			tv_qq_login.setVisibility(View.GONE);
			tv_wechat_login.setVisibility(View.GONE);
			tv_facebook_login.setVisibility(View.VISIBLE);
		}else if(country.equals("TW")){
			tv_qq_login.setVisibility(View.GONE);
			tv_wechat_login.setVisibility(View.GONE);
			tv_facebook_login.setVisibility(View.VISIBLE);
		}else{
			tv_qq_login.setVisibility(View.GONE);
			tv_wechat_login.setVisibility(View.GONE);
			tv_facebook_login.setVisibility(View.VISIBLE);
		}
		
		//facebook的回到函数
		mCallbackManager = CallbackManager.Factory.create();
		//设置对象，到界面上回调
		HWControl.getInstance().setmCallbackManager(mCallbackManager);
		
		// Set the initial permissions to request from the user while logging in
		tv_facebook_login.setReadPermissions(Arrays.asList(EMAIL));

		tv_facebook_login.setAuthType(AUTH_TYPE);
		
		// Callback registration
		tv_facebook_login.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                // App code
            	LogUtils.e("facebook登录成功--->"+loginResult.toString());
            	facePresenter.getFacebookUserInfo(faceBookView);
            }

            @Override
            public void onCancel() {
                // App code
            	LogUtils.e("facebook登录取消---->");
            }

            @Override
            public void onError(final FacebookException exception) {
                // App code
            	LogUtils.e("facebook登录失败---->"+exception.toString());
            	Toast.makeText(context, "FaceBook登錄錯誤", Toast.LENGTH_SHORT).show();
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

	@Override
	public void ThirdLoginResult(String result, HWInfoUser infoUser) {
		// TODO Auto-generated method stub
	}

	/**
	 * 微信第三方登录返回
	 */
	@Override
	public void LoginCallBack(int status, String code) {
		// TODO Auto-generated method stub
		if(status == 0){
			//用户授权成功，进行下一步获取token
			Toast.makeText(context, "用户授权成功", Toast.LENGTH_SHORT).show();
			presenter.getWechatToken(code,this);
		}else{
			Toast.makeText(context, "用户授权失败", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void callbackLogin() {
		// TODO Auto-generated method stub
		if(dialog.isShowing()){
			dialog.dismiss();
		}
		LoginDialog.getInstance().close();
	}

	
	@Override
	public void FacebookLoginResult(int code, String msg) {
		
		if(code == 0){
			LogUtils.e("facebook登錄完成");
			LoginDialog.getInstance().close();
			close();
		}else{
			LogUtils.e("facebook登錄失敗");
		}
		
	}
	
}
