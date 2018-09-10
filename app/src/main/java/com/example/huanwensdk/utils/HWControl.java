package com.example.huanwensdk.utils;

import android.content.Context;

import com.example.huanwensdk.googleUtils.IabHelper;
import com.example.huanwensdk.mvp.contract.OrderContract;
import com.example.huanwensdk.mvp.contract.initcontract;
import com.example.huanwensdk.mvp.contract.listener.HWGameMemberListener;
import com.example.huanwensdk.mvp.contract.listener.LoginListener;
import com.example.huanwensdk.mvp.contract.listener.PayLisenter;
import com.example.huanwensdk.mvp.contract.listener.WXPayListener;
import com.example.huanwensdk.mvp.contract.listener.WeChatLoginListener;
import com.facebook.CallbackManager;

/**
 * 
 * @Title:  HWControl.java   
 * @Package utils   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: Android_ruan     
 * @date:   2018-3-22 上午10:52:55   
 * @version V1.0
 */
public class HWControl {

	private Context context;
	
	private initcontract.initListener initListener;
	
	private LoginListener loginListener;
	
	private PayLisenter payLisenter;
	
	private WXPayListener wxPayListener;
	
	private OrderContract.View orderView;
	
	private WeChatLoginListener weChatLoginListener;
	
	private HWGameMemberListener gameMemberListener;
	
	private String extraData = "";
	
	private CallbackManager mCallbackManager;
	
	public CallbackManager getmCallbackManager() {
		return mCallbackManager;
	}

	public void setmCallbackManager(CallbackManager mCallbackManager) {
		this.mCallbackManager = mCallbackManager;
	}

	private IabHelper mHelper;
	
	public IabHelper getmHelper() {
		return mHelper;
	}

	public void setmHelper(IabHelper mHelper) {
		this.mHelper = mHelper;
	}

	public String getExtraData() {
		return extraData;
	}

	public void setExtraData(String extraData) {
		this.extraData = extraData;
	}

	public HWGameMemberListener getGameMemberListener() {
		return gameMemberListener;
	}

	public void setGameMemberListener(HWGameMemberListener gameMemberListener) {
		this.gameMemberListener = gameMemberListener;
	}

	private String gameItemId ;
	
	public String getGameItemId() {
		return gameItemId;
	}

	public void setGameItemId(String gameItemId) {
		this.gameItemId = gameItemId;
	}

	public WeChatLoginListener getWeChatLoginListener() {
		return weChatLoginListener;
	}

	public void setWeChatLoginListener(WeChatLoginListener weChatLoginListener) {
		this.weChatLoginListener = weChatLoginListener;
	}

	public OrderContract.View getOrderView() {
		return orderView;
	}

	public void setOrderView(OrderContract.View orderView) {
		this.orderView = orderView;
	}

	public WXPayListener getWxPayListener() {
		return wxPayListener;
	}

	public void setWxPayListener(WXPayListener wxPayListener) {
		this.wxPayListener = wxPayListener;
	}

	public PayLisenter getPayLisenter() {
		return payLisenter;
	}

	public void setPayLisenter(PayLisenter payLisenter) {
		this.payLisenter = payLisenter;
	}

	public LoginListener getLoginListener() {
		return loginListener;
	}

	public void setLoginListener(LoginListener loginListener) {
		this.loginListener = loginListener;
	}

	private HWControl(){}
	//单例的一种新写法
	private static class HWControlHepler{
		
		private static HWControl hwControl = new HWControl();
		
	}
	
	public static HWControl getInstance(){
		
		return HWControlHepler.hwControl;
		
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public initcontract.initListener getInitListener() {
		return initListener;
	}

	public void setInitListener(initcontract.initListener initListener) {
		this.initListener = initListener;
	}

	
}
