package com.example.huanwensdk.bean.google;

import java.util.List;

/**
 * 
 * @Title: HWPayItemBean.java
 * @Package com.example.huanwensdk.bean.google
 * @Description: 获取请求后台，谷歌品项返回的实体类
 * @author: Android_ruan
 * @date: 2018-5-21 下午1:46:29
 * @version V1.0
 */
public class HWPayItemBean {

	private List<HWGpPayItem> data;

	private String code;
	private String message;
	private String publicKey;

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	public List<HWGpPayItem> getData() {
		return data;
	}

	public void setData(List<HWGpPayItem> data) {
		this.data = data;
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

	@Override
	public String toString() {
		return "HWPayItemBean [data=" + data + ", code=" + code + ", message="
				+ message + "]";
	}

}
