package com.example.huanwensdk.mvp.contract;

import com.example.huanwensdk.bean.user.HWInfoUser;

public interface ThirdPartyLoginContract {

	interface Presenter{
		
		void wechatLogin(ThirdLoginView loginView);
		void qqLogin();
		void facebookLogin();
		void getWechatToken(String code,InitLoginTrial.LoginTrialView loginView);
	}
	
	interface Model{
		
		void wechatLogin();
		void qqLogin();
		void facebookLogin();
		void getWechatToken(String code,InitLoginTrial.LoginTrialView loginView);
		
	}
	
	interface ThirdLoginView{
		
		void ThirdLoginResult(String result,HWInfoUser infoUser);
		
	}
	
}
