package com.example.huanwensdk.mvp.presenter;

import com.example.huanwensdk.mvp.contract.RetrieveGuestAccountContract;
import com.example.huanwensdk.mvp.model.RetrieveGuestModel;

public class RetrieveGuestPresenter implements RetrieveGuestAccountContract.RetrieveGusetPresenter{

	RetrieveGuestAccountContract.RetrieveGusetModel model;
	
	@Override
	public void checkUid(String uid, String RandCode,
			RetrieveGuestAccountContract.RetrieveGuestView retrieveGuestView) {
		// TODO Auto-generated method stub
		
		if(model == null){
			model = new RetrieveGuestModel();
		}
		model.checkUid(uid, RandCode, retrieveGuestView);
	}

}
