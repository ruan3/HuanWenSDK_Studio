package com.example.huanwensdk.mvp.presenter;

import com.example.huanwensdk.mvp.contract.OrderContract;
import com.example.huanwensdk.mvp.contract.OrderContract.View;
import com.example.huanwensdk.mvp.model.OrderGetModel;

/**
 * 
 * @Title:  OrderGetPresenter.java   
 * @Package com.example.huanwensdk.mvp.presenter   
 * @Description:    获取订单中间类 
 * @author: Android_ruan     
 * @date:   2018-5-14 下午3:05:48   
 * @version V1.0
 */
public class OrderGetPresenter implements OrderContract.Presnter {

	OrderContract.Model model;
	
	@Override
	public void getOrderList(String status, String startTime, String endTime,
			View orderView) {
		// TODO Auto-generated method stub
		
		if(model == null){
			
			model = new OrderGetModel();
			
		}
		
		model.getOrderList(status, startTime, endTime, orderView);
		
	}

}
