package com.example.huanwensdk.mvp.contract;

import java.util.List;

import com.example.huanwensdk.bean.order.HWOrderInfo;

public interface OrderContract {

	interface Presnter{
		
		void getOrderList(String status,String startTime,String endTime,View orderView);
		
	}
	
	interface Model{
		void getOrderList(String status,String startTime,String endTime,View orderView);
	}
	
	interface View{
		void getOrderResult(String result,List<HWOrderInfo> hwOrderInfos);
		void getOrderDate(String type,String startTime,String endTime);
	}
	
}
