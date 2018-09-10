package com.example.huanwensdk.mvp.contract;

import java.util.List;

import com.example.huanwensdk.bean.wxpay.PayItemListBean;
import com.example.huanwensdk.bean.wxpay.PayItemListBean.DataBean;
import com.example.huanwensdk.mvp.contract.WXPayContract.CoinView;
import com.example.huanwensdk.mvp.contract.WXPayContract.WXPayView;

public interface AliPayContract {

	interface AliPresenter {

		void getPayList(String serverCode, String roleId, AliView payView);

		void getOrder(String serverCode, String roleId,
				PayItemListBean.DataBean dataBean,PaySingleContract.View paySingleView);
		
		void getPayResult(String serverCode,String orderId,PaySingleContract.View paySingleView);
		
		void checkPlatformCoin(String gameItemId,DataBean dataBean,CoinView coinView);
		
		void PayForPlatformCoin(String serverCode,String roleId,PayItemListBean.DataBean dataBean,PaySingleContract.View paySingleView,CoinView coinView);
		
	}

	interface AliModel {

		void getPayList(String serverCode, String roleId, AliView payView);

		void getOrder(String serverCode, String roleId,
				PayItemListBean.DataBean dataBean,PaySingleContract.View paySingleView);
		
		void getPayResult(String serverCode,String orderId,PaySingleContract.View paySingleView);
	}

	interface AliView {

		void getPayList(int code, List<PayItemListBean.DataBean> payList);

		void getOrderId(int code, String orderId);

		void getPayStatus(int code, String msg);
		
		void getAilPayResult(int code,String msg);

	}

}
