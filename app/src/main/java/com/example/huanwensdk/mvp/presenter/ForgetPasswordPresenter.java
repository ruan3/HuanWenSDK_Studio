package com.example.huanwensdk.mvp.presenter;

import com.example.huanwensdk.mvp.contract.ForgetPasswordContract;
import com.example.huanwensdk.mvp.model.ForgetPasswordModel;

public class ForgetPasswordPresenter implements ForgetPasswordContract.ForgetPasswordPresenter {

	ForgetPasswordContract.ForgetPasswordModel model;
	
	@Override
	public void sendEmail(String email,ForgetPasswordContract.ForgetPassWordView forgetView) {
		// TODO Auto-generated method stub
		
		if(model == null){
			model = new ForgetPasswordModel();
		}
		model.sendEmail(email,forgetView);
		
	}

	@Override
	public void validEmail(String email, String code,
			ForgetPasswordContract.ForgetPassWordView forgetView) {
		// TODO Auto-generated method stub
		if(model == null){
			model = new ForgetPasswordModel();
		}
		model.validEmail(email, code, forgetView);
	}

}
