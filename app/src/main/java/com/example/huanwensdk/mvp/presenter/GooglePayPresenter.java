package com.example.huanwensdk.mvp.presenter;

import com.example.huanwensdk.bean.google.HWGpPayItem;
import com.example.huanwensdk.bean.wxpay.PayItemListBean.DataBean;
import com.example.huanwensdk.mvp.contract.GooglePayContract;
import com.example.huanwensdk.mvp.contract.WXPayContract;
import com.example.huanwensdk.mvp.contract.GooglePayContract.View;
import com.example.huanwensdk.mvp.contract.WXPayContract.CoinView;
import com.example.huanwensdk.mvp.model.GooglePayModel;
import com.example.huanwensdk.mvp.model.WXModel;

public class GooglePayPresenter implements GooglePayContract.Presenter{

	GooglePayContract.Model model;
	WXPayContract.WXPayModel wxModel;
	
//	@Override
//	public void pay(String sdkOriginal,String sdkStatus,String gpOrderId,String serverCode,String orderid,boolean isShowToast) {
//		// TODO Auto-generated method stub
//		
//	
//	}

	@Override
	public void GetItemList(String serverCode, String roleId, String proItemid,
			View googleView) {
		// TODO Auto-generated method stub
		
		if(model == null){
			
			model = new GooglePayModel();
			
		}
		
		model.GetItemList(serverCode, roleId, proItemid, googleView);
		
	}

	@Override
	public void init(GooglePayContract.View googleView) {
		// TODO Auto-generated method stub
		
		if(model == null){
			
			model = new GooglePayModel();
		}
		
		model.init(googleView);
		
	}

	@Override
	public void getOrder(String itemId, String serverCode, String roleId) {
		// TODO Auto-generated method stub
		
		if(model == null){
			model = new GooglePayModel();
		}
		
		model.getOrder(itemId, serverCode, roleId);
		
	}

	@Override
	public void checkPlatformCoin(String gameItemId, HWGpPayItem dataBean,
			CoinView coinView) {

		if(wxModel == null){
			wxModel = new WXModel();
		}
		wxModel.checkPlatformCoin(gameItemId, "android", dataBean, coinView);
		
	}

	@Override
	public void PayForPlatformCoin(
			String serverCode,
			String roleId,
			HWGpPayItem dataBean,
			com.example.huanwensdk.mvp.contract.PaySingleContract.View paySingleView,
			CoinView coinView) {
		
		if(wxModel == null){
			wxModel = new WXModel();
		}
		wxModel.PayForPlatformCoin(serverCode, roleId, "google", dataBean, paySingleView, coinView);
		
	}

	@Override
	public void pay(String sdkOriginal, String sdkStatus, String gpOrderId,
			String serverCode, String orderid, String productId,
			String strPurchas, boolean isShowToast) {
	if(model == null){
			
			model = new GooglePayModel();
			
		}
		
		model.pay(sdkOriginal,sdkStatus,gpOrderId,serverCode,orderid,productId,strPurchas,true);
		
	}

}
