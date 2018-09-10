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
import com.example.huanwensdk.DataBase.DBUtils;
import com.example.huanwensdk.bean.loginTrial.OrderResultBean;
import com.example.huanwensdk.bean.user.HWInfoUser;
import com.example.huanwensdk.http.RequestQueueHepler;
import com.example.huanwensdk.mvp.contract.ExceptionContract;
import com.example.huanwensdk.mvp.contract.OrderContract;
import com.example.huanwensdk.mvp.contract.OrderContract.View;
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

/**
 * 
 * @Title: OrderGetModel.java
 * @Package com.example.huanwensdk.mvp.model
 * @Description: 获取对应订单业务类
 * @author: Android_ruan
 * @date: 2018-5-14 下午2:51:37
 * @version V1.0
 */
public class OrderGetModel implements OrderContract.Model {

	Context context;
	private String gameCode;
	
	ExceptionContract.ExceptionPresenter exceptionPresenter;

	@Override
	public void getOrderList(final String status, final String startTime, final String endTime,
			final View orderView) {

		context = HWControl.getInstance().getContext();
		final String userid = HWConfigSharedPreferences.getInstance(context).getUserId();
		gameCode = ResLoader.getString(context, "hw_gamecode");
		HWInfoUser infoUser = DBUtils.getInstance().queryInfoUser(userid);
		// 设置字段
		final String sessionid = infoUser.getSessionid();
		final String token = infoUser.getToken();
		final String gamecode = gameCode;
		final String comefrom = "android";
		final String timestamp = HWUtils.getTimestamp();
		final String platform = ResLoader.getString(context, "platform");
		final String channel = ResLoader.getString(context, "channel");
		if (TextUtils.isEmpty(channel)) {
			Toast.makeText(context, "channel为空", Toast.LENGTH_SHORT).show();
			return;
		}
		final String strSign = gamecode + platform + "android" + channel
				+ timestamp + Constant.HW_APP_KEY;
		final String signature = MD5.getMD5(strSign.toLowerCase());
		final String language = HWConfigSharedPreferences.getInstance(
				HWControl.getInstance().getContext()).getLanguage();

//		final String strSign = sessionid + token + gamecode + platform + "android"
//				+ timestamp + "FAN#_GA*)KK:LK%%";
//		final String signature = MD5.getMD5(strSign);
		// 请求网络
		StringRequest stringRequest = new StringRequest(Method.POST,
				Constant.GET_STORES, new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {
						// TODO Auto-generated method stub
						Log.e("Com", "获取订单肯定没错--->" + response);
						exceptionPresenter = new ExceptionPresenter();
						try{
							Gson gson = new Gson();
							OrderResultBean result = gson.fromJson(response,
									OrderResultBean.class);
							Log.e("Com", "gson解释后数据--->" + result.toString());
							int code = Integer.parseInt(result.getCode());
							if (code == 1000) {
								// 1000就是成功
								// 检测数据绑定
								LogUtils.e("获取订单成功返回字段---->" + response);
								orderView.getOrderResult(result.getMessage(),result.getData());
							} else if (code == 1001) {
							} else {
								LogUtils.e("获取订单失败返回字段---->" + response);
								orderView.getOrderResult(result.getMessage(),null);
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
				map.put("gamecode", gamecode);
				map.put("comefrom", comefrom);
				map.put("timestamp", timestamp);
				map.put("platform", platform);
				map.put("channel", channel);
				map.put("signature", signature);
				map.put("sessionid", sessionid);
				map.put("token", token);
				map.put("pageId", "0");
				map.put("pageSize", "100");
				map.put("status", status);
				map.put("startTime", startTime);
				map.put("endTime", endTime);
				map.put("userid", userid);

				LogUtils.e("获取订单请求地址---->" + Constant.GET_STORES);
				LogUtils.e("获取订单请求字段----->" + map.toString());
				Log.e("Com", "获取订单请求字段----->" + map.toString());

				return map;
			}
		};

		RequestQueueHepler.getInstance().getQueue().add(stringRequest);

	}

}
