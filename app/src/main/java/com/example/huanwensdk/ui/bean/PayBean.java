package com.example.huanwensdk.ui.bean;

import java.util.List;

import com.example.huanwensdk.bean.loginTrial.HWBindingUserAccountInfo;

/**
 * 
 * @Title:  PayBean.java   
 * @Package com.example.huanwensdk.ui.bean   
 * @Description:   封装服务器返回的支付方式
 * @author: Android_ruan     
 * @date:   2018-5-18 下午2:08:10   
 * @version V1.0
 */
public class PayBean {

	String channelBanner;
	String code;
	String message;
	String publicKey;
	public String getPublicKey() {
		return publicKey;
	}
	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}
	List<PayInfo> data;
	public String getChannelBanner() {
		return channelBanner;
	}
	public void setChannelBanner(String channelBanner) {
		this.channelBanner = channelBanner;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<PayInfo> getData() {
		return data;
	}
	public void setData(List<PayInfo> data) {
		this.data = data;
	}
	
}
