package com.example.huanwensdk.ui.dialog;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.example.huanwensdk.utils.ResLoader;
/**
 * 
 * 更新提示对话框
 * @author  Ruan
 * @data:  2018-6-25 上午11:00:20
 * @version:  V1.0
 */
public class UpdateDialog extends DialogBase{

	Button btn_update;
	Button btn_back;
	TextView tv_notice_content;

	String url;
	String content;
	
	
	private UpdateDialog(){
		
//		initView(R.layout.dialog_update);
		initView(ResLoader.getLayout(context, "dialog_update"));
		
	}
	
	private static class UpdateDialogHepler{
		
		private static UpdateDialog updateDialog = new UpdateDialog();
		
	}
	
	public static UpdateDialog getInstance(){
		
		return UpdateDialogHepler.updateDialog;
		
	}
	
	@Override
	public void initView(int ContentId) {
		// TODO Auto-generated method stub
		super.initView(ContentId);
//		btn_back = (Button) dialog.findViewById(R.id.btn_back);
		btn_back = (Button) dialog.findViewById(ResLoader.getId(context, "btn_back"));
		
//		btn_update = (Button) dialog.findViewById(R.id.btn_update);
		btn_update = (Button) dialog.findViewById(ResLoader.getId(context, "btn_update"));
		
//		tv_notice_content = (TextView) dialog.findViewById(R.id.tv_notice_content);
		tv_notice_content = (TextView) dialog.findViewById(ResLoader.getId(context, "tv_notice_content"));
		
		btn_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				close();
			}
		});
		
		//点击更新后操作
		btn_update.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//暂时跳转到浏览器
				Uri uri = Uri.parse(url);  
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);  
				context.startActivity(intent); 
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
	 * 获取数据
	 */
	public void getContent(String content,String url){
		
		if(!TextUtils.isEmpty(content)&&!TextUtils.isEmpty(url)){
			tv_notice_content.setText(content);
			this.url = url;
		}
		
	}

}
