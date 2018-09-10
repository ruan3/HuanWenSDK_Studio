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
import com.example.huanwensdk.bean.user.HWInfoUser;
import com.example.huanwensdk.http.RequestQueueHepler;
import com.example.huanwensdk.mvp.contract.ExceptionContract;
import com.example.huanwensdk.mvp.contract.PayContract;
import com.example.huanwensdk.mvp.contract.PayContract.View;
import com.example.huanwensdk.mvp.presenter.ExceptionPresenter;
import com.example.huanwensdk.ui.bean.PayBean;
import com.example.huanwensdk.utils.Constant;
import com.example.huanwensdk.utils.HWControl;
import com.example.huanwensdk.utils.HWUtils;
import com.example.huanwensdk.utils.LogUtils;
import com.example.huanwensdk.utils.ResLoader;
import com.example.huanwensdk.utils.sp.HWConfigSharedPreferences;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;

/**
 * 
 * @Title: PayModel.java
 * @Package com.example.huanwensdk.mvp.model
 * @Description: 获取支付列表
 * @author: Android_ruan
 * @date: 2018-5-18 下午1:43:35
 * @version V1.0
 */
public class PayModel implements PayContract.Model {

	Context context;
	private String gameCode;
	String sessionid;
	String token;
	
	ExceptionContract.ExceptionPresenter exceptionPresenter;

	@Override
	public void getPayList(final String serverCode, final String level,
			final View payView) {
		// TODO Auto-generated method stub

		context = HWControl.getInstance().getContext();
		gameCode = ResLoader.getString(context, "hw_gamecode");
		String userId = HWConfigSharedPreferences.getInstance(context)
				.getUserId();
		if(TextUtils.isEmpty(userId)){
			Toast.makeText(context, "获取支付列表失败", Toast.LENGTH_SHORT).show();
			return;
		}
		HWInfoUser infoUser = DBUtils.getInstance().queryInfoUser(userId);
		if(infoUser==null){
			Toast.makeText(context, "获取支付列表失败", Toast.LENGTH_SHORT).show();
			return;
		}else{
			// 设置字段
			sessionid = DBUtils
					.getInstance()
					.queryInfoUser(
							HWConfigSharedPreferences.getInstance(context)
							.getUserId()).getSessionid();
			token = DBUtils
					.getInstance()
					.queryInfoUser(
							HWConfigSharedPreferences.getInstance(context)
							.getUserId()).getToken();
		}
		final String gamecode = gameCode;
		final String comefrom = "android";
		final String timestamp = HWUtils.getTimestamp();
		final String platform = ResLoader.getString(context, "platform");
		final String cpu = Build.CPU_ABI;
		final String channel = ResLoader.getString(context, "channel");
		if (TextUtils.isEmpty(channel)) {
			Toast.makeText(context, "channel为空", Toast.LENGTH_SHORT).show();
			return;
		}
		// final String strSign = gamecode + platform + "android" + channel
		// + timestamp + Constant.HW_APP_KEY;
		// final String signature = MD5.getMD5(strSign.toLowerCase());
		final String language = HWConfigSharedPreferences.getInstance(
				HWControl.getInstance().getContext()).getLanguage();

		// 请求网络
		StringRequest stringRequest = new StringRequest(Method.POST,
				Constant.HW_GET_PAY_LIST, new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {
						// TODO Auto-generated method stub
						Log.e("Com", "获取支付方式肯定没错--->" + response);
						exceptionPresenter = new ExceptionPresenter();
						try{
							Gson gson = new Gson();
							PayBean result = gson.fromJson(response,
									PayBean.class);
							Log.e("Com", "gson解释后数据--->" + result.toString());
							int code = Integer.parseInt(result.getCode());
							if (code == 1000) {
								// 1000就是成功
								// 存入谷歌的公钥，如果是外网
								HWConfigSharedPreferences.getInstance(context).setGPPublicKey(result.getPublicKey());
								LogUtils.e("获取支付方式成功返回字段---->" + response);
								payView.getPayListResult("1000", result.getData());
							} else if (code == 1001) {
							} else {
								LogUtils.e("获取支付方式失败返回字段---->" + response);
								payView.getPayListResult(result.getMessage(), null);
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
						payView.getPayListResult("网络错误", null);
					}
				}) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> map = new HashMap<String, String>();
				map.put("gamecode", gamecode);
				map.put("comefrom", comefrom);
				map.put("timestamp", timestamp);
				map.put("platform", platform);
				map.put("cpu", cpu);
				map.put("sessionid", sessionid);
				map.put("token", token);
				map.put("servercode", serverCode);
				map.put("channel", channel);
				map.put("level", level);
				map.put("language", language);

				LogUtils.e("获取支付方式请求地址---->" + Constant.HW_GET_PAY_LIST);
				LogUtils.e("获取支付方式请求字段----->" + map.toString());

				return map;
			}
		};

		RequestQueueHepler.getInstance().getQueue().add(stringRequest);

	}

}
