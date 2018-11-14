package com.example.huanwensdk.sdk;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;

import com.example.huanwensdk.DataBase.DBUtils;
import com.example.huanwensdk.bean.user.HWInfoUser;
import com.example.huanwensdk.bean.user.RoleInfo;
import com.example.huanwensdk.bean.wxpay.PayItemListBean;
import com.example.huanwensdk.googleUtils.IabHelper;
import com.example.huanwensdk.mvp.contract.CheckServerContract;
import com.example.huanwensdk.mvp.contract.FreeLoginContract;
import com.example.huanwensdk.mvp.contract.PayPalContract;
import com.example.huanwensdk.mvp.contract.SaveRoleContract;
import com.example.huanwensdk.mvp.contract.initcontract;
import com.example.huanwensdk.mvp.contract.listener.HWGameMemberListener;
import com.example.huanwensdk.mvp.contract.listener.LoginListener;
import com.example.huanwensdk.mvp.contract.listener.PayLisenter;
import com.example.huanwensdk.mvp.presenter.CheckServerPresenter;
import com.example.huanwensdk.mvp.presenter.FreeLoginPresenter;
import com.example.huanwensdk.mvp.presenter.PayPalPresenter;
import com.example.huanwensdk.mvp.presenter.SaveRolePresenter;
import com.example.huanwensdk.ui.SplashActivity;
import com.example.huanwensdk.ui.dialog.ForgetPasswordDialog;
import com.example.huanwensdk.ui.dialog.LoginDialog;
import com.example.huanwensdk.ui.dialog.LoginTrialDialog;
import com.example.huanwensdk.ui.dialog.MemberLoginDialog;
import com.example.huanwensdk.ui.dialog.PayDialog;
import com.example.huanwensdk.ui.dialog.PaySingleDialog;
import com.example.huanwensdk.ui.dialog.RegisterDialog;
import com.example.huanwensdk.ui.dialog.ResetPasswordDialog;
import com.example.huanwensdk.ui.dialog.RetrieveGuestAccountDialog;
import com.example.huanwensdk.ui.dialog.TrialBindingEmailDialog;
import com.example.huanwensdk.ui.dialog.UserCenterDialog;
import com.example.huanwensdk.utils.DialogUtils;
import com.example.huanwensdk.utils.HWControl;
import com.example.huanwensdk.utils.LogUtils;
import com.example.huanwensdk.utils.sp.HWConfigSharedPreferences;
import com.facebook.CallbackManager;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

/**
 * 
 * @Title:  HWSDK.java   
 * @Package    
 * @Description:    SDK调用入口
 * @author: Android_ruan     
 * @date:   2018-3-22 下午2:20:59   
 * @version V1.0
 */
public class HWSDK {
	
	private final static int pay = 1;
	private final static int login = 2;
	private final static int usercenter = 3;
	private final static int paysingle = 4;

	private HWSDK(){}
	
	private static class HWSDKHelper{
		
		private static HWSDK hwsdk = new HWSDK();
		
	}
	
	public static HWSDK getInstance(){
		return HWSDKHelper.hwsdk;
	}
	
	public void initSDK(Context context,initcontract.initListener iniListener){
		HWControl.getInstance().setContext(context);
		if(iniListener!=null){
			
//		HWControl.getInstance().setContext(context);//给SDK设置上下文
			HWControl.getInstance().setInitListener(iniListener);//给SDK设置回调监听
			Intent intent = new Intent(context,SplashActivity.class);
			context.startActivity(intent);//跳转到splash界面
		}else{
			LogUtils.e("initListenser为空");
		}
		
	}
	
	public void Login(Context context,LoginListener loginListener){
		HWControl.getInstance().setContext(context);
		if(loginListener!=null){
			LogUtils.e("请求登录接口--->"+loginListener);
			HWControl.getInstance().setContext(context);
			HWControl.getInstance().setLoginListener(loginListener);
//			FreeLoginContract.FreeLoginPresenter presenter = new FreeLoginPresenter();
//			presenter.Login();
			TDHandler.sendEmptyMessage(login);
		}else{
			
			LogUtils.e("loginLisenter为空");
			
		}
		
	}
	
	public void PaySingel(Context context,String roleId,String gameItemId,PayLisenter payLisenter){
		HWControl.getInstance().setContext(context);
		if(!TextUtils.isEmpty(roleId)&&!TextUtils.isEmpty(roleId)&&payLisenter!=null){
			String isShow = HWConfigSharedPreferences.getInstance(context).getShowPay();
			String status = HWConfigSharedPreferences.getInstance(context).getAppStatus();
			if(TextUtils.isEmpty(isShow)){
				isShow = "1";
				
			}
			if(TextUtils.isEmpty(status)){
				status = "1";
			}
			if(status.equals("1")){
				HWControl.getInstance().setPayLisenter(payLisenter);
				HWControl.getInstance().setContext(context);
				HWConfigSharedPreferences.getInstance(context).setRoleId(roleId);
				HWControl.getInstance().setGameItemId(gameItemId);
//				PaySingleDialog.getInstance().show();
				TDHandler.sendEmptyMessage(paysingle);
			}else{
				LogUtils.e("返回字段不为1--->"+isShow);
			}
			
			
		}else{
			LogUtils.e("数据为空");
		}
		
	}
	
	/**
	 * 带穿透参数
	 * @param context
	 * @param roleId
	 * @param gameItemId
	 * @param extraData
	 * @param payLisenter
	 */
	public void PaySingel(Context context,String roleId,String gameItemId,String extraData,PayLisenter payLisenter){
		HWControl.getInstance().setContext(context);
		if(!TextUtils.isEmpty(roleId)&&!TextUtils.isEmpty(roleId)&&payLisenter!=null){
			String isShow = HWConfigSharedPreferences.getInstance(context).getShowPay();
			String status = HWConfigSharedPreferences.getInstance(context).getAppStatus();
			if(TextUtils.isEmpty(isShow)){
				isShow = "1";
				
			}
			if(TextUtils.isEmpty(status)){
				status = "1";
			}
			if(status.equals("1")){
				HWControl.getInstance().setPayLisenter(payLisenter);
				HWControl.getInstance().setContext(context);
				HWControl.getInstance().setExtraData(extraData);
				HWConfigSharedPreferences.getInstance(context).setRoleId(roleId);
				HWControl.getInstance().setGameItemId(gameItemId);
//				PaySingleDialog.getInstance().show();
				TDHandler.sendEmptyMessage(paysingle);
			}else{
				LogUtils.e("返回字段不为1--->"+isShow);
			}
			
			
		}else{
			LogUtils.e("数据为空");
		}
		
	}
	
	
	public void Pay(Context context,String roleLeve,String roleId,PayLisenter payLisenter){
		HWControl.getInstance().setContext(context);
		if(!TextUtils.isEmpty(roleId)&&!TextUtils.isEmpty(roleLeve)&&payLisenter!=null){
			
			String isShow = HWConfigSharedPreferences.getInstance(context).getShowPay();
			String status = HWConfigSharedPreferences.getInstance(context).getAppStatus();
			LogUtils.e("status--->"+status);
			if(TextUtils.isEmpty(isShow)){
				isShow = "1";
				
			}
			if(TextUtils.isEmpty(status)){
				status = "1";
			}
			if(status.equals("1")){
				
				HWConfigSharedPreferences.getInstance(context).setRoleLevel(roleLeve);
				HWConfigSharedPreferences.getInstance(context).setRoleId(roleId);
				HWControl.getInstance().setPayLisenter(payLisenter);
				HWControl.getInstance().setContext(context);
//				DialogUtils.getInstance().showPay(context);
				TDHandler.sendEmptyMessage(pay);
			}else{
				LogUtils.e("支付status---->"+status);
			}
			
		}else{
			LogUtils.e("roleId 或 roleLeve 或 payLisenter 为空");
		}
//		Intent intent = new Intent(context,PayTestActivity.class);
//		context.startActivity(intent);
		
	}
	
	
	/**
	 * 带穿透参数
	 * @param context
	 * @param roleLeve
	 * @param roleId
	 * @param extraData
	 * @param payLisenter
	 */
	public void Pay(Context context,String roleLeve,String roleId,String extraData,PayLisenter payLisenter){
		HWControl.getInstance().setContext(context);
		if(!TextUtils.isEmpty(roleId)&&!TextUtils.isEmpty(roleLeve)&&payLisenter!=null){
			
			String isShow = HWConfigSharedPreferences.getInstance(context).getShowPay();
			String status = HWConfigSharedPreferences.getInstance(context).getAppStatus();
			LogUtils.e("status--->"+status);
			if(TextUtils.isEmpty(isShow)){
				isShow = "1";
				
			}
			if(TextUtils.isEmpty(status)){
				status = "1";
			}
			if(status.equals("1")){
				
				HWConfigSharedPreferences.getInstance(context).setRoleId(roleId);
				HWConfigSharedPreferences.getInstance(context).setRoleLevel(roleLeve);
				HWControl.getInstance().setPayLisenter(payLisenter);
				HWControl.getInstance().setExtraData(extraData);
//				DialogUtils.getInstance().showPay(context);
				TDHandler.sendEmptyMessage(pay);
			}else{
				LogUtils.e("支付status---->"+status);
			}
			
		}else{
			LogUtils.e("roleId 或 roleLeve 或 payLisenter 为空");
		}
//		Intent intent = new Intent(context,PayTestActivity.class);
//		context.startActivity(intent);
		
	}
	
	public void CheckServer(Context context,String userId, String serverCode){
		
		if(!TextUtils.isEmpty(userId)&&!TextUtils.isEmpty(serverCode)){
			
			HWControl.getInstance().setContext(context);
			CheckServerContract.CheckServerPresenter presenter = new CheckServerPresenter();
			presenter.checkServer(userId, serverCode);
		}else{
			
			LogUtils.e("userId或serverCode为空");
			
		}
		
	}
	
	public void SaveRole(Context context,RoleInfo role){
		if(role!=null){
			HWControl.getInstance().setContext(context);
			DBUtils.getInstance().saveRole(role);
			SaveRoleContract.Presenter presenter = new SaveRolePresenter();
			presenter.saveRole(role);
		}else{
			LogUtils.e("role对象为空--->"+role.toString());
		}
	}
	
	public void logout(Context context){
		
		String userId = HWConfigSharedPreferences.getInstance(context).getUserId();
		HWInfoUser infoUser = DBUtils.getInstance().queryInfoUser(userId);
		if(infoUser!=null){
			DBUtils.getInstance().deleteInfoUser(infoUser);
			HWConfigSharedPreferences.getInstance(context).setUserId("");
		}
		LoginListener loginListener = HWControl.getInstance().getLoginListener();
		if(loginListener!=null){
			LogUtils.e("请求登录接口--->"+loginListener);
			HWControl.getInstance().setContext(context);
//			HWControl.getInstance().setLoginListener(loginListener);
//			FreeLoginContract.FreeLoginPresenter presenter = new FreeLoginPresenter();
//			presenter.Login();
			TDHandler.sendEmptyMessage(login);
		}else{
			
			LogUtils.e("loginLisenter为空");
			
		}
	}
	
	public void exit(Context context){
		HWControl.getInstance().setContext(context);
//		AliPayDialog.getInstance().exitDialog();
		ForgetPasswordDialog.getInstance().exitDialog();
		LoginDialog.getInstance().exitDialog();
		LoginTrialDialog.getInstance().exitDialog();
		MemberLoginDialog.getInstance().exitDialog();
		PayDialog.getInstance().exitDialog();
		RegisterDialog.getInstance().exitDialog();
		ResetPasswordDialog.getInstance().exitDialog();
		RetrieveGuestAccountDialog.getInstance().exitDialog();
		TrialBindingEmailDialog.getInstance().exitDialog();
		UserCenterDialog.getInstance().exitDialog();
//		WXPayDialog.getInstance().exitDialog();
		//sdk退出后，停掉PayPalService服务。
		context.stopService(new Intent(context, PayPalService.class));
	}
	
	public void HWMemberCenter(Context context,HWGameMemberListener hwGameMemberListener){
		
		HWControl.getInstance().setContext(context);
		HWControl.getInstance().setGameMemberListener(hwGameMemberListener);
//		UserCenterDialog.getInstance().show();
		TDHandler.sendEmptyMessage(usercenter);
	}
	
	/**
	 * 处理Iappay下Lua的GL线程与Java的UI线程交互
	 * 为了处理他们在不同线程去调用了这个方法，导致了不用线程调用UI界面
	 */
	private static Handler TDHandler = new Handler(){
		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
				case pay:						// 初始化
					DialogUtils.getInstance().showPay(HWControl.getInstance().getContext());
					break;
				case login:
					FreeLoginContract.FreeLoginPresenter presenter = new FreeLoginPresenter();
					presenter.Login();
					break;
				case usercenter:
					UserCenterDialog.getInstance().show();
					break;
				case paysingle:
					PaySingleDialog.getInstance().show();
					break;
				default:
					break;
			}
		};
	};
	
	public void onActivityResult(int requestCode, int resultCode, Intent data){
		
		IabHelper mHelper = HWControl.getInstance().getmHelper();
		CallbackManager mCallbackManager = HWControl.getInstance().getmCallbackManager();
		if(mCallbackManager!=null){
			mCallbackManager.onActivityResult(requestCode, resultCode, data);
		}else{
			LogUtils.e("facebook返回错误");
		}
		
		if(mHelper!=null){
			mHelper.handleActivityResult(requestCode, resultCode, data);
		}else{
			LogUtils.e("谷歌回到错误！");
		}
		LogUtils.e("requestCode---->"+requestCode+"------>Activity.RESULT_OK"+Activity.RESULT_OK);
		if (resultCode== Activity.RESULT_OK){
			PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
			String mPaymentId;
			LogUtils.e(confirm.toString());
			if (confirm != null) {
				try {
					// 得到回传的交易流水号
					mPaymentId = confirm.toJSONObject().getJSONObject("response").getString("id");
					LogUtils.e("PayPal支付回调数据--->"+confirm.toJSONObject());
					// 交易服务端是什么鬼
					@SuppressWarnings("unused")
					String payment_client = confirm.getPayment().toJSONObject().toString();

				} catch (Exception e) {
					LogUtils.e("an extremely unlikely failure occurred: ");
				}
			}
		} else if (resultCode == Activity.RESULT_CANCELED) {
			// Log.i(TAG, "The user canceled.");
			LogUtils.e("PayPal支付取消");
		} else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
			LogUtils.e("An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
		}
	}

	public void PayPalPay(){
		PayItemListBean.DataBean dataBean = new PayItemListBean.DataBean();
		dataBean.setAmount(100);
		dataBean.setCurrency("USD");
		dataBean.setDescription("测试paypal支付item");
		dataBean.setGameItemId("C10");
		dataBean.setGamecode("1001");
		PayPalContract.Presenter presenter = new PayPalPresenter();
		presenter.payWithPayPal(dataBean);
	}
}
