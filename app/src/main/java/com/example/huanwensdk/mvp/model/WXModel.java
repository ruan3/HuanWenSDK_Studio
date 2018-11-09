package com.example.huanwensdk.mvp.model;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.huanwensdk.DataBase.DBUtils;
import com.example.huanwensdk.bean.google.HWGpPayItem;
import com.example.huanwensdk.bean.wxpay.PayItemListBean;
import com.example.huanwensdk.bean.wxpay.PayItemListBean.DataBean;
import com.example.huanwensdk.bean.wxpay.PayOrderBean;
import com.example.huanwensdk.bean.wxpay.WXPayOrderBean;
import com.example.huanwensdk.http.RequestQueueHepler;
import com.example.huanwensdk.mvp.contract.ExceptionContract;
import com.example.huanwensdk.mvp.contract.PaySingleContract;
import com.example.huanwensdk.mvp.contract.PaySingleContract.View;
import com.example.huanwensdk.mvp.contract.WXPayContract;
import com.example.huanwensdk.mvp.contract.WXPayContract.CoinView;
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
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WXModel implements WXPayContract.WXPayModel {

	private IWXAPI weChatApi;
	private Context context;

	static WXPayContract.WXPayView payView;

	PayOrderBean.Data order;
	private String gameCode;
	PaySingleContract.View paySingleView;

	ExceptionContract.ExceptionPresenter exceptionPresenter;

	/**
	 * 调起微信支付
	 */
	@Override
	public void pay(WXPayOrderBean order) {
		// TODO Auto-generated method stub
		LogUtils.e("order--->" + order);
		weChatApi = WXAPIFactory.createWXAPI(context, Constant.WX_APP_ID);
		if (payView != null) {
			payView.getOrderId(1000, order.getOrderid());
		}
		if (paySingleView != null) {
			paySingleView.getOrderId(1000, order.getOrderid());
		}
		PayReq localPayReq = new PayReq();
		localPayReq.appId = order.getData().getAppid();
		localPayReq.partnerId = order.getData().getPartnerid();
		localPayReq.prepayId = order.getData().getPrepayid();
		localPayReq.packageValue = order.getData().getPackageX();
		localPayReq.nonceStr = order.getData().getNoncestr();
		localPayReq.timeStamp = order.getData().getTimestamp();
		localPayReq.sign = order.getData().getSign();
		localPayReq.extData = "app data";
		// localPayReq.appId = Constant.WX_APP_ID;
		// localPayReq.partnerId = "1900006771";
		// localPayReq.prepayId = "wx0215320902917598cb2427fc0152819140";
		// localPayReq.packageValue = "Sign=WXPay";
		// localPayReq.nonceStr = "e444f1117d0a4d3e775e1ee57a8c2395";
		// localPayReq.timeStamp = "1525246329";
		// localPayReq.sign = "173C25EFC4174CBA493FF6F4E1187177";
		// localPayReq.extData = "app data";
		// LogUtils.e("wechatPay: request" + localPayReq);
		weChatApi.sendReq(localPayReq);
	}

	/**
	 * 获取支付列表
	 */
	@Override
	public void getPayList(final String serverCode, final String roleId,
						   final WXPayContract.WXPayView payView) {
		// TODO Auto-generated method stub

		this.payView = payView;
		context = HWControl.getInstance().getContext();
		weChatApi = WXAPIFactory
				.createWXAPI(context, Constant.WX_APP_ID, false);
		weChatApi.registerApp(Constant.WX_APP_ID);
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
		final String channel = "wechat";
		final String cpu = Build.CPU_ABI;
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
						LogUtils.e("获取支付列表成功返回字段---->" + response);
						payView.getPayList(code, result.getData());
						if (paySingleView != null) {
							paySingleView.getPayList(code, result.getData());
						}
					} else {
						LogUtils.e("获取支付列表返回字段---->" + response);
						if (paySingleView != null) {
							paySingleView.getPayList(code, result.getData());
						}
						payView.getPayList(code, result.getData());
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
				map.put("channel", "wechat");
				map.put("sessionid", sessionid);
				map.put("token", token);
				map.put("version", "1.0");
				map.put("servercode", serverCode);
				map.put("roleid", roleId);
				map.put("cpu", cpu);
				map.put("signature", signature);
				map.put("language", language);
				map.put("channel", channel);

				LogUtils.e("微信支付请求地址---->" + Constant.HW_PAY_LIST);
				LogUtils.e("微信支付请求字段---->" + map.toString());

				return map;
			}
		};

		RequestQueueHepler.getInstance().getQueue().add(stringRequest);

	}

	/**
	 * 获取订单信息
	 */
	@Override
	public void getOrder(final String serverCode, final String roleId,
						 DataBean dataBean, PaySingleContract.View paySingleView) {
		// TODO Auto-generated method stub
		context = HWControl.getInstance().getContext();
		this.paySingleView = paySingleView;
		gameCode = ResLoader.getString(context, "hw_gamecode");
		// 设置字段
		final String userId = HWConfigSharedPreferences.getInstance(context)
				.getUserId();
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
		final String gameName = ResLoader.getString(context, "game_name");
		final int itemid = dataBean.getId();
		final String description = dataBean.getDescription();
		final String activeDescription = dataBean.getActiveDescription();
		final String cpu = Build.CPU_ABI;
		final String channel = "wechat";
		final String ItemKey = dataBean.getItemKey();
		final String language = HWConfigSharedPreferences.getInstance(
				HWControl.getInstance().getContext()).getLanguage();

		// 请求网络
		StringRequest stringRequest = new StringRequest(Method.POST,
				Constant.HW_GET_WECHAT_ORDER, new Response.Listener<String>() {

			@Override
			public void onResponse(String response) {
				// TODO Auto-generated method stub
				Log.e("Com", "获取支付订单肯定没错--->" + response);
				exceptionPresenter = new ExceptionPresenter();
				try{
					Gson gson = new Gson();
					WXPayOrderBean result = gson.fromJson(response,
							WXPayOrderBean.class);
					Log.e("Com", "gson解释后数据--->" + result.toString());
					int code = Integer.parseInt(result.getCode());
					if (code == 1000) {
						// 1000就是成功
						LogUtils.e("获取支付订单成功返回字段---->" + response);
						// ParseOrder(result);
						pay(result);
						//调用微信支付前，先保存订单号
//								HWConfigSharedPreferences.getInstance(context).setOrderId(result.getOrderid());
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
				map.put("channel", channel);
				map.put("item_key", ItemKey);

				LogUtils.e("微信支付订单地址---->" + Constant.HW_GET_WECHAT_ORDER);
				LogUtils.e("微信支付订单请求字段---->" + map.toString());

				return map;
			}
		};

		RequestQueueHepler.getInstance().getQueue().add(stringRequest);

	}

	public void ParseOrder(WXPayOrderBean orderBean) {

		// String beanStr = orderBean.getData();
		// Gson gson = new Gson();
		// order = gson.fromJson(beanStr, PayOrderBean.Data.class);
		LogUtils.e("order---->" + order);

		pay(orderBean);
	}

	String extraData = "";

	/**
	 * 请求后台进行双向验证
	 */
	@Override
	public void getPayResult(final String serverCode, final String orderId) {
		// TODO Auto-generated method stub
		if (context == null) {
			context = HWControl.getInstance().getContext();
		}
		gameCode = ResLoader.getString(context, "hw_gamecode");
		// 设置字段
		final String gamecode = gameCode;
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
					//主要走到双验证，就说明orderid没作用了
//							HWConfigSharedPreferences.getInstance(context).setOrderId("");
					if (code == 1000) {
						// 1000就是成功
						LogUtils.e("获取支付验证成功返回字段---->" + response);
						payView.getPayStatus(1000, result.getMessage());
						if (paySingleView != null) {
							paySingleView.getPayStatus(1000,
									result.getMessage());
						}
					} else {
						LogUtils.e("获取支付验证返回字段---->" + response);
						payView.getPayStatus(code, result.getMessage());
						if (paySingleView != null) {
							paySingleView.getPayStatus(code,
									result.getMessage());
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
				payView.getPayStatus(10014, error.getMessage());
				//主要能走到双验证,就说明orderid已经没作用了
//						HWConfigSharedPreferences.getInstance(context).setOrderId("");

				if (paySingleView != null) {

					paySingleView.getPayStatus(10014,
							error.getMessage());
				}
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

				LogUtils.e("微信支付验证请求地址---->" + Constant.HW_CHECK_PAY);
				LogUtils.e("微信支付验证请求字段---->" + map.toString());

				return map;
			}
		};

		RequestQueueHepler.getInstance().getQueue().add(stringRequest);

	}

	/**
	 * 先验证平台币是否够支付
	 */
	@Override
	public void checkPlatformCoin(final String ItemKey, final String channel,final DataBean dataBean,final CoinView coinView) {
		// TODO Auto-generated method stub

		if (context == null) {
			context = HWControl.getInstance().getContext();
		}
		gameCode = ResLoader.getString(context, "hw_gamecode");
		// 设置字段
		final String gamecode = gameCode;
		final String comefrom = "android";
		final String version = "1.0";// 暂时固定
		final String userId = HWConfigSharedPreferences.getInstance(context)
				.getUserId();
		final String timestamp = HWUtils.getTimestamp();
		final String language = HWConfigSharedPreferences.getInstance(
				HWControl.getInstance().getContext()).getLanguage();
		// 请求网络
		StringRequest stringRequest = new StringRequest(Method.POST,
				Constant.HW_CHECK_COIN, new Response.Listener<String>() {

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
						coinView.getPlatFormResult(code, result.getPoint(),dataBean,channel);
						if (paySingleView != null) {
							paySingleView.getPayStatus(1000,
									result.getMessage());
						}
					} else {
						LogUtils.e("获取支付验证返回字段---->" + response);
						coinView.getPlatFormResult(code, result.getMessage(),dataBean,channel);
						if (paySingleView != null) {
							paySingleView.getPayStatus(code,
									result.getMessage());
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
				coinView.getPlatFormResult(2003, error.getMessage(),dataBean,channel);
				if (paySingleView != null) {

					paySingleView.getPayStatus(10014,
							error.getMessage());
				}
			}
		}) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> map = new HashMap<String, String>();
				map.put("gamecode", gamecode);
				map.put("comefrom", comefrom);
				map.put("version", version);
				map.put("channel", channel);
				map.put("userid", userId);
				map.put("item_key", ItemKey);
				map.put("timestamp", timestamp);
				map.put("language", language);

				LogUtils.e("平台币验证请求地址---->" + Constant.HW_CHECK_COIN);
				LogUtils.e("平台币验证请求字段---->" + map.toString());

				return map;
			}
		};

		RequestQueueHepler.getInstance().getQueue().add(stringRequest);

	}

	/**
	 * 使用平台币进行购买
	 */
	@Override
	public void PayForPlatformCoin(final String serverCode, final String roleId,final String channel,
								   DataBean dataBean, View paySingleView,final CoinView coinView) {
		// TODO Auto-generated method stub

		context = HWControl.getInstance().getContext();
		this.paySingleView = paySingleView;
		gameCode = ResLoader.getString(context, "hw_gamecode");
		// 设置字段
		final String userId = HWConfigSharedPreferences.getInstance(context)
				.getUserId();
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
		final String extraData = HWControl.getInstance().getExtraData();
		final String timestamp = HWUtils.getTimestamp();
		final String platform = ResLoader.getString(context, "platform");
		final String gameName = ResLoader.getString(context, "game_name");
		final int itemid = dataBean.getId();
		final String description = dataBean.getDescription();
		final String activeDescription = dataBean.getActiveDescription();
		final String cpu = Build.CPU_ABI;
		final String ItemKey = dataBean.getItemKey();
		final String language = HWConfigSharedPreferences.getInstance(
				HWControl.getInstance().getContext()).getLanguage();

		// 请求网络
		StringRequest stringRequest = new StringRequest(Method.POST,
				Constant.HW_PAY_WITH_COIN, new Response.Listener<String>() {

			@Override
			public void onResponse(String response) {
				// TODO Auto-generated method stub
				Log.e("Com", "获取平台币支付结果肯定没错--->" + response);
				exceptionPresenter = new ExceptionPresenter();
				try{
					Gson gson = new Gson();
					WXPayOrderBean result = gson.fromJson(response,
							WXPayOrderBean.class);
					Log.e("Com", "gson解释后数据--->" + result.toString());
					int code = Integer.parseInt(result.getCode());
					if (code == 1000) {
						// 1000就是成功
						LogUtils.e("获取平台币支付订单成功返回字段---->" + response);
						// ParseOrder(result);
						coinView.getPayStatus(code, result.getMessage());
					} else if (code == 1001) {
						coinView.getPayStatus(code, result.getMessage());
					} else {
						LogUtils.e("获取平台币支付订单返回字段---->" + response);
						coinView.getPayStatus(code, result.getMessage());
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
				coinView.getPayStatus(2003, error.getMessage());
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
				map.put("extraData", extraData);
				map.put("description", description);
				map.put("activeDescription", activeDescription);
				map.put("userid", userId);
				map.put("channel", channel);
				map.put("item_key", ItemKey);

				LogUtils.e("平台币支付订单地址---->" + Constant.HW_PAY_WITH_COIN);
				LogUtils.e("平台币支付请求字段---->" + map.toString());

				return map;
			}
		};

		RequestQueueHepler.getInstance().getQueue().add(stringRequest);

	}

	@Override
	public void checkPlatformCoin(final String gameItemId, final String channel,
								  final HWGpPayItem dataBean, final CoinView coinView) {
		LogUtils.e("谷歌的平台币验证");
		// 专门针对谷歌弄得
		if (context == null) {
			context = HWControl.getInstance().getContext();
		}
		gameCode = ResLoader.getString(context, "hw_gamecode");
		// 设置字段
		final String gamecode = gameCode;
		final String comefrom = "android";
		final String extraData = HWControl.getInstance().getExtraData();
		final String version = "1.0";// 暂时固定
		final String userId = HWConfigSharedPreferences.getInstance(context)
				.getUserId();
		final String timestamp = HWUtils.getTimestamp();
		final String language = HWConfigSharedPreferences.getInstance(
				HWControl.getInstance().getContext()).getLanguage();
		// 请求网络
		StringRequest stringRequest = new StringRequest(Method.POST,
				Constant.HW_CHECK_COIN, new Response.Listener<String>() {

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
						coinView.getPlatFormResult(code, result.getPoint(),dataBean,channel);
						if (paySingleView != null) {
							paySingleView.getPayStatus(1000,
									result.getMessage());
						}
					} else {
						LogUtils.e("获取支付验证返回字段---->" + response);
						coinView.getPlatFormResult(code, result.getMessage(),dataBean,channel);
						if (paySingleView != null) {
							paySingleView.getPayStatus(code,
									result.getMessage());
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
				coinView.getPlatFormResult(2003, error.getMessage(),dataBean,channel);
				if (paySingleView != null) {

					paySingleView.getPayStatus(10014,
							error.getMessage());
				}
			}
		}) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> map = new HashMap<String, String>();
				map.put("gamecode", gamecode);
				map.put("comefrom", comefrom);
				map.put("version", version);
				map.put("channel", channel);
				map.put("userid", userId);
				map.put("item_key", gameItemId);
				map.put("timestamp", timestamp);
				map.put("language", language);

				LogUtils.e("平台币验证请求地址---->" + Constant.HW_CHECK_COIN);
				LogUtils.e("平台币验证请求字段---->" + map.toString());

				return map;
			}
		};

		RequestQueueHepler.getInstance().getQueue().add(stringRequest);
	}

	@Override
	public void PayForPlatformCoin(final String serverCode, final String roleId,
								   final String channel, HWGpPayItem dataBean, View paySingleView,
								   final CoinView coinView) {
		// 专门针对谷歌弄得
		context = HWControl.getInstance().getContext();
		this.paySingleView = paySingleView;
		gameCode = ResLoader.getString(context, "hw_gamecode");
		// 设置字段
		final String userId = HWConfigSharedPreferences.getInstance(context)
				.getUserId();
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
		final String gameName = ResLoader.getString(context, "game_name");
		final String itemid = dataBean.getGameItemId();
		final String extraData = HWControl.getInstance().getExtraData();
		final String description = dataBean.getDescription();
		final String activeDescription = dataBean.getActiveDescription();
		final String cpu = Build.CPU_ABI;
		final String ItemKey = dataBean.getGameItemId();
		final String language = HWConfigSharedPreferences.getInstance(
				HWControl.getInstance().getContext()).getLanguage();

		// 请求网络
		StringRequest stringRequest = new StringRequest(Method.POST,
				Constant.HW_PAY_WITH_COIN, new Response.Listener<String>() {

			@Override
			public void onResponse(String response) {
				// TODO Auto-generated method stub
				Log.e("Com", "获取平台币支付结果肯定没错--->" + response);
				exceptionPresenter = new ExceptionPresenter();
				try{
					Gson gson = new Gson();
					WXPayOrderBean result = gson.fromJson(response,
							WXPayOrderBean.class);
					Log.e("Com", "gson解释后数据--->" + result.toString());
					int code = Integer.parseInt(result.getCode());
					if (code == 1000) {
						// 1000就是成功
						LogUtils.e("获取平台币支付订单成功返回字段---->" + response);
						// ParseOrder(result);
						coinView.getPayStatus(code, result.getMessage());
					} else if (code == 1001) {
						coinView.getPayStatus(code, result.getMessage());
					} else {
						LogUtils.e("获取平台币支付订单返回字段---->" + response);
						coinView.getPayStatus(code, result.getMessage());
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
				coinView.getPayStatus(2003, error.getMessage());
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
				map.put("extraData", extraData);
				map.put("language", language);
				map.put("gameName", gameName);
				map.put("description", description);
				map.put("activeDescription", activeDescription);
				map.put("userid", userId);
				map.put("channel", "android");
				map.put("item_key", ItemKey);

				LogUtils.e("平台币支付订单地址---->" + Constant.HW_PAY_WITH_COIN);
				LogUtils.e("平台币支付请求字段---->" + map.toString());

				return map;
			}
		};

		RequestQueueHepler.getInstance().getQueue().add(stringRequest);
	}
}
