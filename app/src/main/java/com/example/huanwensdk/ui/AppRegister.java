package com.example.huanwensdk.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.huanwensdk.utils.Constant;
import com.example.huanwensdk.utils.LogUtils;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class AppRegister extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		final IWXAPI msgApi = WXAPIFactory.createWXAPI(context, null);
		LogUtils.e("有回调？？");
		// 将该app注册到微信
		msgApi.registerApp(Constant.WX_APP_ID);
	}
}
