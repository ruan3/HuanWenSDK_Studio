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
import com.example.huanwensdk.bean.loginTrial.HWTourLoginTrialResult;
import com.example.huanwensdk.bean.user.RoleInfo;
import com.example.huanwensdk.http.RequestQueueHepler;
import com.example.huanwensdk.mvp.contract.ExceptionContract;
import com.example.huanwensdk.mvp.contract.SaveRoleContract;
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
 * @Title:  SaveRoleModel.java   
 * @Package com.example.huanwensdk.mvp.model   
 * @Description:    保存角色信息 
 * @author: Android_ruan     
 * @date:   2018-5-9 下午2:47:17   
 * @version V1.0
 */
public class SaveRoleModel implements SaveRoleContract.Model{

	Context context;
	private String gameCode;
	
	ExceptionContract.ExceptionPresenter exceptionPresenter;
	
	@Override
	public void saveRole(RoleInfo roleInfo) {
		// TODO Auto-generated method stub
		
		context = HWControl.getInstance().getContext();
		gameCode = ResLoader.getString(context, "hw_gamecode");
		final String userId = HWConfigSharedPreferences.getInstance(context).getUserId();
		final String roleId = roleInfo.getRoleId();
		final String roleName = roleInfo.getRoleName();
		final String roleLevel = roleInfo.getRoleLevel();
		// 设置字段
		final String serverCode = roleInfo.getServerCode();
		final String serverName = roleInfo.getServerName();
		final String machineid = HWUtils.getDeviceId(context);
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
		final String strSign = gamecode + platform + "android" + channel
				+ timestamp + Constant.HW_APP_KEY;
		Log.e("Com", "strSign---->"+strSign);
		final String signature = MD5.getMD5(strSign.toLowerCase());
		final String language = HWConfigSharedPreferences.getInstance(
				HWControl.getInstance().getContext()).getLanguage();

		// 请求网络
		StringRequest stringRequest = new StringRequest(Method.POST,
				Constant.HW_SAVE_ROLE, new Response.Listener<String>() {

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
								LogUtils.e("保存角色成功返回字段---->" + response);
							} else if (code == 1001) {
							} else {
								LogUtils.e("保存角色返回字段---->" + response);
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
				map.put("machineid", machineid);
				map.put("gamecode", gamecode);
				map.put("comefrom", comefrom);
				map.put("timestamp", timestamp);
				map.put("platform", platform);
				map.put("cpu", cpu);
				map.put("channel", channel);
				map.put("language", language);
				map.put("userid", userId);
				map.put("roleid", roleId);
				map.put("roleName", roleName);
				map.put("roleLevel", roleLevel);
				map.put("servercode", serverCode);
				map.put("servername", serverName);
				map.put("version", "1.0");
				map.put("signature", signature);

				LogUtils.e("保存角色请求地址---->" + Constant.HW_SAVE_ROLE);
				LogUtils.e("保存角色请求字段----->" + map.toString());

				return map;
			}
		};

		RequestQueueHepler.getInstance().getQueue().add(stringRequest);
		
	}

}
