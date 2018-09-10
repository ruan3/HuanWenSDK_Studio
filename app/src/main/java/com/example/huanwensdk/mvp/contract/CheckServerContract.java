package com.example.huanwensdk.mvp.contract;

public interface CheckServerContract {

	interface CheckServerPresenter{
		
		void checkServer(String userId,String serverCode);
		
	}
	
	interface CheckServerModel{
		
		void checkServer(String userId,String serverCode);
		
	}
	
}
