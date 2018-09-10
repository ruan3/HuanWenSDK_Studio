package com.example.huanwensdk.DataBase.infoUser;

import org.xutils.DbManager;
import org.xutils.ex.DbException;

import com.example.huanwensdk.base.HWApplication;
import com.example.huanwensdk.bean.user.HWInfoUser;
import com.example.huanwensdk.utils.LogUtils;

/**
 * 
 * @Title:  BingUserDB.java   
 * @Package DataBase.bindUser   
 * @Description:    保存已经绑定用户（数据库操作）
 * @author: Android_ruan     
 * @date:   2018-3-27 下午2:49:27   
 * @version V1.0
 */
public class InfoUserDB {
	 //获取数据库管理器
    DbManager manager ;
	private InfoUserDB(){
		manager = HWApplication.getInstance().getDbManager();
	}
	
	private static class InfoUserDBHelper{
		
		private static InfoUserDB bindUserDB = new InfoUserDB();
		
	}
	
	public static InfoUserDB getInstance(){
		return InfoUserDBHelper.bindUserDB;
	}
	
	/**
	 * 保存
	 * @param hwBindingUserRecord
	 * //db.save(user);//保存成功之后【不会】对user的主键进行赋值绑定
        //db.saveOrUpdate(user);//保存成功之后【会】对user的主键进行赋值绑定
        //db.saveBindingId(user);//保存成功之后【会】对user的主键进行赋值绑定,并返回保存是否成功
	 */
	public void save(HWInfoUser infoUser){
		LogUtils.e("运行infoUserDb保存---->"+infoUser);
		if(infoUser!=null){
			   //先根据userID去查数据库，有就更新，没就保存
	        HWInfoUser userRecord = query(infoUser.getUserid());
			if(userRecord == null){
				//保存
				try {
					manager.saveBindingId(infoUser);
				} catch (DbException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				LogUtils.e("保存成功");
			}else{
				//更新
				try {
					manager.update(infoUser);
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
	public HWInfoUser query(String userId) {
		
		HWInfoUser record;
		try {
			record = manager.findById(HWInfoUser.class, userId);
			LogUtils.e("查询到user---->"+record);
			if(record!=null){
				
				LogUtils.e("查询到user---->"+record.toString());
			}
			return record;
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 删除数据
	 * @param hwBindingUserRecord
	 */
	public void delete(HWInfoUser infoUser){
		
		try {
			manager.delete(infoUser);
			LogUtils.e("删除记录成功");
			query(infoUser.getUserid());
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 更新数据
	 * @param hwBindingUserRecord
	 */
	public void updata(HWInfoUser infoUser){
		
		try {
			manager.update(infoUser);
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
