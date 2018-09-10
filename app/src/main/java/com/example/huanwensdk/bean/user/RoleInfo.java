package com.example.huanwensdk.bean.user;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * 
 * @Title:  RoleInfo.java   
 * @Package bean.user   
 * @Description:   角色实体类
 * @author: Android_ruan     
 * @date:   2018-4-13 下午4:32:28   
 * @version V1.0
 */
@Table(name = "hw_role")
public class RoleInfo {

	@Column(name = "roleId",isId = true)//注释列名
	String roleId;//角色id
	
	@Column(name = "roleName")//注释列名
	String roleName;//角色名称
	
	@Column(name = "roleLevel")//注释列名
	String roleLevel;//角色等级
	
	@Column(name = "eventType")//注释列名
	int eventType;
	
	@Column(name = "serverCode")
	String serverCode;//服务器代码
	
	@Column(name = "serverName")
	String serverName;//服务器名称
	
	public String getServerCode() {
		return serverCode;
	}
	public void setServerCode(String serverCode) {
		this.serverCode = serverCode;
	}
	
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	@Column(name = "userId")
	String userId;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleLevel() {
		return roleLevel;
	}
	public void setRoleLevel(String roleLevel) {
		this.roleLevel = roleLevel;
	}
	public int getEventType() {
		return eventType;
	}
	public void setEventType(int eventType) {
		this.eventType = eventType;
	}
	@Override
	public String toString() {
		return "RoleInfo [roleId=" + roleId + ", roleName=" + roleName
				+ ", roleLevel=" + roleLevel + ", eventType=" + eventType + "]";
	}
	
}
