package com.com.galaxystar.ysbz.ad1.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.huanwensdk.mvp.contract.listener.WeChatLoginListener;
import com.example.huanwensdk.utils.Constant;
import com.example.huanwensdk.utils.HWControl;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler{
	
	private static final int TIMELINE_SUPPORTED_VERSION = 0x21020001;
	
	
	// IWXAPI 是第三方app和微信通信的openapi接口
    private IWXAPI api;
    
    WeChatLoginListener weChatLoginListener;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.pay_result);
        
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
    	api = WXAPIFactory.createWXAPI(this, Constant.WX_APP_ID, false);
    	 api.registerApp(Constant.WX_APP_ID);   
        
        
        api.handleIntent(getIntent(), this);
    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	// 微信发送请求到第三方应用时，会回调到该方法
	@Override
	public void onReq(BaseReq req) {
		Toast.makeText(this, "openid = " + req.openId, Toast.LENGTH_SHORT).show();
		
		switch (req.getType()) {
		case ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX:
			break;
		case ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX:
			break;
		case ConstantsAPI.COMMAND_LAUNCH_BY_WX:
			break;
		default:
			break;
		}
	}

	// 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
	@Override
	public void onResp(BaseResp resp) {
		Log.e("Com", "openid = " + resp.openId);
		Log.e("Com", "baseResp:"+resp.toString());
		if (resp.getType() == ConstantsAPI.COMMAND_SENDAUTH) {
//			Toast.makeText(this, "code = " + ((SendAuth.Resp) resp).code, Toast.LENGTH_SHORT).show();
		}
		weChatLoginListener = HWControl.getInstance().getWeChatLoginListener();
		int result = 0;
		
		switch (resp.errCode) {
		case BaseResp.ErrCode.ERR_OK:
			weChatLoginListener.LoginCallBack(0, ((SendAuth.Resp) resp).code);
			finish();
			break;
		case BaseResp.ErrCode.ERR_USER_CANCEL:
			weChatLoginListener.LoginCallBack(BaseResp.ErrCode.ERR_USER_CANCEL, ((SendAuth.Resp) resp).code);
			finish();
			break;
		case BaseResp.ErrCode.ERR_AUTH_DENIED:
			weChatLoginListener.LoginCallBack(BaseResp.ErrCode.ERR_AUTH_DENIED, ((SendAuth.Resp) resp).code);
			finish();
			break;
		default:
			break;
		}
		
	}
}