package com.example.huanwensdk.mvp.model;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

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
import com.example.huanwensdk.mvp.contract.RegisterContract;
import com.example.huanwensdk.mvp.contract.listener.LoginListener;
import com.example.huanwensdk.mvp.presenter.ExceptionPresenter;
import com.example.huanwensdk.ui.dialog.LoginDialog;
import com.example.huanwensdk.ui.dialog.NoticeDialog;
import com.example.huanwensdk.utils.Constant;
import com.example.huanwensdk.utils.HWControl;
import com.example.huanwensdk.utils.HWUtils;
import com.example.huanwensdk.utils.LogUtils;
import com.example.huanwensdk.utils.MD5;
import com.example.huanwensdk.utils.ResLoader;
import com.example.huanwensdk.utils.Validator;
import com.example.huanwensdk.utils.sp.HWConfigSharedPreferences;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;

/**
 *
 * @Title: RegisterModel.java
 * @Package mvp.model
 * @Description: 注册model
 * @author: Android_ruan
 * @date: 2018-4-2 下午1:47:48
 * @version V1.0
 */
public class RegisterModel implements RegisterContract.RegisterModel {

	Context context;

	RegisterContract.RegisterView registerView;
	LoginListener loginListener;
	private String gameCode;

	ExceptionContract.ExceptionPresenter exceptionPresenter;

	@Override
	public void register(final String user, final String pwd,
						 RegisterContract.RegisterView reView) {

		context = HWControl.getInstance().getContext();
		registerView = reView;
		loginListener = HWControl.getInstance().getLoginListener();
		gameCode = ResLoader.getString(context, "hw_gamecode");
		if(!Validator.isEmail(user)){
			Toast.makeText(context, "请输入正确邮箱", Toast.LENGTH_SHORT).show();
			registerView.callBackRegiterFail();// 回调给dialog界面
			return;
		}

		// 设置字段
		final String machineid = HWUtils.getDeviceId(context);
		final String gamecode = gameCode;
		final String comefrom = "android";
		final String timestamp = HWUtils.getTimestamp();
		final String platform = ResLoader.getString(context, "platform");
		final String cpu = Build.CPU_ABI;
		final String channel = ResLoader.getString(context, "channel");
		if (TextUtils.isEmpty(channel)) {
			Toast.makeText(context, "channel为空", Toast.LENGTH_SHORT).show();
			registerView.callBackRegiterFail();// 回调给dialog界面
			return;
		}
		final String strSign = gamecode + platform + "android" + channel
				+ timestamp + Constant.HW_APP_KEY;
		final String signature = MD5.getMD5(strSign.toLowerCase());
		final String language = HWConfigSharedPreferences.getInstance(
				HWControl.getInstance().getContext()).getLanguage();

		// 请求网络
		StringRequest stringRequest = new StringRequest(Method.POST,
				Constant.HW_REGISTER_URL, new Response.Listener<String>() {

			@Override
			public void onResponse(String response) {
				// TODO Auto-generated method stub
				Log.e("Com", "注册肯定没错--->" + response);
				exceptionPresenter = new ExceptionPresenter();
				try{
					Gson gson = new Gson();
					HWTourLoginTrialResult result = gson.fromJson(response,
							HWTourLoginTrialResult.class);
					Log.e("Com", "gson解释后数据--->" + result.toString());
					int code = Integer.parseInt(result.getCode());
					if (code == 1000) {
						// 1000就是成功
						// 检测数据绑定
						checkBinding(
								Integer.parseInt(result.getCurrentType()),
								result.getUserid(), result.getData());
						saveUserInfo(result);// 保存用户
						callRegister(result, loginListener);
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
						LogUtils.e("注册成功返回字段---->" + response);
					} else if (code == 1001) {
						loginListener.fail(301, result.getMessage());
						Toast.makeText(context, "注册失败"+result.getMessage(), Toast.LENGTH_SHORT).show();
						registerView.callBackRegiterFail();// 回调给dialog界面
					} else {
						loginListener.fail(301, result.getMessage());
						Toast.makeText(context, "注册失败"+result.getMessage(), Toast.LENGTH_SHORT).show();
						LogUtils.e("注册失败返回字段---->" + response);
						registerView.callBackRegiterFail();// 回调给dialog界面
					}
				}catch(JsonSyntaxException e){
					exceptionPresenter.tips(context, e);
				}catch(JsonIOException e){
					exceptionPresenter.tips(context, e);
				}catch(JsonParseException e){
					exceptionPresenter.tips(context, e);
				}catch (Exception e) {
					// TODO: handle exception
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
				map.put("machineid", machineid);
				map.put("gamecode", gamecode);
				map.put("comefrom", comefrom);
				map.put("timestamp", timestamp);
				map.put("platform", platform);
				map.put("username", user);
				map.put("password", pwd);
				map.put("cpu", cpu);
				map.put("channel", channel);
				map.put("signature", signature);
				map.put("language", language);

				LogUtils.e("注册请求地址---->" + Constant.HW_REGISTER_URL);
				LogUtils.e("注册请求字段----->" + map.toString());

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
	/*
	 * private void checkBinding(int currentType, String userId,
	 * List<HWBindingUserAccountInfo> bindUserList) { if ((bindUserList != null)
	 * && (bindUserList.size() > 0)) { HWBindingUserRecord
	 * localFGBindingUserRecord = new HWBindingUserRecord();
	 * localFGBindingUserRecord.setUserId(userId); // 循环或许Data里面的数据 for
	 * (HWBindingUserAccountInfo localFGBindingUserAccountInfo : bindUserList) {
	 *
	 * int type = localFGBindingUserAccountInfo.getType(); if (type == 4) {
	 * localFGBindingUserRecord.setUserTypePhone(type); localFGBindingUserRecord
	 * .setUserPhoneName(localFGBindingUserAccountInfo .getUsername()); }
	 *
	 * if (type == 3) { localFGBindingUserRecord.setUserTypeEmail(type);
	 * localFGBindingUserRecord .setUserEmailName(localFGBindingUserAccountInfo
	 * .getUsername()); }
	 *
	 * if (type != 5) {
	 *
	 * } localFGBindingUserRecord.setUserTypeFacebook(type);
	 * localFGBindingUserRecord
	 * .setUserFacebookName(localFGBindingUserAccountInfo .getUsername()); } //
	 * 然后保存到数据库
	 * DBUtils.getInstance().saveBindUserRecord(localFGBindingUserRecord); } }
	 */
	/**
	 * 设置保存绑定信息
	 *
	 * @param paramInt
	 * @param paramString
	 * @param paramList
	 */
	private void checkBinding(int currentType, String userId,
							  HWBindingUserAccountInfo bindUserList) {

		if (bindUserList != null) {

			HWBindingUserRecord localFGBindingUserRecord = new HWBindingUserRecord();
			localFGBindingUserRecord.setUserId(userId);
			localFGBindingUserRecord.setUserTypePhone(bindUserList.getType());
			localFGBindingUserRecord.setUserPhoneName(bindUserList
					.getUsername());
			// 然后保存到数据库
			DBUtils.getInstance().saveBindUserRecord(localFGBindingUserRecord);
		}
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
		infoUser.setUsername(loginResult.getData().getUsername());
		// GuestFindDialog.getInstance().show();

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
	public void callRegister(HWTourLoginTrialResult result,
							 LoginListener loginListener) {
		LogUtils.e("注册成功---->"+loginListener);
		User user = new User();
		user.setLoginType(Integer.parseInt(result.getCurrentType()));
		user.setSessionId(result.getSessionid());
		user.setToken(result.getToken());
		user.setUserId(result.getUserid());
		loginListener.onLogin(user,signLogin(user));
		registerView.callBackRegiterSucess();// 回调给dialog界面
		LoginDialog.getInstance().close();
	}

	private String signLogin(User user){
		StringBuilder sign = new StringBuilder();
		sign.append(user.getUserId());
		sign.append(user.getToken());
		sign.append(user.getSessionId());
		sign.append(user.getLoginType());
		String loginSign = MD5.getMD5(sign.toString());
		LogUtils.e("生成Login签名---->"+loginSign);
		return loginSign;
	}

	/**
	 * 返回网络请求错误
	 *
	 * @param error
	 */
	public void callbackError(VolleyError error) {

		String errorStr = VolleyErrorHelper.getMessage(error, context);

		loginListener.fail(101, errorStr);
		registerView.callBackRegiterFail();// 回调给dialog界面
	}

}
