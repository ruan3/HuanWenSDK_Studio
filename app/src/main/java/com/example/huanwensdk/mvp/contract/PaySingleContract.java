package com.example.huanwensdk.mvp.contract;

import java.util.List;

import com.example.huanwensdk.bean.wxpay.PayItemListBean;

public interface PaySingleContract {

	interface View{
		void getPayList(int code, List<PayItemListBean.DataBean> payList);

		void getOrderId(int code, String orderId);

		void getPayStatus(int code, String msg);
		
		void payResult(int code,String msg);
	}
	
}
