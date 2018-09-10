package com.example.huanwensdk.mvp.contract;

public interface ForgetPasswordContract {

	interface ForgetPasswordPresenter{
		
		void sendEmail(String email,ForgetPassWordView forgetView);
		
		void validEmail(String email,String code,ForgetPassWordView forgetView);
		
	}
	
	interface ForgetPasswordModel{
		
		void sendEmail(String email,ForgetPassWordView forgetView);
		void validEmail(String email,String code,ForgetPassWordView forgetView);
	}

	interface ForgetPassWordView{
		
		void sendEmailResult(String msg,String sms);
		
		void vaildEmailSuccess(String msg);
		
		void vaildEmailFail(String msg);
	
	}
}
