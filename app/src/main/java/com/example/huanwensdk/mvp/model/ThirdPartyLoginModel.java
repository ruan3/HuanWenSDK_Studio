package com.example.huanwensdk.mvp.model;

import java.util.HashMap;
import java.util.Map;

import org.xutils.x;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

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
import com.example.huanwensdk.bean.user.HWInfoUser;
import com.example.huanwensdk.bean.wxlogin.WeChatLoginBean;
import com.example.huanwensdk.bean.wxlogin.WeChatUserBean;
import com.example.huanwensdk.http.RequestQueueHepler;
import com.example.huanwensdk.http.VolleyErrorHelper;
import com.example.huanwensdk.mvp.contract.ExceptionContract;
import com.example.huanwensdk.mvp.contract.FacebookContract;
import com.example.huanwensdk.mvp.contract.InitLoginTrial;
import com.example.huanwensdk.mvp.contract.ThirdPartyLoginContract;
import com.example.huanwensdk.mvp.contract.listener.LoginListener;
import com.example.huanwensdk.mvp.presenter.ExceptionPresenter;
import com.example.huanwensdk.utils.Constant;
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
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class ThirdPartyLoginModel implements ThirdPartyLoginContract.Model {

	private IWXAPI weChatApi;

	Context context;
	WeChatLoginBean weChatLoginBean;
	WeChatUserBean weChatUserBean;

	LoginListener loginListener;

	InitLoginTrial.LoginTrialView loginTrialView;

	private String gameCode;

	ExceptionContract.ExceptionPresenter exceptionPresenter;

	/**
	 * 微信登录
	 */
	@Override
	public void wechatLogin() {
		// TODO Auto-generated method stub
		context = HWControl.getInstance().getContext();
		weChatApi = WXAPIFactory.createWXAPI(context, Constant.WX_APP_ID);
		weChatApi.registerApp(Constant.WX_APP_ID);
		SendAuth.Req req = new SendAuth.Req();
		req.scope = "snsapi_userinfo";//
		req.state = "wechat_sdk_demo_test";
		weChatApi.sendReq(req);
	}

	@Override
	public void qqLogin() {
		// TODO Auto-generated method stub

	}

	@Override
	public void facebookLogin() {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("unchecked")
	@Override
	public void getWechatToken(String code,InitLoginTrial.LoginTrialView loginView) {
		// TODO Auto-generated method stub
		this.loginTrialView = loginView;

		//设置Url地址
		RequestParams entity = new RequestParams("https://api.weixin.qq.com/sns/oauth2/access_token?");
		entity.addQueryStringParameter("appid", Constant.WX_APP_ID);
		entity.addQueryStringParameter("secret", Constant.WX_APP_SECRET);
		entity.addQueryStringParameter("code", code);
		entity.addQueryStringParameter("grant_type", "authorization_code");
		//数据请求，这里先要设置回到的call接口对象,数据在接口对象的方法中获取
		x.http().get(entity, call);

	}

	/**
	 * 网络请求时的callBack回调对象
	 */
	Callback.CommonCallback call = new Callback.CommonCallback<String>() {
		@Override
		public void onSuccess(String result) {
//            Toast.makeText(x.app(), result, Toast.LENGTH_LONG).show();
			Log.e("Com", "微信获取token成功---->"+result);
			Gson gson = new Gson();
			weChatLoginBean = gson.fromJson(result, WeChatLoginBean.class);
		}

		@Override
		public void onError(Throwable ex, boolean isOnCallback) {
			Log.e("Com", "微信获取token失败---->"+ex.toString());
		}

		@Override
		public void onCancelled(Callback.CancelledException cex) {
			Log.e("Com", "微信获取token取消---->"+cex.toString());
		}

		@Override
		public void onFinished() {
			Log.e("Com", "请求完成");
			if(weChatLoginBean!=null){
				GetweChatUser();
			}
		}
	};

	/**
	 * 获取微信用户信息
	 */
	private void GetweChatUser(){


		//设置Url地址
		RequestParams entity = new RequestParams("https://api.weixin.qq.com/sns/userinfo");
		entity.addQueryStringParameter("access_token", weChatLoginBean.getAccess_token());
		entity.addQueryStringParameter("openid", weChatLoginBean.getOpenid());
		//数据请求，这里先要设置回到的call接口对象,数据在接口对象的方法中获取
		x.http().get(entity, WechatCall);

	}

	/**
	 * 网络请求时的callBack回调对象
	 */
	Callback.CommonCallback WechatCall = new Callback.CommonCallback<String>() {
		@Override
		public void onSuccess(String result) {
//            Toast.makeText(x.app(), result, Toast.LENGTH_LONG).show();
			Log.e("Com", "微信获取user成功---->"+result);
			Gson gson = new Gson();
			weChatUserBean = gson.fromJson(result, WeChatUserBean.class);
		}

		@Override
		public void onError(Throwable ex, boolean isOnCallback) {
			Log.e("Com", "微信获取user失败---->"+ex.toString());
		}

		@Override
		public void onCancelled(Callback.CancelledException cex) {
			Log.e("Com", "微信获取user取消---->"+cex.toString());
		}

		@Override
		public void onFinished() {
			Log.e("Com", "请求完成");
			if(weChatUserBean!=null){
				loginTrial(loginTrialView, weChatUserBean.getOpenid(), "wx");
			}
		}
	};


	public void loginTrial(InitLoginTrial.LoginTrialView loginTrialView,final String user_token,final String type) {
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
				Constant.Third_Party_Login, new Response.Listener<String>() {

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
						// 保存截图
//								HWBitmap.saveTrialUIDBitmap(HWControl.getInstance()
//										.getContext(), result.getShowid(), result
//										.getRandomPwd(), ResLoader.getString(
//										context, "game_name"));
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
				map.put("machineid", HWUtils.getDeviceId(context));
				map.put("gamecode", gamecode);
				map.put("sitecode", sitecode);
				map.put("comefrom", comefrom);
				map.put("timestamp", timestamp);
				map.put("platform", platform);
				map.put("cpu", cpu);
				map.put("signature", signature);
				map.put("language", language);
				map.put("channel", "google1-1");
				map.put("user_token", user_token);
				map.put("login_mothed", type);
				LogUtils.e("试玩登录接口地址---->" + Constant.Third_Party_Login);
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
	public void callLogin(HWTourLoginTrialResult result,
						  LoginListener loginListener) {

		User user = new User();
		user.setLoginType(Integer.parseInt(result.getCurrentType()));
		user.setSessionId(result.getSessionid());
		user.setToken(result.getToken());
		user.setUserId(result.getUserid());
		loginListener.onLogin(user,signLogin(user));
		loginTrialView.callbackLogin();// 回调给dialog界面
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
		loginTrialView.callbackLogin();// 回调给dialog界面
	}
}
