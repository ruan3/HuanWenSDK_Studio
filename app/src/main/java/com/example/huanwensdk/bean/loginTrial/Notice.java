package com.example.huanwensdk.bean.loginTrial;

/**
 * 
 * @Title:  Notice.java   
 * @Package com.example.huanwensdk.bean.loginTrial   
 * @Description:    公告栏文本
 * @author: Android_ruan     
 * @date:   2018-5-28 下午4:05:53   
 * @version V1.0
 */
public class Notice {

	String vnotice_is_show;
	String notice_info;
	public String getVnotice_is_show() {
		return vnotice_is_show;
	}
	public void setVnotice_is_show(String vnotice_is_show) {
		this.vnotice_is_show = vnotice_is_show;
	}
	public String getNotice_info() {
		return notice_info;
	}
	public void setNotice_info(String notice_info) {
		this.notice_info = notice_info;
	}
	@Override
	public String toString() {
		return "Notice [vnotice_is_show=" + vnotice_is_show + ", notice_info="
				+ notice_info + "]";
	}
	
	
	
}
