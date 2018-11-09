package com.example.huanwensdk.mvp.contract.listener;

import com.example.huanwensdk.DataBase.infoUser.User;

public interface LoginListener {

	void onLogin(User user,String sign);

	void fail(int code,String error);
}
  
 
