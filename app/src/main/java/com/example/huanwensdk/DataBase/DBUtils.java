package com.example.huanwensdk.DataBase;

import java.util.List;

import com.example.huanwensdk.DataBase.bindUser.BindUserDB;
import com.example.huanwensdk.DataBase.infoUser.InfoUserDB;
import com.example.huanwensdk.DataBase.infoUser.RoleInfoDB;
import com.example.huanwensdk.DataBase.server.ServerDB;
import com.example.huanwensdk.bean.loginTrial.HWBindingUserRecord;
import com.example.huanwensdk.bean.server.Server;
import com.example.huanwensdk.bean.user.HWInfoUser;
import com.example.huanwensdk.bean.user.RoleInfo;
import com.example.huanwensdk.utils.HWControl;
import com.example.huanwensdk.utils.sp.HWConfigSharedPreferences;

public class DBUtils {

	private DBUtils(){}
	
	private static class DBUtilsHelper{
		
		private static DBUtils dbUtils = new DBUtils();
		
	}
	
	public static DBUtils getInstance(){
		
		return DBUtilsHelper.dbUtils;
		
	}
	
	/**
	 * 保存绑定用户记录
	 * @param hwBindingUserRecord
	 */
	public void saveBindUserRecord(HWBindingUserRecord hwBindingUserRecord){
		
		BindUserDB.getInstance().save(hwBindingUserRecord);
		
	}
	
	/**
	 * 更新绑定用户记录
	 * @param hwBindingUserRecord
	 */
	public void updataBindUserRecord(HWBindingUserRecord hwBindingUserRecord){
		
		BindUserDB.getInstance().updata(hwBindingUserRecord);
		
	}
	
	/**
	 * 查询绑定用户记录
	 * @param hwBindingUserRecord
	 */
	public void queryBindUserRecord(HWBindingUserRecord hwBindingUserRecord){
		
		BindUserDB.getInstance().query(hwBindingUserRecord.getUserId());
		
	}
	
	/**
	 * 删除绑定用户记录
	 * @param hwBindingUserRecord
	 */
	public void deleteBindUserRecord(HWBindingUserRecord hwBindingUserRecord){
		
		BindUserDB.getInstance().delete(hwBindingUserRecord);
		
	}

	/**
	 * 保存用户
	 * @param infoUser
	 */
	public void saveInfoUser(HWInfoUser infoUser){
		
		HWConfigSharedPreferences.getInstance(HWControl.getInstance().getContext()).setLoginFree(true);
		
		InfoUserDB.getInstance().save(infoUser);
		
	}
	
	/**
	 * 查询用户
	 * @param infoUser
	 */
	public void queryInfoUser(HWInfoUser infoUser){
		
		InfoUserDB.getInstance().query(infoUser.getUserid());
		
	}
	
	/**
	 * 查询用户
	 * @param userId
	 * @return
	 */
	public HWInfoUser queryInfoUser(String userId){
		
		HWInfoUser infoUser = InfoUserDB.getInstance().query(userId);
		if(infoUser != null){
			return infoUser;
		}
		return null;
		
	}
	
	/**
	 * 删除用户
	 * @param infoUser
	 */
	public void deleteInfoUser(HWInfoUser infoUser){
		
		InfoUserDB.getInstance().delete(infoUser);
		
	}
	
	/**
	 * 更新用户
	 * @param infoUser
	 */
	public void updataInfoUser(HWInfoUser infoUser){
		
		InfoUserDB.getInstance().updata(infoUser);
		
	}
	
	/**
	 * 保存服务
	 * @param server
	 */
	public void saveServer(Server server){
		
		ServerDB.getInstance().save(server);
		
	}
	
	/**
	 * 查询服务
	 * @param server
	 */
	public void queryServer(Server server){
		
		ServerDB.getInstance().query(server.getServercode());
		
	}
	/**
	 * 保存角色
	 * @param role
	 */
	public void saveRole(RoleInfo role){
		
		RoleInfoDB.getInstance().save(role);
		
	}
	
	/**
	 * 根据roleid查询角色
	 * @param roleId
	 * @return
	 */
	public RoleInfo queryRole(String roleId){
		
		return RoleInfoDB.getInstance().query(roleId);
		
	}
	
	/**
	 * 根据用户id查询角色
	 * @param userId
	 * @return
	 */
	public List<RoleInfo> querRoleByUserId(String userId){

		List<RoleInfo> roleInfos;
		roleInfos = RoleInfoDB.getInstance().queryByUserid(userId);
		if(roleInfos!=null){
			
			return roleInfos;
		}
		return null;
	}
	
	
}
