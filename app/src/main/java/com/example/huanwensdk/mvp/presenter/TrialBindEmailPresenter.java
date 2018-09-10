package com.example.huanwensdk.mvp.presenter;

import com.example.huanwensdk.mvp.contract.TrialBindEmailContract;
import com.example.huanwensdk.mvp.model.TrialBindEmailModel;


public class TrialBindEmailPresenter implements TrialBindEmailContract.TrialBindEmailPresenter{

	TrialBindEmailContract.TrialBindEmailModle model;
	
	@Override
	public void trialBindingEmail(String uid, String randpwd, String pwd,
			String email, TrialBindEmailContract.TrialBIndEmailView trialBIndEmailView) {
		// TODO Auto-generated method stub
		
		if(model == null){
			model = new TrialBindEmailModel();
		}
		model.trialBindingEmail(uid, randpwd, pwd, email, trialBIndEmailView);
	}

}
