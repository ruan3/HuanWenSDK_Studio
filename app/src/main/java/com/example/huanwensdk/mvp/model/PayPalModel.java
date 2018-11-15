package com.example.huanwensdk.mvp.model;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.huanwensdk.DataBase.DBUtils;
import com.example.huanwensdk.bean.wxpay.PayItemListBean;
import com.example.huanwensdk.bean.wxpay.WXPayOrderBean;
import com.example.huanwensdk.http.RequestQueueHepler;
import com.example.huanwensdk.mvp.contract.ExceptionContract;
import com.example.huanwensdk.mvp.contract.PayPalContract;
import com.example.huanwensdk.mvp.presenter.ExceptionPresenter;
import com.example.huanwensdk.utils.Constant;
import com.example.huanwensdk.utils.HWControl;
import com.example.huanwensdk.utils.HWUtils;
import com.example.huanwensdk.utils.LogUtils;
import com.example.huanwensdk.utils.ResLoader;
import com.example.huanwensdk.utils.sp.HWConfigSharedPreferences;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalItem;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * PayPal支付逻辑处理
 */
public class PayPalModel implements PayPalContract.Model{

    Context context;
    PayPalContract.View PayPalView;
    String gameCode;
    ExceptionContract.ExceptionPresenter exceptionPresenter;

    // 真实环境
//    private static final String CONFIG_ENVIRONMENT =
//            PayPalConfiguration.ENVIRONMENT_PRODUCTION;
    // paypal申请到的设备ID
    private static final String CONFIG_CLIENT_ID = Constant.PAYPAL_CLIENT_ID;

    private static final int REQUEST_CODE_PAYMENT = 1000;

    // 沙盒测试
     private static final String CONFIG_ENVIRONMENT =
    PayPalConfiguration.ENVIRONMENT_SANDBOX;
    // 沙盒测试的设备ID
    // private static final String CONFIG_CLIENT_ID = "paypal官方申请的沙盒设备ID";

    private static PayPalConfiguration paypalConfig = new
            PayPalConfiguration().environment(CONFIG_ENVIRONMENT).clientId(CONFIG_CLIENT_ID);

    @Override
    public void getOrder(final String itemId, final String serverCode, final String roleId, PayItemListBean.DataBean dataBean, PayPalContract.View PayPalView) {

      LogUtils.e("---PaPay支付获取订单开始---");
        context = HWControl.getInstance().getContext();
        this.PayPalView = PayPalView;
        gameCode = ResLoader.getString(context, "hw_gamecode");
        // 设置字段
        final String userId = HWConfigSharedPreferences.getInstance(context)
                .getUserId();
        final String sessionid = DBUtils
                .getInstance()
                .queryInfoUser(
                        HWConfigSharedPreferences.getInstance(context)
                                .getUserId()).getSessionid();
        final String token = DBUtils
                .getInstance()
                .queryInfoUser(
                        HWConfigSharedPreferences.getInstance(context)
                                .getUserId()).getToken();
        final String machineid = HWUtils.getDeviceId(context);
        final String gamecode = gameCode;
        final String comefrom = "android";
        final String timestamp = HWUtils.getTimestamp();
        final String platform = ResLoader.getString(context, "platform");
        final String gameName = ResLoader.getString(context, "game_name");
        final int itemid = dataBean.getId();
        final String cpu = Build.CPU_ABI;
        final String channel = "wechat";
        final String ItemKey = dataBean.getItemKey();
        final String language = HWConfigSharedPreferences.getInstance(
                HWControl.getInstance().getContext()).getLanguage();

        // 请求网络
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constant.HW_GET_WECHAT_ORDER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                // TODO Auto-generated method stub
                Log.e("Com", "获取支付订单肯定没错--->" + response);
                exceptionPresenter = new ExceptionPresenter();
                try{
                    Gson gson = new Gson();
                    WXPayOrderBean result = gson.fromJson(response,
                            WXPayOrderBean.class);
                    Log.e("Com", "gson解释后数据--->" + result.toString());
                    int code = Integer.parseInt(result.getCode());
                    if (code == 1000) {
                        // 1000就是成功
                        LogUtils.e("获取支付订单成功返回字段---->" + response);
                        // ParseOrder(result);
                    } else if (code == 1001) {
                    } else {
                        LogUtils.e("获取支付订单返回字段---->" + response);
                    }
                }catch(JsonSyntaxException e){
                    exceptionPresenter.tips(context, e);
                }catch(JsonIOException e){
                    exceptionPresenter.tips(context, e);
                }catch(JsonParseException e){
                    exceptionPresenter.tips(context, e);
                }catch (Exception e) {
                    // TODO: handle exception
                    exceptionPresenter.tips(context, e);
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                Log.e("Com", "出错--->" + error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("machineid", machineid);
                map.put("gamecode", gamecode);
                map.put("comefrom", comefrom);
                map.put("timestamp", timestamp);
                map.put("platform", platform);
                map.put("payType", "test_pay_type_1");
                map.put("pid", "test_pay_id");
                map.put("sessionid", sessionid);
                map.put("token", token);
                map.put("version", "1.0");
                map.put("servercode", serverCode);
                map.put("roleid", roleId);
                map.put("cpu", cpu);
                map.put("itemid", itemid + "");
                map.put("language", language);
                map.put("gameName", gameName);
                map.put("userid", userId);
                map.put("channel", channel);
                map.put("item_key", ItemKey);

                LogUtils.e("PayPay支付订单地址---->" + Constant.HW_GET_WECHAT_ORDER);
                LogUtils.e("PayPay支付订单请求字段---->" + map.toString());

                return map;
            }
        };

        RequestQueueHepler.getInstance().getQueue().add(stringRequest);

    }

    @Override
    public void payWithPayPal(PayItemListBean.DataBean dataBean) {
        if (context==null){
            context = HWControl.getInstance().getContext();
        }
    // 开启PayPal服务
        Intent intent = new Intent(context, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, paypalConfig);
        context.startService(intent);
        BigDecimal amout = new BigDecimal(dataBean.getAmount());
    // PayPalItem的四个参数. 1.商品名称 2.商品数量 3.商品单价 4.货币 5.商品描述
        PayPalItem item = new PayPalItem(dataBean.getTitle(), 1, amout, dataBean.getCurrency(), dataBean.getDescription());
        String order = "";
        PayPalPayment palPayment = new PayPalPayment(amout,dataBean.getCurrency(),dataBean.getDescription(),PayPalPayment.PAYMENT_INTENT_SALE);
        palPayment.custom("test_for_orderid");
    // 跳转到Paypal
        Intent i = new Intent(context, PaymentActivity.class);

        i.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, paypalConfig);

        i.putExtra(PaymentActivity.EXTRA_PAYMENT, palPayment);

        ((Activity)context).startActivityForResult(i, REQUEST_CODE_PAYMENT);
    }
}
