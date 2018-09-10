package com.example.huanwensdk.mvp.contract;

import java.util.List;

import com.example.huanwensdk.bean.google.HWGpPayItem;
import com.example.huanwensdk.bean.wxpay.PayItemListBean;
import com.example.huanwensdk.bean.wxpay.PayItemListBean.DataBean;
import com.example.huanwensdk.bean.wxpay.WXPayOrderBean;

/**
 * 
 * @Title:  WXPayContract.java   
 * @Package mvp.contract   
 * @Description:    微信支付
 * @author: Android_ruan     
 * @date:   2018-4-16 下午3:38:45   
 * @version V1.0
 */
public interface WXPayContract {

	interface WXPayPresenter{
		
		void pay(WXPayOrderBean order);
		
		void getPayList(String serverCode ,String roleId,WXPayView payView);
		
		void getOrder(String serverCode,String roleId,PayItemListBean.DataBean dataBean,PaySingleContract.View paySingleView);
		
		void getPayResult(String serverCode,String orderId);
		
		void checkPlatformCoin(String gameItemId,DataBean dataBean,CoinView coinView);
		
		void checkPlatformCoin(String gameItemId,String channel,HWGpPayItem dataBean,CoinView coinView);
		
		void PayForPlatformCoin(String serverCode,String roleId,PayItemListBean.DataBean dataBean,PaySingleContract.View paySingleView,CoinView coinView);
		
		void PayForPlatformCoin(String serverCode,String roleId,String channel,HWGpPayItem dataBean,PaySingleContract.View paySingleView,CoinView coinView);
	}
	
	interface WXPayModel{
		
		void pay(WXPayOrderBean order);
		
		void getPayList(String serverCode,String roleId,WXPayView payView);
		
		void getOrder(String serverCode,String roleId,PayItemListBean.DataBean dataBean,PaySingleContract.View paySingleView);
		
		void getPayResult(String serverCode,String orderId);

		void checkPlatformCoin(String gameItemId,String channel,DataBean dataBean,CoinView coinView);
		
		void checkPlatformCoin(String gameItemId,String channel,HWGpPayItem dataBean,CoinView coinView);
		
		void PayForPlatformCoin(String serverCode,String roleId,String channel,PayItemListBean.DataBean dataBean,PaySingleContract.View paySingleView,CoinView coinView);
	
		void PayForPlatformCoin(String serverCode,String roleId,String channel,HWGpPayItem dataBean,PaySingleContract.View paySingleView,CoinView coinView);
	}
	
	interface WXPayView{
		
		void getPayList(int code,List<PayItemListBean.DataBean> payList);
		
		void getOrderId(int code,String orderId);
		
		void getPayStatus(int code,String msg);
		
	}
	
	interface CoinView{
		
		void getPlatFormResult(int code,String msg,DataBean dataBean,String type);
		
		void getPlatFormResult(int code,String msg,HWGpPayItem dataBean,String type);
		
		void getPayStatus(int code,String msg);
		
	}
}
