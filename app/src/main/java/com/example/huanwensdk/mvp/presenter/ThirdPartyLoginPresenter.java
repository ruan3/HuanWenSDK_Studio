package com.example.huanwensdk.mvp.presenter;

import com.example.huanwensdk.mvp.contract.InitLoginTrial;
import com.example.huanwensdk.mvp.contract.ThirdPartyLoginContract;
import com.example.huanwensdk.mvp.contract.ThirdPartyLoginContract.ThirdLoginView;
import com.example.huanwensdk.mvp.model.ThirdPartyLoginModel;

public class ThirdPartyLoginPresenter implements ThirdPartyLoginContract.Presenter{

	ThirdPartyLoginContract.Model model;
	
	@Override
	public void wechatLogin(ThirdLoginView loginView) {
		// TODO Auto-generated method stub
		
		if(model == null){
			model = new ThirdPartyLoginModel();
		}
		
		model.wechatLogin();
		
	}

	@Override
	public void qqLogin() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void facebookLogin() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getWechatToken(String code,InitLoginTrial.LoginTrialView loginView) {
		// TODO Auto-generated method stub
		if(model == null){
			model = new ThirdPartyLoginModel();
		}
		model.getWechatToken(code,loginView);
	}

}
