package com.example.huanwensdk.ui.dialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.example.huanwensdk.mvp.contract.OrderContract;
import com.example.huanwensdk.ui.adapter.MyOrderAdapter;
import com.example.huanwensdk.utils.HWControl;
import com.example.huanwensdk.utils.LogUtils;
import com.example.huanwensdk.utils.ResLoader;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.ScrollIndicatorView;
import com.shizhefei.view.indicator.slidebar.ColorBar;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;

public class OrderDetailDialog extends DialogBase{

	Button btn_back;
	Button btn_last_month;
	Button btn_this_month;
	TextView tv_order_time;
	ScrollIndicatorView moretab_indicator;
	ViewPager moretab_viewPager;
	
	Calendar calendar;
	
	SimpleDateFormat format ;
	SimpleDateFormat format1;
	
	OrderContract.View orderView;
	
	private IndicatorViewPager indicatorViewPager;
	
	MyOrderAdapter myOrderAdapter;
	
	private OrderDetailDialog(){
	
//		initView(R.layout.dialog_order_detail);
		initView(ResLoader.getLayout(context, "dialog_order_detail"));
		
	}
	
	private static class OrderDetailHepler{
		
		private static OrderDetailDialog orderDialog = new OrderDetailDialog();
		
	}
	
	public static OrderDetailDialog getInstance(){
		
		return OrderDetailHepler.orderDialog;
		
	}
	
	@Override
	public void initView(int ContentId) {
		// TODO Auto-generated method stub
		super.initView(ContentId);
//		btn_back = (Button) dialog.findViewById(R.id.btn_back);
		btn_back = (Button) dialog.findViewById(ResLoader.getId(context, "btn_back"));
		
//		btn_last_month = (Button) dialog.findViewById(R.id.btn_last_month);
		btn_last_month = (Button) dialog.findViewById(ResLoader.getId(context, "btn_last_month"));
		
//		btn_this_month = (Button) dialog.findViewById(R.id.btn_this_month);
		btn_this_month = (Button) dialog.findViewById(ResLoader.getId(context, "btn_this_month"));
		
//		tv_order_time = (TextView) dialog.findViewById(R.id.tv_order_time);
		tv_order_time = (TextView) dialog.findViewById(ResLoader.getId(context, "tv_order_time"));
		
//		moretab_indicator = (ScrollIndicatorView) dialog.findViewById(R.id.moretab_indicator);
		moretab_indicator = (ScrollIndicatorView) dialog.findViewById(ResLoader.getId(context, "moretab_indicator"));
		
//		moretab_viewPager = (ViewPager) dialog.findViewById(R.id.moretab_viewPager);
		moretab_viewPager = (ViewPager) dialog.findViewById(ResLoader.getId(context, "moretab_viewPager"));
		
		calendar = Calendar.getInstance();
		format = new SimpleDateFormat("yyyy/MM");
		format1 = new SimpleDateFormat("yyyy-MM");
		initData();
	}
	
	/**
	 * 初始化数据
	 */
	private void initData(){
		
		myOrderAdapter = new MyOrderAdapter(getStratTime(),getEndTime());
		float unSelectSize = 12;
        float selectSize = unSelectSize * 1.3f;
        moretab_indicator.setOnTransitionListener(new OnTransitionTextListener().setColor(0xFF2196F3, Color.GRAY).setSize(selectSize, unSelectSize));

        moretab_indicator.setScrollBar(new ColorBar(context, 0xFF2196F3, 4));

        moretab_viewPager.setOffscreenPageLimit(2);
        indicatorViewPager = new IndicatorViewPager(moretab_indicator, moretab_viewPager);
        indicatorViewPager.setAdapter(myOrderAdapter);
        btn_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				close();
			}
		});
        LogUtils.e("tv_order_time---->"+tv_order_time);
        LogUtils.e("getCurrentDate()---->"+getCurrentDate());
        tv_order_time.setText(getCurrentDate());
        btn_this_month.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				tv_order_time.setText(getCurrentDate());
				orderView = HWControl.getInstance().getOrderView();
				myOrderAdapter = new MyOrderAdapter(getStratTime(),getEndTime());
				indicatorViewPager.setAdapter(myOrderAdapter);
//				orderView.getOrderDate(indicatorViewPager.getCurrentItem()+"",getStratTime(), getEndTime());
				
			}
		});
        
        btn_last_month.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				tv_order_time.setText(getLastMonth());
				orderView = HWControl.getInstance().getOrderView();
				myOrderAdapter = new MyOrderAdapter(getStratTime(),getEndTime());
				indicatorViewPager.setAdapter(myOrderAdapter);
//				orderView.getOrderDate(indicatorViewPager.getCurrentItem()+"",getStratTime(), getEndTime());
			}
		});
        
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		if(dialog.isShowing()){
			dialog.dismiss();
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
	
	/**
	 * 获取当前月份
	 * @return
	 */
	private String getCurrentDate()
	  {
	    calendar.setTime(new Date(System.currentTimeMillis()));
	    return format.format(new Date(System.currentTimeMillis()));
	  }

	/**
	 * 获取上一月份
	 * @return
	 */
	private String getLastMonth()
	  {
	    Calendar localCalendar = calendar;
	    localCalendar.add(Calendar.MONTH, -1);
	    Date localDate = calendar.getTime();
	    return this.format.format(localDate);
	  }

	private String getEndTime()
	  {
	    return format1.format(calendar.getTime()) + "-" + 31;
	  }
	
	private String getStratTime()
	  {
	    return format1.format(calendar.getTime()) + "-" + 1;
	  }
	
}
