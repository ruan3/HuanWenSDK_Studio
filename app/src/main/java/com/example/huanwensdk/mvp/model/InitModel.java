package com.example.huanwensdk.mvp.model;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.huanwensdk.bean.activation.ActivationBean;
import com.example.huanwensdk.http.RequestQueueHepler;
import com.example.huanwensdk.http.VolleyErrorHelper;
import com.example.huanwensdk.mvp.contract.ExceptionContract;
import com.example.huanwensdk.mvp.contract.initcontract;
import com.example.huanwensdk.mvp.presenter.ExceptionPresenter;
import com.example.huanwensdk.ui.dialog.UpdateDialog;
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

public class InitModel implements initcontract.initModel {

	Context context;
	initcontract.initView view;
	
	ExceptionContract.ExceptionPresenter exceptionPresenter;
	
	private String gameCode;
	
	@Override
	public void init(initcontract.initView view) {
		// TODO Auto-generated method stub
		this.view = view;
		context = HWControl.getInstance().getContext();
		gameCode = ResLoader.getString(context, "hw_gamecode");
		String language = ResLoader.getString(HWControl.getInstance()
				.getContext(), "hw_language");// 获取对应的SDK语言
		
		HWConfigSharedPreferences.getInstance(
				HWControl.getInstance().getContext()).setLanguage(language);
		// 设置SDK语言
		HWUtils.setLanguage(HWControl.getInstance().getContext(), HWControl
				.getInstance().getContext().getResources());

		Log.e("Com", "获取到string.xml中的字段---->" + language);

		String name = ResLoader.getString(HWControl.getInstance().getContext(),
				"string_try_play");// 使用这个方法去保险一点，没有这个字段的话，还可能不崩溃
		
		Log.e("Com", "获取到string.xml中的字段---->" + name);
		
		String orientation = ResLoader.getString(HWControl.getInstance()
				.getContext(), "hw_orientation");
		if(!TextUtils.isEmpty(orientation)){
			int or = Integer.parseInt(orientation);
			view.getOrientationResult(0, or);//返回到界面
			boolean isactivation = HWConfigSharedPreferences.getInstance(context).Activation();
			activation();
			if(!isactivation){
				//激活方法
				activation();
			}
		}else{
			view.getOrientationResult(-1, -3);
		}
	}
	
	/**
	 * 激活设备
	 */
	private void activation(){
		
		String machineid = HWUtils.getDeviceId(context);
		final String gamecode = gameCode;
		final String sitecode = "gd";
		final String comefrom = "android";
		final String timestamp = HWUtils.getTimestamp();
		final String platform = ResLoader.getString(context, "platform");
		final String channel = ResLoader.getString(context, "channel");
		final String game_version = HWUtils.getAppVersionName(context);
		final String cpu = Build.CPU_ABI;
		final String strSign = gamecode + platform
				+ "android"+ channel + timestamp + "HWGAME#_DR*)HW";
		final String signature = MD5.getMD5(strSign.toLowerCase());
		Log.e("Com", "strSign---->"+strSign.toLowerCase());
		final String language = HWConfigSharedPreferences.getInstance(
				HWControl.getInstance().getContext()).getLanguage();
		
		// 请求网络
				StringRequest stringRequest = new StringRequest(Method.POST,
						Constant.HW_ACTIVATION, new Response.Listener<String>() {

							@Override
							public void onResponse(String response) {
								// TODO Auto-generated method stub
								try{
									exceptionPresenter = new ExceptionPresenter();
									Log.e("Com", "激活肯定没错--->" + response);
									String responseUrl = Uri.decode(response);
									Gson gson = new Gson();
									ActivationBean result = gson.fromJson(response,
											ActivationBean.class);
									Log.e("Com", "gson解释后数据--->" + result.toString());
									int code = result.getState().getCode();
									if (code == 1000) {
										LogUtils.e("激活的成功-返回字段---->"+responseUrl);
//									LogUtils.e("result.getData().getCountry_id()---->"+result.getData().getCountry_id());
//									HWConfigSharedPreferences.getInstance(context).setCountry(result.getData().getCountry_id());
										HWConfigSharedPreferences.getInstance(context).setAppStatus(result.getVersion_status().getStatus());
										//设置语言
										HWConfigSharedPreferences.getInstance(context).setLanguage(result.getLanguage());
										HWConfigSharedPreferences.getInstance(context).setShowPay(result.getVersion_status().getLogin_btn_show());
										LogUtils.e("是否显示更新------>"+result.getVersion_status().getShow_update());
										if(result.getVersion_status().getShow_update().equals("1")){
											//要显示登录对话框了
											String content = result.getVersion_status().getUpdate_content();
											String url = result.getVersion_status().getUpdate_url();
											UpdateDialog.getInstance().getContent(content, url);
											UpdateDialog.getInstance().show();
											HWConfigSharedPreferences.getInstance(context).setShowUpdate(true);
										}else{
											HWConfigSharedPreferences.getInstance(context).setShowUpdate(false);
										}
										LogUtils.e("getLogin_btn_show---->"+result.getVersion_status().getLogin_btn_show());
										if(result.getVersion_status().getLogin_btn_show().equals("1")){
											//判断是否显示第三登录
											HWConfigSharedPreferences.getInstance(context).setShowAuthorLogin(true);
										}else{
											HWConfigSharedPreferences.getInstance(context).setShowAuthorLogin(false);
										}
										view.activationSuccess();
									}else if(code == 1002){
										LogUtils.e("是否显示更新------>"+result.getVersion_status().getShow_update());
//									HWConfigSharedPreferences.getInstance(context).setCountry(result.getData().getCountry_id());
										HWConfigSharedPreferences.getInstance(context).setAppStatus(result.getVersion_status().getStatus());
										HWConfigSharedPreferences.getInstance(context).setShowPay(result.getVersion_status().getLogin_btn_show());
										//设置语言
										HWConfigSharedPreferences.getInstance(context).setLanguage(result.getLanguage());
										if(result.getVersion_status().getLogin_btn_show().equals("1")){
											//判断是否显示第三登录
											HWConfigSharedPreferences.getInstance(context).setShowAuthorLogin(true);
										}else{
											HWConfigSharedPreferences.getInstance(context).setShowAuthorLogin(false);
										}
										LogUtils.e("getLogin_btn_show---->"+result.getVersion_status().getLogin_btn_show());
										if(result.getVersion_status().getShow_update().equals("1")){
											//要显示登录对话框了
											
											String content = result.getVersion_status().getUpdate_content();
											String url = result.getVersion_status().getUpdate_url();
											LogUtils.e("是否显示content------>"+content+"-----"+url);
											
											UpdateDialog.getInstance().getContent(content, url);
											UpdateDialog.getInstance().show();
											HWConfigSharedPreferences.getInstance(context).setShowUpdate(true);
										}else{
											HWConfigSharedPreferences.getInstance(context).setShowUpdate(false);
										}
										
										view.fail(result.getState().getMsg());
										
										LogUtils.e("激活的失败-返回字段---->"+response);
									}else{
										//激活失败的操作
										view.fail(result.getState().getMsg());
										
										LogUtils.e("激活的失败-返回字段---->"+response);
									}
								}catch(JsonSyntaxException jse){
//									LogUtils.e("JsonSyntaxException---->"+jse.toString());
//									Toast.makeText(context, ResLoader.getString(context, "json_encode_error"), Toast.LENGTH_SHORT).show();
									exceptionPresenter.tips(context, jse);
								}catch (JsonIOException e) {
									exceptionPresenter.tips(context, e);
//									LogUtils.e("JsonIOException---->"+e.toString());
//									Toast.makeText(context, ResLoader.getString(context, "json_encode_error"), Toast.LENGTH_SHORT).show();
									// TODO: handle exception
								}catch (JsonParseException e){
									exceptionPresenter.tips(context, e);
//									LogUtils.e("JsonParseException---->"+e.toString());
//									Toast.makeText(context, ResLoader.getString(context, "json_encode_error"), Toast.LENGTH_SHORT).show();
								}catch (Exception e) {
									// TODO: handle exception
									exceptionPresenter.tips(context, e);
//									LogUtils.e("Exception---->"+e.toString());
//									Toast.makeText(context, ResLoader.getString(context, "json_encode_error"), Toast.LENGTH_SHORT).show();
								}
							}
						}, new Response.ErrorListener() {

							@Override
							public void onErrorResponse(VolleyError error) {
								// TODO Auto-generated method stub
								Log.e("Com", "出错--->" + error.getMessage());
								LogUtils.e("激活接口--->"+Constant.HW_ACTIVATION);
								callbackError(error);
							}
						}) {
					@Override
					protected Map<String, String> getParams() throws AuthFailureError {
						Map<String, String> map = new HashMap<String, String>();
						map.put("machineid", HWUtils.getDeviceId(context));
						map.put("gamecode", gamecode);
						map.put("comefrom", comefrom);
						map.put("timestamp", timestamp);
						map.put("platform", platform);
						map.put("cpu", cpu);
						map.put("signature", signature);
						map.put("language", language);
						map.put("channel", channel);
						map.put("game_version", game_version);
						LogUtils.e("激活接口地址---->"+Constant.HW_ACTIVATION);
						LogUtils.e("激活请求字段---->"+map.toString());
						
						return map;
					}
				};

				RequestQueueHepler.getInstance().getQueue().add(stringRequest);
		
	}
	
	/**
	 * 返回网络请求错误
	 * @param error
	 */
	public void callbackError(VolleyError error){
		
		String errorStr = VolleyErrorHelper.getMessage(error, context);
		
		view.fail(errorStr);
	}

}
