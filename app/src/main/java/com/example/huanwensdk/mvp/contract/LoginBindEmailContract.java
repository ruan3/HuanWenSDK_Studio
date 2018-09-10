package com.example.huanwensdk.mvp.contract;


public interface LoginBindEmailContract {
	interface LoginBindEmailPresenter{
		void loginBindingEmail(String pwd,String email,LoginBIndEmailView trialBIndEmailView);
	}
	
	interface LoginBindEmailModle{
		void loginBindingEmail(String pwd,String email,LoginBIndEmailView trialBIndEmailView);
	}
	
	interface LoginBIndEmailView{
		
		void loginBindEmailSuccess(String msg);
		void loginBindEmailFail(String msg);
		
	}
}
