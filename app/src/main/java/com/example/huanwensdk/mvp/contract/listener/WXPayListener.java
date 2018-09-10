package com.example.huanwensdk.mvp.contract.listener;

/**
 * 
 * @Title:  WXPayListener.java   
 * @Package com.example.huanwensdk.mvp.contract.listener   
 * @Description:    接收微信支付结果回调   
 * @author: Android_ruan     
 * @date:   2018-5-3 上午10:45:00   
 * @version V1.0
 */
public interface WXPayListener {

	void WXCallback(int code,String result);
	
}
