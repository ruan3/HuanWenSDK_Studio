package com.example.huanwensdk.bean.wxlogin;

public class WeChatUserBean {

	String city;
	String country;
	String headimgurl;
	String language;
	String nickname;
	String openid;
	String province;
	String sex;
	String unionid;
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getHeadimgurl() {
		return headimgurl;
	}
	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getUnionid() {
		return unionid;
	}
	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}
	@Override
	public String toString() {
		return "WeChatUserBean [city=" + city + ", country=" + country
				+ ", headimgurl=" + headimgurl + ", language=" + language
				+ ", nickname=" + nickname + ", openid=" + openid
				+ ", province=" + province + ", sex=" + sex + ", unionid="
				+ unionid + "]";
	}
	
	
	
	
}
