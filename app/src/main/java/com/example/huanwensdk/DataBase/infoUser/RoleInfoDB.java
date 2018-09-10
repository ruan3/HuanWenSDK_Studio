package com.example.huanwensdk.DataBase.infoUser;

import java.util.List;

import org.xutils.DbManager;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.ex.DbException;

import com.example.huanwensdk.base.HWApplication;
import com.example.huanwensdk.bean.user.RoleInfo;
import com.example.huanwensdk.utils.HWControl;
import com.example.huanwensdk.utils.LogUtils;
import com.example.huanwensdk.utils.sp.HWConfigSharedPreferences;

/**
 * 
 * @Title:  RoleInfoDB.java   
 * @Package DataBase.infoUser   
 * @Description:    角色数据库操作类
 * @author: Android_ruan     
 * @date:   2018-4-13 下午4:35:54   
 * @version V1.0
 */
public class RoleInfoDB {
	 //获取数据库管理器
    DbManager manager ;
	private RoleInfoDB(){
		manager = HWApplication.getInstance().getDbManager();
	}
	
	private static class RoleInfoDBHepler{
		
		private static RoleInfoDB roleInfoDB = new RoleInfoDB();
		
	}
	
	public static RoleInfoDB getInstance(){
		return RoleInfoDBHepler.roleInfoDB;
	}
	
	/**
	 * 保存
	 * @param hwBindingUserRecord
	 * //db.save(user);//保存成功之后【不会】对user的主键进行赋值绑定
        //db.saveOrUpdate(user);//保存成功之后【会】对user的主键进行赋值绑定
        //db.saveBindingId(user);//保存成功之后【会】对user的主键进行赋值绑定,并返回保存是否成功
	 */
	public void save(RoleInfo role){
		LogUtils.e("运行RoleInfo保存---->"+role);
		//保存roleid
		HWConfigSharedPreferences.getInstance(HWControl.getInstance().getContext()).setRoleId(role.getRoleId());
		//绑定userid
		role.setUserId(HWConfigSharedPreferences.getInstance(HWControl.getInstance().getContext()).getUserId());
		if(role!=null){
			   //先根据userID去查数据库，有就更新，没就保存
			RoleInfo userRecord = query(role.getRoleId());
			if(userRecord == null){
				//保存
				try {
					manager.saveBindingId(role);
				} catch (DbException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				LogUtils.e("保存成功");
			}else{
				//更新
				try {
					manager.update(role);
				} catch (DbException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				LogUtils.e("更新成功");
			}
		}
	}

	/**
	 * 查询对象
	 * @param userId
	 * @return
	 */
	public RoleInfo query(String roleId) {
		
		RoleInfo record;
		try {
			record = manager.findById(RoleInfo.class, roleId);
			LogUtils.e("查询到role---->"+record);
			if(record!=null){
				
				LogUtils.e("查询到role---->"+record.toString());
			}
			return record;
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 根据账号id查询
	 * @param userId
	 * @return
	 */
	public List<RoleInfo> queryByUserid(String userId){
		
		List<RoleInfo> roleInfos;
		
		 //加条件查询 根据userId查询
        WhereBuilder b = WhereBuilder.b();
		b.and(userId, "=", userId);
		try {
			roleInfos = manager.selector(RoleInfo.class).where(b).findAll();
			return roleInfos;
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 删除数据
	 * @param hwBindingUserRecord
	 */
	public void delete(RoleInfo role){
		
		try {
			manager.delete(role);
			LogUtils.e("删除记录成功");
			query(role.getRoleId());
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 更新数据
	 * @param hwBindingUserRecord
	 */
	public void updata(RoleInfo role){
		
		try {
			manager.update(role);
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
