package com.example.huanwensdk.ui.dialog;

import java.util.List;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huanwensdk.DataBase.DBUtils;
import com.example.huanwensdk.bean.google.HWGpPayItem;
import com.example.huanwensdk.bean.user.HWInfoUser;
import com.example.huanwensdk.bean.user.RoleInfo;
import com.example.huanwensdk.mvp.contract.GooglePayContract;
import com.example.huanwensdk.mvp.contract.PayContract;
import com.example.huanwensdk.mvp.presenter.GooglePayPresenter;
import com.example.huanwensdk.mvp.presenter.PayPresenter;
import com.example.huanwensdk.ui.adapter.PayAdapter;
import com.example.huanwensdk.ui.bean.PayInfo;
import com.example.huanwensdk.utils.HWControl;
import com.example.huanwensdk.utils.LogUtils;
import com.example.huanwensdk.utils.ResLoader;
import com.example.huanwensdk.utils.sp.HWConfigSharedPreferences;

/**
 * 
 * @Title:  PayDialog.java   
 * @Package ui.dialog   
 * @Description:    充点小钱界面
 * @author: Android_ruan     
 * @date:   2018-4-12 上午10:53:07   
 * @version V1.0
 */
public class PayDialog extends DialogBase implements PayContract.View,GooglePayContract.View{

	Button btn_back;
	TextView tv_title;
	TextView tv_pay_role;
	TextView tv_pay_user;
	RecyclerView recy_pay_list;
	TextView tv_pay_tips;
	String serverCode;
	String roleLevel;
	TextView tv_pay_explain;
	PayContract.View payView;
	
	List<PayInfo> payInfos;
	
	Context context;
	
	PayAdapter payAdapter;
	
	PayContract.Presenter presenter;
	
	GooglePayContract.Presenter goolePresenter;
	
	GooglePayContract.View googleView;
	
	private PayDialog(){
		
//		initView(R.layout.dialog_pay);
		initView(ResLoader.getLayout(context, "dialog_pay"));
		
	}
	
	private static class PayDialogHepler{
		
		private static PayDialog payDialog = new PayDialog();
		
	}
	
	public static PayDialog getInstance(){
		
		return PayDialogHepler.payDialog;
		
	}
	
	@Override
	public void initView(int ContentId) {
		// TODO Auto-generated method stub
		super.initView(ContentId);
		
		context = HWControl.getInstance().getContext();
		payView = this;
		googleView = this;
//		btn_back = (Button) dialog.findViewById(R.id.btn_back);
		btn_back = (Button) dialog.findViewById(ResLoader.getId(context, "btn_back"));
		
//		tv_title = (TextView) dialog.findViewById(R.id.tv_title);
		tv_title = (TextView) dialog.findViewById(ResLoader.getId(context, "tv_title"));
		
//		tv_pay_role = (TextView) dialog.findViewById(R.id.tv_pay_role);
		tv_pay_role = (TextView) dialog.findViewById(ResLoader.getId(context, "tv_pay_role"));
		
//		tv_pay_user = (TextView) dialog.findViewById(R.id.tv_pay_user);
		tv_pay_user = (TextView) dialog.findViewById(ResLoader.getId(context, "tv_pay_user"));
		
//		recy_pay_list = (RecyclerView) dialog.findViewById(R.id.recy_pay_list);
		recy_pay_list = (RecyclerView) dialog.findViewById(ResLoader.getId(context, "recy_pay_list"));
		
//		tv_pay_tips = (TextView) dialog.findViewById(R.id.tv_pay_tips);
		tv_pay_tips = (TextView) dialog.findViewById(ResLoader.getId(context, "tv_pay_tips"));

		tv_pay_explain = dialog.findViewById(ResLoader.getId(context,"tv_pay_explain"));
		tv_pay_explain.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); // 下划线

		tv_pay_explain.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				//充值说明点击
				LogUtils.e("充值说明被点击");
			}
		});

		tv_title.setText(ResLoader.getString(context, "string_pay_title"));
		
		btn_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				close();
			}
		});
		
		
		initData();
	}
	
	/**
	 * 初始化数据
	 */
	public void initData(){
		String roleName;
		String roleId = HWConfigSharedPreferences.getInstance(context).getRoleId();
		String userId = HWConfigSharedPreferences.getInstance(context).getUserId();
		roleLevel = HWConfigSharedPreferences.getInstance(context).getRoleLevel();
		
		RoleInfo roleInfo = DBUtils.getInstance().queryRole(roleId);
		HWInfoUser infoUser = DBUtils.getInstance().queryInfoUser(userId);
//		List<RoleInfo> roleInfos = DBUtils.getInstance().querRoleByUserId(userId);
		if(roleInfo!=null){
			roleName = roleInfo.getRoleName();
			serverCode = roleInfo.getServerCode();
		}else{
			LogUtils.e("角色为空--->");
			roleName = "";
			serverCode = "101";
		}
		if(infoUser != null){
			tv_pay_role.setText(ResLoader.getString(context, "string_pay_role_name")+roleName);
			tv_pay_user.setText(ResLoader.getString(context, "string_pay_user")+infoUser.getShowname());
		}
		
		LogUtils.e("运行了吗？");
		
		goolePresenter = new GooglePayPresenter();
//		
//		goolePresenter.GetItemList("101", "60447", "", this);
		
		presenter = new PayPresenter();
		
//		presenter.getPayList(serverCode, roleLevel, this);
		
//		payInfos = new ArrayList<PayInfo>();
//		String country = HWConfigSharedPreferences.getInstance(context).getCountry();
//		if(country.equals("CN")){
//			//微信，支付宝支付
//			PayInfo payInfoAipay = new PayInfo();
//			payInfoAipay.setImage(R.drawable.ic_ai_pay);
//			payInfoAipay.setPay_hint("推荐使用支付宝支付");
//			payInfoAipay.setPay_type("支付宝");
//			payInfos.add(payInfoAipay);
//
//			PayInfo payInfoWx = new PayInfo();
//			payInfoWx.setImage(R.drawable.ic_weixin_pay);
//			payInfoWx.setPay_hint("推荐使用微信支付");
//			payInfoWx.setPay_type("微信");
//			payInfos.add(payInfoWx);
//		}else if(country.equals("TW")){
//			//台湾地区，谷歌，mycard支付
//		}
//		
//		
//		
//		payAdapter= new PayAdapter(payInfos);  
//        LinearLayoutManager layoutManager = new LinearLayoutManager(context);  
//         //设置布局管理器  
//        recy_pay_list.setLayoutManager(layoutManager);  
//         //设置为垂直布局，这也是默认的  
//        layoutManager.setOrientation(OrientationHelper.HORIZONTAL);  
//         //设置Adapter  
//        recy_pay_list.setAdapter( payAdapter);  
		
		tv_pay_tips.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				presenter.getPayList(serverCode, roleLevel, payView);
			}
		});
		
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
		if(dialog.isShowing()){
			dialog.dismiss();
		}
		String roleName;
		String roleId = HWConfigSharedPreferences.getInstance(context).getRoleId();
		String userId = HWConfigSharedPreferences.getInstance(context).getUserId();
		roleLevel = HWConfigSharedPreferences.getInstance(context).getRoleLevel();
		
		RoleInfo roleInfo = DBUtils.getInstance().queryRole(roleId);
		HWInfoUser infoUser = DBUtils.getInstance().queryInfoUser(userId);
//		List<RoleInfo> roleInfos = DBUtils.getInstance().querRoleByUserId(userId);
		if(roleInfo!=null){
			roleName = roleInfo.getRoleName();
			serverCode = roleInfo.getServerCode();
		}else{
			LogUtils.e("角色为空--->");
			roleName = "";
			serverCode = "101";
		}
		if(infoUser != null){
			tv_pay_role.setText(ResLoader.getString(context, "string_pay_role_name")+roleName);
			tv_pay_user.setText(ResLoader.getString(context, "string_pay_user")+infoUser.getShowname());
		}
		presenter.getPayList(serverCode, roleLevel, this);
		dialog.show();
		
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
		if(dialog.isShowing()){
			dialog.dismiss();
		}
		
	}

	@Override
	public void getPayListResult(String result, List<PayInfo> payInfos) {
		// TODO Auto-generated method stub
		
		if(result.equals("1000")){
			recy_pay_list.setVisibility(View.VISIBLE);
			payAdapter= new PayAdapter(payInfos);  
	        LinearLayoutManager layoutManager = new LinearLayoutManager(context);  
	         //设置布局管理器  
	        recy_pay_list.setLayoutManager(layoutManager);  
	         //设置为垂直布局，这也是默认的  
	        layoutManager.setOrientation(OrientationHelper.HORIZONTAL);  
	         //设置Adapter  
	        recy_pay_list.setAdapter( payAdapter);  
			LogUtils.e("获取支付列表成功---->"+payInfos.size());
			tv_pay_tips.setVisibility(View.GONE);
			
			
			/**
			 * 循环如果是google支付的就是初始化谷歌
			 */
			for(PayInfo info:payInfos){
				
				if(info.getName().equals("Google Wallet")){
					goolePresenter.init(googleView);
				}
				
			}
			
		}else{
			LogUtils.e("获取支付列表失败----->"+result);
			tv_pay_tips.setVisibility(View.VISIBLE);
			recy_pay_list.setVisibility(View.GONE);
		}
		
	}

	@Override
	public void getGDItems(int code, List<HWGpPayItem> data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void consumeResult(int code, String msg, String orderId) {
		// TODO Auto-generated method stub
		
		if(code == 1000){
			LogUtils.e("消费成功");
		}else{
			LogUtils.e("消费失败");
		}
		
	}

	@Override
	public void payResult(int code, String msg) {
		// TODO Auto-generated method stub
		
		if(code == 1000){
			LogUtils.e("谷歌支付成功");
		}else{
			Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
		}
		
	}


}
