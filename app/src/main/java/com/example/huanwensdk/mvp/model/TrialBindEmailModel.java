package com.example.huanwensdk.mvp.model;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.huanwensdk.bean.loginTrial.HWTourLoginTrialResult;
import com.example.huanwensdk.http.RequestQueueHepler;
import com.example.huanwensdk.mvp.contract.ExceptionContract;
import com.example.huanwensdk.mvp.contract.TrialBindEmailContract;
import com.example.huanwensdk.mvp.presenter.ExceptionPresenter;
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

public class TrialBindEmailModel implements TrialBindEmailContract.TrialBindEmailModle{

	Context context;
	private String gameCode;
	
	ExceptionContract.ExceptionPresenter exceptionPresenter;
	
	@Override
	public void trialBindingEmail(final String uid,final String randpwd,final String pwd,
			final String email,final TrialBindEmailContract.TrialBIndEmailView trialBIndEmailView) {
		// TODO Auto-generated method stub
		context = HWControl.getInstance().getContext();
		
		if(!Validator.isEmail(email)){
			Toast.makeText(context, "请输入正确邮箱", Toast.LENGTH_SHORT).show();
			trialBIndEmailView.trialBindEmailFail("请输入正确邮箱");
			return;
		}
		gameCode = ResLoader.getString(context, "hw_gamecode");
		final String machineid = HWUtils.getDeviceId(context);
		final String gamecode = gameCode;
		final String comefrom = "android";
		final String sitecode = "gd";
		final String timestamp = HWUtils.getTimestamp();
		final String platform = ResLoader.getString(context, "platform");
		final String channel = ResLoader.getString(context, "channel");
		if (TextUtils.isEmpty(channel)) {
			Toast.makeText(context, "channel为空", Toast.LENGTH_SHORT).show();
			trialBIndEmailView.trialBindEmailFail("channel为空");
			return;
		}
		final String strSign = gamecode + platform + "android" + channel
				+ timestamp + Constant.HW_APP_KEY;
		final String signature = MD5.getMD5(strSign.toLowerCase());
		final String language = HWConfigSharedPreferences.getInstance(
				HWControl.getInstance().getContext()).getLanguage();
		
		// 请求网络
				StringRequest stringRequest = new StringRequest(Method.POST,
						Constant.HW_BING_EMAIL, new Response.Listener<String>() {

							@Override
							public void onResponse(String response) {
								// TODO Auto-generated method stub
								Log.e("Com", "邮箱绑定账号肯定没错--->" + response);
								exceptionPresenter = new ExceptionPresenter();
								try{
									Gson gson = new Gson();
									HWTourLoginTrialResult result = gson.fromJson(response,
											HWTourLoginTrialResult.class);
									Log.e("Com", "gson解释后数据--->" + result.toString());
									int code = Integer.parseInt(result.getCode());
									if (code == 1000) {
										// 1000就是成功
										trialBIndEmailView.trialBindEmailSuccess(result.getMessage());
										LogUtils.e("试玩账号绑定邮箱成功返回字段----->"+response);
									} else {
										trialBIndEmailView.trialBindEmailFail(result.getMessage());
										LogUtils.e("试玩账号绑定邮箱失败返回字段----->"+response);
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
								trialBIndEmailView.trialBindEmailFail("请求服务器错误");
							}
						}) {
					@Override
					protected Map<String, String> getParams() throws AuthFailureError {
						Map<String, String> map = new HashMap<String, String>();
						map.put("showid", uid);
						map.put("email", email);
						map.put("password", pwd);
						map.put("randPwd", randpwd);
						map.put("sitecode", sitecode);
						map.put("gamecode", gamecode);
						map.put("comefrom", comefrom);
						map.put("timestamp", timestamp);
						map.put("platform", platform);
						map.put("signature", signature);
						map.put("language", language);
						map.put("machineid", machineid);
						map.put("channel", channel);
						
						LogUtils.e("试玩账号绑定邮箱请求字段----->"+map.toString());
						LogUtils.e("试玩账号绑定邮箱请求地址---->"+Constant.HW_BING_EMAIL);
						
						return map;
					}
				};
				RequestQueueHepler.getInstance().getQueue().add(stringRequest);
		
	}

}
