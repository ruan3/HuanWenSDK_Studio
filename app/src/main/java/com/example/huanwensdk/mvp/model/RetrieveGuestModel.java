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
import com.example.huanwensdk.mvp.contract.RetrieveGuestAccountContract;
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

public class RetrieveGuestModel implements RetrieveGuestAccountContract.RetrieveGusetModel{

	Context context;
	private String gameCode;
	
	ExceptionContract.ExceptionPresenter exceptionPresenter;
	@Override
	public void checkUid(final String uid, final String RandCode,
			final RetrieveGuestAccountContract.RetrieveGuestView retrieveGuestView) {
		// TODO Auto-generated method stub
		
		context = HWControl.getInstance().getContext();
		gameCode = ResLoader.getString(context, "hw_gamecode");
		final String gamecode = gameCode;
		final String comefrom = "android";
		final String sitecode = "gd";
		final String timestamp = HWUtils.getTimestamp();
		final String platform = ResLoader.getString(context, "platform");
		final String channel = ResLoader.getString(context, "channel");
		if (TextUtils.isEmpty(channel)) {
			Toast.makeText(context, "channel为空", Toast.LENGTH_SHORT).show();
			return;
		}
		final String strSign = gamecode + platform + "android" + channel
				+ timestamp + Constant.HW_APP_KEY;
		final String language = HWConfigSharedPreferences.getInstance(
				HWControl.getInstance().getContext()).getLanguage();
		final String signature = MD5.getMD5(strSign.toLowerCase());
		// 请求网络
		StringRequest stringRequest = new StringRequest(Method.POST,
				Constant.HW_CHECK_UID, new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {
						// TODO Auto-generated method stub
						Log.e("Com", "试玩账号肯定没错--->" + response);
						exceptionPresenter = new ExceptionPresenter();
						try{
							Gson gson = new Gson();
							HWTourLoginTrialResult result = gson.fromJson(response,
									HWTourLoginTrialResult.class);
							Log.e("Com", "gson解释后数据--->" + result.toString());
							int code = Integer.parseInt(result.getCode());
							if (code == 1000) {
								// 1000就是成功
								retrieveGuestView.checkUidSuccess(result.getMessage());
								LogUtils.e("试玩账号找回成功返回字段---->"+response);
							} else {
								retrieveGuestView.checkUidFail(result.getMessage());
								LogUtils.e("试玩账号找回失败返回字段--->"+result.getMessage());
							}
						}catch(JsonSyntaxException e){
							exceptionPresenter.tips(context, e);
						}catch(JsonIOException e){
							exceptionPresenter.tips(context, e);
						}catch(JsonParseException e){
							exceptionPresenter.tips(context, e);
						}catch(Exception e){
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
				map.put("showid", uid);
				map.put("randPwd", RandCode);
				map.put("sitecode", sitecode);
				map.put("gamecode", gamecode);
				map.put("comefrom", comefrom);
				map.put("timestamp", timestamp);
				map.put("platform", platform);
				map.put("signature", signature);
				map.put("language", language);
				map.put("channel", channel);
				
				LogUtils.e("试玩账号找回请求字段---->"+map.toString());
				LogUtils.e("试玩账号找回地址----->"+Constant.HW_CHECK_UID);
				
				return map;
			}
		};
		RequestQueueHepler.getInstance().getQueue().add(stringRequest);
		
	}

}
