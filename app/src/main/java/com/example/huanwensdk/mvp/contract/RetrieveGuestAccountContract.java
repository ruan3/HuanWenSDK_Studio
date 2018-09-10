package com.example.huanwensdk.mvp.contract;

public interface RetrieveGuestAccountContract {

	interface RetrieveGusetPresenter{
		
		void checkUid(String uid,String RandCode,RetrieveGuestView retrieveGuestView);
		
	}
	
	interface RetrieveGusetModel{
		void checkUid(String uid,String RandCode,RetrieveGuestView retrieveGuestView);
	}
	
	interface RetrieveGuestView{
		
		void checkUidSuccess(String msg);
		void checkUidFail(String msg);
		
	}
	
}
