package com.example.huanwensdk.ui.dialog;

import android.view.View;
import android.widget.Button;

import com.example.huanwensdk.utils.ResLoader;

public class PayExplainDialog extends DialogBase {

    Button btn_back;

    private PayExplainDialog(){

        initView(ResLoader.getLayout(context,"dialog_pay_explain"));

    }

    private static class PayExplainDialogHelper{

        private static PayExplainDialog payExplainDialog = new PayExplainDialog();

    }

    public static PayExplainDialog getInstance(){

        return PayExplainDialogHelper.payExplainDialog;

    }

    @Override
    public void initView(int ContentId) {
        super.initView(ContentId);
        btn_back = dialog.findViewById(ResLoader.getId(context,"btn_back"));

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                close();
            }
        });
    }

    @Override
    public void show() {

        if (dialog.isShowing()){
            dialog.dismiss();
        }
        dialog.show();

    }

    @Override
    public void close() {

        if(dialog.isShowing()){
            dialog.dismiss();
        }

    }
}
