package com.example.huanwensdk.bean.user;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * 
 * @Title:  HWInfoUser.java   
 * @Package bean.user   
 * @Description:    用户类保存
 * @author: Android_ruan     
 * @date:   2018-3-27 下午5:01:42   
 * @version V1.0
 */
@Table(name = "hw_user")
public class HWInfoUser {
	@Column(name = "loginType")//注释列名
	private int loginType;
	
	@Column(name = "sessionid")//注释列名
	  private String sessionid;
	
	@Column(name = "showname")//注释列名
	  private String showname;
	
	@Column(name = "token")//注释列名
	  private String token;
	
	@Column(name = "userid",isId = true)//注释列名
	  private String userid;
	
	@Column(name = "username")//注释列名
	private String username;
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "showid")
		private String showId;
	
	  public String getShowId() {
		return showId;
	}

	public void setShowId(String showId) {
		this.showId = showId;
	}

	public int getLoginType()
	  {
	    return this.loginType;
	  }

	  public String getSessionid()
	  {
	    return this.sessionid;
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

	  public void setLoginType(int paramInt)
	  {
	    this.loginType = paramInt;
	  }

	  public void setSessionid(String paramString)
	  {
	    this.sessionid = paramString;
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
	    return "FGInfoUser{loginType=" + this.loginType + ", userid='" + this.userid + '\'' + ", sessionid='" + this.sessionid + '\'' + ", token='" + this.token + '\'' + ", showname='" + this.showname + '\'' + '}';
	  }
}
