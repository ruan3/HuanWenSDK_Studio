package com.example.huanwensdk.mvp.presenter;

import com.example.huanwensdk.mvp.contract.MemberLoginContract;
import com.example.huanwensdk.mvp.model.MemberLoginModel;

public class MemberLoginPresenter implements MemberLoginContract.MemberLoginPresenter{

	MemberLoginContract.MemberLoginModel memberLoginModel;
	
	@Override
	public void login(String user, String pwd,  MemberLoginContract.MemberLoginView loginView) {
		// TODO Auto-generated method stub
		
		if(memberLoginModel == null){
			
			memberLoginModel = new MemberLoginModel();
			 
		}
		
		memberLoginModel.login(user, pwd, loginView);
	}

}
