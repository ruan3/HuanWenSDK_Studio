package com.example.huanwensdk.mvp.contract;

/**
 *
 * facebook相关接口控制类
 * @author  Ruan
 * @data:  2018-9-5 上午10:11:19
 * @version:  V1.0
 */
public interface FacebookContract {

	interface FaceBookPresenter{
		
		void getFacebookUserInfo(FaceBookView faceBookView);
		
	}
	
	interface FaceBookModel{
		
		void getFacebookUserInfo(FaceBookView faceBookView);
		
	}
	
	interface FaceBookView{
		
		void FacebookLoginResult(int code,String msg);
		
	}
	
}
