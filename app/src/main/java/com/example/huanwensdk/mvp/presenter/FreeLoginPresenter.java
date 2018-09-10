package com.example.huanwensdk.mvp.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.example.huanwensdk.DataBase.DBUtils;
import com.example.huanwensdk.bean.user.HWInfoUser;
import com.example.huanwensdk.mvp.contract.FreeLoginContract;
import com.example.huanwensdk.mvp.model.FreeLoginModel;
import com.example.huanwensdk.utils.DialogUtils;
import com.example.huanwensdk.utils.HWControl;
import com.example.huanwensdk.utils.LogUtils;
import com.example.huanwensdk.utils.sp.HWConfigSharedPreferences;

/**
 * 
 * @Title:  FreeLoginPresenter.java   
 * @Package mvp.presenter   
 * @Description:    处理自动登录
 * @author: Android_ruan     
 * @date:   2018-4-11 上午11:26:49   
 * @version V1.0
 */
public class FreeLoginPresenter implements FreeLoginContract.FreeLoginPresenter{

	Context context;
	FreeLoginContract.FreeLoginModel model;
	
	@Override
	public void Login() {
		// TODO Auto-generated method stub
		context = HWControl.getInstance().getContext();
		boolean isLoginFree = HWConfigSharedPreferences.getInstance(context).isLoginFree();
		if(isLoginFree){
			//走自动登录流程
			String userId = HWConfigSharedPreferences.getInstance(context).getUserId();
			LogUtils.e("userId---->"+userId);
			HWInfoUser infoUser = DBUtils.getInstance().queryInfoUser(userId);
			if(infoUser!=null){
				//走自动登录流程
				if(!TextUtils.isEmpty(infoUser.getShowname())){
					FreeLogin(infoUser.getSessionid(), infoUser.getLoginType()+"", infoUser.getShowname(), infoUser.getUserid());
				}else{
					
					//走登录流程
					DialogUtils.getInstance().showLogin(context);
				}
			}else{
				LogUtils.e("自动登录获取的用户对象为空");
				//走登录流程
				DialogUtils.getInstance().showLogin(context);
			}
		}else{
			//走登录流程
			DialogUtils.getInstance().showLogin(context);
		}
		
	}

	@Override
	public void FreeLogin(String sessionId, String LoginType, String showName,
			String userId) {
		// TODO Auto-generated method stub
		
		if(model == null){
			
			model = new FreeLoginModel();
			
		}
		model.FreeLogin(sessionId, LoginType, showName, userId);
		
	}

}
