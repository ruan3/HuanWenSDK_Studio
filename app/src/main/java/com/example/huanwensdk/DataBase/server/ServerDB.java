package com.example.huanwensdk.DataBase.server;

import org.xutils.DbManager;
import org.xutils.ex.DbException;

import com.example.huanwensdk.base.HWApplication;
import com.example.huanwensdk.bean.server.Server;
import com.example.huanwensdk.utils.LogUtils;

/**
 * 
 * @Title:  ServerDB.java   
 * @Package DataBase.server   
 * @Description:    服务器本地保存
 * @author: Android_ruan     
 * @date:   2018-4-13 下午3:21:06   
 * @version V1.0
 */
public class ServerDB {
	//获取数据库管理器
    DbManager manager ;
	private ServerDB(){
		manager = HWApplication.getInstance().getDbManager();
	}
	
	private static class ServerDBHepler{
		
		private static ServerDB serDb = new ServerDB();
		
	}
	
	public static ServerDB getInstance(){
		
		return ServerDBHepler.serDb;
		
	}
	
	/**
	 * 保存
	 * @param server
	 */
	public void save(Server server){
		
		if(server!=null){
			
			Server myServer = query(server.getServercode());
			
			if(myServer == null){
				try {
					manager.saveBindingId(server);
				} catch (DbException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				LogUtils.e("保存成功");
			}else{
				
				updata(server);
				
			}
		}
		
	}
	
	/**
	 * 查询服务器
	 * @param serverCode
	 * @return
	 */
	public Server query(String serverCode){
		
		Server server;
		
		try {
			server = manager.findById(Server.class, serverCode);
			LogUtils.e("查询到server---->"+server);
			if(server!=null){
				
				LogUtils.e("查询到server---->"+server.toString());
			}
			return server;
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 删除
	 * @param server
	 */
	public void delete(Server server){
		
		try {
			manager.delete(server);
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 更新服务器
	 * @param server
	 */
	public void updata(Server server){
		
		try {
			manager.update(server);
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
