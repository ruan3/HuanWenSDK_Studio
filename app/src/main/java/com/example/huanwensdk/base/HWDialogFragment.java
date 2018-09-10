package com.example.huanwensdk.base;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.example.huanwensdk.R;


public class HWDialogFragment extends DialogFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.dialog_login, container, false);
		setStyle(DialogFragment.STYLE_NO_TITLE, R.style.dialog);
		return v;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Dialog dialog = super.onCreateDialog(savedInstanceState); // 设置actionbar的隐藏
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		return dialog;
	}

}
