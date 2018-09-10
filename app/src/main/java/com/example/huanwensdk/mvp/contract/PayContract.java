package com.example.huanwensdk.mvp.contract;

import java.util.List;

import com.example.huanwensdk.ui.bean.PayInfo;

public interface PayContract {

	interface Presenter{
	
		void getPayList(String serverCode,String level,View payView);
		
	}
	
	
	interface Model{
		
		void getPayList(String serverCode,String level,View payView);
		
	}
	
	interface View{
		
		void getPayListResult(String result,List<PayInfo> payInfos);
		
	}
	
}
