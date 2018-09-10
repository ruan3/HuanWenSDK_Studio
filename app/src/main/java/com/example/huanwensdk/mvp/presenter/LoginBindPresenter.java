package com.example.huanwensdk.mvp.presenter;

import com.example.huanwensdk.mvp.contract.LoginBindEmailContract;
import com.example.huanwensdk.mvp.contract.LoginBindEmailContract.LoginBIndEmailView;
import com.example.huanwensdk.mvp.model.LoginBindEmailModel;

public class LoginBindPresenter implements LoginBindEmailContract.LoginBindEmailPresenter{

	LoginBindEmailContract.LoginBindEmailModle model;
	
	@Override
	public void loginBindingEmail(String pwd,
			String email, LoginBIndEmailView trialBIndEmailView) {
		// TODO Auto-generated method stub
		if(model==null){
			model = new LoginBindEmailModel();
		}
		
		model.loginBindingEmail(pwd, email, trialBIndEmailView);
	}

}
