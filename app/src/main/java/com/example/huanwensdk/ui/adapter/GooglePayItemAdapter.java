package com.example.huanwensdk.ui.adapter;

import java.util.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.huanwensdk.bean.google.HWGpPayItem;
import com.example.huanwensdk.mvp.contract.GooglePayContract;
import com.example.huanwensdk.mvp.contract.WXPayContract;
import com.example.huanwensdk.mvp.presenter.GooglePayPresenter;
import com.example.huanwensdk.utils.HWControl;
import com.example.huanwensdk.utils.LogUtils;
import com.example.huanwensdk.utils.ResLoader;

public class GooglePayItemAdapter extends
		RecyclerView.Adapter<GooglePayItemAdapter.PayItemViewHolder> {

	List<HWGpPayItem> payList;

	private LayoutInflater inflater;
	Context context;
	WXPayContract.CoinView coinView;

	public GooglePayItemAdapter(List<HWGpPayItem> payList,
			WXPayContract.CoinView coinView) {
		this.payList = payList;
		this.coinView = coinView;
		context = HWControl.getInstance().getContext();
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getItemCount() {
		// TODO Auto-generated method stub
		return payList.size();
	}

	@Override
	public void onBindViewHolder(PayItemViewHolder viewHolder,
			int location) {
		// TODO Auto-generated method stub
		viewHolder.tv_items_name.setText(payList.get(location).getTitle());
		final int position = location;
		viewHolder.tv_items_name.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				LogUtils.e("谷歌单个品项点击了---->" + payList.get(position).getGameItemId());
				GooglePayContract.Presenter presenter = new GooglePayPresenter();
				HWGpPayItem dataBean = payList.get(position);
				// String roleId =
				// HWConfigSharedPreferences.getInstance(context).getRoleId();
				// RoleInfo roleInfo = DBUtils.getInstance().queryRole(roleId);
				// presenter.getOrder(roleInfo.getServerCode(), roleId,
				// payList.get(location),null);
				presenter.checkPlatformCoin(dataBean.getGameItemId(), dataBean,
						coinView);
			}
		});
	}

	@Override
	public PayItemViewHolder onCreateViewHolder(ViewGroup parent, int arg1) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(ResLoader.getLayout(context, "dialog_pay_items"),parent, false);  
		PayItemViewHolder holder= new PayItemViewHolder(view);  
         return holder;  
	}

	class PayItemViewHolder extends ViewHolder {

		TextView tv_items_name;

		public PayItemViewHolder(View itemView) {
			super(itemView);
//			tv_items_name = (TextView) itemView
//					.findViewById(R.id.tv_items_name);
tv_items_name = (TextView) itemView
					.findViewById(ResLoader.getId(context,"tv_items_name"));
		}
	}

}
