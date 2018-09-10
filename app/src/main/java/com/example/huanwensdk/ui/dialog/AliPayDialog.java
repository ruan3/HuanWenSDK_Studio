package com.example.huanwensdk.ui.dialog;

import java.util.List;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huanwensdk.DataBase.DBUtils;
import com.example.huanwensdk.bean.google.HWGpPayItem;
import com.example.huanwensdk.bean.user.RoleInfo;
import com.example.huanwensdk.bean.wxpay.PayItemListBean;
import com.example.huanwensdk.bean.wxpay.PayItemListBean.DataBean;
import com.example.huanwensdk.mvp.contract.AliPayContract;
import com.example.huanwensdk.mvp.contract.WXPayContract;
import com.example.huanwensdk.mvp.contract.listener.PayLisenter;
import com.example.huanwensdk.mvp.presenter.AliPresenter;
import com.example.huanwensdk.ui.adapter.AliPayItemAdapter;
import com.example.huanwensdk.utils.HWControl;
import com.example.huanwensdk.utils.LogUtils;
import com.example.huanwensdk.utils.ResLoader;
import com.example.huanwensdk.utils.sp.HWConfigSharedPreferences;

public class AliPayDialog extends DialogBase implements AliPayContract.AliView,WXPayContract.CoinView{

	RecyclerView recy_pay_list;
	List<PayItemListBean.DataBean> payList;
	AliPayItemAdapter adapter ;
	Button btn_back;
	TextView tv_title;
	
	AliPayContract.AliPresenter presenter;
	
	LinearLayout loading;
	
	PayLisenter payLisenter;
	
	String orderId;
	String roleId ;
	String serverCode;
	
	private AliPayDialog(){
//		initView(R.layout.dialog_wx_pay);
		initView(ResLoader.getLayout(context, "dialog_wx_pay"));
	}
	
	private static class AliPayDialogHepler{
		
		private static AliPayDialog aliPayDialog = new AliPayDialog();
		
	}
	
	public static AliPayDialog getInstance(){
		
		return AliPayDialogHepler.aliPayDialog;
		
	}
	

	@Override
	public void initView(int ContentId) {
		// TODO Auto-generated method stub
		super.initView(ContentId);
//		recy_pay_list = (RecyclerView) dialog.findViewById(R.id.recy_pay_list);
		recy_pay_list = (RecyclerView) dialog.findViewById(ResLoader.getId(context, "recy_pay_list"));
		
//		btn_back = (Button) dialog.findViewById(R.id.btn_back);
		btn_back = (Button) dialog.findViewById(ResLoader.getId(context, "btn_back"));
		
//		loading = (LinearLayout) dialog.findViewById(R.id.loading);
		loading = (LinearLayout) dialog.findViewById(ResLoader.getId(context, "loading"));
		
//		tv_title = (TextView) dialog.findViewById(R.id.tv_title);
		tv_title = (TextView) dialog.findViewById(ResLoader.getId(context, "tv_title"));
		
		tv_title.setText(ResLoader.getString(context, "string_pay_aipay"));
		btn_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				close();
			}
		});
		presenter = new AliPresenter();
		roleId = HWConfigSharedPreferences.getInstance(context).getRoleId();
		RoleInfo roleInfo = DBUtils.getInstance().queryRole(roleId);
		if(roleInfo!=null){
			serverCode = roleInfo.getServerCode();
			presenter.getPayList(roleInfo.getServerCode(), roleId, this);
			loading.setVisibility(View.VISIBLE);
			payLisenter = HWControl.getInstance().getPayLisenter();
		}else{
			LogUtils.e("支付宝走了模拟数据");
			presenter.getPayList("1", "60447", this);
			loading.setVisibility(View.VISIBLE);
			payLisenter = HWControl.getInstance().getPayLisenter();
		}
		
//		AliPayContract.AliPresenter presenter = new AliPresenter();
//		DataBean payItem = new DataBean();
//		payItem.setDescription("1000元宝");
//		payItem.setId(90507);
//		payItem.setActiveDescription("购买即可获得1000元宝");
//		presenter.getOrder("1", "60447", payItem);
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
		presenter = new AliPresenter();
		roleId = HWConfigSharedPreferences.getInstance(context).getRoleId();
		RoleInfo roleInfo = DBUtils.getInstance().queryRole(roleId);
		if(roleInfo!=null){
			serverCode = roleInfo.getServerCode();
			presenter.getPayList(roleInfo.getServerCode(), roleId, this);
			loading.setVisibility(View.VISIBLE);
			payLisenter = HWControl.getInstance().getPayLisenter();
		}else{
			LogUtils.e("支付宝走了模拟数据");
			presenter.getPayList("1", "60447", this);
			loading.setVisibility(View.VISIBLE);
			payLisenter = HWControl.getInstance().getPayLisenter();
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
			adapter = new AliPayItemAdapter(payList,this);
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


	@Override
	public void getOrderId(int code, String orderId) {
		// TODO Auto-generated method stub
		this.orderId = orderId;
		
	}


	@Override
	public void getPayStatus(int code, String msg) {
		// TODO Auto-generated method stub
		loading.setVisibility(View.GONE);
		LogUtils.e("msg---->"+msg);
		if(code == 1000){
			Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
			payLisenter.onPayResult(true);
			PayDialog.getInstance().close();
			close();
		}else{
			payLisenter.onPayResult(false);
			Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
		}
		
	}


	@Override
	public void getAilPayResult(int code, String msg) {
		// TODO Auto-generated method stub
		loading.setVisibility(View.GONE);
		if(code == 9000){
			loading.setVisibility(View.VISIBLE);
			presenter.getPayResult(serverCode, orderId,null);
		}else{
			Toast.makeText(context, "获取订单信息失败", Toast.LENGTH_SHORT).show();
		}
	}


	@Override
	public void getPlatFormResult(int code, String msg, DataBean dataBean,String type) {
		// TODO Auto-generated method stub
		
		if(code == 1000){
			
//			presenter.PayForPlatformCoin(serverCode, roleId, dataBean, null, this);
			//询问用户是否支付
			PayForCoinDialog.getInstance().getAliData(presenter, serverCode, roleId, dataBean, this,msg);
			PayForCoinDialog.getInstance().show();
			
		}else{
			presenter.getOrder(serverCode, roleId, dataBean, null);
		}
		
	}


	@Override
	public void getPlatFormResult(int code, String msg, HWGpPayItem dataBean,
			String type) {
		// TODO Auto-generated method stub
		
	}
	
}
