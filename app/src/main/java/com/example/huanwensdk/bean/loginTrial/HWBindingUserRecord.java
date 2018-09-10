package com.example.huanwensdk.bean.loginTrial;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * 
 * @Title: HWBindingUserRecord.java
 * @Package bean.loginTrial
 * @Description: 绑定的用户数据(加上保存到数据库)
 * @author: Android_ruan
 * @date: 2018-3-27 下午2:36:47
 * @version V1.0
 */

@Table(name = "hw_user_binding_account")

// 注释表名
public class HWBindingUserRecord {
	
	@Column(name = "userEmailName")//注释列名
	private String userEmailName;
	
	@Column(name = "userFacebookName")//注释列名
	private String userFacebookName;
	
	@Column(name = "userId" ,isId = true)//注释列名
	private String userId;
	
	@Column(name = "userPhoneName")//注释列名
	private String userPhoneName;
	
	@Column(name = "userTypeEmail")//注释列名
	private int userTypeEmail;
	
	@Column(name = "userTypeFacebook")//注释列名
	private int userTypeFacebook;
	
	@Column(name = "userTypePhone")//注释列名
	private int userTypePhone;

	public String getUserEmailName() {
		return this.userEmailName;
	}

	public String getUserFacebookName() {
		return this.userFacebookName;
	}

	public String getUserId() {
		return this.userId;
	}

	public String getUserPhoneName() {
		return this.userPhoneName;
	}

	public int getUserTypeEmail() {
		return this.userTypeEmail;
	}

	public int getUserTypeFacebook() {
		return this.userTypeFacebook;
	}

	public int getUserTypePhone() {
		return this.userTypePhone;
	}

	public void setUserEmailName(String paramString) {
		this.userEmailName = paramString;
	}

	public void setUserFacebookName(String paramString) {
		this.userFacebookName = paramString;
	}

	public void setUserId(String paramString) {
		this.userId = paramString;
	}

	public void setUserPhoneName(String paramString) {
		this.userPhoneName = paramString;
	}

	public void setUserTypeEmail(int paramInt) {
		this.userTypeEmail = paramInt;
	}

	public void setUserTypeFacebook(int paramInt) {
		this.userTypeFacebook = paramInt;
	}

	public void setUserTypePhone(int paramInt) {
		this.userTypePhone = paramInt;
	}

	public String toString() {
		return "GDBindingUserRecord{userId='" + this.userId + '\''
				+ ", userTypePhone=" + this.userTypePhone + ", userPhoneName='"
				+ this.userPhoneName + '\'' + ", userTypeEmail="
				+ this.userTypeEmail + ", userEmailName='" + this.userEmailName
				+ '\'' + ", userTypeFacebook=" + this.userTypeFacebook
				+ ", userFacebookName='" + this.userFacebookName + '\'' + '}';
	}
}
