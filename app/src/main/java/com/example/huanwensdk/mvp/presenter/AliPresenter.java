package com.example.huanwensdk.mvp.presenter;

import com.example.huanwensdk.bean.wxpay.PayItemListBean.DataBean;
import com.example.huanwensdk.mvp.contract.AliPayContract;
import com.example.huanwensdk.mvp.contract.PaySingleContract;
import com.example.huanwensdk.mvp.contract.WXPayContract;
import com.example.huanwensdk.mvp.contract.PaySingleContract.View;
import com.example.huanwensdk.mvp.contract.WXPayContract.CoinView;
import com.example.huanwensdk.mvp.model.AliModel;
import com.example.huanwensdk.mvp.model.WXModel;

public class AliPresenter implements AliPayContract.AliPresenter{

	AliPayContract.AliModel model;
	
	WXPayContract.WXPayModel wxModel;
	
	@Override
	public void getPayList(String serverCode, String roleId, AliPayContract.AliView payView) {
		// TODO Auto-generated method stub
		
		if(model == null){
			model = new AliModel();
		}
		
		model.getPayList(serverCode, roleId, payView);
		
	}

	@Override
	public void getOrder(String serverCode, String roleId, DataBean dataBean,PaySingleContract.View paySingleView) {
		// TODO Auto-generated method stub
		
		if(model == null){
			model = new AliModel();
		}
		
		model.getOrder(serverCode, roleId, dataBean,paySingleView);
		
	}

	@Override
	public void getPayResult(String serverCode, String orderId,PaySingleContract.View paySingleView) {
		// TODO Auto-generated method stub
		if(model == null){
			model = new AliModel();
		}
		model.getPayResult(serverCode, orderId,paySingleView);
	}

	@Override
	public void checkPlatformCoin(String gameItemId, DataBean dataBean,
			CoinView coinView) {
		// TODO Auto-generated method stub
		
		if(wxModel == null){
			wxModel = new WXModel();
		}
		wxModel.checkPlatformCoin(gameItemId, "alipay", dataBean, coinView);
		
	}

	@Override
	public void PayForPlatformCoin(String serverCode, String roleId,
			DataBean dataBean, View paySingleView, CoinView coinView) {
		// TODO Auto-generated method stub
		if(wxModel == null){
			wxModel = new WXModel();
		}
		wxModel.PayForPlatformCoin(serverCode, roleId, "alipay", dataBean, paySingleView, coinView);
	}

}
