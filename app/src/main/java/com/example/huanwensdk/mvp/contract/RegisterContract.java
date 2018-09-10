package com.example.huanwensdk.mvp.contract;

public interface RegisterContract {

	interface RegisterPresenter{
		
		void register(String user,String pwd,RegisterView reView);
		
	}
	
	interface RegisterModel{
		
		void register(String user,String pwd,RegisterView reView);
		
	}
	
	interface RegisterView{
		
		void callBackRegiterSucess();
		
		void callBackRegiterFail();
		
	}
	
}
