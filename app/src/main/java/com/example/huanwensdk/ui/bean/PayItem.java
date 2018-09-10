package com.example.huanwensdk.ui.bean;

/**
 * 
 * @Title: PayItem.java
 * @Package ui.bean
 * @Description: 单项支付实体类
 * @author: Android_ruan
 * @date: 2018-4-16 下午2:40:04
 * @version V1.0
 */
public class PayItem {
	String activeDescription;
	String amount;
	String currency;
	String description;
	String extraJson;
	String gameItemId;
	String icon;
	String id;
	String productid;
	String title;

	public String getActiveDescription() {
		return this.activeDescription;
	}

	public String getAmount() {
		return this.amount;
	}

	public String getCoin() {
		return this.currency + " " + this.amount;
	}

	public String getCurrency() {
		return this.currency;
	}

	public String getDescription() {
		return this.description;
	}

	public String getExtraJson() {
		return this.extraJson;
	}

	public String getGameItemId() {
		return this.gameItemId;
	}

	public String getIcon() {
		return this.icon;
	}

	public String getId() {
		return this.id;
	}

	public String getProductid() {
		return this.productid;
	}

	public String getTitle() {
		return this.title;
	}

	public void setActiveDescription(String paramString) {
		this.activeDescription = paramString;
	}

	public void setAmount(String paramString) {
		this.amount = paramString;
	}

	public void setCurrency(String paramString) {
		this.currency = paramString;
	}

	public void setDescription(String paramString) {
		this.description = paramString;
	}

	public void setExtraJson(String paramString) {
		this.extraJson = paramString;
	}

	public void setGameItemId(String paramString) {
		this.gameItemId = paramString;
	}

	public void setIcon(String paramString) {
		this.icon = paramString;
	}

	public void setId(String paramString) {
		this.id = paramString;
	}

	public void setProductid(String paramString) {
		this.productid = paramString;
	}

	public void setTitle(String paramString) {
		this.title = paramString;
	}
}
