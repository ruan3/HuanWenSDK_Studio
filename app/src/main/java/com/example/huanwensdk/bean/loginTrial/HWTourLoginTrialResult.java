package com.example.huanwensdk.bean.loginTrial;

import java.util.List;

/**
 * 
 * @Title: HWTourLoginTrialResult.java
 * @Package com.example.huanwensdk.bean.loginTrial
 * @Description: 游客登录字段
 * @author: Android_ruan
 * @date: 2018-4-23 上午11:48:39
 * @version V1.0
 */
public class HWTourLoginTrialResult {

	private String code;
	private String currentType;
	private String message;
	private String randomPwd;
	private String sessionid;
	private String showid;
	private String showname;
	private String token;
	private String userid;

	private HWBindingUserAccountInfo data;

	private Notice notice;
	
	public Notice getNotice() {
		return notice;
	}

	public void setNotice(Notice notice) {
		this.notice = notice;
	}

	public HWBindingUserAccountInfo getData() {
		return data;
	}

	public void setData(HWBindingUserAccountInfo data) {
		this.data = data;
	}

	private String sms;

	public String getSms() {
		return sms;
	}

	public void setSms(String sms) {
		this.sms = sms;
	}

	public String getCode() {
		return this.code;
	}

	public String getCurrentType() {
		return this.currentType;
	}

	public String getMessage() {
		return this.message;
	}

	public String getRandomPwd() {
		return this.randomPwd;
	}

	public String getSessionid() {
		return this.sessionid;
	}

	public String getShowid() {
		return this.showid;
	}

	public String getShowname() {
		return this.showname;
	}

	public String getToken() {
		return this.token;
	}

	public String getUserid() {
		return this.userid;
	}

	public void setCode(String paramString) {
		this.code = paramString;
	}

	public void setCurrentType(String paramString) {
		this.currentType = paramString;
	}

	public void setMessage(String paramString) {
		this.message = paramString;
	}

	public void setRandomPwd(String paramString) {
		this.randomPwd = paramString;
	}

	public void setSessionid(String paramString) {
		this.sessionid = paramString;
	}

	public void setShowid(String paramString) {
		this.showid = paramString;
	}

	public void setShowname(String paramString) {
		this.showname = paramString;
	}

	public void setToken(String paramString) {
		this.token = paramString;
	}

	public void setUserid(String paramString) {
		this.userid = paramString;
	}

	public String toString() {
		return "FGTourLogin{code='" + this.code + '\'' + ", sessionid='"
				+ this.sessionid + '\'' + ", token='" + this.token + '\''
				+ ", userid='" + this.userid + '\'' + ", currentType='"
				+ this.currentType + '\'' + ", randomPwd='" + this.randomPwd
				+ '\'' + ", showid='" + this.showid + '\'' + ", showname='"
				+ this.showname + '\'' + ", message='" + this.message + '\''
				+ '}';
	}

}
