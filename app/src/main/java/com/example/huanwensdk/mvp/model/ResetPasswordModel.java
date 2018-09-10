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
import com.example.huanwensdk.mvp.contract.ResetPasswordContract;
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

public class ResetPasswordModel implements
		ResetPasswordContract.ResetPasswordModel {

	ResetPasswordContract.ResetPasswordView resetPasswordView;
	Context context;
	private String gameCode;

	ExceptionContract.ExceptionPresenter exceptionPresenter;
	
	@Override
	public void reset(final String password, final String email,
			final String code, final ResetPasswordContract.ResetPasswordView resetPasswordView) {
		// TODO Auto-generated method stub

		this.resetPasswordView = resetPasswordView;
		context = HWControl.getInstance().getContext();
		gameCode = ResLoader.getString(context, "hw_gamecode");
		final String gamecode = gameCode;
		final String comefrom = "android";
		final String timestamp = HWUtils.getTimestamp();
		final String platform = ResLoader.getString(context, "platform");
		final String channel = ResLoader.getString(context, "channel");
		if (TextUtils.isEmpty(channel)) {
			Toast.makeText(context, "channel为空", Toast.LENGTH_SHORT).show();
			resetPasswordView.resetFail("channel为空");
			return;
		}
		final String strSign = gamecode + platform + "android" + channel
				+ timestamp + Constant.HW_APP_KEY;
		final String signature = MD5.getMD5(strSign.toLowerCase());
		final String language = HWConfigSharedPreferences.getInstance(
				HWControl.getInstance().getContext()).getLanguage();

		// 请求网络
		StringRequest stringRequest = new StringRequest(Method.POST,
				Constant.HW_RESET_PASSWORD, new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {
						// TODO Auto-generated method stub
						Log.e("Com", "重设密码肯定没错--->" + response);
						exceptionPresenter = new ExceptionPresenter();
						try{
							Gson gson = new Gson();
							HWTourLoginTrialResult result = gson.fromJson(response,
									HWTourLoginTrialResult.class);
							Log.e("Com", "gson解释后数据--->" + result.toString());
							int code = Integer.parseInt(result.getCode());
							if (code == 1000) {
								// 1000就是成功
								resetPasswordView.resetSuccess(result.getMessage());
								LogUtils.e("重置密码成功返回字段---->"+response);
							} else {
								resetPasswordView.resetFail(result.getMessage());
								LogUtils.e("重置密码失败返回字段----->"+response);
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
					}
				}) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> map = new HashMap<String, String>();
				map.put("username", email);
				map.put("password", password);
				map.put("validCode", code);
				map.put("gamecode", gamecode);
				map.put("comefrom", comefrom);
				map.put("timestamp", timestamp);
				map.put("platform", platform);
				map.put("signature", signature);
				map.put("language", language);
				map.put("channel", channel);
				
				LogUtils.e("重置密码请求字段----->"+map.toString());
				LogUtils.e("重置密码请求地址----->"+Constant.HW_RESET_PASSWORD);
				
				return map;
			}
		};
		RequestQueueHepler.getInstance().getQueue().add(stringRequest);

	}

}
