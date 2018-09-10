package com.example.huanwensdk.mvp.contract;

public interface ResetPasswordContract {

	interface ResetPasswordPresenter{
		
		void reset(String password,String email,String code,ResetPasswordView resetPasswordView);
		
	}
	
	interface ResetPasswordModel{
		void reset(String password,String email,String code,ResetPasswordView resetPasswordView);
	}
	
	interface ResetPasswordView{
		
		void resetSuccess(String msg);
		
		void resetFail(String msg);
		
	}
	
}
