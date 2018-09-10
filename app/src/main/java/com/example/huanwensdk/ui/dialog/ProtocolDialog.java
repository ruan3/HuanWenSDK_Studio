package com.example.huanwensdk.ui.dialog;

import android.graphics.Bitmap;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.huanwensdk.R;
import com.example.huanwensdk.base.BaseDialog;
import com.example.huanwensdk.utils.Constant;
import com.example.huanwensdk.utils.HWControl;

/**
 * 
 * @Title:  ProtocolDialog.java   
 * @Package ui.dialog   
 * @Description:    隐私条例
 * @author: Android_ruan     
 * @date:   2018-3-23 下午2:51:16   
 * @version V1.0
 */
public class ProtocolDialog {

	BaseDialog dialog;
	
	WebView wv_privacy;//隱私條例
	
	Button btn_back;
	
	LinearLayout loading;

	private ProtocolDialog() {
		// TODO Auto-generated constructor stub
		initView();
	}

	//单例模式
	private static class ProtocolDialogHepler {

		private static ProtocolDialog loginDialog = new ProtocolDialog();

	}

	public static ProtocolDialog getInstance() {

		return ProtocolDialogHepler.loginDialog;

	}

	//初始化view
	public void initView() {
		
		dialog = new BaseDialog(HWControl.getInstance().getContext(),
				R.style.dialog);
		dialog.setContentView(R.layout.dialog_protocol);
		wv_privacy = (WebView) dialog.findViewById(R.id.wv_privacy);//webView
		btn_back = (Button) dialog.findViewById(R.id.btn_back);//返回按钮
		loading = (LinearLayout) dialog.findViewById(R.id.loading);
		
		WebSettings webSettings = wv_privacy.getSettings();

		webSettings.setJavaScriptEnabled(true);
		webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
		wv_privacy.loadUrl(Constant.PRIVACY_URL);//加载隐私政策
		wv_privacy.setWebViewClient(new WebViewClient(){
			
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				view.loadUrl(url);
				return true;
			}
			
			@Override
			public void onPageFinished(WebView view, String url) {
				// TODO Auto-generated method stub
				super.onPageFinished(view, url);
				loading.setVisibility(View.GONE);
			}
			
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				// TODO Auto-generated method stub
				super.onPageStarted(view, url, favicon);
				loading.setVisibility(View.VISIBLE);
			}
			
		});
		
		btn_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(dialog.isShowing()){
					
					dialog.dismiss();
				}
				
			}
		});
		
		dialog.setCancelable(false);
	}

	
	void show(){
		
		if(dialog.isShowing()){
			dialog.dismiss();
		}
		dialog.show();
		
	}
}
