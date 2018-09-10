package com.example.huanwensdk.http;

import java.io.UnsupportedEncodingException;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;

public class StringRequest extends Request<String> {    
    private final Listener<String> mListener;    
    public StringRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {    
        super(method, url, errorListener);    
        mListener = listener;    
    }    
    public StringRequest(String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {    
        this(Method.GET, url, listener, errorListener);    
    }    
    
    @Override    
    protected void deliverResponse(String response) {    
        mListener.onResponse(response);    
    }    
    
    @Override    
    protected Response<String> parseNetworkResponse(NetworkResponse response) {    
        String parsed;    
        try {    
            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));    
        } catch (UnsupportedEncodingException e) {    
            parsed = new String(response.data);    
        }    
        return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));    
    }    
}    