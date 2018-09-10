package com.example.huanwensdk.mvp.presenter;

import com.example.huanwensdk.mvp.contract.CheckServerContract;
import com.example.huanwensdk.mvp.model.CheckServerModel;

public class CheckServerPresenter implements CheckServerContract.CheckServerPresenter{

	CheckServerContract.CheckServerModel model;
	
	@Override
	public void checkServer(String userId, String serverCode) {
		// TODO Auto-generated method stub
		
		if(model == null){
			model = new CheckServerModel();
		}
		
		model.checkServer(userId, serverCode);
		
	}

}
