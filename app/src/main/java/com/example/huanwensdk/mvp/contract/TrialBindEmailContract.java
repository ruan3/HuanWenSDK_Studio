package com.example.huanwensdk.mvp.contract;

public interface TrialBindEmailContract {

	interface TrialBindEmailPresenter{
		void trialBindingEmail(String uid,String randpwd,String pwd,String email,TrialBIndEmailView trialBIndEmailView);
	}
	
	interface TrialBindEmailModle{
		void trialBindingEmail(String uid,String randpwd,String pwd,String email,TrialBIndEmailView trialBIndEmailView);
	}
	
	interface TrialBIndEmailView{
		
		void trialBindEmailSuccess(String msg);
		void trialBindEmailFail(String msg);
		
	}
	
}
