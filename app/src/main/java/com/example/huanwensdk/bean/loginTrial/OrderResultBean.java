package com.example.huanwensdk.bean.loginTrial;

import java.util.List;

import com.example.huanwensdk.bean.order.HWOrderInfo;
/**
 * 
 * @Title:  OrderResultBean.java   
 * @Package com.example.huanwensdk.bean.loginTrial   
 * @Description:    获取订单   
 * @author: Android_ruan     
 * @date:   2018-5-15 上午11:16:43   
 * @version V1.0
 */
public class OrderResultBean {

	private String code;
	  private String currentType;
	  private List<HWOrderInfo> data;
	  public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCurrentType() {
		return currentType;
	}

	public void setCurrentType(String currentType) {
		this.currentType = currentType;
	}

	public List<HWOrderInfo> getData() {
		return data;
	}

	public void setData(List<HWOrderInfo> data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getRandomPwd() {
		return randomPwd;
	}

	public void setRandomPwd(String randomPwd) {
		this.randomPwd = randomPwd;
	}

	public String getSessionid() {
		return sessionid;
	}

	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}

	public String getShowid() {
		return showid;
	}

	public void setShowid(String showid) {
		this.showid = showid;
	}

	public String getShowname() {
		return showname;
	}

	public void setShowname(String showname) {
		this.showname = showname;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getSms() {
		return sms;
	}

	public void setSms(String sms) {
		this.sms = sms;
	}

	private String message;
	  private String randomPwd;
	  private String sessionid;
	  private String showid;
	  private String showname;
	  private String token;
	  private String userid;
	  
	  private String sms;
	
}
