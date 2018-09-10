package com.example.huanwensdk.ui.dialog;

import java.util.List;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.huanwensdk.DataBase.DBUtils;
import com.example.huanwensdk.bean.google.HWGpPayItem;
import com.example.huanwensdk.bean.user.RoleInfo;
import com.example.huanwensdk.bean.wxpay.PayItemListBean;
import com.example.huanwensdk.bean.wxpay.PayItemListBean.DataBean;
import com.example.huanwensdk.mvp.contract.WXPayContract;
import com.example.huanwensdk.mvp.contract.listener.PayLisenter;
import com.example.huanwensdk.mvp.contract.listener.WXPayListener;
import com.example.huanwensdk.mvp.presenter.WXPresenter;
import com.example.huanwensdk.ui.adapter.PayItemAdapter;
import com.example.huanwensdk.utils.HWControl;
import com.example.huanwensdk.utils.LogUtils;
import com.example.huanwensdk.utils.ResLoader;
import com.example.huanwensdk.utils.sp.HWConfigSharedPreferences;

/**
 * 
 * @Title:  WXPayDialog.java   
 * @Package ui.dialog   
 * @Description:    微信支付
 * @author: Android_ruan     
 * @date:   2018-4-16 下午2:28:11   
 * @version V1.0
 */
public class WXPayDialog extends DialogBase implements WXPayContract.WXPayView,WXPayListener,WXPayContract.CoinView{

	RecyclerView recy_pay_list;
	List<PayItemListBean.DataBean> payList;
	PayItemAdapter adapter ;
	Button btn_back;
	
	WXPayContract.WXPayPresenter presenter;
	
	LinearLayout loading;
	
	String order;
	
	String serverCode;
	String roleId;
	
	PayLisenter payLisenter;
	
	private WXPayDialog(){
//		initView(R.layout.dialog_wx_pay);
		initView(ResLoader.getLayout(context, "dialog_wx_pay"));
	}
	
	private static class WXPayDialogHepler{
		
		private static WXPayDialog wxPayDialog = new WXPayDialog();
		
	}
	
	public static WXPayDialog getInstance(){
		
		return WXPayDialogHepler.wxPayDialog;
		
	}
	
	@Override
	public void initView(int ContentId) {
		// TODO Auto-generated method stub
		super.initView(ContentId);
		HWControl.getInstance().setWxPayListener(this);
//		recy_pay_list = (RecyclerView) dialog.findViewById(R.id.recy_pay_list);
		recy_pay_list = (RecyclerView) dialog.findViewById(ResLoader.getId(context, "recy_pay_list"));
		
//		btn_back = (Button) dialog.findViewById(R.id.btn_back);
		btn_back = (Button) dialog.findViewById(ResLoader.getId(context, "btn_back"));
		
//		loading = (LinearLayout) dialog.findViewById(R.id.loading);
		loading = (LinearLayout) dialog.findViewById(ResLoader.getId(context, "loading"));
		
		btn_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				close();
			}
		});
		roleId = HWConfigSharedPreferences.getInstance(context).getRoleId();
		RoleInfo roleInfo = DBUtils.getInstance().queryRole(roleId);
		
		if(roleInfo!=null){
			serverCode = roleInfo.getServerCode();
			roleId = roleInfo.getRoleId();
			payLisenter = HWControl.getInstance().getPayLisenter();
			presenter = new WXPresenter();
			presenter.getPayList(roleInfo.getServerCode(), roleId, this);
			loading.setVisibility(View.VISIBLE);
		}else{
			LogUtils.e("微信支付走了模拟数据");
			payLisenter = HWControl.getInstance().getPayLisenter();
			presenter = new WXPresenter();
			presenter.getPayList("1", "60447", this);
			loading.setVisibility(View.VISIBLE);
		}
	}
	
	/**
	 * 模拟支付品项数据
	 */
	public void initData(){
//		
//		payList = new ArrayList<PayItemListBean.DataBean>();
//		PayItem payItem1 = new PayItem();
//		payItem1.setTitle("10元=100元宝");
//		payItem1.setAmount("10");
//		payList.add(payItem1);
//		PayItem payItem2 = new PayItem();
//		payItem2.setTitle("500元=5000元宝");
//		payItem2.setAmount("500");
//		payList.add(payItem2);
//		
		
		
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		if(dialog.isShowing()){
			dialog.dismiss();
		}
		roleId = HWConfigSharedPreferences.getInstance(context).getRoleId();
		RoleInfo roleInfo = DBUtils.getInstance().queryRole(roleId);
		
		if(roleInfo!=null){
			serverCode = roleInfo.getServerCode();
			roleId = roleInfo.getRoleId();
			payLisenter = HWControl.getInstance().getPayLisenter();
			presenter = new WXPresenter();
			presenter.getPayList(roleInfo.getServerCode(), roleId, this);
			loading.setVisibility(View.VISIBLE);
		}else{
			LogUtils.e("微信支付走了模拟数据");
			payLisenter = HWControl.getInstance().getPayLisenter();
			presenter = new WXPresenter();
			presenter.getPayList("1", "60447", this);
			loading.setVisibility(View.VISIBLE);
		}
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
	public void getPayList(int code, List<DataBean> payList) {
		// TODO Auto-generated method stub
		loading.setVisibility(View.GONE);
		if(code == 1000){
			recy_pay_list.setVisibility(View.VISIBLE);
			adapter = new PayItemAdapter(payList,this);
			LinearLayoutManager layoutManager = new LinearLayoutManager(context);  
	        //设置布局管理器  
	       recy_pay_list.setLayoutManager(layoutManager);  
	        //设置为垂直布局，这也是默认的  
	       layoutManager.setOrientation(OrientationHelper.VERTICAL);  
	        //设置Adapter  
	       recy_pay_list.setAdapter( adapter);  
		}else{
			Toast.makeText(context, "获取品项失败", Toast.LENGTH_SHORT).show();
			recy_pay_list.setVisibility(View.GONE);
		}
		
	}

	/**
	 * 微信支付返回
	 */
	@Override
	public void WXCallback(int code, String result) {
		
		if(code==0){
			loading.setVisibility(View.VISIBLE);
			//支付成功
			//开始走流程
			LogUtils.e("支付成功--->"+order);
			presenter.getPayResult(serverCode, order);
		}else if(code == -2){
			//支付取消
			LogUtils.e("支付取消");
		}else if(code == -1){
			//调起支付错误
			LogUtils.e("调起支付错误");
		}
		
		
	}

	@Override
	public void getOrderId(int code, String orderId) {
		// TODO Auto-generated method stub
		order = orderId;
		LogUtils.e("order---->"+order);
	}

	@Override
	public void getPayStatus(int code, String msg) {
		// TODO Auto-generated method stub
		loading.setVisibility(View.GONE);
		if(code == 1000){
			Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
			payLisenter.onPayResult(true);
			close();
			PayDialog.getInstance().close();
		}else{
			Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
			payLisenter.onPayResult(false);
		}
		LogUtils.e("双验证--->"+msg);
	}

	@Override
	public void getPlatFormResult(int code, String msg,DataBean dataBean,String type) {
		// TODO Auto-generated method stub
		if(code == 1000){
			//使用平台币支付
//			presenter.PayForPlatformCoin(serverCode, roleId, dataBean, null, this);
			//询问用户是否支付
			PayForCoinDialog.getInstance().getWXData(presenter, serverCode, roleId, dataBean, this,msg);
			PayForCoinDialog.getInstance().show();
		}else{
			//使用微信支付
			presenter.getOrder(serverCode, roleId, dataBean,null);
		}
	}

	@Override
	public void getPlatFormResult(int code, String msg, HWGpPayItem dataBean,
			String type) {
		// TODO Auto-generated method stub
		
	}


}
