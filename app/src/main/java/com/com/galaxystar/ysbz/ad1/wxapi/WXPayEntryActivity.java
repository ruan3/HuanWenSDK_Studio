package com.com.galaxystar.ysbz.ad1.wxapi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.huanwensdk.mvp.contract.listener.WXPayListener;
import com.example.huanwensdk.utils.Constant;
import com.example.huanwensdk.utils.HWControl;
import com.example.huanwensdk.utils.LogUtils;
import com.example.huanwensdk.utils.ResLoader;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler{
	
	private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";
	
    private IWXAPI api;
    
    private WXPayListener payLisenter;
    
    Context context;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.pay_result);
        
    	api = WXAPIFactory.createWXAPI(this, Constant.WX_APP_ID);
    	api.registerApp(Constant.WX_APP_ID);
        boolean result = api.handleIntent(getIntent(), this);
        Log.e("Com", "result---->"+result);
        
    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@Override
	public void onResp(BaseResp resp) {
		Log.e("Com", "onPayFinish, errCode = " + resp.errCode);
		Log.e("Com", "onPayFinish, openId = " + resp.openId);
		Log.e("Com", "onPayFinish, errStr = " + resp.errStr);
		context = HWControl.getInstance().getContext();
		payLisenter = HWControl.getInstance().getWxPayListener();
		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
//			AlertDialog.Builder builder = new AlertDialog.Builder(this);
//			builder.setTitle(R.string.app_tip);
//			builder.setMessage(getString(R.string.pay_result_callback_msg, String.valueOf(resp.errCode)));
//			builder.show();
			LogUtils.e("payLisenter--->"+payLisenter);
			if(payLisenter!=null){
				if(resp.errCode == -1){
					payLisenter.WXCallback(resp.errCode, ResLoader.getString(context, "pay_fail"));
					Toast.makeText(context, "调起微信错误", Toast.LENGTH_SHORT).show();
				}
				if(resp.errCode == -2){
					LogUtils.e("用户取消支付");
					payLisenter.WXCallback(resp.errCode, ResLoader.getString(context, "pay_cancel"));
				}
				if(resp.errCode == 0){
					LogUtils.e("支付成功");
					payLisenter.WXCallback(resp.errCode,ResLoader.getString(context, "pay_success"));
				}
			}
			finish();
		}
	}
}