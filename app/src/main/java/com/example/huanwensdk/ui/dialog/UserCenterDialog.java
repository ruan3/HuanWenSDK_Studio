package com.example.huanwensdk.ui.dialog;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huanwensdk.DataBase.DBUtils;
import com.example.huanwensdk.bean.user.HWInfoUser;
import com.example.huanwensdk.mvp.contract.listener.HWGameMemberListener;
import com.example.huanwensdk.sdk.HWSDK;
import com.example.huanwensdk.utils.HWControl;
import com.example.huanwensdk.utils.LogUtils;
import com.example.huanwensdk.utils.ResLoader;
import com.example.huanwensdk.utils.sp.HWConfigSharedPreferences;

/**
 * 
 * @Title:  UserCenterDialog.java   
 * @Package com.example.huanwensdk.ui.dialog   
 * @Description:    用户中心界面
 * @author: Android_ruan     
 * @date:   2018-5-9 上午9:52:54   
 * @version V1.0
 */
public class UserCenterDialog extends DialogBase{

	ImageView iv_user_icon;
	TextView tv_user_id;
	Button btn_bind_id;
	Button btn_login_out;
	TextView tv_wechat_service;
	Button btn_services_center;
	Button btn_order_query;
	Button btn_back;
	Button btn_exchange_login;
	HWGameMemberListener hwGameMemberListener;
	
	ClipboardManager mClipboardManager;
	ClipData mClipData;
	
	private UserCenterDialog(){
//		initView(R.layout.dialog_user_center);
		initView(ResLoader.getLayout(context, "dialog_user_center"));
	}
	
	private static class UserCenterHepler{
		
		private static UserCenterDialog userCenterDialog = new UserCenterDialog();
		
	}
	
	public static UserCenterDialog getInstance(){
		return UserCenterHepler.userCenterDialog;
	}

	@Override
	public void initView(int ContentId) {
		// TODO Auto-generated method stub
		super.initView(ContentId);
		this.hwGameMemberListener = HWControl.getInstance().getGameMemberListener();
//		iv_user_icon = (ImageView) dialog.findViewById(R.id.iv_user_icon);
		iv_user_icon = (ImageView) dialog.findViewById(ResLoader.getId(context, "iv_user_icon"));
		
//		tv_user_id = (TextView) dialog.findViewById(R.id.tv_user_id);
		tv_user_id = (TextView) dialog.findViewById(ResLoader.getId(context, "tv_user_id"));
		
//		btn_bind_id = (Button) dialog.findViewById(R.id.btn_bind_id);
		btn_bind_id = (Button) dialog.findViewById(ResLoader.getId(context, "btn_bind_id"));
		
//		btn_login_out = (Button) dialog.findViewById(R.id.btn_login_out);
		btn_login_out = (Button) dialog.findViewById(ResLoader.getId(context, "btn_login_out"));
		
//		tv_wechat_service = (TextView) dialog.findViewById(R.id.tv_wechat_service);
		tv_wechat_service = (TextView) dialog.findViewById(ResLoader.getId(context, "tv_wechat_service"));
		
//		btn_services_center = (Button) dialog.findViewById(R.id.btn_services_center);
		btn_services_center = (Button) dialog.findViewById(ResLoader.getId(context, "btn_services_center"));
		
//		btn_order_query = (Button) dialog.findViewById(R.id.btn_order_query);
		btn_order_query = (Button) dialog.findViewById(ResLoader.getId(context, "btn_order_query"));
		
//		btn_exchange_login = (Button) dialog.findViewById(R.id.btn_exchange_login);
		btn_exchange_login = (Button) dialog.findViewById(ResLoader.getId(context, "btn_exchange_login"));
		
//		btn_back = (Button) dialog.findViewById(R.id.btn_back);
		btn_back = (Button) dialog.findViewById(ResLoader.getId(context, "btn_back"));
		
		btn_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				close();
			}
		});
		
		dialog.setCancelable(false);
		
		initData();
		
	}
	
	private void initData(){
		
		btn_bind_id.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				RetrieveGuestAccountDialog.getInstance().show();
				LoginBindEmailDialog.getInstance().show();
			}
		});
		String userId = HWConfigSharedPreferences.getInstance(context).getUserId();
		HWInfoUser infoUser = DBUtils.getInstance().queryInfoUser(userId);
		
		if(infoUser!=null){
			LogUtils.e("infoUser---->"+infoUser.toString());
			String name = infoUser.getShowname();
			if(!TextUtils.isEmpty(name)){
				//设置名字
				tv_user_id.setText(infoUser.getShowname());
			}
			if(infoUser.getLoginType()!=2){
				if(infoUser.getLoginType()==3){
					btn_bind_id.setVisibility(View.GONE);
				}else{
					btn_bind_id.setVisibility(View.GONE);
				}
			}else{
				btn_bind_id.setVisibility(View.VISIBLE);
			}
			if(infoUser.getLoginType()==1){
				btn_bind_id.setVisibility(View.GONE);
			}
		}
		
		btn_order_query.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// 启动订单查询页
				OrderDetailDialog.getInstance().show();
			}
		});
		
		btn_login_out.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				HWSDK.getInstance().exit(context);
				System.exit(0);
				
			}
		});
		
		tv_wechat_service.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(context, ResLoader.getString(context, "string_service_tips"), Toast.LENGTH_LONG).show();
				copy();
			}
		});
		
		btn_services_center.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(context, ResLoader.getString(context, "string_service_tips"), Toast.LENGTH_LONG).show();
				copy();
			}
		});
		
		btn_exchange_login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//清除用户登录数据
				String userId = HWConfigSharedPreferences.getInstance(context).getUserId();
				HWInfoUser infoUser = DBUtils.getInstance().queryInfoUser(userId);
				if(infoUser!=null){
					DBUtils.getInstance().deleteInfoUser(infoUser);
					HWConfigSharedPreferences.getInstance(context).setUserId("");
				}
				
				close();
				//回调给住界面
				hwGameMemberListener.onLogout();
			}
		});
		
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		if(dialog.isShowing()){
			dialog.dismiss();
		}
		String userId = HWConfigSharedPreferences.getInstance(context).getUserId();
		HWInfoUser infoUser = DBUtils.getInstance().queryInfoUser(userId);
		if(infoUser!=null){
			LogUtils.e("infoUser---->"+infoUser.toString());
			String name = infoUser.getShowname();
			if(!TextUtils.isEmpty(name)){
				//设置名字
				tv_user_id.setText(infoUser.getShowname());
			}
			if(infoUser.getLoginType()!=2){
				if(infoUser.getLoginType()==3){
					btn_bind_id.setVisibility(View.GONE);
				}else{
					btn_bind_id.setVisibility(View.GONE);
				}
			}else{
				btn_bind_id.setVisibility(View.VISIBLE);
			}
			if(infoUser.getLoginType()==1){
				btn_bind_id.setVisibility(View.GONE);
			}
		}
		dialog.show();
	}
	
	/**
	 * 复制微信公众号
	 */
	private void copy(){
		mClipboardManager =(ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
		mClipData =ClipData.newPlainText("WX", "galaxystargame");    
		mClipboardManager.setPrimaryClip(mClipData);
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		if(dialog.isShowing()){
			dialog.dismiss();
		}
	}
	
}
