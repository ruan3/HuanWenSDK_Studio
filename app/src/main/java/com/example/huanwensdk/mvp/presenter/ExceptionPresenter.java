package com.example.huanwensdk.mvp.presenter;

import android.content.Context;

import com.example.huanwensdk.mvp.contract.ExceptionContract;
import com.example.huanwensdk.mvp.model.ExceptionModel;

public class ExceptionPresenter implements ExceptionContract.ExceptionPresenter{

	ExceptionContract.ExcepitonModel model;
	
	@Override
	public <T> void tips(Context context, T e) {
		// TODO Auto-generated method stub
		if(model==null){
			model = new ExceptionModel();
		}
		model.tips(context, e);
	}

}
