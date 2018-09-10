package com.example.huanwensdk.ui.pager;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.huanwensdk.bean.order.HWOrderInfo;
import com.example.huanwensdk.mvp.contract.OrderContract;
import com.example.huanwensdk.mvp.presenter.OrderGetPresenter;
import com.example.huanwensdk.ui.adapter.HistoryAdapter;
import com.example.huanwensdk.utils.HWControl;
import com.example.huanwensdk.utils.ResLoader;

public class OrderPager extends BasePager implements OrderContract.View{

	Context context;
	OrderContract.Presnter presenter;
	
	String startTime;
	String endTime;
	String type;
	TextView tv_no_data;
	
	ListView lv_order_list;
	
	HistoryAdapter historyAdapter ;
	
	public OrderPager(String type,String startTime,String endTime) {
		// TODO Auto-generated constructor stub
		this.startTime = startTime;
		this.endTime = endTime;
		this.type = type;
	}
	
	
	@Override
	public View initView() {
		context = HWControl.getInstance().getContext();
//		View view = View.inflate(context, R.layout.pager_order, null);
		View view = View.inflate(context, ResLoader.getLayout(context, "pager_order"), null);
		
//		tv_no_data = (TextView) view.findViewById(R.id.tv_no_data);
		tv_no_data = (TextView) view.findViewById(ResLoader.getId(context, "tv_no_data"));
		
//		lv_order_list = (ListView) view.findViewById(R.id.lv_order_list);
		lv_order_list = (ListView) view.findViewById(ResLoader.getId(context, "lv_order_list"));
		initData(type);
		return view;
	}

	@Override
	public void initData(String type) {
		// TODO Auto-generated method stub
		Log.e("Com", "type---->"+type);
		HWControl.getInstance().setOrderView(this);
		presenter = new OrderGetPresenter();
		presenter.getOrderList(type, startTime, endTime, this);
	}

	@Override
	public void getOrderResult(String result, List<HWOrderInfo> hwOrderInfos) {
		// TODO Auto-generated method stub
		
		if(hwOrderInfos==null||hwOrderInfos.size()==0){
			tv_no_data.setVisibility(View.VISIBLE);
		}else{
			tv_no_data.setVisibility(View.GONE);
			historyAdapter = new HistoryAdapter(context, hwOrderInfos);
			lv_order_list.setAdapter(historyAdapter);
		}
	}


	@Override
	public void getOrderDate(String type,String startTime, String endTime) {
		// TODO Auto-generated method stub
		Log.e("Com", "type  getOrderDate---->"+type);
		presenter.getOrderList(type, startTime, endTime, this);
	}
	
	
}
