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
import com.example.huanwensdk.bean.loginTrial.HWTourLoginResult;
import com.example.huanwensdk.bean.loginTrial.HWTourLoginTrialResult;
import com.example.huanwensdk.http.RequestQueueHepler;
import com.example.huanwensdk.mvp.contract.ForgetPasswordContract;
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

public class ForgetPasswordModel implements
		ForgetPasswordContract.ForgetPasswordModel {

	Context context;
	private String gameCode;

	@Override
	public void sendEmail(final String email,final ForgetPasswordContract.ForgetPassWordView forgetView) {
		// TODO Auto-generated method stub

		context = HWControl.getInstance().getContext();
		gameCode = ResLoader.getString(context, "hw_gamecode");
		if(!Validator.isEmail(email)){
			Toast.makeText(context, "请输入正确邮箱", Toast.LENGTH_SHORT).show();
			forgetView.sendEmailResult("请输入正确邮箱","");
			return;
		}

		final String gamecode = gameCode;
		final String comefrom = "android";
		final String timestamp = HWUtils.getTimestamp();
		final String platform = ResLoader.getString(context, "platform");
		final String channel = ResLoader.getString(context, "channel");
		if (TextUtils.isEmpty(channel)) {
			Toast.makeText(context, "channel为空", Toast.LENGTH_SHORT).show();
			forgetView.sendEmailResult("channel为空","");
			return;
		}
		final String strSign = gamecode + platform + "android" + channel
				+ timestamp + Constant.HW_APP_KEY;
		final String signature = MD5.getMD5(strSign.toLowerCase());
		final String language = HWConfigSharedPreferences.getInstance(
				HWControl.getInstance().getContext()).getLanguage();

		// 请求网络
		StringRequest stringRequest = new StringRequest(Method.POST,
				Constant.HW_FORGET_SEND_EMAIL, new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {
						// TODO Auto-generated method stub
						Log.e("Com", "忘记密码肯定没错--->" + response);
						try{
							Gson gson = new Gson();
							HWTourLoginTrialResult result = gson.fromJson(response,
									HWTourLoginTrialResult.class);
							Log.e("Com", "gson解释后数据--->" + result.toString());
							int code = Integer.parseInt(result.getCode());
							if (code == 1000) {
								// 1000就是成功
								forgetView.sendEmailResult(result.getMessage(),result.getSms());
								LogUtils.e("忘记密码 的获取验证码成功 返回字段--->"+response);
							} else {
								forgetView.sendEmailResult(result.getMessage(),"");
								LogUtils.e("忘记密码 的获取验证码失败 返回字段--->"+response);
							}
						}catch(JsonIOException e){
							LogUtils.e("JsonIOException---->"+e.toString());
							Toast.makeText(context, ResLoader.getString(context, "json_encode_error"), Toast.LENGTH_SHORT).show();
						}catch(JsonParseException e){
							LogUtils.e("JsonParseException---->"+e.toString());
							Toast.makeText(context, ResLoader.getString(context, "json_encode_error"), Toast.LENGTH_SHORT).show();
						}catch(Exception e){
							LogUtils.e("Exception---->"+e.toString());
							Toast.makeText(context, ResLoader.getString(context, "json_encode_error"), Toast.LENGTH_SHORT).show();
						}
						
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub
						Log.e("Com", "出错--->" + error.getMessage());
					}
				}) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> map = new HashMap<String, String>();
				map.put("email", email);
				map.put("gamecode", gamecode);
				map.put("comefrom", comefrom);
				map.put("timestamp", timestamp);
				map.put("platform", platform);
				map.put("signature", signature);
				map.put("language", language);
				map.put("channel", channel);
				
				LogUtils.e("忘记密码 的 获取验证码 地址接口--->"+Constant.HW_FORGET_SEND_EMAIL);
				LogUtils.e("忘记密码 的获取验证码 请求字段--->"+map.toString());
				
				return map;
			}
		};
		RequestQueueHepler.getInstance().getQueue().add(stringRequest);
	}

	@Override
	public void validEmail(final String email, final String code,
			final ForgetPasswordContract.ForgetPassWordView forgetView) {
		// TODO Auto-generated method stub
		

		final String comefrom = "android";
		final String timestamp = HWUtils.getTimestamp();
		final String platform = ResLoader.getString(context, "platform");
		final String gamecode = "MYZN";
		final String channel = ResLoader.getString(context, "channel");
		if (TextUtils.isEmpty(channel)) {
			Toast.makeText(context, "channel为空", Toast.LENGTH_SHORT).show();
			forgetView.vaildEmailFail("channel为空");
			return;
		}
		final String strSign = gamecode + platform + "android" + channel
				+ timestamp + Constant.HW_APP_KEY;
		final String signature = MD5.getMD5(strSign.toLowerCase());

		final String language = HWConfigSharedPreferences.getInstance(
				HWControl.getInstance().getContext()).getLanguage();
		
		// 请求网络
				StringRequest stringRequest = new StringRequest(Method.POST,
						Constant.HW_FORGET_VALID_EMAIL, new Response.Listener<String>() {

							@Override
							public void onResponse(String response) {
								// TODO Auto-generated method stub
								Log.e("Com", "输入验证码肯定没错--->" + response);
								try{
									Gson gson = new Gson();
									HWTourLoginTrialResult result = gson.fromJson(response,
											HWTourLoginTrialResult.class);
									Log.e("Com", "gson解释后数据--->" + result.toString());
									int code = Integer.parseInt(result.getCode());
									if (code == 1000) {
										// 1000就是成功
										forgetView.vaildEmailSuccess(result.getMessage());
										LogUtils.e("忘记密码 验证 验证码成功 返回字段--->"+response);
									} else {
										forgetView.vaildEmailFail(result.getMessage());
										LogUtils.e("忘记密码 验证 验证码失败 返回字段--->"+response);
									}
								}catch(JsonIOException e){
									LogUtils.e("JsonIOException---->"+e.toString());
									Toast.makeText(context, ResLoader.getString(context, "json_encode_error"), Toast.LENGTH_SHORT).show();
								}catch(JsonParseException e){
									LogUtils.e("JsonParseException---->"+e.toString());
									Toast.makeText(context, ResLoader.getString(context, "json_encode_error"), Toast.LENGTH_SHORT).show();
								}catch(Exception e){
									LogUtils.e("Exception---->"+e.toString());
									Toast.makeText(context, ResLoader.getString(context, "json_encode_error"), Toast.LENGTH_SHORT).show();
								}
								
							}
						}, new Response.ErrorListener() {

							@Override
							public void onErrorResponse(VolleyError error) {
								// TODO Auto-generated method stub
								Log.e("Com", "出错--->" + error.getMessage());
							}
						}) {
					@Override
					protected Map<String, String> getParams() throws AuthFailureError {
						Map<String, String> map = new HashMap<String, String>();
						map.put("email", email);
						map.put("mailcode", code);
						map.put("gamecode", gamecode);
						map.put("comefrom", comefrom);
						map.put("timestamp", timestamp);
						map.put("platform", platform);
						map.put("signature", signature);
						map.put("language", language);
						map.put("channel", channel);
						
						LogUtils.e("忘记密码 验证 验证码 地址接口--->"+Constant.HW_FORGET_VALID_EMAIL);
						LogUtils.e("忘记密码 验证 验证码 请求字段--->"+map.toString());
						
						return map;
					}
				};
				RequestQueueHepler.getInstance().getQueue().add(stringRequest);
		
	}

}
