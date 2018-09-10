package com.example.huanwensdk.ui.bean;
/**
 * 
 * @Title:  PayInfo.java   
 * @Package ui.bean   
 * @Description:    支付渠道实体类
 * @author: Android_ruan     
 * @date:   2018-4-12 下午5:01:34   
 * @version V1.0
 */
public class PayInfo {
	
	int image;
	String pay_type;
	String pay_hint;
	String channelCode;
	int childType;
	String description;
	String name;
	String id;
	String picUrl;
	String rootid;
	String url;
	public String getChannelCode() {
		return channelCode;
	}
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	public int getChildType() {
		return childType;
	}
	public void setChildType(int childType) {
		this.childType = childType;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public String getRootid() {
		return rootid;
	}
	public void setRootid(String rootid) {
		this.rootid = rootid;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getImage() {
		return image;
	}
	public void setImage(int image) {
		this.image = image;
	}
	public String getPay_type() {
		return pay_type;
	}
	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}
	public String getPay_hint() {
		return pay_hint;
	}
	public void setPay_hint(String pay_hint) {
		this.pay_hint = pay_hint;
	}
	@Override
	public String toString() {
		return "PayInfo [image=" + image + ", pay_type=" + pay_type
				+ ", pay_hint=" + pay_hint + ", channelCode=" + channelCode
				+ ", childType=" + childType + ", description=" + description
				+ ", name=" + name + ", id=" + id + ", picUrl=" + picUrl
				+ ", rootid=" + rootid + ", url=" + url + "]";
	}

}
