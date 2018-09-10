package com.example.huanwensdk.mvp.contract;

public interface MemberLoginContract {

	interface MemberLoginPresenter{
		void login(String user ,String pwd,MemberLoginView loginView);
	}
	
	interface MemberLoginModel{
		void login(String user,String pwd,MemberLoginView loginView);
		
	}
	
	interface MemberLoginView{
		
		void loginSuccess();
		void loginFail();
		
	}
	
}
