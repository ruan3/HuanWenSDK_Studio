package com.example.huanwensdk.mvp.presenter;

import com.example.huanwensdk.mvp.contract.InitLoginTrial;
import com.example.huanwensdk.mvp.model.LoginTrialModel;

public class LoginTrialPresenter implements InitLoginTrial.LoginTrialPresenter{

	InitLoginTrial.LoginTrialModel model;
	
	@Override
	public void loginTrial(InitLoginTrial.LoginTrialView loginTrialView) {
		// TODO Auto-generated method stub
		
		if(model == null){
			model = new LoginTrialModel();
		}
		model.loginTrial(loginTrialView);
	}

}
