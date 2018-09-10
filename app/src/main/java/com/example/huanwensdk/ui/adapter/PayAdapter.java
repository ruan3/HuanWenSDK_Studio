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

import com.example.huanwensdk.R;
import com.example.huanwensdk.ui.bean.PayInfo;
import com.example.huanwensdk.ui.dialog.AliPayDialog;
import com.example.huanwensdk.ui.dialog.GooglePayDialog;
import com.example.huanwensdk.ui.dialog.WXPayDialog;
import com.example.huanwensdk.utils.HWControl;
import com.example.huanwensdk.utils.LogUtils;
import com.example.huanwensdk.utils.ResLoader;

public class PayAdapter extends RecyclerView.Adapter<PayAdapter.PayViewHolder> {

	List<PayInfo> infos;
	private LayoutInflater inflater;
	Context context;

	public PayAdapter(List<PayInfo> infos) {

		this.infos = infos;
		context = HWControl.getInstance().getContext();
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getItemCount() {
		return infos.size();
	}

	@Override
	public void onBindViewHolder(PayViewHolder viewHolder, int location) {
		LogUtils.e("支付名字---->"+infos.get(location).getName());
		viewHolder.iv_pay_name.setText(infos.get(location).getName());
//		viewHolder.iv_pay_logo.setImageResource(infos.get(location).getImage());
		x.image().bind(viewHolder.iv_pay_logo, infos.get(location).getPicUrl());//第一个参数显示图片的imageview，第二个图片地址
		if (infos.get(location).getName().equals("微信支付")) {

			viewHolder.iv_pay_logo.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					WXPayDialog.getInstance().show();
				}
			});

		}

		if (infos.get(location).getName().equals("支付寶")) {

			viewHolder.iv_pay_logo.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					AliPayDialog.getInstance().show();
				}
			});
		}
		if(infos.get(location).getName().equals("Google Wallet")){
			LogUtils.e("进了谷歌支付--->");
			viewHolder.iv_pay_logo.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					GooglePayDialog.getInstance().show();
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
