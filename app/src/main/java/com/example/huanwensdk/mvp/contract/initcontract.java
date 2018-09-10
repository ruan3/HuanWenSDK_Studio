package com.example.huanwensdk.mvp.contract;

/**
 * 
 * @Title:  initcontract.java   
 * @Package contract   
 * @Description:    初始化专用的contract
 * @author: Android_ruan     
 * @date:   2018-3-22 上午10:39:25   
 * @version V1.0
 */
public interface initcontract {

	interface initView{
		
		void getOrientationResult(int code,int orientation);
		void fail(String result);
		void activationSuccess();
		
	}
	
	interface initModel{
		
		void init(initcontract.initView view);
		
	}
	
	interface initPresenter{
		
		void init(initcontract.initView view);
		
	}
	
	interface initListener{
		
		void success();
		void fail(String result);
		
	}
	
}
