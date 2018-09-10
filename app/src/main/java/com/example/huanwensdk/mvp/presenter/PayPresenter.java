package com.example.huanwensdk.mvp.presenter;

import com.example.huanwensdk.mvp.contract.PayContract;
import com.example.huanwensdk.mvp.contract.PayContract.View;
import com.example.huanwensdk.mvp.model.PayModel;

/**
 * 
 * @Title:  PayPresenter.java   
 * @Package com.example.huanwensdk.mvp.presenter   
 * @Description:    获取支付方式
 * @author: Android_ruan     
 * @date:   2018-5-18 下午1:52:09   
 * @version V1.0
 */
public class PayPresenter implements PayContract.Presenter{

	PayContract.Model model;
	
	@Override
	public void getPayList(String serverCode, String level, View payView) {
		// TODO Auto-generated method stub
		
		if(model == null){
			
			model = new PayModel();
			
		}
		
		model.getPayList(serverCode, level, payView);
		
	}

}
