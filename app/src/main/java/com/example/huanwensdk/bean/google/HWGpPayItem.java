package com.example.huanwensdk.bean.google;

/**
 * 
 * @Title: HWGpPayItem.java
 * @Package com.example.huanwensdk
 * @Description: 获取谷歌支付实体类
 * @author: Android_ruan
 * @date: 2018-5-21 上午11:40:48
 * @version V1.0
 */
public class HWGpPayItem {

	String activeDescription;
	String amount;
	String currency;
	String description;
	String extraJson;
	String gameItemId;
	String icon;
	String id;
	String productId;
	String title;

	public String getActiveDescription() {
		return activeDescription;
	}

	public void setActiveDescription(String activeDescription) {
		this.activeDescription = activeDescription;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getExtraJson() {
		return extraJson;
	}

	public void setExtraJson(String extraJson) {
		this.extraJson = extraJson;
	}

	public String getGameItemId() {
		return gameItemId;
	}

	public void setGameItemId(String gameItemId) {
		this.gameItemId = gameItemId;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProductid() {
		return productId;
	}

	public void setProductid(String productid) {
		this.productId = productid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return "HWGpPayItem [activeDescription=" + activeDescription
				+ ", amount=" + amount + ", currency=" + currency
				+ ", description=" + description + ", extraJson=" + extraJson
				+ ", gameItemId=" + gameItemId + ", icon=" + icon + ", id="
				+ id + ", productid=" + productId + ", title=" + title + "]";
	}

}
