package com.example.huanwensdk.http;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.huanwensdk.utils.HWControl;

/**
 * 
 * @Title:  RequestQueue.java   
 * @Package http   
 * @Description:    volley请求队列
 * @author: Android_ruan     
 * @date:   2018-3-22 下午5:23:26   
 * @version V1.0
 */
public class RequestQueueHepler{

	
	RequestQueue queue;


	private static class RequestQueueHeplerInstance{
		
		private static RequestQueueHepler requestQueue = new RequestQueueHepler();
		
	}
	
	public static RequestQueueHepler getInstance(){
		
		return RequestQueueHeplerInstance.requestQueue;
		
	}
	
	public RequestQueue getQueue(){
		
		if(queue == null){
			queue = Volley  
                    .newRequestQueue(HWControl.getInstance().getContext()); 
		}
		
		return queue;
		
		
	}
	
	
	
	public <T> void addQueue(Request<T> req){
		
		queue.add(req);
		
	}
	
}
