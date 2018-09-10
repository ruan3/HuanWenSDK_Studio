package com.example.huanwensdk.bean.order;

/**
 * 
 * @Title: HWOrderInfo.java
 * @Package com.example.huanwensdk.bean.order
 * @Description: 订单实体类
 * @author: Android_ruan
 * @date: 2018-5-14 下午2:39:05
 * @version V1.0
 */
public class HWOrderInfo {

	private String createTime;
	private String currency;
	private String orderid;
	private String price;
	private String productID;
	private String roleid;
	private String servercode;
	private int status;

	public String getCreateTime() {
		return createTime;
	}

	@Override
	public String toString() {
		return "HWOrderInfo [createTime=" + createTime + ", currency="
				+ currency + ", orderid=" + orderid + ", price=" + price
				+ ", productID=" + productID + ", roleid=" + roleid
				+ ", servercode=" + servercode + ", status=" + status + "]";
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getProductID() {
		return productID;
	}

	public void setProductID(String productID) {
		this.productID = productID;
	}

	public String getRoleid() {
		return roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

	public String getServercode() {
		return servercode;
	}

	public void setServercode(String servercode) {
		this.servercode = servercode;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
