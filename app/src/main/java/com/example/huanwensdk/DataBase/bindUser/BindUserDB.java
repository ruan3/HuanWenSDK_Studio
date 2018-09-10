package com.example.huanwensdk.DataBase.bindUser;

import org.xutils.DbManager;
import org.xutils.ex.DbException;

import com.example.huanwensdk.base.HWApplication;
import com.example.huanwensdk.bean.loginTrial.HWBindingUserRecord;
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
public class BindUserDB {
	 //获取数据库管理器
    DbManager manager ;
	private BindUserDB(){
		manager = HWApplication.getInstance().getDbManager();
	}
	
	private static class BindUserDBHelper{
		
		private static BindUserDB bindUserDB = new BindUserDB();
		
	}
	
	public static BindUserDB getInstance(){
		return BindUserDBHelper.bindUserDB;
	}
	
	/**
	 * 保存
	 * @param hwBindingUserRecord
	 * //db.save(user);//保存成功之后【不会】对user的主键进行赋值绑定
        //db.saveOrUpdate(user);//保存成功之后【会】对user的主键进行赋值绑定
        //db.saveBindingId(user);//保存成功之后【会】对user的主键进行赋值绑定,并返回保存是否成功
	 */
	public void save(HWBindingUserRecord hwBindingUserRecord){
		LogUtils.e("运行bindUserDb保存---->"+hwBindingUserRecord);
		if(hwBindingUserRecord!=null){
			   //先根据userID去查数据库，有就更新，没就保存
	        HWBindingUserRecord userRecord = query(hwBindingUserRecord.getUserId());
			if(userRecord == null){
				//保存
				try {
					manager.saveBindingId(hwBindingUserRecord);
				} catch (DbException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				LogUtils.e("保存成功");
			}else{
				//更新
				try {
					manager.update(hwBindingUserRecord);
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
	public HWBindingUserRecord query(String userId) {
		
		HWBindingUserRecord record;
		try {
			record = manager.findById(HWBindingUserRecord.class, userId);
			LogUtils.e("查询到绑定user---->"+record);
			if(record!=null){
				
				LogUtils.e("查询到绑定user---->"+record.toString());
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
	public void delete(HWBindingUserRecord hwBindingUserRecord){
		
		try {
			manager.delete(hwBindingUserRecord);
			LogUtils.e("删除记录成功");
			query(hwBindingUserRecord.getUserId());
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 更新数据
	 * @param hwBindingUserRecord
	 */
	public void updata(HWBindingUserRecord hwBindingUserRecord){
		
		try {
			manager.update(hwBindingUserRecord);
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
