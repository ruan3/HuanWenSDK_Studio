package com.example.huanwensdk.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.huanwensdk.ui.pager.OrderPager;
import com.example.huanwensdk.utils.DisplayUtil;
import com.example.huanwensdk.utils.HWControl;
import com.example.huanwensdk.utils.ResLoader;
import com.shizhefei.view.indicator.IndicatorViewPager;

public class MyOrderAdapter extends IndicatorViewPager.IndicatorViewPagerAdapter{

	String orderAll = ResLoader.getString(HWControl.getInstance().getContext(), "order_all");
	String orderSuccess = ResLoader.getString(HWControl.getInstance().getContext(), "order_success");
	String orderFial = ResLoader.getString(HWControl.getInstance().getContext(), "order_fail");
	private String[] names = {orderAll, orderSuccess,orderFial};
	
	String startTime;
	String endTime;
	
	Context context;
	
	public MyOrderAdapter(String startTime,String endTime) {
		// TODO Auto-generated constructor stub
		this.startTime = startTime;
		this.endTime = endTime;
		context = HWControl.getInstance().getContext();
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return names.length;
	}

	@Override
	public View getViewForPage(int position, View convertView, ViewGroup container) {
		// TODO Auto-generated method stub
		Log.e("Com", "position---->"+position);
		if (convertView == null) {
			Log.e("Com", "position---->"+position);
            convertView = new OrderPager(position+"",startTime,endTime).initView();
        }
//        TextView textView = (TextView) convertView;
//        textView.setText(names[position]);
//        textView.setGravity(Gravity.CENTER);
//        textView.setTextColor(Color.GRAY);
        return convertView;
	}

	@Override
	public int getItemPosition(Object object) {
		// TODO Auto-generated method stub
		//这是ViewPager适配器的特点,有两个值 POSITION_NONE，POSITION_UNCHANGED，默认就是POSITION_UNCHANGED,
        // 表示数据没变化不用更新.notifyDataChange的时候重新调用getViewForPage
        return PagerAdapter.POSITION_UNCHANGED;
	}
	
	@Override
	public View getViewForTab(int position, View convertView, ViewGroup container) {
		// TODO Auto-generated method stub
		if (convertView == null) {
//            convertView = ((Activity) HWControl.getInstance().getContext()).getLayoutInflater().inflate(R.layout.tab_top, container, false);
            convertView = ((Activity) HWControl.getInstance().getContext()).getLayoutInflater().inflate(ResLoader.getLayout(context, "tab_top"), container, false);
        }
        TextView textView = (TextView) convertView;
        textView.setText(names[position]);

        int witdh = getTextWidth(textView);
        int padding = DisplayUtil.dipToPix(HWControl.getInstance().getContext(), 8);
        //因为wrap的布局 字体大小变化会导致textView大小变化产生抖动，这里通过设置textView宽度就避免抖动现象
        //1.3f是根据上面字体大小变化的倍数1.3f设置
        textView.setWidth((int) (witdh * 1.3f) + padding);

        return convertView;
	}

	 private int getTextWidth(TextView textView) {
         if (textView == null) {
             return 0;
         }
         Rect bounds = new Rect();
         String text = textView.getText().toString();
         Paint paint = textView.getPaint();
         paint.getTextBounds(text, 0, text.length(), bounds);
         int width = bounds.left + bounds.width();
         return width;
     }
	
}
