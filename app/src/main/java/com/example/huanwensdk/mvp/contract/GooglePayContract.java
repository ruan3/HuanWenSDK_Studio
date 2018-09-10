package com.example.huanwensdk.mvp.contract;

import java.util.List;

import com.example.huanwensdk.bean.google.HWGpPayItem;
import com.example.huanwensdk.bean.google.HWPayItemBean;
import com.example.huanwensdk.bean.wxpay.PayItemListBean;
import com.example.huanwensdk.bean.wxpay.PayItemListBean.DataBean;
import com.example.huanwensdk.mvp.contract.WXPayContract.CoinView;

/**
 * 
 * @Title:  GooglePayContract.java   
 * @Package com.example.huanwensdk.mvp.contract   
 * @Description:    谷歌支付接口管理   
 * @author: Android_ruan     
 * @date:   2018-5-21 上午10:25:22   
 * @version V1.0
 */
public interface GooglePayContract {

	interface Presenter{
		
		void init(View googleView);
		
		void pay(String sdkOriginal,String sdkStatus,String gpOrderId,String serverCode,String orderid,String productId,String strPurchas,boolean isShowToast);
		
		void GetItemList(String serverCode,String roleId,String proItemid,View googleView);
		
		void getOrder(String itemId,String serverCode,String roleId);
		
		void checkPlatformCoin(String gameItemId,HWGpPayItem dataBean,CoinView coinView);
		
		void PayForPlatformCoin(String serverCode,String roleId,HWGpPayItem dataBean,PaySingleContract.View paySingleView,CoinView coinView);
		
		
	}

	interface Model{
		
		void init(View googleView);
		
		void pay(String sdkOriginal,String sdkStatus,String gpOrderId,String serverCode,String orderid,String productId,String strPurchas,boolean isShowToast);
		
		void GetItemList(String serverCode,String roleId,String proItemid,View googleView);
		
		void getOrder(String itemId,String serverCode,String roleId);
		
	}
	
	interface View{
		
		void getGDItems(int code,List<HWGpPayItem> data);
		
		void consumeResult(int code,String msg,String orderId);
		
		void payResult(int code,String msg);
	}
	
}
