package com.example.huanwensdk.mvp.presenter;

import com.example.huanwensdk.bean.wxpay.PayItemListBean;
import com.example.huanwensdk.mvp.contract.PayPalContract;
import com.example.huanwensdk.mvp.model.PayPalModel;

public class PayPalPresenter implements PayPalContract.Presenter {

    PayPalContract.Model model;

    @Override
    public void getOrder(String itemId, String serverCode, String roleId, PayItemListBean.DataBean dataBean, PayPalContract.View PayPalView) {

        if (model == null){
            model = new PayPalModel();
        }

        model.getOrder(itemId,serverCode,roleId,dataBean,PayPalView);

    }

    @Override
    public void payWithPayPal(PayItemListBean.DataBean dataBean) {
        if (model == null){
            model = new PayPalModel();
        }
        model.payWithPayPal(dataBean);
    }
}
