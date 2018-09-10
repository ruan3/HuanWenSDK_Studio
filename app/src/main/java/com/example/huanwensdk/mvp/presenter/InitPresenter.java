package com.example.huanwensdk.mvp.presenter;

import com.example.huanwensdk.mvp.contract.initcontract;
import com.example.huanwensdk.mvp.model.InitModel;

public class InitPresenter implements initcontract.initPresenter{

	initcontract.initModel model;
	
	@Override
	public void init(initcontract.initView view) {
		// TODO Auto-generated method stub
		
		if(model == null){
			model = new InitModel();
		}
		
		model.init(view);
		
	}

}
