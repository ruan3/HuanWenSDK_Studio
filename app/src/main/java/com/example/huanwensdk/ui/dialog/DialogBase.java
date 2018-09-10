package com.example.huanwensdk.ui.dialog;

import android.content.Context;

import com.example.huanwensdk.base.BaseDialog;
import com.example.huanwensdk.utils.HWControl;
import com.example.huanwensdk.utils.HWUtils;
import com.example.huanwensdk.utils.LogUtils;
import com.example.huanwensdk.utils.ResLoader;

public abstract class DialogBase {

	public BaseDialog dialog;
	Context context;
	
	public void initView(int ContentId){
		// 设置SDK语言这是本地设置的一种方式
		HWUtils.setLanguage(HWControl.getInstance().getContext(), HWControl
								.getInstance().getContext().getResources());
		context = HWControl.getInstance().getContext();
		//网络设置的一种的方式
//		String language = HWConfigSharedPreferences.getInstance(context).getLanguage();
//		dialog = new BaseDialog(HWControl.getInstance().getContext(),
//				R.style.dialog);
		dialog = new BaseDialog(HWControl.getInstance().getContext(),
				ResLoader.getStyle(context, "dialog"));
		dialog.setContentView(ContentId);
		LogUtils.e("ContentId---->"+ContentId);
		dialog.setCanceledOnTouchOutside(false);
	}
	//显示对话框
	public abstract void show();
	//关闭对话框
	public abstract void close();
	
	public void exitDialog(){
		dialog = null;
	}
	
}
