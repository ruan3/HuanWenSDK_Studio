package com.example.huanwensdk.mvp.contract;

public interface FreeLoginContract {

	interface FreeLoginPresenter{

		void Login();
		
		void FreeLogin(String sessionId,String LoginType,String showName,String userId);
		
	}
	
	interface FreeLoginModel{
		void FreeLogin(String sessionId,String LoginType,String showName,String userId);
	}
	
	
}
