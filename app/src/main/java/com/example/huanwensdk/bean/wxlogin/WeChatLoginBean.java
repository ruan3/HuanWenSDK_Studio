package com.example.huanwensdk.bean.wxlogin;

public class WeChatLoginBean {

	String access_token;
	String expires_in;
	String openid;
	String refresh_token;
	String scope;
	String unionid;
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public String getExpires_in() {
		return expires_in;
	}
	public void setExpires_in(String expires_in) {
		this.expires_in = expires_in;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getRefresh_token() {
		return refresh_token;
	}
	public void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	public String getUnionid() {
		return unionid;
	}
	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}
	@Override
	public String toString() {
		return "WeChatLoginBean [access_token=" + access_token
				+ ", expires_in=" + expires_in + ", openid=" + openid
				+ ", refresh_token=" + refresh_token + ", scope=" + scope
				+ ", unionid=" + unionid + "]";
	}
	
}
