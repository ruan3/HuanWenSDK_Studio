package com.example.huanwensdk.bean.loginTrial;

import java.util.List;

/**
 * 
 * @Title:  HWTourLoginResult.java   
 * @Package bean.loginTrial   
 * @Description:    调用试玩接口后，返回的数据类型   
 * @author: Android_ruan     
 * @date:   2018-3-27 上午10:36:21   
 * @version V1.0
 */
public class HWTourLoginResult {

	private String code;
	  private String currentType;
	  private List<HWBindingUserAccountInfo> data;
	  private String message;
	  private String randomPwd;
	  private String sessionid;
	  private String showid;
	  private String showname;
	  private String token;
	  private String userid;
	  
	  private String sms;

	  public String getSms() {
		return sms;
	}

	public void setSms(String sms) {
		this.sms = sms;
	}

	public String getCode()
	  {
	    return this.code;
	  }

	  public String getCurrentType()
	  {
	    return this.currentType;
	  }

	  public List<HWBindingUserAccountInfo> getData()
	  {
	    return this.data;
	  }

	  public String getMessage()
	  {
	    return this.message;
	  }

	  public String getRandomPwd()
	  {
	    return this.randomPwd;
	  }

	  public String getSessionid()
	  {
	    return this.sessionid;
	  }

	  public String getShowid()
	  {
	    return this.showid;
	  }

	  public String getShowname()
	  {
	    return this.showname;
	  }

	  public String getToken()
	  {
	    return this.token;
	  }

	  public String getUserid()
	  {
	    return this.userid;
	  }

	  public void setCode(String paramString)
	  {
	    this.code = paramString;
	  }

	  public void setCurrentType(String paramString)
	  {
	    this.currentType = paramString;
	  }

	  public void setData(List<HWBindingUserAccountInfo> paramList)
	  {
	    this.data = paramList;
	  }

	  public void setMessage(String paramString)
	  {
	    this.message = paramString;
	  }

	  public void setRandomPwd(String paramString)
	  {
	    this.randomPwd = paramString;
	  }

	  public void setSessionid(String paramString)
	  {
	    this.sessionid = paramString;
	  }

	  public void setShowid(String paramString)
	  {
	    this.showid = paramString;
	  }

	  public void setShowname(String paramString)
	  {
	    this.showname = paramString;
	  }

	  public void setToken(String paramString)
	  {
	    this.token = paramString;
	  }

	  public void setUserid(String paramString)
	  {
	    this.userid = paramString;
	  }

	  public String toString()
	  {
	    return "FGTourLogin{code='" + this.code + '\'' + ", sessionid='" + this.sessionid + '\'' + ", token='" + this.token + '\'' + ", userid='" + this.userid + '\'' + ", currentType='" + this.currentType + '\'' + ", randomPwd='" + this.randomPwd + '\'' + ", showid='" + this.showid + '\'' + ", showname='" + this.showname + '\'' + ", message='" + this.message + '\'' + ", data=" + this.data + '}';
	  }
	
}
