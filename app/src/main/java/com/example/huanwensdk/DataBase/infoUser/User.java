package com.example.huanwensdk.DataBase.infoUser;

public class User {
	
	private String userId;
	private String token;
	private	 String sessionId;
	private int LoginType;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public int getLoginType() {
		return LoginType;
	}
	public void setLoginType(int loginType) {
		LoginType = loginType;
	}
	@Override
	public String toString() {
		return "User [userId=" + userId + ", token=" + token + ", sessionId="
				+ sessionId + ", LoginType=" + LoginType + "]";
	}

	
	
}
