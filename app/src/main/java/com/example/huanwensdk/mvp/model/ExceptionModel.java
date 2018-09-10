package com.example.huanwensdk.mvp.model;

import android.content.Context;
import android.widget.Toast;

import com.example.huanwensdk.mvp.contract.ExceptionContract;
import com.example.huanwensdk.utils.HWControl;
import com.example.huanwensdk.utils.LogUtils;
import com.example.huanwensdk.utils.ResLoader;
import com.tencent.bugly.crashreport.CrashReport;

public class ExceptionModel implements ExceptionContract.ExcepitonModel{

	@Override
	public <T> void tips(Context context, T e) {
		// TODO Auto-generated method stub
		LogUtils.e("T---->"+e.toString());
		CrashReport.postCatchedException((Throwable) e);
		if(context == null){
			context = HWControl.getInstance().getContext();
		}
		Toast.makeText(context, ResLoader.getString(context, "json_encode_error"), Toast.LENGTH_SHORT).show();
	}

}
