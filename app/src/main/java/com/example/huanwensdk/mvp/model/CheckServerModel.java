package com.example.huanwensdk.mvp.model;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.huanwensdk.DataBase.DBUtils;
import com.example.huanwensdk.bean.loginTrial.HWTourLoginResult;
import com.example.huanwensdk.bean.server.Server;
import com.example.huanwensdk.http.RequestQueueHepler;
import com.example.huanwensdk.mvp.contract.CheckServerContract;
import com.example.huanwensdk.mvp.contract.listener.LoginListener;
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

public class CheckServerModel implements CheckServerContract.CheckServerModel{

	Context context;

	LoginListener loginListener;
	private String gameCode;
	
	@Override
	public void checkServer(String userId, String serverCode) {
		// TODO Auto-generated method stub
		
		Server server = new Server();
		server.setServercode(serverCode);
		if(userId!=null){
			DBUtils.getInstance().saveServer(server);
		}
//		checkServer(serverCode);
	}

	public void checkServer(final String code){
		
		context = HWControl.getInstance().getContext();
		gameCode = ResLoader.getString(context, "hw_gamecode");
		final String sessionId = DBUtils.getInstance().queryInfoUser(HWConfigSharedPreferences.getInstance(context).getUserId()).getSessionid();
		final String token = DBUtils.getInstance().queryInfoUser(HWConfigSharedPreferences.getInstance(context).getUserId()).getToken();
		// 设置字段
				final String machineid = HWUtils.getDeviceId(context);
				final String gamecode = gameCode;
				final String comefrom = "android";
				final String timestamp = HWUtils.getTimestamp();
				final String platform = ResLoader.getString(context, "platform");
				final String channel = ResLoader.getString(context, "channel");
				final String cpu = Build.CPU_ABI;
				final String language = HWConfigSharedPreferences.getInstance(
						HWControl.getInstance().getContext()).getLanguage();
				final String signature = MD5.getMD5(sessionId + token + gamecode + platform + "android"
						+ language + "1.1.0" + timestamp + "FAN#_GA*)KK:LK%%");
				

				// 请求网络
						StringRequest stringRequest = new StringRequest(Method.POST,
								Constant.HW_CHECK_SERVER, new Response.Listener<String>() {

									@Override
									public void onResponse(String response) {
										// TODO Auto-generated method stub
										Log.e("Com", "检查服务器肯定没错--->" + response);
										try{
											Gson gson = new Gson();
											HWTourLoginResult result = gson.fromJson(response,
													HWTourLoginResult.class);
											Log.e("Com", "gson解释后数据--->" + result.toString());
											int code = Integer.parseInt(result.getCode());
											if (code == 1000) {
												// 1000就是成功
												// 检测数据绑定
												LogUtils.e("登录成功返回字段---->"+response);
											}else if(code == 1001){
											}else{
												LogUtils.e("登录失败返回字段---->"+response);
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
								map.put("machineid", machineid);
								map.put("gamecode", gamecode);
								map.put("comefrom", comefrom);
								map.put("timestamp", timestamp);
								map.put("platform", platform);
								map.put("sessionid", sessionId);
								map.put("servercode", code);
								map.put("version", "1.1.0");
								map.put("cpu", cpu);
								map.put("signature", signature);
								map.put("language", language);
								map.put("token", token);

								LogUtils.e("登录请求地址---->"+Constant.HW_CHECK_SERVER);
								LogUtils.e("登录请求字段---->"+map.toString());
								
								return map;
							}
						};

						RequestQueueHepler.getInstance().getQueue().add(stringRequest);
	}
	
}
