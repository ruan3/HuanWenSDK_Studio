package com.example.huanwensdk.ui.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.huanwensdk.bean.order.HWOrderInfo;
import com.example.huanwensdk.utils.ResLoader;

/**
 * 
 * @Title:  HistoryAdapter.java   
 * @Package com.ruan.devicesanalysis.view.adapter   
 * @Description:    历史适配器   
 * @author: Android_ruan     
 * @date:   2018-5-7 下午2:38:59   
 * @version V1.0
 */
public class HistoryAdapter extends BaseAdapter{

	List<HWOrderInfo> orderInfos;
	Context context;
	ViewHolder viewHolder;
	public HistoryAdapter(Context context,List<HWOrderInfo> orderInfos){
		this.context = context;
		this.orderInfos = orderInfos;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return orderInfos.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView != null && convertView instanceof LinearLayout){
			viewHolder = (ViewHolder) convertView.getTag();
		}else{
			viewHolder = new ViewHolder(); 
//			convertView = View.inflate(context, R.layout.item_history, null);
			convertView = View.inflate(context, ResLoader.getLayout(context, "item_history"), null);
//			viewHolder.tv_oder_id = (TextView) convertView.findViewById(R.id.tv_order_id);//id
			viewHolder.tv_oder_id = (TextView) convertView.findViewById(ResLoader.getId(context, "tv_order_id"));//id
//			viewHolder.tv_oder_date = (TextView)convertView.findViewById(R.id.tv_oder_date);//时间
			viewHolder.tv_oder_date = (TextView)convertView.findViewById(ResLoader.getId(context, "tv_oder_date"));//时间
//			viewHolder.tv_oder_status = (TextView)convertView.findViewById(R.id.tv_oder_status);
			viewHolder.tv_oder_status = (TextView)convertView.findViewById(ResLoader.getId(context, "tv_oder_status"));
			
			convertView.setTag(viewHolder);
		}
		
		viewHolder.tv_oder_id.setText("单号："+orderInfos.get(position).getOrderid());
		viewHolder.tv_oder_date.setText("时间："+orderInfos.get(position).getCreateTime());
		int status = orderInfos.get(position).getStatus();
		if(status==1){		
			viewHolder.tv_oder_status.setText("状态：成功");
			
		}else{
			viewHolder.tv_oder_status.setText("状态：失败");
		}
		return convertView;
	}

	static class ViewHolder{
		TextView tv_oder_id;//时间
		TextView tv_oder_date;//温度
		TextView tv_oder_status;//订单状态
	}
	
	
}
