package com.example.huanwensdk.ui.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huanwensdk.mvp.contract.RetrieveGuestAccountContract;
import com.example.huanwensdk.mvp.presenter.RetrieveGuestPresenter;
import com.example.huanwensdk.utils.HWControl;
import com.example.huanwensdk.utils.LogUtils;
import com.example.huanwensdk.utils.ResLoader;

/**
 * 
 * @Title:  RetrieveGuestAccountDialog.java   
 * @Package ui.dialog   
 * @Description:    试玩账号找回
 * @author: Android_ruan     
 * @date:   2018-4-4 上午11:05:17   
 * @version V1.0
 */
public class RetrieveGuestAccountDialog extends DialogBase implements RetrieveGuestAccountContract.RetrieveGuestView{

	EditText et_guest_uid;
	EditText et_retrieve_code;
	Button btn_retrieve_next;
	
	Button btn_close;
	TextView tv_title;
	
	Context context;
	
	String uid;
	String code;
	
	RetrieveGuestAccountContract.RetrieveGusetPresenter presenter;
	RetrieveGuestAccountContract.RetrieveGuestView retrieveGuestView;
	
	LinearLayout loading;
	
	private RetrieveGuestAccountDialog(){
		
//		initView(R.layout.dialog_retrieve_guest_account);
		initView(ResLoader.getLayout(context, "dialog_retrieve_guest_account"));
		
	}
	
	private static class RetrieveGuestDialogHepler{
		
		private static RetrieveGuestAccountDialog retrieveGuestAccountDialog = new RetrieveGuestAccountDialog();
		
	}
	
	public static RetrieveGuestAccountDialog getInstance(){
		
		return RetrieveGuestDialogHepler.retrieveGuestAccountDialog;
		
	}
	
	@Override
	public void initView(int ContentId) {
		// TODO Auto-generated method stub
		super.initView(ContentId);
		
		context = HWControl.getInstance().getContext();
		presenter = new RetrieveGuestPresenter();
		retrieveGuestView = this;
		
//		et_guest_uid = (EditText) dialog.findViewById(R.id.et_guest_uid);
		et_guest_uid = (EditText) dialog.findViewById(ResLoader.getId(context, "et_guest_uid"));
		
//		et_retrieve_code = (EditText) dialog.findViewById(R.id.et_retrieve_code);
		et_retrieve_code = (EditText) dialog.findViewById(ResLoader.getId(context, "et_retrieve_code"));
		
//		btn_retrieve_next = (Button) dialog.findViewById(R.id.btn_retrieve_next);
		btn_retrieve_next = (Button) dialog.findViewById(ResLoader.getId(context, "btn_retrieve_next"));
		
//		btn_close = (Button) dialog.findViewById(R.id.btn_close);
		btn_close = (Button) dialog.findViewById(ResLoader.getId(context, "btn_close"));
		
//		tv_title = (TextView) dialog.findViewById(R.id.tv_title);
		tv_title = (TextView) dialog.findViewById(ResLoader.getId(context, "tv_title"));
		
//		loading = (LinearLayout) dialog.findViewById(R.id.loading);
		loading = (LinearLayout) dialog.findViewById(ResLoader.getId(context, "loading"));
		
		tv_title.setText(ResLoader.getString(context, "string_trial_find_title"));
		
		//关闭对话框
		btn_close.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				close();
			}
		});
		//下一步
		btn_retrieve_next.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				if(isEmpty()){
					//提示输入uid和恢复码
					Toast.makeText(context, ResLoader.getString(context, "string_trial_uidOrcode_null"), Toast.LENGTH_LONG).show();
				}else{
					//走找回账号流程
					presenter.checkUid(uid, code, retrieveGuestView);
					loading.setVisibility(View.VISIBLE);
				}
				
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
	
	private boolean isEmpty(){
		
		uid = et_guest_uid.getText().toString().trim();
		code = et_retrieve_code.getText().toString().trim();
		
		if(TextUtils.isEmpty(uid) || TextUtils.isEmpty(code)){
			return true;
		}
		return false;
		
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		if(dialog.isShowing()){
			dialog.dismiss();
		}
	}

	@Override
	public void checkUidSuccess(String msg) {
		// 跳转到绑定邮箱界面
		loading.setVisibility(View.GONE);
		TrialBindingEmailDialog.getInstance().show();
		LogUtils.e("uid--->"+uid+"code---->"+code);
		TrialBindingEmailDialog.getInstance().getData(uid, code);
	}

	@Override
	public void checkUidFail(String msg) {
		loading.setVisibility(View.GONE);
		// 提示失败
		Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
	}

}
