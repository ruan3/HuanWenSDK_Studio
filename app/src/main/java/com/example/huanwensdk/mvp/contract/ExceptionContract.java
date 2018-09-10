package com.example.huanwensdk.mvp.contract;

import android.content.Context;

public interface ExceptionContract {

	interface ExceptionPresenter{
		
		<T> void tips(Context context,T e);
		
	}
	
	interface ExcepitonModel{
		<T> void tips(Context context,T e);
	}
	
}
