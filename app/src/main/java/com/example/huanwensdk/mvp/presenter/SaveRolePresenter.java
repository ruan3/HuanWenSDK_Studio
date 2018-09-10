package com.example.huanwensdk.mvp.presenter;

import com.example.huanwensdk.bean.user.RoleInfo;
import com.example.huanwensdk.mvp.contract.SaveRoleContract;
import com.example.huanwensdk.mvp.model.SaveRoleModel;

public class SaveRolePresenter implements SaveRoleContract.Presenter{

	SaveRoleContract.Model model;
	
	@Override
	public void saveRole(RoleInfo roleInfo) {
		// TODO Auto-generated method stub

		if(model == null){
			
			model = new SaveRoleModel();
			
		}
		
		model.saveRole(roleInfo);
		
	}

}
