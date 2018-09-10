package com.example.huanwensdk.mvp.presenter;

import com.example.huanwensdk.mvp.contract.ResetPasswordContract;
import com.example.huanwensdk.mvp.model.ResetPasswordModel;

public class ResetPasswordPresenter implements ResetPasswordContract.ResetPasswordPresenter{

	ResetPasswordContract.ResetPasswordModel resetPasswordModel;
	
	@Override
	public void reset(String password, String email, String code,
			ResetPasswordContract.ResetPasswordView resetPasswordView) {
		// TODO Auto-generated method stub
		
		if(resetPasswordModel == null){
			resetPasswordModel = new ResetPasswordModel();
		}
		
		resetPasswordModel.reset(password, email, code, resetPasswordView);
		
		
	}

}
