package com.example.huanwensdk.mvp.model;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.huanwensdk.DataBase.DBUtils;
import com.example.huanwensdk.DataBase.infoUser.User;
import com.example.huanwensdk.bean.loginTrial.HWBindingUserAccountInfo;
import com.example.huanwensdk.bean.loginTrial.HWBindingUserRecord;
import com.example.huanwensdk.bean.loginTrial.HWTourLoginTrialResult;
import com.example.huanwensdk.bean.loginTrial.Notice;
import com.example.huanwensdk.bean.user.HWInfoUser;
import com.example.huanwensdk.http.RequestQueueHepler;
import com.example.huanwensdk.http.VolleyErrorHelper;
import com.example.huanwensdk.mvp.contract.ExceptionContract;
import com.example.huanwensdk.mvp.contract.InitLoginTrial;
import com.example.huanwensdk.mvp.contract.listener.LoginListener;
import com.example.huanwensdk.mvp.presenter.ExceptionPresenter;
import com.example.huanwensdk.ui.dialog.NoticeDialog;
import com.example.huanwensdk.utils.Constant;
import com.example.huanwensdk.utils.HWBitmap;
import com.example.huanwensdk.utils.HWControl;
import com.example.huanwensdk.utils.HWUtils;
import com.example.huanwensdk.utils.LogUtils;
import com.example.huanwensdk.utils.MD5;
import com.example.huanwensdk.utils.ResLoader;
import com.example.huanwensdk.utils.sp.HWConfigSharedPreferences;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;

public class LoginTrialModel implements InitLoginTrial.LoginTrialModel {

	LoginListener loginListener;
	Context context;

	InitLoginTrial.LoginTrialView loginTrialView;

	private String gameCode;
	
	ExceptionContract.ExceptionPresenter exceptionPresenter;
	
	@Override
	public void loginTrial(InitLoginTrial.LoginTrialView loginTrialView) {
		// TODO Auto-generated method stub
		this.loginTrialView = loginTrialView;
		loginListener = HWControl.getInstance().getLoginListener();
		context = HWControl.getInstance().getContext();
		gameCode = ResLoader.getString(context, "hw_gamecode");
		String machineid = HWUtils.getDeviceId(context);
		final String gamecode = gameCode;
		final String sitecode = "gd";
		final String comefrom = "android";
		final String timestamp = HWUtils.getTimestamp();
		final String platform = ResLoader.getString(context, "platform");
		final String channel = ResLoader.getString(context, "channel");
		final String cpu = Build.CPU_ABI;
		final String strSign = gamecode + platform + "android" + channel
				+ timestamp + "HWGAME#_DR*)HW";
		final String signature = MD5.getMD5(strSign.toLowerCase());
		Log.e("Com", "strSign---->" + strSign.toLowerCase());
		final String language = HWConfigSharedPreferences.getInstance(
				HWControl.getInstance().getContext()).getLanguage();
		// 请求网络
		StringRequest stringRequest = new StringRequest(Method.POST,
				Constant.HW_LOGIN_TRIAL, new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {
						// TODO Auto-generated method stub
						Log.e("Com", "试玩肯定没错--->" + response);
						exceptionPresenter = new ExceptionPresenter();
						try{
							String responseUrl = Uri.decode(response);
							Gson gson = new Gson();
//							HWTourLoginResult result = gson.fromJson(response,
//									HWTourLoginResult.class);
							HWTourLoginTrialResult result = gson.fromJson(response,
									HWTourLoginTrialResult.class);
							Log.e("Com", "gson解释后数据--->" + result.toString());
							int code = Integer.parseInt(result.getCode());
							if (code == 1000) {
								LogUtils.e("试玩登录的成功-返回字段---->" + responseUrl);
								// 1000就是成功
								// 检测数据绑定
								checkBinding(
										Integer.parseInt(result.getCurrentType()),
										result.getUserid(), result.getData());
								// 绑定用户
								saveUserInfo(result);
								if(result.getRandomPwd()!=null){
									// 保存截图
									HWBitmap.saveTrialUIDBitmap(HWControl.getInstance()
											.getContext(), result.getShowid(), result
											.getRandomPwd(), ResLoader.getString(
											context, "game_name"));
								}
								//根据返回状态，显示公告框
								Notice notice = result.getNotice();
								if(notice!=null){
									if(result.getNotice().getVnotice_is_show()!=null){
										if(result.getNotice().getVnotice_is_show().equals("1")){
											NoticeDialog.getInstance().show();
											NoticeDialog.getInstance().getText(result.getNotice().getNotice_info());
										}
									}
								}
								
								
								// callback回去调用方法那里
								callLogin(result, loginListener);
							} else {
								LogUtils.e("试玩登录的失败-返回字段---->" + response);
								loginListener.fail(301, result.getMessage());
							}
						}catch(JsonSyntaxException e){
							exceptionPresenter.tips(context, e);
						}catch(JsonIOException e){
							exceptionPresenter.tips(context, e);
						}catch(JsonParseException e){
							exceptionPresenter.tips(context, e);
						}catch (Exception e) {
							exceptionPresenter.tips(context, e);
						}
						
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub
						Log.e("Com", "出错--->" + error.getMessage());
						callbackError(error);
					}
				}) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> map = new HashMap<String, String>();
				map.put("machineid", HWUtils.getDeviceId(context));
				map.put("gamecode", gamecode);
				map.put("sitecode", sitecode);
				map.put("comefrom", comefrom);
				map.put("timestamp", timestamp);
				map.put("platform", platform);
				map.put("cpu", cpu);
				map.put("signature", signature);
				map.put("language", language);
				map.put("channel", channel);
				LogUtils.e("试玩登录接口地址---->" + Constant.HW_LOGIN_TRIAL);
				LogUtils.e("试玩登录的请求字段---->" + map.toString());

				return map;
			}
		};

		RequestQueueHepler.getInstance().getQueue().add(stringRequest);

	}

	/**
	 * 设置保存绑定信息
	 * 
	 * @param paramInt
	 * @param paramString
	 * @param paramList
	 */
//	 private void checkBinding(int currentType, String userId,
//	 List<HWBindingUserAccountInfo> bindUserList) {
//	 if ((bindUserList != null) && (bindUserList.size() > 0)) {
//	 HWBindingUserRecord localFGBindingUserRecord = new HWBindingUserRecord();
//	 localFGBindingUserRecord.setUserId(userId);
//	 // 循环或许Data里面的数据
//	 for (HWBindingUserAccountInfo localFGBindingUserAccountInfo :
//	 bindUserList) {
//	
//	 int type = localFGBindingUserAccountInfo.getType();
//	 if (type == 4) {
//	 localFGBindingUserRecord.setUserTypePhone(type);
//	 localFGBindingUserRecord
//	 .setUserPhoneName(localFGBindingUserAccountInfo
//	 .getUsername());
//	 }
//	
//	 if (type == 3) {
//	 localFGBindingUserRecord.setUserTypeEmail(type);
//	 localFGBindingUserRecord
//	 .setUserEmailName(localFGBindingUserAccountInfo
//	 .getUsername());
//	 }
//	
//	 if (type != 5) {
//	
//	 }
//	 localFGBindingUserRecord.setUserTypeFacebook(type);
//	 localFGBindingUserRecord
//	 .setUserFacebookName(localFGBindingUserAccountInfo
//	 .getUsername());
//	 }
//	 // 然后保存到数据库
//	 DBUtils.getInstance().saveBindUserRecord(localFGBindingUserRecord);
//	 }
//	 }

	/**
	 * 设置保存绑定信息
	 * 
	 * @param paramInt
	 * @param paramString
	 * @param paramList
	 */
	private void checkBinding(int currentType, String userId,
			HWBindingUserAccountInfo bindUserList) {

		HWBindingUserRecord localFGBindingUserRecord = new HWBindingUserRecord();
		localFGBindingUserRecord.setUserId(userId);
		localFGBindingUserRecord.setUserTypePhone(bindUserList.getType());
		localFGBindingUserRecord.setUserPhoneName(bindUserList.getUsername());
		// 然后保存到数据库
		DBUtils.getInstance().saveBindUserRecord(localFGBindingUserRecord);
	}

	/**
	 * 保存用户类
	 * 
	 * @param loginResult
	 */
	public void saveUserInfo(HWTourLoginTrialResult loginResult) {

		HWInfoUser infoUser = new HWInfoUser();
		infoUser.setUserid(loginResult.getUserid());
		infoUser.setLoginType(Integer.parseInt(loginResult.getCurrentType()));
		infoUser.setSessionid(loginResult.getSessionid());
		infoUser.setToken(loginResult.getToken());
		infoUser.setShowname(loginResult.getShowname());
		infoUser.setShowId(loginResult.getShowid());
		// GuestFindDialog.getInstance().show();
		infoUser.setUsername(loginResult.getData().getUsername());
		DBUtils.getInstance().saveInfoUser(infoUser);
		HWConfigSharedPreferences.getInstance(context).setUserId(
				loginResult.getUserid());

		// GuestFindDialog.getInstance().initData(infoUser);

	}

	/**
	 * 返回给调用
	 * 
	 * @param result
	 * @param loginListener
	 */
	public void callLogin(HWTourLoginTrialResult result,
			LoginListener loginListener) {
		LogUtils.e("试玩登录---->"+loginListener);
		User user = new User();
		user.setLoginType(Integer.parseInt(result.getCurrentType()));
		user.setSessionId(result.getSessionid());
		user.setToken(result.getToken());
		user.setUserId(result.getUserid());
		loginListener.onLogin(user);
		loginTrialView.callbackLogin();// 回调给dialog界面
	}

	/**
	 * 返回网络请求错误
	 * 
	 * @param error
	 */
	public void callbackError(VolleyError error) {

		String errorStr = VolleyErrorHelper.getMessage(error, context);

		loginListener.fail(101, errorStr);
		loginTrialView.callbackLogin();// 回调给dialog界面
	}

}
