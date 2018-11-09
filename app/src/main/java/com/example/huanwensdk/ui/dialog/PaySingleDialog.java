package com.example.huanwensdk.ui.dialog;

import java.util.List;

import android.content.Context;
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
import com.example.huanwensdk.bean.user.HWInfoUser;
import com.example.huanwensdk.bean.user.RoleInfo;
import com.example.huanwensdk.bean.wxpay.PayItemListBean.DataBean;
import com.example.huanwensdk.mvp.contract.AliPayContract;
import com.example.huanwensdk.mvp.contract.GooglePayContract;
import com.example.huanwensdk.mvp.contract.PayContract;
import com.example.huanwensdk.mvp.contract.PaySingleContract;
import com.example.huanwensdk.mvp.contract.WXPayContract;
import com.example.huanwensdk.mvp.contract.listener.PayLisenter;
import com.example.huanwensdk.mvp.contract.listener.WXPayListener;
import com.example.huanwensdk.mvp.presenter.AliPresenter;
import com.example.huanwensdk.mvp.presenter.GooglePayPresenter;
import com.example.huanwensdk.mvp.presenter.PayPresenter;
import com.example.huanwensdk.mvp.presenter.WXPresenter;
import com.example.huanwensdk.ui.adapter.PaySingleChannelAdatper;
import com.example.huanwensdk.ui.bean.PayInfo;
import com.example.huanwensdk.utils.HWControl;
import com.example.huanwensdk.utils.LogUtils;
import com.example.huanwensdk.utils.ResLoader;
import com.example.huanwensdk.utils.sp.HWConfigSharedPreferences;

/**
 * 单个品项支付
 * 
 * @Title: PaySingleDialog.java
 * @Package com.example.huanwensdk.ui.dialog
 * @Description: TODO(用一句话描述该文件做什么)
 * @author: Android_ruan
 * @date: 2018-5-31 上午9:45:50
 * @version V1.0
 */
public class PaySingleDialog extends DialogBase implements PayContract.View,
		PaySingleContract.View, WXPayListener, WXPayContract.CoinView,GooglePayContract.View {

	Button btn_back;
	TextView tv_title;
	TextView tv_pay_role;
	TextView tv_pay_user;
	TextView tv_pay_tips;
	RecyclerView recy_pay_list;
	PayContract.View payView;

	String roleLevel;

	List<PayInfo> payInfos;

	Context context;

	PaySingleChannelAdatper payAdapter;

	PayContract.Presenter presenter;

	AliPayContract.AliPresenter Ailpresenter;

	PaySingleContract.View paySingleView;

	PayLisenter payLisenter;

	String orderId;

	String serverCode;

	LinearLayout loading;

	WXPayContract.WXPayPresenter wxPresenter;

	GooglePayContract.Presenter googlePresenter;
	GooglePayContract.View googleView;

	private PaySingleDialog() {

		// initView(R.layout.dialog_pay_single);
		initView(ResLoader.getLayout(context, "dialog_pay_single"));

	}

	private static class PayDialogHepler {

		private static PaySingleDialog payDialog = new PaySingleDialog();

	}

	public static PaySingleDialog getInstance() {

		return PayDialogHepler.payDialog;

	}

	@Override
	public void initView(int ContentId) {
		// TODO Auto-generated method stub
		super.initView(ContentId);
		paySingleView = this;
		payView = this;
		HWControl.getInstance().setWxPayListener(this);
		context = HWControl.getInstance().getContext();
		// loading = (LinearLayout) dialog.findViewById(R.id.loading);
		loading = (LinearLayout) dialog.findViewById(ResLoader.getId(context,
				"loading"));

		// btn_back = (Button) dialog.findViewById(R.id.btn_back);
		btn_back = (Button) dialog.findViewById(ResLoader.getId(context,
				"btn_back"));

		// tv_title = (TextView) dialog.findViewById(R.id.tv_title);
		tv_title = (TextView) dialog.findViewById(ResLoader.getId(context,
				"tv_title"));

		// tv_pay_role = (TextView) dialog.findViewById(R.id.tv_pay_role);
		tv_pay_role = (TextView) dialog.findViewById(ResLoader.getId(context,
				"tv_pay_role"));

		// tv_pay_user = (TextView) dialog.findViewById(R.id.tv_pay_user);
		tv_pay_user = (TextView) dialog.findViewById(ResLoader.getId(context,
				"tv_pay_user"));

		// recy_pay_list = (RecyclerView)
		// dialog.findViewById(R.id.recy_pay_list);
		recy_pay_list = (RecyclerView) dialog.findViewById(ResLoader.getId(
				context, "recy_pay_list"));

		// tv_pay_tips = (TextView) dialog.findViewById(R.id.tv_pay_tips);
		tv_pay_tips = (TextView) dialog.findViewById(ResLoader.getId(context,
				"tv_pay_tips"));

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
	public void initData() {
		wxPresenter = new WXPresenter();
		googleView = this;
		String roleName;
		String roleId = HWConfigSharedPreferences.getInstance(context)
				.getRoleId();
		String userId = HWConfigSharedPreferences.getInstance(context)
				.getUserId();
		roleLevel = HWConfigSharedPreferences.getInstance(context)
				.getRoleLevel();
		RoleInfo roleInfo = DBUtils.getInstance().queryRole(roleId);
		HWInfoUser infoUser = DBUtils.getInstance().queryInfoUser(userId);
		// List<RoleInfo> roleInfos =
		// DBUtils.getInstance().querRoleByUserId(userId);
		if (roleInfo != null) {
			roleName = roleInfo.getRoleName();
			serverCode = roleInfo.getServerCode();
		} else {
			roleName = "";
			serverCode = "1";
		}
		if (infoUser != null) {
			tv_pay_role.setText(ResLoader.getString(context,
					"string_pay_role_name") + roleName);
			tv_pay_user.setText(ResLoader.getString(context, "string_pay_user")
					+ infoUser.getShowname());
		}

		presenter = new PayPresenter();

		tv_pay_tips.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				presenter.getPayList(serverCode, roleLevel, payView);

			}
		});

		// payInfos = new ArrayList<PayInfo>();
		// String country =
		// HWConfigSharedPreferences.getInstance(context).getCountry();
		// if(country.equals("CN")){
		// //微信，支付宝支付
		// PayInfo payInfoAipay = new PayInfo();
		// payInfoAipay.setImage(R.drawable.ic_ai_pay);
		// payInfoAipay.setPay_hint("推荐使用支付宝支付");
		// payInfoAipay.setPay_type("支付宝");
		// payInfos.add(payInfoAipay);
		//
		// PayInfo payInfoWx = new PayInfo();
		// payInfoWx.setImage(R.drawable.ic_weixin_pay);
		// payInfoWx.setPay_hint("推荐使用微信支付");
		// payInfoWx.setPay_type("微信");
		// payInfos.add(payInfoWx);
		// }else if(country.equals("TW")){
		// //台湾地区，谷歌，mycard支付
		// }
		//
		//
		//
		// payAdapter= new PayAdapter(payInfos);
		// LinearLayoutManager layoutManager = new LinearLayoutManager(context);
		// //设置布局管理器
		// recy_pay_list.setLayoutManager(layoutManager);
		// //设置为垂直布局，这也是默认的
		// layoutManager.setOrientation(OrientationHelper.HORIZONTAL);
		// //设置Adapter
		// recy_pay_list.setAdapter( payAdapter);

		Ailpresenter = new AliPresenter();
		payLisenter = HWControl.getInstance().getPayLisenter();
		googlePresenter = new GooglePayPresenter();

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

		if (dialog.isShowing()) {
			dialog.dismiss();
		}
		presenter.getPayList(serverCode, roleLevel, payView);
		dialog.show();

	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

		if (dialog.isShowing()) {
			dialog.dismiss();
		}

	}

	@Override
	public void getPayListResult(String result, List<PayInfo> payInfos) {
		// TODO Auto-generated method stub

		if (result.equals("1000")) {
			recy_pay_list.setVisibility(View.VISIBLE);
			tv_pay_tips.setVisibility(View.GONE);
			payAdapter = new PaySingleChannelAdatper(payInfos, paySingleView,
					this);
			LinearLayoutManager layoutManager = new LinearLayoutManager(context);
			// 设置布局管理器
			recy_pay_list.setLayoutManager(layoutManager);
			// 设置为垂直布局，这也是默认的
			layoutManager.setOrientation(OrientationHelper.HORIZONTAL);
			// 设置Adapter
			recy_pay_list.setAdapter(payAdapter);
			LogUtils.e("获取支付列表成功---->" + payInfos.size());

			/**
			 * 循环如果是google支付的就是初始化谷歌
			 */
			for(PayInfo info:payInfos){
				
				if(info.getName().equals("Google Wallet")){
					googlePresenter.init(googleView);
				}
				
			}

		} else {
			LogUtils.e("获取支付列表失败----->" + result);
			tv_pay_tips.setVisibility(View.VISIBLE);
			recy_pay_list.setVisibility(View.GONE);
		}

	}

	@Override
	public void getPayList(int code, List<DataBean> payList) {
		// TODO Auto-generated method stub

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
		LogUtils.e("msg---->" + msg);
		if (code == 1000) {
			Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
			payLisenter.onPayResult(true);
			PaySingleDialog.getInstance().close();
			close();
		} else {
			payLisenter.onPayResult(false);
			Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void payResult(int code, String msg) {
		// TODO Auto-generated method stub
		loading.setVisibility(View.GONE);
		if (code == 1000) {
			Ailpresenter.getPayResult(serverCode, orderId, paySingleView);
		} else if(code == 1110){
			LogUtils.e("谷歌支付成功");
			payLisenter.onPayResult(true);
			close();
		}else {
			loading.setVisibility(View.GONE);
			Toast.makeText(context, "订单取消", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void WXCallback(int code, String result) {
		// TODO Auto-generated method stub
		if (code == 0) {
			// loading.setVisibility(View.VISIBLE);
			// 支付成功
			// 开始走流程
			LogUtils.e("支付成功--->" + orderId);
			Ailpresenter.getPayResult(serverCode, orderId, paySingleView);
		} else if (code == -2) {
			// 支付取消
			LogUtils.e("支付取消");
			Toast.makeText(context, "订单取消", Toast.LENGTH_SHORT).show();
		} else if (code == -1) {
			// 调起支付错误
			LogUtils.e("调起支付错误");
		}
	}

	/**
	 * 支付平台返回结果
	 */
	@Override
	public void getPlatFormResult(int code, String msg, DataBean dataBean,
			String type) {

		LogUtils.e("单个品项支付返回数据了---->" + msg);

		if (code == 1000) {
			// 根据判断使用平台币
			if (type.equals("wechat")) {
				LogUtils.e("点击了微信支付");
				String gameItemId = HWControl.getInstance().getGameItemId();
				String roleId = HWConfigSharedPreferences.getInstance(context)
						.getRoleId();
				RoleInfo roleInfo = DBUtils.getInstance().queryRole(roleId);
				DataBean data = new DataBean();
				data.setGameItemId(gameItemId);
				data.setItemKey(gameItemId);
				data.setActiveDescription("");
				data.setDescription("");
				if (roleInfo != null) {
					LogUtils.e("点击了获取订单");
					PayForCoinDialog.getInstance().getWXData(wxPresenter,
							serverCode, roleId, data, this, msg);
					PayForCoinDialog.getInstance().show();
				} else {
					LogUtils.e("role为空");
				}
			} else {

				LogUtils.e("点击了支付宝支付");
				String gameItemId = HWControl.getInstance().getGameItemId();
				String roleId = HWConfigSharedPreferences.getInstance(context)
						.getRoleId();
				RoleInfo roleInfo = DBUtils.getInstance().queryRole(roleId);
				DataBean data = new DataBean();
				data.setGameItemId(gameItemId);
				data.setItemKey(gameItemId);
				data.setActiveDescription("");
				data.setDescription("");
				if (roleInfo != null) {
					LogUtils.e("点击了支付宝支付");
					PayForCoinDialog.getInstance().getAliData(Ailpresenter,
							serverCode, roleId, data, this, msg);
					PayForCoinDialog.getInstance().show();
				} else {
					LogUtils.e("role为空");
				}

			}

		} else if (code == 1107) {
			Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
		} else {
			// 使用现金支付
			if (type.equals("wechat")) {

				LogUtils.e("点击了微信支付");
				String gameItemId = HWControl.getInstance().getGameItemId();
				String roleId = HWConfigSharedPreferences.getInstance(context)
						.getRoleId();
				RoleInfo roleInfo = DBUtils.getInstance().queryRole(roleId);
				DataBean data = new DataBean();
				data.setGameItemId(gameItemId);
				data.setItemKey(gameItemId);
				data.setActiveDescription("");
				data.setDescription("");
				if (roleInfo != null) {
					wxPresenter.getOrder(roleInfo.getServerCode(), roleId,
							data, paySingleView);
					LogUtils.e("点击了获取订单");
				} else {
					LogUtils.e("role为空");
				}

			} else {
				LogUtils.e("点击了支付宝支付");
				String gameItemId = HWControl.getInstance().getGameItemId();
				String roleId = HWConfigSharedPreferences.getInstance(context)
						.getRoleId();
				RoleInfo roleInfo = DBUtils.getInstance().queryRole(roleId);
				DataBean data = new DataBean();
				data.setGameItemId(gameItemId);
				data.setItemKey(gameItemId);
				data.setActiveDescription("");
				data.setDescription("");
				if (roleInfo != null) {
					LogUtils.e("点击了支付宝支付---->" + data.toString());
					Ailpresenter.getOrder(roleInfo.getServerCode(), roleId,
							data, paySingleView);
				} else {
					LogUtils.e("role为空");
				}
			}

		}

	}

	@Override
	public void getPlatFormResult(int code, String msg, HWGpPayItem dataBean,
			String type) {
		// TODO Auto-generated method stub
		LogUtils.e("谷歌支付专门响应");
		if (code == 1000) {

			LogUtils.e("点击了谷歌支付");
			String gameItemId = HWControl.getInstance().getGameItemId();
			String roleId = HWConfigSharedPreferences.getInstance(context)
					.getRoleId();
			RoleInfo roleInfo = DBUtils.getInstance().queryRole(roleId);
			HWGpPayItem data = new HWGpPayItem();
			data.setGameItemId(gameItemId);
			data.setActiveDescription("");
			data.setDescription("");
			if (roleInfo != null) {
				LogUtils.e("点击了支付宝支付");
				PayForCoinDialog.getInstance().getGoogleData(googlePresenter,
						serverCode, roleId, data, this, msg);
				PayForCoinDialog.getInstance().show();
			} else {
				LogUtils.e("role为空");
			}

		} else {

			LogUtils.e("点击了谷歌支付");
			String gameItemId = HWControl.getInstance().getGameItemId();
			String roleId = HWConfigSharedPreferences.getInstance(context)
					.getRoleId();
			RoleInfo roleInfo = DBUtils.getInstance().queryRole(roleId);
			DataBean data = new DataBean();
			data.setGameItemId(gameItemId);
			data.setItemKey(gameItemId);
			data.setActiveDescription("");
			data.setDescription("");
			if (roleInfo != null) {
				LogUtils.e("点击了谷歌支付");
				LogUtils.e("点击了谷歌支付---->" + data.toString());
				googlePresenter.getOrder(dataBean.getGameItemId(), serverCode,
						roleId);
			} else {
				LogUtils.e("role为空");
			}

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
            loading.setVisibility(View.VISIBLE);
            LogUtils.e("谷歌支付消费成功");
		}else{
			LogUtils.e("谷歌支付消费失败");
		}
		
	}

}
