package com.example.huanwensdk.utils.sp;

import android.content.Context;
import android.content.SharedPreferences;

public class HWViewConfigSP {

	public static final String VIEWCONFIG = "view_config";
	private String FILE_NAME = "gd_view_config";
	private SharedPreferences.Editor editor;
	private SharedPreferences mSharedPreferences;

	public HWViewConfigSP(Context paramContext) {
		this.mSharedPreferences = paramContext.getSharedPreferences(
				this.FILE_NAME, 0);
		this.editor = this.mSharedPreferences.edit();
	}

	public String getVIEWCONFIG() {
		return this.mSharedPreferences.getString("view_config", null);
	}

	public void setViewConfig(String paramString) {
		this.editor.putString("view_config", paramString);
		this.editor.commit();
	}
}
