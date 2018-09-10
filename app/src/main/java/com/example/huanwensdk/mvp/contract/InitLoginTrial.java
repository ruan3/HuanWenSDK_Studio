package com.example.huanwensdk.mvp.contract;

public interface InitLoginTrial {

	interface LoginTrialPresenter{
		
		void loginTrial(LoginTrialView loginTrialView);
		
	}
	
	interface LoginTrialModel{
		
		void loginTrial(LoginTrialView loginTrialView);
		
	}
	
	interface LoginTrialView{
		
		void callbackLogin();
		
	}
	
}
