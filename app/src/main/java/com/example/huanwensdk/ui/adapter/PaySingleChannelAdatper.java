package com.example.huanwensdk.ui.adapter;

import java.util.List;

import org.xutils.x;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.huanwensdk.DataBase.DBUtils;
import com.example.huanwensdk.bean.google.HWGpPayItem;
import com.example.huanwensdk.bean.user.RoleInfo;
import com.example.huanwensdk.bean.wxpay.PayItemListBean.DataBean;
import com.example.huanwensdk.mvp.contract.AliPayContract;
import com.example.huanwensdk.mvp.contract.GooglePayContract;
import com.example.huanwensdk.mvp.contract.PaySingleContract;
import com.example.huanwensdk.mvp.contract.WXPayContract;
import com.example.huanwensdk.mvp.presenter.AliPresenter;
import com.example.huanwensdk.mvp.presenter.GooglePayPresenter;
import com.example.huanwensdk.mvp.presenter.WXPresenter;
import com.example.huanwensdk.ui.bean.PayInfo;
import com.example.huanwensdk.utils.HWControl;
import com.example.huanwensdk.utils.LogUtils;
import com.example.huanwensdk.utils.ResLoader;
import com.example.huanwensdk.utils.sp.HWConfigSharedPreferences;

/**
 * 
 * @Title:  PaySingleChannelAdatper.java   
 * @Package com.example.huanwensdk.ui.adapter   
 * @Description:   单个品项支付方式
 * @author: Android_ruan     
 * @date:   2018-5-22 下午2:48:00   
 * @version V1.0
 */
public class PaySingleChannelAdatper extends RecyclerView.Adapter<PaySingleChannelAdatper.PayViewHolder> {

	List<PayInfo> infos;
	private LayoutInflater inflater;
	Context context;
	
	PaySingleContract.View paySingleView;
	WXPayContract.WXPayPresenter wxPresenter;
	AliPayContract.AliPresenter aliPresenter;
	GooglePayContract.Presenter gpPresenter;
	WXPayContract.CoinView coinView;

	public PaySingleChannelAdatper(List<PayInfo> infos,PaySingleContract.View paySingleView,WXPayContract.CoinView coinView) {

		this.infos = infos;
		this.paySingleView = paySingleView;
		context = HWControl.getInstance().getContext();
		inflater = LayoutInflater.from(context);
		wxPresenter = new WXPresenter();
		aliPresenter = new AliPresenter();
		gpPresenter = new GooglePayPresenter();
		this.coinView = coinView;
	}

	@Override
	public int getItemCount() {
		return infos.size();
	}

	@Override
	public void onBindViewHolder(PayViewHolder viewHolder, int location) {

		viewHolder.iv_pay_name.setText(infos.get(location).getName());
//		viewHolder.iv_pay_logo.setImageResource(infos.get(location).getImage());
		x.image().bind(viewHolder.iv_pay_logo, infos.get(location).getPicUrl());//第一个参数显示图片的imageview，第二个图片地址
		if (infos.get(location).getName().equals("微信支付")) {

			viewHolder.iv_pay_logo.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
//					WXPayDialog.getInstance().show();
					LogUtils.e("点击了微信支付");
					String gameItemId = HWControl.getInstance().getGameItemId();
					String roleId = HWConfigSharedPreferences.getInstance(context).getRoleId();
					RoleInfo roleInfo = DBUtils.getInstance().queryRole(roleId);
					DataBean data = new DataBean();
					data.setGameItemId(gameItemId);
					data.setActiveDescription("");
					data.setDescription("");
					if(roleInfo!=null){
//						wxPresenter.getOrder(roleInfo.getServerCode(), roleId, data, paySingleView);
						wxPresenter.checkPlatformCoin(gameItemId, data, coinView);
						LogUtils.e("点击了获取订单");
					}else{
						LogUtils.e("role为空");
					}
					
				}
			});

		}

		if (infos.get(location).getName().equals("支付寶")) {

			viewHolder.iv_pay_logo.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					LogUtils.e("点击了支付宝支付");
					String gameItemId = HWControl.getInstance().getGameItemId();
					String roleId = HWConfigSharedPreferences.getInstance(context).getRoleId();
					RoleInfo roleInfo = DBUtils.getInstance().queryRole(roleId);
					DataBean data = new DataBean();
					data.setGameItemId(gameItemId);
					data.setActiveDescription("");
					data.setDescription("");
					if(roleInfo!=null){
						LogUtils.e("点击了支付宝支付");
//						aliPresenter.getOrder(roleInfo.getServerCode(), roleId, data, paySingleView);
						aliPresenter.checkPlatformCoin(gameItemId, data, coinView);
					}else{
						LogUtils.e("role为空");
					}
				}
			});

		}
		
		//谷歌支付
		if(infos.get(location).getName().equals("Google Wallet")){
			
			viewHolder.iv_pay_logo.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {

					LogUtils.e("点击了谷歌支付");
					String gameItemId = HWControl.getInstance().getGameItemId();
					String roleId = HWConfigSharedPreferences.getInstance(context).getRoleId();
					RoleInfo roleInfo = DBUtils.getInstance().queryRole(roleId);
					HWGpPayItem data = new HWGpPayItem();
					data.setGameItemId(gameItemId);
					data.setActiveDescription("");
					data.setDescription("");
					if(roleInfo!=null){
						LogUtils.e("点击了谷歌支付");
//						aliPresenter.getOrder(roleInfo.getServerCode(), roleId, data, paySingleView);
						gpPresenter.checkPlatformCoin(gameItemId, data, coinView);
					}else{
						LogUtils.e("role为空");
					}
					
				}
			});
			
		}

	}

	@Override
	public PayViewHolder onCreateViewHolder(ViewGroup parent, int arg1) {

//		View view = inflater.inflate(R.layout.pay_item, parent, false);
		View view = inflater.inflate(ResLoader.getLayout(context, "pay_item"), parent, false);
		PayViewHolder holder = new PayViewHolder(view);
		return holder;
	}

	class PayViewHolder extends ViewHolder {

		ImageView iv_pay_logo;
		TextView iv_pay_name;

		public PayViewHolder(View itemView) {
			super(itemView);
//			iv_pay_logo = (ImageView) itemView.findViewById(R.id.iv_pay_logo);
			iv_pay_logo = (ImageView) itemView.findViewById(ResLoader.getId(context, "iv_pay_logo"));
			
//			iv_pay_name = (TextView) itemView.findViewById(R.id.iv_pay_name);
			iv_pay_name = (TextView) itemView.findViewById(ResLoader.getId(context, "iv_pay_name"));
		}
	}
	
}
