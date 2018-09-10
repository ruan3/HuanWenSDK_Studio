package com.example.huanwensdk.mvp.contract;

import com.example.huanwensdk.bean.user.RoleInfo;

public interface SaveRoleContract {

	interface Presenter{
		
		void saveRole(RoleInfo roleInfo);
		
	}
	
	interface Model{
		
		void saveRole(RoleInfo roleInfo);
		
	}
	
}
