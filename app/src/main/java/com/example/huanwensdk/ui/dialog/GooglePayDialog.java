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

import com.example.huanwensdk.R;
import com.example.huanwensdk.DataBase.DBUtils;
import com.example.huanwensdk.bean.google.HWGpPayItem;
import com.example.huanwensdk.bean.user.RoleInfo;
import com.example.huanwensdk.bean.wxpay.PayItemListBean.DataBean;
import com.example.huanwensdk.mvp.contract.GooglePayContract;
import com.example.huanwensdk.mvp.contract.WXPayContract;
import com.example.huanwensdk.mvp.contract.listener.PayLisenter;
import com.example.huanwensdk.mvp.presenter.GooglePayPresenter;
import com.example.huanwensdk.ui.adapter.GooglePayItemAdapter;
import com.example.huanwensdk.utils.HWControl;
import com.example.huanwensdk.utils.LogUtils;
import com.example.huanwensdk.utils.ResLoader;
import com.example.huanwensdk.utils.sp.HWConfigSharedPreferences;

/**
 * 
 * 谷歌支付对话框
 * 
 * @author Ruan
 * @data: 2018-6-14 上午10:38:33
 * @version: V1.0
 */
public class GooglePayDialog extends DialogBase implements
		GooglePayContract.View, WXPayContract.CoinView {

	RecyclerView recy_pay_list;
	Button btn_back;
	TextView tv_title;

	LinearLayout loading;

	PayLisenter payLisenter;

	String orderId;
	String roleId;
	String serverCode;

	GooglePayItemAdapter adapter;

	GooglePayContract.Presenter presenter;

	private GooglePayDialog() {
		initView(R.layout.dialog_wx_pay);
	}

	private static class GooglePayHepler {

		private static GooglePayDialog googlePayDialog = new GooglePayDialog();

	}

	public static GooglePayDialog getInstance() {
		return GooglePayHepler.googlePayDialog;
	}

	@Override
	public void initView(int ContentId) {
		// TODO Auto-generated method stub
		super.initView(ContentId);

		tv_title = (TextView) dialog.findViewById(R.id.tv_title);
		tv_title.setText(ResLoader.getString(context, "string_pay_google"));
		recy_pay_list = (RecyclerView) dialog.findViewById(R.id.recy_pay_list);
		btn_back = (Button) dialog.findViewById(R.id.btn_back);
		loading = (LinearLayout) dialog.findViewById(R.id.loading);

		roleId = HWConfigSharedPreferences.getInstance(context).getRoleId();
		RoleInfo roleInfo = DBUtils.getInstance().queryRole(roleId);
		if (roleInfo != null) {
			serverCode = roleInfo.getServerCode();
			loading.setVisibility(View.VISIBLE);
			payLisenter = HWControl.getInstance().getPayLisenter();
		} else {
			LogUtils.e("谷歌走了模拟数据");
			loading.setVisibility(View.VISIBLE);
			payLisenter = HWControl.getInstance().getPayLisenter();
		}
		
		btn_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				close();
			}
		});

		initData();
	}

	private void initData() {

		presenter = new GooglePayPresenter();
		presenter.init(this);// 初始化谷歌配置

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		if (dialog.isShowing()) {
			dialog.dismiss();
		}
		dialog.show();
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		if (dialog.isShowing()) {
			dialog.dismiss();
		}
	}

	/**
	 * 获取道相应的品项
	 */
	@Override
	public void getGDItems(int code, List<HWGpPayItem> data) {
		loading.setVisibility(View.GONE);
		if (code == 1000 && data.size() > 0) {
			LogUtils.e("谷歌品项获取成功"+data.size());
			adapter = new GooglePayItemAdapter(data, this);
			LinearLayoutManager layoutManager = new LinearLayoutManager(context);
			// 设置布局管理器
			recy_pay_list.setLayoutManager(layoutManager);
			// 设置为垂直布局，这也是默认的
			layoutManager.setOrientation(OrientationHelper.VERTICAL);
			// 设置Adapter
			recy_pay_list.setAdapter(adapter);

		} else if(code == 1100){
			
		}else{
			Toast.makeText(context, "获取品项失败", Toast.LENGTH_SHORT).show();
		}

	}

	@Override
	public void getPlatFormResult(int code, String msg, DataBean dataBean,
			String type) {
		// TODO Auto-generated method stub
		LogUtils.e("谷歌支付走了平台币接口---->"+msg);

	}

	@Override
	public void getPlatFormResult(int code, String msg, HWGpPayItem dataBean,
			String type) {
		// TODO Auto-generated method stub
		LogUtils.e("检查平台币回调");
		if (code == 1000) {

			// presenter.PayForPlatformCoin(serverCode, roleId, dataBean, null,
			// this);
			// 询问用户是否支付
			PayForCoinDialog.getInstance().getGoogleData(presenter, serverCode,
					roleId, dataBean, this, msg);
			PayForCoinDialog.getInstance().show();

		} else {
			presenter.getOrder(dataBean.getId(), serverCode, roleId);
		}

	}

	@Override
	public void getPayStatus(int code, String msg) {
		// TODO Auto-generated method stub

		if(code == 1110){
			LogUtils.e("支付成功");
		}else{
			LogUtils.e("支付失败--->"+msg);
		}
		
	}

	/**
	 * 谷歌支付完成后，消费项目回调
	 */
	@Override
	public void consumeResult(int code, String msg, String orderId) {
		// TODO Auto-generated method stub
		
		if(code == 1000){
			//消费成功
			LogUtils.e("消费成功");
			loading.setVisibility(View.VISIBLE);
		}else{
			LogUtils.e("消费失败");
		}
		
	}

	@Override
	public void payResult(int code, String msg) {
		// TODO Auto-generated method stub
		loading.setVisibility(View.GONE);
		if(code == 1110){
			payLisenter.onPayResult(true);
			PayDialog.getInstance().close();
			close();
		}else{
			Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
			payLisenter.onPayResult(false);
		}
	}

}
