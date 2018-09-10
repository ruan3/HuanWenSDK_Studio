package com.example.huanwensdk.mvp.presenter;

import com.example.huanwensdk.bean.google.HWGpPayItem;
import com.example.huanwensdk.bean.wxpay.PayItemListBean.DataBean;
import com.example.huanwensdk.bean.wxpay.PayOrderBean;
import com.example.huanwensdk.bean.wxpay.WXPayOrderBean;
import com.example.huanwensdk.mvp.contract.PaySingleContract;
import com.example.huanwensdk.mvp.contract.PaySingleContract.View;
import com.example.huanwensdk.mvp.contract.WXPayContract;
import com.example.huanwensdk.mvp.contract.WXPayContract.CoinView;
import com.example.huanwensdk.mvp.contract.WXPayContract.WXPayView;
import com.example.huanwensdk.mvp.model.WXModel;
/**
 * 
 * @Title:  WXPresenter.java   
 * @Package mvp.presenter   
 * @Description:    微信支付
 * @author: Android_ruan     
 * @date:   2018-4-16 下午3:40:55   
 * @version V1.0
 */
public class WXPresenter implements WXPayContract.WXPayPresenter{

	WXPayContract.WXPayModel model;
	
	@Override
	public void pay(WXPayOrderBean order) {
		// TODO Auto-generated method stub
		
		if(model == null){
			model = new WXModel();
		}
		model.pay(order);
		
	}

	@Override
	public void getPayList(String serverCode, String roleId,WXPayContract.WXPayView payView) {
		// TODO Auto-generated method stub
		
		if(model == null){
			model = new WXModel();
		}
		
		model.getPayList(serverCode, roleId, payView);
		
	}

	@Override
	public void getOrder(String serverCode, String roleId, DataBean dataBean,PaySingleContract.View paySingleView) {
		// TODO Auto-generated method stub
		
		if(model == null){
			model = new WXModel();
		}
		
		model.getOrder(serverCode, roleId, dataBean,paySingleView);
		
	}

	/**
	 * 请求后台进行双向验证
	 */
	@Override
	public void getPayResult(String serverCode, String orderId) {
		// TODO Auto-generated method stub
		if(model == null){
			model = new WXModel();
		}
		model.getPayResult(serverCode, orderId);
	}

	@Override
	public void checkPlatformCoin(String gameItemId,DataBean dataBean, CoinView coinView) {
		// TODO Auto-generated method stub
		if(model == null){
			model = new WXModel();
		}
		model.checkPlatformCoin(gameItemId, "wechat", dataBean, coinView);
	}

	@Override
	public void PayForPlatformCoin(String serverCode, String roleId,
			DataBean dataBean, View paySingleView,CoinView coinView) {
		// TODO Auto-generated method stub
		if(model == null){
			model = new WXModel();
		}
		model.PayForPlatformCoin(serverCode, roleId, "wechat", dataBean, paySingleView, coinView);
	}

	@Override
	public void checkPlatformCoin(String gameItemId, String channel,
			HWGpPayItem dataBean, CoinView coinView) {
		// TODO Auto-generated method stub
		if(model == null){
			model = new WXModel();
		}
		model.checkPlatformCoin(gameItemId, channel, dataBean, coinView);
	}

	@Override
	public void PayForPlatformCoin(String serverCode, String roleId,
			String channel, HWGpPayItem dataBean, View paySingleView,
			CoinView coinView) {
		// TODO Auto-generated method stub
		if(model == null){
			model = new WXModel();
		}
		model.PayForPlatformCoin(serverCode, roleId, channel, dataBean, paySingleView, coinView);
	}

}
