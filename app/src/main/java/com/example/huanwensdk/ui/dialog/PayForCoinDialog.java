package com.example.huanwensdk.ui.dialog;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.example.huanwensdk.bean.google.HWGpPayItem;
import com.example.huanwensdk.bean.wxpay.PayItemListBean.DataBean;
import com.example.huanwensdk.mvp.contract.AliPayContract;
import com.example.huanwensdk.mvp.contract.GooglePayContract;
import com.example.huanwensdk.mvp.contract.WXPayContract;
import com.example.huanwensdk.utils.ResLoader;

public class PayForCoinDialog extends DialogBase{

	Button btn_back;
	TextView tv_title;
	Button btn_cancle;
	Button btn_confrom;
	TextView tv_pay_tips;
	
	WXPayContract.WXPayPresenter wxPayPresenter;
	
	AliPayContract.AliPresenter aliPresenter;
	
	GooglePayContract.Presenter googlePresenter;
	
	String serverCode;
	String roleId;
	DataBean databean;
	HWGpPayItem payItem;
	String point;
	WXPayContract.CoinView coinView;
	int type;//0-微信  1-支付宝 2-谷歌支付
	
	private PayForCoinDialog(){
	
//		initView(R.layout.dialog_paycoin_tips);
		initView(ResLoader.getLayout(context, "dialog_paycoin_tips"));
		
	}
	
	private static class PayForCoinHelper{
		
		private static PayForCoinDialog payForCoinDialog = new PayForCoinDialog();
		
	}
	
	public static PayForCoinDialog getInstance(){
		return PayForCoinHelper.payForCoinDialog;
	}
	
	/**
	 * 获取微信平台币支付
	 * @param wxPayPresenter
	 * @param serverCode
	 * @param roleId
	 * @param databean
	 * @param coinView
	 */
	public void getWXData(WXPayContract.WXPayPresenter wxPayPresenter,String serverCode,
			String roleId,DataBean databean,WXPayContract.CoinView coinView,String point){
		
		this.wxPayPresenter = wxPayPresenter;
		this.serverCode = serverCode;
		this.roleId = roleId;
		this.databean = databean;
		this.coinView = coinView;
		this.point = point;
		type = 0;
		tv_pay_tips.setText("本次消耗平台币为："+point+",是否确定购买道具？");
	}
	
	/**
	 * 获取支付宝平台币支付
	 * @param wxPayPresenter
	 * @param serverCode
	 * @param roleId
	 * @param databean
	 * @param coinView
	 */
	public void getAliData(AliPayContract.AliPresenter aliPresenter,String serverCode,
			String roleId,DataBean databean,WXPayContract.CoinView coinView,String point){
		
		this.aliPresenter = aliPresenter;
		this.serverCode = serverCode;
		this.roleId = roleId;
		this.databean = databean;
		this.coinView = coinView;
		this.point = point;
		type = 1;
		tv_pay_tips.setText("本次消耗平台币为："+point+",是否确定购买道具？");
	}
	
	/**
	 * 获取谷歌平台币支付
	 * @param wxPayPresenter
	 * @param serverCode
	 * @param roleId
	 * @param databean
	 * @param coinView
	 */
	public void getGoogleData(GooglePayContract.Presenter googlePresenter,String serverCode,
			String roleId,HWGpPayItem databean,WXPayContract.CoinView coinView,String point){
		
		this.googlePresenter = googlePresenter;
		this.serverCode = serverCode;
		this.roleId = roleId;
		this.payItem = databean;
		this.coinView = coinView;
		this.point = point;
		type = 2;
		tv_pay_tips.setText("本次消耗平台币为："+point+",是否确定购买道具？");
	}
	
	@Override
	public void initView(int ContentId) {
		// TODO Auto-generated method stub
		super.initView(ContentId);

//		btn_back = (Button) dialog.findViewById(R.id.btn_close);
		btn_back = (Button) dialog.findViewById(ResLoader.getId(context, "btn_close"));
		
//		tv_title = (TextView) dialog.findViewById(R.id.tv_title);
		tv_title = (TextView) dialog.findViewById(ResLoader.getId(context, "tv_title"));
		
//		btn_cancle = (Button) dialog.findViewById(R.id.btn_cancle);
		btn_cancle = (Button) dialog.findViewById(ResLoader.getId(context, "btn_cancle"));
		
//		btn_confrom = (Button) dialog.findViewById(R.id.btn_confrom);
		btn_confrom = (Button) dialog.findViewById(ResLoader.getId(context, "btn_confrom"));
		
//		tv_pay_tips = (TextView) dialog.findViewById(R.id.tv_pay_tips);
		tv_pay_tips = (TextView) dialog.findViewById(ResLoader.getId(context, "tv_pay_tips"));
		
		tv_title.setText(ResLoader.getString(context, "string_pay_coin_coin"));
		
		btn_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				close();
			}
		});
		
		btn_cancle.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				close();
			}
		});
		
		btn_confrom.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(type==0){
					wxPayPresenter.PayForPlatformCoin(serverCode, roleId, databean, null, coinView);
				}else if(type == 1){
					aliPresenter.PayForPlatformCoin(serverCode, roleId, databean, null, coinView);
				}else if(type == 2){
					googlePresenter.PayForPlatformCoin(serverCode, roleId, payItem, null, coinView);
				}
				close();
			}
		});
		
	}
	
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		if(dialog.isShowing()){
			dialog.dismiss();
		}
		dialog.show();
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		if(dialog.isShowing()){
			dialog.dismiss();
		}
	}

}
