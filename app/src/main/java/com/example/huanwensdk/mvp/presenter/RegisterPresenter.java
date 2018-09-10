package com.example.huanwensdk.mvp.presenter;

import com.example.huanwensdk.mvp.contract.RegisterContract;
import com.example.huanwensdk.mvp.model.RegisterModel;

public class RegisterPresenter implements RegisterContract.RegisterPresenter{

	RegisterContract.RegisterModel reModel;
	
	@Override
	public void register(String user, String pwd, RegisterContract.RegisterView reView) {
		// TODO Auto-generated method stub
		
		if(reModel == null){
			reModel = new RegisterModel();
		}
		reModel.register(user, pwd, reView);
		
	}

}
