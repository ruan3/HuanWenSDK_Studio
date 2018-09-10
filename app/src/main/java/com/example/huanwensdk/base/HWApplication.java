package com.example.huanwensdk.base;

import org.xutils.DbManager;
import org.xutils.x;

import android.app.Application;
import android.content.Context;

import com.example.huanwensdk.utils.HWControl;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.tencent.bugly.crashreport.CrashReport;

public class HWApplication extends Application {

	Context context;
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		context = this;
		x.Ext.init(this);//注册xUtils
		HWControl.getInstance().setContext(context);
		//記錄時間（如果國外的就要接這個，如果國內的就不用接這個。）
		FacebookSdk.sdkInitialize(getApplicationContext());
		AppEventsLogger.activateApp(context);
		//初始化异常日志
		CrashReport.initCrashReport(getApplicationContext(), "87d48a915f", false);

	}
	
	 /**
     * 实现单例，任何一个页面都能拿到这个类的数据和对象
     */
    public HWApplication(){}
    
    private static class HWApplicationHelper{
    	
    	public static HWApplication application = new HWApplication();
    	
    }

    public static HWApplication getInstance() {
       return HWApplicationHelper.application;
    }
	
	 /**
     * 获取数据库的管理器
     * 通过管理器进行增删改查
     */
    public DbManager getDbManager() {
        DbManager.DaoConfig daoconfig = new DbManager.DaoConfig();
        //默认在data/data/包名/database/数据库名称
//        daoconfig.setDbDir()
        daoconfig.setDbName("HWSDK.db");
//        daoconfig.setDbVersion(1);//默认1
        //通过manager进行增删改查
        return x.getDb(daoconfig);
    }
	
}
