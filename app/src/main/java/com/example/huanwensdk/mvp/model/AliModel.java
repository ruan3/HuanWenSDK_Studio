package com.example.huanwensdk.mvp.model;

import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.huanwensdk.DataBase.DBUtils;
import com.example.huanwensdk.bean.aipay.AuthResult;
import com.example.huanwensdk.bean.aipay.PayResult;
import com.example.huanwensdk.bean.wxpay.PayItemListBean;
import com.example.huanwensdk.bean.wxpay.PayItemListBean.DataBean;
import com.example.huanwensdk.bean.wxpay.PayOrderBean;
import com.example.huanwensdk.http.RequestQueueHepler;
import com.example.huanwensdk.mvp.contract.AliPayContract;
import com.example.huanwensdk.mvp.contract.ExceptionContract;
import com.example.huanwensdk.mvp.contract.PaySingleContract;
import com.example.huanwensdk.mvp.contract.listener.PayLisenter;
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

public class AliModel implements AliPayContract.AliModel {

	private Context context;

	private static final int SDK_PAY_FLAG = 1;
	private static final int SDK_AUTH_FLAG = 2;
	
	private PayLisenter payLisenter;
	
	static AliPayContract.AliView payView;
	
	static PaySingleContract.View paySingleView;
	
	ExceptionContract.ExceptionPresenter exceptionPresenter;
	
	private String gameCode;
	
	@Override
	public void getPayList(final String serverCode, final String roleId,
			final AliPayContract.AliView payView) {
		// TODO Auto-generated method stub
		this.payView = payView;
		context = HWControl.getInstance().getContext();
		payLisenter = HWControl.getInstance().getPayLisenter();
		gameCode = ResLoader.getString(context, "hw_gamecode");

		// 设置字段
		final String sessionid = DBUtils
				.getInstance()
				.queryInfoUser(
						HWConfigSharedPreferences.getInstance(context)
								.getUserId()).getSessionid();
		final String token = DBUtils
				.getInstance()
				.queryInfoUser(
						HWConfigSharedPreferences.getInstance(context)
								.getUserId()).getToken();
		final String machineid = HWUtils.getDeviceId(context);
		final String gamecode = gameCode;
		final String comefrom = "android";
		final String timestamp = HWUtils.getTimestamp();
		final String platform = ResLoader.getString(context, "platform");
		final String cpu = Build.CPU_ABI;
		final String channel = "alipay";
		final String language = HWConfigSharedPreferences.getInstance(
				HWControl.getInstance().getContext()).getLanguage();
		final String signature = MD5.getMD5(sessionid + token + gamecode
				+ "101" + "1.0" + language + platform + "android" + "wechat"
				+ timestamp + "FAN#_GA*)KK:LK%%");

		// 请求网络
		StringRequest stringRequest = new StringRequest(Method.POST,
				Constant.HW_PAY_LIST, new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {
						// TODO Auto-generated method stub
						Log.e("Com", "获取支付列表肯定没错--->" + response);
						exceptionPresenter = new ExceptionPresenter();
						try{
							Gson gson = new Gson();
							PayItemListBean result = gson.fromJson(response,
									PayItemListBean.class);
							Log.e("Com", "gson解释后数据--->" + result.toString());
							int code = Integer.parseInt(result.getCode());
							if (code == 1000) {
								// 1000就是成功
								LogUtils.e("获取支付宝成功返回字段---->" + response);
								payView.getPayList(code, result.getData());
								if(paySingleView!=null){
									
									paySingleView.getPayList(code, result.getData());
								}
							} else if (code == 1001) {
							} else {
								payView.getPayList(code, result.getData());
								if(paySingleView!=null){
									
									paySingleView.getPayList(code, result.getData());
								}
								LogUtils.e("获取支付宝返回字段---->" + response);
							}
						}catch(JsonIOException e){
							exceptionPresenter.tips(context, e);
//							LogUtils.e("JsonIOException---->"+e.toString());
//							Toast.makeText(context, ResLoader.getString(context, "json_encode_error"), Toast.LENGTH_SHORT).show();
						}catch(JsonParseException e){
							exceptionPresenter.tips(context, e);
//							LogUtils.e("JsonParseException---->"+e.toString());
//							Toast.makeText(context, ResLoader.getString(context, "json_encode_error"), Toast.LENGTH_SHORT).show();
						}catch(Exception e){
							exceptionPresenter.tips(context, e);
//							LogUtils.e("Exception---->"+e.toString());
//							Toast.makeText(context, ResLoader.getString(context, "json_encode_error"), Toast.LENGTH_SHORT).show();
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
				map.put("channel", "alipay");
				map.put("sessionid", sessionid);
				map.put("token", token);
				map.put("version", "1.0");
				map.put("servercode", serverCode);
				map.put("roleid", roleId);
				map.put("cpu", cpu);
				map.put("signature", signature);
				map.put("language", language);
				map.put("channel", channel);

				LogUtils.e("支付宝请求地址---->" + Constant.HW_PAY_LIST);
				LogUtils.e("支付宝请求字段---->" + map.toString());

				return map;
			}
		};

		RequestQueueHepler.getInstance().getQueue().add(stringRequest);
	}

	@Override
	public void getOrder(final String serverCode, final String roleId, DataBean dataBean,PaySingleContract.View paySingleView) {
		// TODO Auto-generated method stub
		context = HWControl.getInstance().getContext();
		payLisenter = HWControl.getInstance().getPayLisenter();
		this.paySingleView = paySingleView;
		// 设置字段
		final String sessionid = DBUtils
				.getInstance()
				.queryInfoUser(
						HWConfigSharedPreferences.getInstance(context)
								.getUserId()).getSessionid();
		final String token = DBUtils
				.getInstance()
				.queryInfoUser(
						HWConfigSharedPreferences.getInstance(context)
								.getUserId()).getToken();
		final String machineid = HWUtils.getDeviceId(context);
		final String gamecode = ResLoader.getString(context, "hw_gamecode");
		final String comefrom = "android";
		final String userId = HWConfigSharedPreferences.getInstance(context).getUserId();
		final String timestamp = HWUtils.getTimestamp();
		final String platform = ResLoader.getString(context, "platform");
		final String ItemKey = dataBean.getItemKey();
		final String gameName = ResLoader.getString(context, "game_name");
		final int itemid = dataBean.getId();
		final String description = dataBean.getDescription();
		final String activeDescription = dataBean.getActiveDescription();
		final String cpu = Build.CPU_ABI;
		final String channel = "alipay";
		final String language = HWConfigSharedPreferences.getInstance(
				HWControl.getInstance().getContext()).getLanguage();

		// 请求网络
		StringRequest stringRequest = new StringRequest(Method.POST,
				Constant.HW_GET_ALI_ORDER, new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {
						// TODO Auto-generated method stub
						Log.e("Com", "获取单个支付数据肯定没错--->" + response);
						exceptionPresenter = new ExceptionPresenter();
						try{
							Gson gson = new Gson();
							PayOrderBean result = gson.fromJson(response,
									PayOrderBean.class);
							Log.e("Com", "gson解释后数据--->" + result.toString());
							int code = Integer.parseInt(result.getCode());
							if (code == 1000) {
								// 1000就是成功
								LogUtils.e("获取支付订单成功返回字段---->" + response);
								ParseOrder(result);
							} else if (code == 1001) {
							} else {
								LogUtils.e("获取支付订单返回字段---->" + response);
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
						Toast.makeText(context, "请求服务器错误", Toast.LENGTH_SHORT).show();
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
				map.put("payType", "test_pay_type_1");
				map.put("pid", "test_pay_id");
				map.put("sessionid", sessionid);
				map.put("token", token);
				map.put("version", "1.0");
				map.put("servercode", serverCode);
				map.put("roleid", roleId);
				map.put("cpu", cpu);
				map.put("itemid", itemid + "");
				map.put("language", language);
				map.put("gameName", gameName);
				map.put("description", description);
				map.put("activeDescription", activeDescription);
				map.put("userid", userId);
				map.put("item_key", ItemKey);
				map.put("channel", channel);

				LogUtils.e("支付宝订单请求地址---->" + Constant.HW_GET_ALI_ORDER);
				LogUtils.e("支付宝订单请求字段---->" + map.toString());

				return map;
			}
		};

		RequestQueueHepler.getInstance().getQueue().add(stringRequest);
	}

	public void ParseOrder(PayOrderBean orderBean) {

		String orderInfo = orderBean.getData();
		String orderId = orderBean.getOrderid();
		if(payView!=null){
			
			payView.getOrderId(1000, orderId);
		}
		if(paySingleView!=null){
			
			paySingleView.getOrderId(1000, orderId);
		}
		pay(orderInfo);
	}
	
	/**
	 * 
	 */
	public void pay(final String orderInfo){
		payLisenter = HWControl.getInstance().getPayLisenter();
		Runnable payRunnable = new Runnable() {

			@Override
			public void run() {
				PayTask alipay = new PayTask((Activity) context);
				Map<String, String> result = alipay.payV2(orderInfo, true);
				Log.i("msp", result.toString());
				
				Message msg = new Message();
				msg.what = SDK_PAY_FLAG;
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		};

		Thread payThread = new Thread(payRunnable);
		payThread.start();
		
	}
	
	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		@SuppressWarnings("unused")
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SDK_PAY_FLAG: {
				@SuppressWarnings("unchecked")
				
				PayResult payResult = new PayResult((Map<String, String>) msg.obj);
				
				AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
				/**
				 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
				 */
				String resultInfo = payResult.getResult();// 同步返回需要验证的信息
				String resultStatus = payResult.getResultStatus();
				LogUtils.e("有错没有getResult---->"+authResult.getResultStatus());
				LogUtils.e("有错没有getResultStatus---->"+authResult.getResult());
				// 判断resultStatus 为9000则代表支付成功
				if (TextUtils.equals(resultStatus, "9000")) {
					// 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
					if(payView!=null){
						
						payView.getAilPayResult(9000, "");
					}
					if(paySingleView!=null){
						
						paySingleView.payResult(1000, "成功");
					}
				} else {
					// 该笔订单真实的支付结果，需要依赖服务端的异步通知。
					if(payView!=null){
						
						payView.getAilPayResult(8000, "");
					}
					if(paySingleView!=null){
						
						paySingleView.payResult(1002, "失败");
					}
				}
				break;
			}
			case SDK_AUTH_FLAG: {
				@SuppressWarnings("unchecked")
				AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
				String resultStatus = authResult.getResultStatus();
				String result = authResult.getResult();
				LogUtils.e("有错没有resultStatus---->"+resultStatus);
				LogUtils.e("有错没有result---->"+authResult.toString());
				LogUtils.e("有错没有getMemo---->"+authResult.getMemo());
				// 判断resultStatus 为“9000”且result_code
				// 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
				if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
					// 获取alipay_open_id，调支付时作为参数extern_token 的value
					// 传入，则支付账户为该授权账户
					Toast.makeText(context,
							"授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
							.show();
				} else {
					// 其他状态值则为授权失败
					Toast.makeText(context,
							"授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();

				}
				
				break;
			}
			default:
				break;
			}
		};
	};
	
	String extraData = "";

	@Override
	public void getPayResult(final String serverCode, final String orderId,final PaySingleContract.View paySingleView) {
		// TODO Auto-generated method stub
		this.paySingleView = paySingleView;
		// 设置字段
				final String gamecode = ResLoader.getString(context, "hw_gamecode");
				final String comefrom = "android";
				extraData = HWControl.getInstance().getExtraData();
				final String platform = ResLoader.getString(context, "platform");
				final String version = "1.0";// 暂时固定
				// 请求网络
				StringRequest stringRequest = new StringRequest(Method.POST,
						Constant.HW_CHECK_PAY, new Response.Listener<String>() {

							@Override
							public void onResponse(String response) {
								// TODO Auto-generated method stub
								Log.e("Com", "获取支付验证肯定没错--->" + response);
								exceptionPresenter = new ExceptionPresenter();
								try{
									Gson gson = new Gson();
									PayItemListBean result = gson.fromJson(response,
											PayItemListBean.class);
									Log.e("Com", "gson解释后数据--->" + result.toString());
									int code = Integer.parseInt(result.getCode());
									if (code == 1000) {
										// 1000就是成功
										LogUtils.e("获取支付验证成功返回字段---->" + response);
										if(payView!=null){
											
											payView.getPayStatus(1000, result.getMessage());
										}
										if(paySingleView!=null){
											
											paySingleView.getPayStatus(1000, result.getMessage());
										}
									} else {
										LogUtils.e("获取支付验证返回字段---->" + response);
										if(payView!=null){
											
											payView.getPayStatus(code, result.getMessage());
										}
										if(paySingleView!=null){
											
											paySingleView.getPayStatus(code, result.getMessage());
										}
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
						map.put("platform", platform);
						map.put("version", version);
						map.put("servercode", serverCode);
						map.put("orderid", orderId);
						map.put("extraData", extraData);

						LogUtils.e("支付宝支付验证请求地址---->" + Constant.HW_CHECK_PAY);
						LogUtils.e("支付宝支付验证请求字段---->" + map.toString());

						return map;
					}
				};

				RequestQueueHepler.getInstance().getQueue().add(stringRequest);
		
	}

}
