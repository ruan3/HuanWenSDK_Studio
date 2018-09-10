package com.example.huanwensdk.bean.order;

import com.example.huanwensdk.bean.google.HWGpPayItem;

/**
 * 
 * google订单解析类
 * @author  Ruan
 * @data:  2018-8-16 下午5:00:31
 * @version:  V1.0
 */
public class HWGpOrderInfo {

	private String code;
    private HWGpPayItem data;
    private String message;
    private String orderid;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public HWGpPayItem getData() {
		return data;
	}
	public void setData(HWGpPayItem data) {
		this.data = data;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	@Override
	public String toString() {
		return "HWGpOrderInfo [code=" + code + ", data=" + data + ", message="
				+ message + ", orderid=" + orderid + "]";
	}
	
    
}
