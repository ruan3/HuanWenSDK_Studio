package com.example.huanwensdk.mvp.contract;

import com.example.huanwensdk.bean.wxpay.PayItemListBean;

public interface PayPalContract {

    interface Presenter{

        void getOrder(String itemId, String serverCode, String roleId,PayItemListBean.DataBean dataBean, View PayPalView);

        void payWithPayPal(PayItemListBean.DataBean dataBean);

    }

    interface Model{

        void getOrder(String itemId,String serverCode,String roleId,PayItemListBean.DataBean dataBean, View PayPalView);

        void payWithPayPal(PayItemListBean.DataBean dataBean);

    }

    interface View{

        void getOrderWithPayPal(int code,String msg);

        void payWithPayPalResult(int code,String msg);

    }

}
