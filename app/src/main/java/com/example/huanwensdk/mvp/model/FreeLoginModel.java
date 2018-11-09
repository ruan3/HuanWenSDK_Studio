package com.example.huanwensdk.mvp.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
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
import com.example.huanwensdk.bean.loginTrial.HWTourLoginResult;
import com.example.huanwensdk.bean.loginTrial.HWTourLoginTrialResult;
import com.example.huanwensdk.bean.loginTrial.Notice;
import com.example.huanwensdk.bean.user.HWInfoUser;
import com.example.huanwensdk.http.RequestQueueHepler;
import com.example.huanwensdk.http.VolleyErrorHelper;
import com.example.huanwensdk.mvp.contract.FreeLoginContract;
import com.example.huanwensdk.mvp.contract.listener.LoginListener;
import com.example.huanwensdk.ui.dialog.LoginDialog;
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

@SuppressLint("DefaultLocale")
public class FreeLoginModel implements FreeLoginContract.FreeLoginModel {

	Context context;

	LoginListener loginListener;
	private String gameCode;
	@SuppressLint("DefaultLocale")
	@Override
	public void FreeLogin(final String sessionId, final String LoginType,
						  final String showName, final String userId) {
		// TODO Auto-generated method stub

		context = HWControl.getInstance().getContext();
		loginListener = HWControl.getInstance().getLoginListener();
		gameCode = ResLoader.getString(context, "hw_gamecode");
		// 设置字段
		final String machineid = HWUtils.getDeviceId(context);
		final String gamecode = gameCode;
		final String comefrom = "android";
		final String timestamp = HWUtils.getTimestamp();
		final String platform = ResLoader.getString(context, "platform");
		final String channel = ResLoader.getString(context, "channel");
		final String strSign = gamecode + platform + "android" + channel
				+ timestamp + Constant.HW_APP_KEY;
		final String signature = MD5.getMD5(strSign.toLowerCase());
		final String language = HWConfigSharedPreferences.getInstance(
				HWControl.getInstance().getContext()).getLanguage();

		// 请求网络
		StringRequest stringRequest = new StringRequest(Method.POST,
				Constant.HW_LOGIN_FREE, new Response.Listener<String>() {

			@Override
			public void onResponse(String response) {
				// TODO Auto-generated method stub
				Log.e("Com", "自动登录肯定没错--->" + response);
				try{
					Gson gson = new Gson();
					HWTourLoginTrialResult result = gson.fromJson(response,
							HWTourLoginTrialResult.class);
					Log.e("Com", "自动登录gson解释后数据--->" + result.toString());
					int code = Integer.parseInt(result.getCode());
					if (code == 1000) {
						// 1000就是成功
						// 检测数据绑定
						checkBinding(
								Integer.parseInt(result.getCurrentType()),
								result.getUserid(), result.getData());
						saveUserInfo(result);// 保存用户
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
						callLogin(result, loginListener);
						LogUtils.e("自动登录成功返回字段---->" + response);
					} else if (code == 1001) {
						loginListener.fail(301, result.getMessage());
					} else {
						loginListener.fail(301, result.getMessage());
						LoginDialog.getInstance().show();
						LogUtils.e("自动登录失败返回字段---->" + response);
					}
				}catch(JsonIOException e){
					LogUtils.e("JsonIOException---->"+e.toString());
					Toast.makeText(context, ResLoader.getString(context, "json_encode_error"), Toast.LENGTH_SHORT).show();
				}catch(JsonParseException e){
					LogUtils.e("JsonParseException---->"+e.toString());
					Toast.makeText(context, ResLoader.getString(context, "json_encode_error"), Toast.LENGTH_SHORT).show();
				}catch (Exception e) {
					LogUtils.e("Exception---->"+e.toString());
					Toast.makeText(context, ResLoader.getString(context, "json_encode_error"), Toast.LENGTH_SHORT).show();
				}

			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				// TODO Auto-generated method stub
				Log.e("Com", "出错--->" + error.getMessage());
				loginListener.fail(-1, "请求失败");
			}
		}) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> map = new HashMap<String, String>();
				map.put("machineid", machineid);
				map.put("sessionid", sessionId);
				map.put("gamecode", gamecode);
				map.put("type", LoginType);
				map.put("comefrom", comefrom);
				map.put("timestamp", timestamp);
				map.put("platform", platform);
				map.put("userid", userId);
				map.put("username", showName);
				map.put("signature", signature);
				map.put("language", language);
				map.put("channel", channel);

				LogUtils.e("自动登录请求地址---->" + Constant.HW_LOGIN_FREE);
				LogUtils.e("自动登录请求字段---->" + map.toString());

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

		HWInfoUser localUser = DBUtils.getInstance().queryInfoUser(
				loginResult.getUserid());
		if (localUser != null) {
			infoUser.setShowname(localUser.getShowname());
		}

		DBUtils.getInstance().saveInfoUser(infoUser);
		HWConfigSharedPreferences.getInstance(context).setUserId(
				loginResult.getUserid());

	}

	/**
	 * 返回给调用
	 *
	 * @param result
	 * @param loginListener
	 */
	public void callLogin(HWTourLoginTrialResult result,
						  LoginListener loginListener) {

		User user = new User();
		user.setLoginType(Integer.parseInt(result.getCurrentType()));
		user.setSessionId(result.getSessionid());
		user.setToken(result.getToken());
		user.setUserId(result.getUserid());
		loginListener.onLogin(user,signLogin(user));
		// loginView.callBackRegiterSucess();//回调给dialog界面
		LoginDialog.getInstance().close();
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
		// loginView.callBackRegiterFail();//回调给dialog界面
		LoginDialog.getInstance().close();
	}

}
