package com.example.huanwensdk.ui.dialog;

import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.example.huanwensdk.utils.ResLoader;


/**
 * 
 * @Title:  NoticeDialog.java   
 * @Package com.example.huanwensdk.ui.dialog   
 * @Description:    公告  
 * @author: Android_ruan     
 * @date:   2018-5-22 上午9:29:58   
 * @version V1.0
 */
public class NoticeDialog extends DialogBase{

	Button btn_back;
	TextView tv_notice_content;
	
	private NoticeDialog(){
		
//		initView(R.layout.dialog_notice);
		initView(ResLoader.getLayout(context, "dialog_notice"));
		
	}
	
	private static class NoticeDialogHelper{
		
		private static NoticeDialog noticeDialog = new NoticeDialog();
		
	}
	
	public static NoticeDialog getInstance(){
		
		return NoticeDialogHelper.noticeDialog;
		
	}
	
	@Override
	public void initView(int ContentId) {
		// TODO Auto-generated method stub
		super.initView(ContentId);
//		btn_back = (Button) dialog.findViewById(R.id.btn_back);
		btn_back = (Button) dialog.findViewById(ResLoader.getId(context, "btn_back"));
		
//		tv_notice_content = (TextView) dialog.findViewById(R.id.tv_notice_content);
		tv_notice_content = (TextView) dialog.findViewById(ResLoader.getId(context, "tv_notice_content"));
		
		btn_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				close();
				
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
	 * 获取网络返回字段
	 * @param content
	 */
	public void getText(String content){
		
		if(content!=null&!TextUtils.isEmpty(content)){
			
			tv_notice_content.setText(Html.fromHtml(content));
		}
		
	}
	
	
}
