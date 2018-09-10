package com.example.huanwensdk.mvp.presenter;

import com.example.huanwensdk.mvp.contract.FacebookContract;
import com.example.huanwensdk.mvp.model.FacebookModel;

/**
 * 
 * Facebook中间调度类
 * @author  Ruan
 * @data:  2018-9-5 上午10:19:09
 * @version:  V1.0
 */
public class FacebookPresenter implements FacebookContract.FaceBookPresenter{

	FacebookContract.FaceBookModel faceBookModel;
	
	@Override
	public void getFacebookUserInfo(FacebookContract.FaceBookView faceBookView) {

		if(faceBookModel==null){
			faceBookModel = new FacebookModel();
		}
		faceBookModel.getFacebookUserInfo(faceBookView);
	}

}
