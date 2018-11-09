package com.example.huanwensdk.mvp.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.huanwensdk.DataBase.DBUtils;
import com.example.huanwensdk.bean.google.HWGpPayItem;
import com.example.huanwensdk.bean.google.HWPayItemBean;
import com.example.huanwensdk.bean.loginTrial.OrderResultBean;
import com.example.huanwensdk.bean.order.HWGpOrderInfo;
import com.example.huanwensdk.bean.user.HWInfoUser;
import com.example.huanwensdk.bean.user.RoleInfo;
import com.example.huanwensdk.googleUtils.IabHelper;
import com.example.huanwensdk.googleUtils.IabHelper.OnConsumeMultiFinishedListener;
import com.example.huanwensdk.googleUtils.IabHelper.OnIabSetupFinishedListener;
import com.example.huanwensdk.googleUtils.IabHelper.QueryInventoryFinishedListener;
import com.example.huanwensdk.googleUtils.IabResult;
import com.example.huanwensdk.googleUtils.Inventory;
import com.example.huanwensdk.googleUtils.Purchase;
import com.example.huanwensdk.http.RequestQueueHepler;
import com.example.huanwensdk.mvp.contract.ExceptionContract;
import com.example.huanwensdk.mvp.contract.GooglePayContract;
import com.example.huanwensdk.mvp.contract.GooglePayContract.View;
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
 * @Title: GooglePayModel.java
 * @Package com.example.huanwensdk.mvp.model
 * @Description: 谷歌支付业务逻辑类
 * @author: Android_ruan
 * @date: 2018-5-21 上午10:28:21
 * @version V1.0
 */
public class GooglePayModel implements GooglePayContract.Model {

	IabHelper mHelper;
	Context context;
	private String gameCode;
	String sessionid = "";
	String token = "";
	String serverCode;
	String roleId;
	String orderId;


	ExceptionContract.ExceptionPresenter exceptionPresenter;

	GooglePayContract.View googleView;

	private List<HWGpPayItem> hwGpPayItems;



	/**
	 * 支付双验证
	 */
	@Override
	public void pay(final String sdkOriginal, final String sdkStatus,
					final String gpOrderId, final String serverCode, final String orderid,final String productId
			,final String strPurchas,
					boolean isShowToast) {
		// TODO Auto-generated method stub

		if (context == null) {
			context = HWControl.getInstance().getContext();
		}

		gameCode = ResLoader.getString(context, "hw_gamecode");
		String userId = HWConfigSharedPreferences.getInstance(context)
				.getUserId();
		HWInfoUser infoUser = DBUtils.getInstance().queryInfoUser(userId);
		if (infoUser != null) {
			sessionid = infoUser.getSessionid();
			token = infoUser.getToken();
		} else {

		}
		// 设置字段
		final String gamecode = gameCode;
		final String comefrom = "android";
		final String GpproductId = HWConfigSharedPreferences.getInstance(context).getGPproductId();
		final String HWorderId = HWConfigSharedPreferences.getInstance(context).getOrderId();
		final String extraData = HWControl.getInstance().getExtraData();
		final String platform = ResLoader.getString(context, "platform");
		// final String authorization =
		// Base64.encode((gamecode+":"+serverCode+":"+"1.0"+":"+"android"+":"+platform).getBytes());
		final String timestamp = HWUtils.getTimestamp();
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
		final String strSign = sessionid + token + gamecode + serverCode
				+ "1.0" + language + platform + "android" + "android"
				+ timestamp + "GAME#_DRE*)AM:E&R";

		// 请求网络
		StringRequest stringRequest = new StringRequest(Method.POST,
				Constant.HW_PAY, new Response.Listener<String>() {

			@Override
			public void onResponse(String response) {
				// TODO Auto-generated method stub
				Log.e("Com", "请求谷歌支付双重验证肯定没错--->" + response);
				try{
					Gson gson = new Gson();
					HWPayItemBean result = gson.fromJson(response,
							HWPayItemBean.class);
					Log.e("Com", "gson解释后数据--->" + result.toString());
					int code = Integer.parseInt(result.getCode());
					if (code == 1000) {
						// 1000就是成功
						// 检测数据绑定
						LogUtils.e("获取谷歌支付双重验证成功返回字段---->" + response);
						googleView.payResult(1110, "支付成功");
					} else if (code == 1001) {
						googleView.payResult(1001, "支付失敗");
					} else {
						LogUtils.e("获取谷歌支付双验证失败返回字段---->" + response);
						googleView.payResult(1001, "支付失敗");
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
//				map.put("authorization", strSign);// 授权
//				map.put("sdkOriginal", sdkOriginal);
//				map.put("sdkStatus", sdkStatus);
				map.put("orderid", HWorderId);
				map.put("gpOrderId", gpOrderId);
				map.put("servercode", serverCode);
				map.put("gamecode", gamecode);
				map.put("comefrom", comefrom);
				map.put("timestamp", timestamp);
				map.put("platform", platform);
				map.put("version", "1.0");
				map.put("productId", GpproductId);
				map.put("purchase", strPurchas);
				map.put("extraData", extraData);


				LogUtils.e("获取谷歌支付双验证请求地址---->" + Constant.HW_PAY);
				LogUtils.e("获取谷歌支付双验证请求字段----->" + map.toString());

				return map;
			}
		};

		stringRequest.setRetryPolicy(new DefaultRetryPolicy(
				20000,//默认超时时间，应设置一个稍微大点儿的，
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,//默认最大尝试次数
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
		));
		RequestQueueHepler.getInstance().getQueue().add(stringRequest);

	}

	/**
	 * 初始化谷歌的支付
	 */
	public void init(GooglePayContract.View googleView) {

		context = HWControl.getInstance().getContext();

		roleId = HWConfigSharedPreferences.getInstance(context).getRoleId();
		RoleInfo roleInfo = DBUtils.getInstance().queryRole(roleId);
		if(roleInfo!=null){
			serverCode = roleInfo.getServerCode();
		}else{
			LogUtils.e("谷歌获取服务器失败");
		}

		this.googleView = googleView;
		// 第一步,先拿到谷歌公钥，也就是appkey
		String base64EncodedPublicKey = HWConfigSharedPreferences.getInstance(context).getGPPublicKey();
		if(TextUtils.isEmpty(base64EncodedPublicKey)&&base64EncodedPublicKey!=null){
			Toast.makeText(context, ResLoader.getString(context, "string_pay_google_init_error"), Toast.LENGTH_SHORT).show();
			return;
		}
		// 创建谷歌支付helper
		mHelper = new IabHelper(context, base64EncodedPublicKey);
		// 设置是否需要输出日志
		mHelper.enableDebugLogging(true, "Com");
		// 进行安装
		mHelper.startSetup(new OnIabSetupFinishedListener() {

			@Override
			public void onIabSetupFinished(IabResult result) {
				LogUtils.e("开始进行安装IAP--->"+result.getMessage());
				// 检测结果是否成功
				if (result.isSuccess()) {
					LogUtils.e("谷歌安装成功--->"+result.getMessage());
					// 成功后，查询是否有未消费订单
					mHelper.queryInventoryAsync(mGotInventoryListener);
				} else {
					//提醒用户谷歌安装时出错，可能是手机没有安装googlepay应用商店
					Toast.makeText(context, ResLoader.getString(context, "string_pay_google_initerror"), Toast.LENGTH_SHORT).show();
					LogUtils.e("谷歌支付进行安装出错----->" + result.getMessage());
				}

			}
		});

		//设置谷歌对象
		HWControl.getInstance().setmHelper(mHelper);

	}

	/**
	 * 调起谷歌支付后，支付结果
	 */
	IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
		public void onIabPurchaseFinished(IabResult paramIabResult,
										  Purchase paramPurchase) {
			LogUtils.e("进入谷歌支付监听");
			if (paramIabResult.isSuccess()) {
//				HashMap localHashMap = new HashMap();
//				localHashMap.put("gpOrderid", paramPurchase.getOrderId());
				// 支付成功，消费一下项目
				// FGGooglePayAction.this.consumeAsync(paramPurchase);
				//支付成功后，进行消费一下
				LogUtils.e("谷歌支付成功，下一步进行消费");
				consumeAsync(paramPurchase);
				return;
			}else{
				Toast.makeText(context, ResLoader.getString(context, "string_pay_google_pay_error"), Toast.LENGTH_SHORT).show();
			}
		}
	};

	/**
	 * 消费项目
	 *
	 * @param paramPurchase
	 */
	public void consumeAsync(Purchase paramPurchase) {
		mHelper.consumeAsync(paramPurchase, mConsumeFinishedListener);
	}

	int times = 0;

	/**
	 * 消费结果监听
	 */
	IabHelper.OnConsumeFinishedListener mConsumeFinishedListener = new IabHelper.OnConsumeFinishedListener() {
		public void onConsumeFinished(Purchase paramPurchase,
									  IabResult paramIabResult) {
			LogUtils.e("支付成功后，进行项目消费--->"+paramPurchase.toString());
			if (paramIabResult.isSuccess()) {
				LogUtils.e("项目消费成功---->");
				times = times + 1;
				googleView.consumeResult(1000, "消费项目成功", orderId);
				//消费成功后，回调给主界面。然后进行双验证
//				HashMap localHashMap = new HashMap();
//				localHashMap.put("gpOrderid", paramPurchase.getOrderId());
//				FGGooglePayAction.this.handlerLogEvent(Integer
//						.valueOf(FGEventType.FG_EVEVT_TYPE_FG_CONSUME_SUCCESS),
//						localHashMap);
				pay(paramPurchase.getOriginalJson(), "1",
						paramPurchase.getOrderId(),
						serverCode,
						orderId, paramPurchase.getSku(),paramPurchase.getToken(),true);
				LogUtils.e("次数："+times);
				return;
			}else{
				Toast.makeText(context, ResLoader.getString(context, "string_pay_google_consume_error"), Toast.LENGTH_SHORT).show();
			}
		}
	};

	/**
	 * 查询为消费订单回调接口
	 */
	IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new QueryInventoryFinishedListener() {

		@Override
		public void onQueryInventoryFinished(IabResult result, Inventory inv) {
			// TODO Auto-generated method stub
			LogUtils.e("======谷歌支付查询未消费订单回调完成======");
			if (mHelper == null) {
				LogUtils.e("mHelper为空");
				return;
			}
			if (result.isFailure()) {
				LogUtils.e("查询未消费订单出错----->" + result);
				// 根据之前的SDK的流程，这里就请求后台，加载品项了
				getGDItemList();
			} else {
				// 开始消费所有商品
				consumeAllAsync(inv.getAllPurchases());
			}

		}
	};


	/**
	 * 请求后台品项
	 */
	private void getGDItemList() {

		if (context == null) {
			context = HWControl.getInstance().getContext();
		}

		gameCode = ResLoader.getString(context, "hw_gamecode");
		String userId = HWConfigSharedPreferences.getInstance(context)
				.getUserId();
		HWInfoUser infoUser = DBUtils.getInstance().queryInfoUser(userId);
		if (infoUser != null) {
			sessionid = infoUser.getSessionid();
			token = infoUser.getToken();
		} else {

		}
		// 设置字段
		final String gamecode = gameCode;
		final String comefrom = "android";
		final String timestamp = HWUtils.getTimestamp();
		final String platform = ResLoader.getString(context, "platform");
		final String cpu = Build.CPU_ABI;
//		final String channel = ResLoader.getString(context, "channel");
		final String channel = "android";
		if (TextUtils.isEmpty(channel)) {
			Toast.makeText(context, "channel为空", Toast.LENGTH_SHORT).show();
			return;
		}
		// final String strSign = gamecode + platform + "android" + channel
		// + timestamp + Constant.HW_APP_KEY;
		// final String signature = MD5.getMD5(strSign.toLowerCase());
		final String language = HWConfigSharedPreferences.getInstance(
				HWControl.getInstance().getContext()).getLanguage();
		final String strSign = MD5.getMD5(sessionid + token + gamecode
				+ "101" + "1.0" + language + platform + "android" + "wechat"
				+ timestamp + "FAN#_GA*)KK:LK%%");

		// 请求网络
		StringRequest stringRequest = new StringRequest(Method.POST,
				Constant.HW_PAY_LIST, new Response.Listener<String>() {

			@Override
			public void onResponse(String response) {
				// TODO Auto-generated method stub
				Log.e("Com", "请求谷歌支付品项肯定没错--->" + response);
				try{
					Gson gson = new Gson();
					HWPayItemBean result = gson.fromJson(response,
							HWPayItemBean.class);
					Log.e("Com", "gson解释后数据--->" + result.toString());
					int code = Integer.parseInt(result.getCode());
					if (code == 1000) {
						// 1000就是成功
						// 检测数据绑定
						LogUtils.e("获取谷歌支付品项成功返回字段---->" + response);
						googleView.getGDItems(1000, result.getData());
						// 获取商品成功后，查看是否有未消费的
						getGpItamList(result);
					} else if (code == 1001) {
						googleView.getGDItems(1001, null);
					} else {
						LogUtils.e("获取谷歌支付品项失败返回字段---->" + response);
						googleView.getGDItems(1002, null);
					}
				}catch(JsonIOException e){
					LogUtils.e("JsonIOException---->"+e.toString());
					Toast.makeText(context, ResLoader.getString(context, "json_encode_error"), Toast.LENGTH_SHORT).show();
				}catch(JsonParseException e){
					LogUtils.e("JsonParseException---->"+e.toString());
					Toast.makeText(context, ResLoader.getString(context, "json_encode_error"), Toast.LENGTH_SHORT).show();
				}catch (Exception e) {
					LogUtils.e("Exception---->"+e.toString());
					Toast.makeText(context, ResLoader.getString(context, "json_encode_error"), Toast.LENGTH_SHORT).show();
				}

			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				// TODO Auto-generated method stub
				Log.e("Com", "出错--->" + error.getMessage());
				Toast.makeText(context, "请求服务出错", Toast.LENGTH_SHORT).show();
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
				map.put("version", "1.0");
				map.put("language", language);
				map.put("roleid", roleId);
				map.put("signature", strSign);

				LogUtils.e("获取谷歌支付品项请求地址---->" + Constant.HW_PAY_LIST);
				LogUtils.e("获取谷歌支付品项请求字段----->" + map.toString());

				return map;
			}
		};

		RequestQueueHepler.getInstance().getQueue().add(stringRequest);

	}

	/**
	 * 获取到网络请求后，操作
	 *
	 * @param result
	 */
	private void getGpItamList(HWPayItemBean result) {

		hwGpPayItems = result.getData();
		ArrayList<String> itemsId = new ArrayList<String>();
		LogUtils.e("谷歌字段--->"+hwGpPayItems.size());
		for (HWGpPayItem hwGpPayItem : hwGpPayItems) {
			LogUtils.e("Prdouct--->"+hwGpPayItem.getProductid());
			itemsId.add(hwGpPayItem.getProductid());
		}
		LogUtils.e("itemsId--->"+itemsId.size());
		if (mHelper != null) {
			mHelper.queryInventoryAsync(true, itemsId, mQueryFinishedListener);
		} else {
			LogUtils.e("mHelpe为空");
			googleView.getGDItems(1004, null);
		}
	}

	IabHelper.QueryInventoryFinishedListener mQueryFinishedListener = new QueryInventoryFinishedListener() {

		@Override
		public void onQueryInventoryFinished(IabResult result, Inventory inv) {
			// TODO Auto-generated method stub
			LogUtils.e("======执行查询所有品项的消费情况=====");

			if (result.isFailure()) {
				// 失败进行
				// 有可能直接返回报错
				LogUtils.e("查询谷歌商品有没有报错了----->" + result.getMessage());
			} else {
				// 证明了后台商品与谷歌的商品是同步的
				ArrayList<HWGpPayItem> items = new ArrayList<HWGpPayItem>();
//				googleView.getGDItems(1000, hwGpPayItems);// 回调给界面对话框
				for (HWGpPayItem payItem : hwGpPayItems) {

					if (inv.getSkuDetails(payItem.getProductid()) == null) {
						// 暂时不知这个要干嘛用
					}
					items.add(payItem);

				}
			}
		}
	};

	/**
	 * 消费所有品项
	 *
	 * @param paramList
	 */
	public void consumeAllAsync(List<Purchase> paramList) {
		mHelper.consumeAsync(paramList, mConsumeMultiFinishedListener);
	}

	/**
	 * 消费结果，回调接口
	 */
	IabHelper.OnConsumeMultiFinishedListener mConsumeMultiFinishedListener = new OnConsumeMultiFinishedListener() {

		@Override
		public void onConsumeMultiFinished(List<Purchase> purchases,
										   List<IabResult> results) {

			LogUtils.e("=====商品消费完成======");

			//重新加载后台的商品数据
//			getGDItemList();

		}
	};

	@Override
	public void GetItemList(final String serverCode, String roleId,
							final String proItemid, View googleView) {
		// TODO Auto-generated method stub
		LogUtils.e("执行谷歌支付请求---->");
		context = HWControl.getInstance().getContext();
		final String userid = HWConfigSharedPreferences.getInstance(context)
				.getUserId();
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

		// final String strSign = sessionid + token + gamecode + platform +
		// "android"
		// + timestamp + "FAN#_GA*)KK:LK%%";
		// final String signature = MD5.getMD5(strSign);
		// 请求网络
		StringRequest stringRequest = new StringRequest(Method.POST,
				Constant.HW_PAY_LIST, new Response.Listener<String>() {

			@Override
			public void onResponse(String response) {
				// TODO Auto-generated method stub
				Log.e("Com", "获取谷歌订单肯定没错--->" + response);
				try{
					Gson gson = new Gson();
					OrderResultBean result = gson.fromJson(response,
							OrderResultBean.class);
					Log.e("Com", "gson解释后数据--->" + result.toString());
					int code = Integer.parseInt(result.getCode());
					if (code == 1000) {
						// 1000就是成功
						// 检测数据绑定
						LogUtils.e("获取谷歌订单成功返回字段---->" + response);
					} else if (code == 1001) {
					} else {
						LogUtils.e("获取谷歌订单失败返回字段---->" + response);
					}
				}catch(JsonSyntaxException e){
					LogUtils.e("JsonSyntaxException---->"+e.toString());
					Toast.makeText(context, ResLoader.getString(context, "json_encode_error"), Toast.LENGTH_SHORT).show();
				}catch(JsonIOException e){
					LogUtils.e("JsonIOException---->"+e.toString());
					Toast.makeText(context, ResLoader.getString(context, "json_encode_error"), Toast.LENGTH_SHORT).show();
				}catch(JsonParseException e){
					LogUtils.e("JsonParseException---->"+e.toString());
					Toast.makeText(context, ResLoader.getString(context, "json_encode_error"), Toast.LENGTH_SHORT).show();
				}catch (Exception e) {
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
				map.put("gamecode", gamecode);
				map.put("comefrom", comefrom);
				map.put("timestamp", timestamp);
				map.put("platform", platform);
				map.put("channel", "android");
				map.put("signature", signature);
				map.put("sessionid", sessionid);
				map.put("token", token);
				map.put("userid", userid);
				map.put("language", language);
				map.put("version", "1.0");
				map.put("servercode", serverCode);
				if (!TextUtils.isEmpty(proItemid)) {
					map.put("proItemid", proItemid);
				}
				LogUtils.e("获取谷歌订单请求地址---->" + Constant.HW_PAY_LIST);
				LogUtils.e("获取谷歌订单请求字段----->" + map.toString());
				Log.e("Com", "获取谷歌订单请求字段----->" + map.toString());

				return map;
			}
		};

		RequestQueueHepler.getInstance().getQueue().add(stringRequest);

	}

	String payType;
	String itemid;

	/**
	 * 获取订单信息
	 */
	@Override
	public void getOrder(final String itemId, final String serverCode,
						 final String roleId) {

		LogUtils.e("执行谷歌支付请求---->"+itemId);
		context = HWControl.getInstance().getContext();
		final String userid = HWConfigSharedPreferences.getInstance(context)
				.getUserId();
		gameCode = ResLoader.getString(context, "hw_gamecode");
		HWInfoUser infoUser = DBUtils.getInstance().queryInfoUser(userid);
		if(hwGpPayItems!=null&&hwGpPayItems.size()>0){
			HWGpPayItem hwPayItemBean = hwGpPayItems.get(0);
			// 设置字段
			payType = hwPayItemBean.getGameItemId();
			itemid = hwPayItemBean.getId();
			LogUtils.e("执行了hwGpPayItems--->"+hwPayItemBean.toString());
		}else{
			payType = itemId;
			itemid = itemId;
		}
		final String machineid = HWUtils.getDeviceId(context);
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
		// final String strSign = sessionid + token + gamecode + platform +
		// "android"
		// + timestamp + "FAN#_GA*)KK:LK%%";
		// final String signature = MD5.getMD5(strSign);
		// 请求网络
		StringRequest stringRequest = new StringRequest(Method.POST,
				Constant.HW_GET_GOOGLE_ORDER, new Response.Listener<String>() {

			@Override
			public void onResponse(String response) {
				// TODO Auto-generated method stub
				Log.e("Com", "获取谷歌订单肯定没错--->" + response);
				exceptionPresenter = new ExceptionPresenter();
				try{
					Gson gson = new Gson();
					HWGpOrderInfo result = gson.fromJson(response,
							HWGpOrderInfo.class);
					Log.e("Com", "gson解释后数据--->" + result.toString());
					int code = Integer.parseInt(result.getCode());
					if (code == 1000) {
						// 1000就是成功
						// 检测数据绑定
						LogUtils.e("获取谷歌订单成功返回字段---->" + response);
						HWConfigSharedPreferences.getInstance(context).setOrderId(result.getOrderid());
						LogUtils.e("获取的orderid--->"+HWConfigSharedPreferences.getInstance(context).getOrderId());
						if (mHelper != null) {
							// 真正调起谷歌支付
							HWConfigSharedPreferences.getInstance(context).setGPproductId(result.getData().getProductid());
							mHelper.launchPurchaseFlow((Activity)context, result.getData().getProductid(), 1000, mPurchaseFinishedListener);
						}
					} else if (code == 1001) {
						Toast.makeText(context, ResLoader.getString(context, "string_pay_google_getorder_error"), Toast.LENGTH_SHORT).show();
					} else {
						LogUtils.e("获取谷歌订单失败返回字段---->" + response);
						Toast.makeText(context, ResLoader.getString(context, "string_pay_google_getorder_error"), Toast.LENGTH_SHORT).show();
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
				Toast.makeText(context, ResLoader.getString(context, "generic_server_down"), Toast.LENGTH_SHORT).show();
			}
		}) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> map = new HashMap<String, String>();
				map.put("gamecode", gamecode);//
				map.put("machineid", machineid);//
				map.put("comefrom", comefrom);//
				map.put("timestamp", timestamp);//
				map.put("platform", platform);//
				map.put("signature", signature);//
				map.put("sessionid", sessionid);//
				map.put("token", token);//
				map.put("itemid", itemId);//
				map.put("version", "1.0");
				map.put("roleid", roleId);//
				map.put("payType", payType);//
				map.put("servercode", serverCode);//
				map.put("userid", userid);
				map.put("item_key", payType);
				map.put("channel", "android");
				LogUtils.e("获取谷歌订单请求地址---->" + Constant.HW_GET_GOOGLE_ORDER);
				LogUtils.e("获取谷歌订单请求字段----->" + map.toString());
				Log.e("Com", "获取谷歌订单请求字段----->" + map.toString());

				return map;
			}
		};

		RequestQueueHepler.getInstance().getQueue().add(stringRequest);

	}

}
