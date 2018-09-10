package com.example.huanwensdk.utils.sp;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.huanwensdk.utils.LogUtils;

/**
 * 
 * @Title: HWConfigSharedPreferences.java
 * @Package sp
 * @Description: 使用xml文件保存，SDK的基本配置
 * @author: Android_ruan
 * @date: 2018-3-21 上午10:42:27
 * @version V1.0
 */
public class HWConfigSharedPreferences {

	private SharedPreferences mSharedPreferences;
	private SharedPreferences.Editor editor;
	public static final String IS_FIRST = "is_first";//是否第一次
	public static final String LOGIN_USERID = "loginUserId";//用户id；
	private String FILE_NAME = "hw_config_sp";// 文件名称
	public static final String IS_LOGIN_FREE = "is_login_free";// 是否自动登录
	public static final String SDK_VERSION = "sdk_version";// SDK的版本号
	public static final String IS_SHOW_AUTHOR = "is_show_author";// 是否显示第三方登录
	public static final String LANGUAGE = "language";// SDK使用的语言
	public static final String IS_TRIAL_SHOW_WRNNING = "warnning";// 显示警告
	public static final String PARAMS = "params";// 参数
	public static final String NOTICE_URL = "noticeUrl";// 提示url
	public static final String GPPAP_ENABLE = "gpPayEnable";
	public static final String FG_URL_TYPE = "gdUrlType";
	public static final String IS_USE_OUR_SERVER_LIST = "isUseOurServerList";// 是否是使用我们自己的服务器
	public static final String COUNTRY = "country";
	public static final String ROLE_ID = "role_id";
	public static final String SERVER_CODE = "server_code";
	public static final String ROLE_LEVEL = "role_level";
	public static final String IS_SHOW_UPDATE = "is_show_update";//是否显示更新
	public static final String PUBLIC_KEY = "publicKey";//谷歌支付的公钥PUBLIC_KEY
	public static final String PRODUCTID = "productId";//谷歌自带的productId
	
	public static final String ORDERID = "orderid";//订单号
	
	
	public static final String SHOW_PAY = "show_pay";//是否显示第三方支付
	public static final String APP_STATUS = "app_status";//app版本状态
	
	public static HWConfigSharedPreferences hwConfigSharedPreferences;
	
	public HWConfigSharedPreferences(Context context) {

		mSharedPreferences = context.getSharedPreferences(FILE_NAME,
				Context.MODE_PRIVATE);// 创建xml文件
		editor = mSharedPreferences.edit();

	}
	
	/**
	 * 单例模式
	 * @param context
	 * @return
	 */
	public static HWConfigSharedPreferences getInstance(Context context){
		
		if(hwConfigSharedPreferences == null){
			hwConfigSharedPreferences = new HWConfigSharedPreferences(context);
		}
		
		return hwConfigSharedPreferences;
		
	}

	public boolean isLoginFree() {
		return mSharedPreferences.getBoolean(IS_LOGIN_FREE, false);
	}

	public void setLoginFree(boolean isLoginFree) {

		editor.putBoolean(IS_LOGIN_FREE, isLoginFree);
		editor.commit();

	}
	
	/**
	 * 获取谷歌productid
	 * @return
	 */
	public String getGPproductId(){
		return mSharedPreferences.getString(PRODUCTID, "");
	}
	
	/**
	 * 存入谷歌productId
	 * @param publicKey
	 */
	public void setGPproductId(String productId){
		editor.putString(PRODUCTID, productId);
		editor.commit();
	}
	
	/**
	 * 获取谷歌支付的公钥
	 * @return
	 */
	public String getGPPublicKey(){
		return mSharedPreferences.getString(PUBLIC_KEY, "");
	}
	
	/**
	 * 存入谷歌支付的公钥
	 * @param publicKey
	 */
	public void setGPPublicKey(String publicKey){
		editor.putString(PUBLIC_KEY, publicKey);
		editor.commit();
	}
	
	/**
	 * 获取缓存订单号，如果获取到缓存的订单号，就证明了用户已支付，但是sdk内部没有接受到回调
	 * @return
	 */
	public String getOrderId(){
		return mSharedPreferences.getString(ORDERID, "");
	}
	
	/**
	 * 设置订单号，在调起支付的时候保存orderid
	 * @param orderId
	 */
	public void setOrderId(String orderId){
		editor.putString(ORDERID, orderId);
		editor.commit();
	}
	
	/**
	 * 是否已经激活
	 * @return
	 */
	public boolean Activation(){
		return mSharedPreferences.getBoolean(IS_FIRST, false);
	}

	/**
	 * 是否显示第三方登录
	 * 
	 * @return
	 */
	public boolean isShowAuthorLogin() {

		return mSharedPreferences.getBoolean(IS_SHOW_AUTHOR, false);

	}
	
	/**
	 * 是否显示更新
	 * @return
	 */
	public boolean isShowUpdate(){
		return mSharedPreferences.getBoolean(IS_SHOW_UPDATE, false);
	}
	
	/**
	 * app状态
	 * @param roleId
	 */
	public void setAppStatus(String status){
		
		LogUtils.e("status--->"+status);
		editor.putString(APP_STATUS, status);
		editor.commit();
		
	}
	
	/**
	 * app状态
	 * @return
	 */
	public String getAppStatus(){
		
		return mSharedPreferences.getString(APP_STATUS, "");
		
	}
	
	/**
	 * 是否显示第三方支付
	 * @param roleId
	 */
	public void setShowPay(String showPay){
		
		LogUtils.e("role--->"+showPay);
		editor.putString(SHOW_PAY, showPay);
		editor.commit();
		
	}
	
	/**
	 * 获取是否显示第三方支付
	 * @return
	 */
	public String getShowPay(){
		
		return mSharedPreferences.getString(SHOW_PAY, "");
		
	}
	
	/**
	 * 设置角色id
	 * @param roleId
	 */
	public void setRoleId(String roleId){
		
		LogUtils.e("role--->"+roleId);
		editor.putString(ROLE_ID, roleId);
		editor.commit();
		
	}
	
	/**
	 * 获取角色id
	 * @return
	 */
	public String getRoleId(){
		
		return mSharedPreferences.getString(ROLE_ID, "");
		
	}
	
	/**
	 * 设置服务器代码
	 * @param roleId
	 */
	public void setServerCode(String serverCode){
		
		LogUtils.e("role--->"+serverCode);
		editor.putString(SERVER_CODE, serverCode);
		editor.commit();
		
	}
	
	/**
	 * 获取角色id
	 * @return
	 */
	public String getServerCode(){
		
		return mSharedPreferences.getString(SERVER_CODE, "");
		
	}
	
	/**
	 * 设置服务器代码
	 * @param roleId
	 */
	public void setRoleLevel(String roleLevel){
		
		LogUtils.e("role--->"+roleLevel);
		editor.putString(ROLE_LEVEL, roleLevel);
		editor.commit();
		
	}
	
	/**
	 * 获取角色id
	 * @return
	 */
	public String getRoleLevel(){
		
		return mSharedPreferences.getString(ROLE_LEVEL, "");
		
	}
	
	/**
	 * 设置用户userID
	 * @param UserId
	 */
	public void setUserId(String UserId){
		LogUtils.e("UserID----->"+UserId);
		editor.putString(LOGIN_USERID, UserId);
		editor.commit();
		
	}
	
	/**
	 * 设置地区
	 * @param country
	 */
	public void setCountry(String country){
		LogUtils.e("country----->"+country);
		editor.putString(COUNTRY, country);
		editor.commit();
	}
	
	/**
	 * 获取地区
	 * @return
	 */
	public String getCountry(){
		return mSharedPreferences.getString(COUNTRY, "");
	}

	/**
	 * 获取用户UserID;
	 * @return
	 */
	public String getUserId(){
		
		return mSharedPreferences.getString(LOGIN_USERID, "");
		
		
	}
	
	/**
	 * 设置显示第三方登录
	 * 
	 * @param isShowAuthorLogin
	 */
	public void setShowAuthorLogin(boolean isShowAuthorLogin) {

		editor.putBoolean(IS_SHOW_AUTHOR, isShowAuthorLogin);
		editor.commit();
	}
	
	/**
	 * 设置显示更新
	 * @param isShowUpdate
	 */
	public void setShowUpdate(boolean isShowUpdate){
		editor.putBoolean(IS_SHOW_UPDATE, isShowUpdate);
		editor.commit();
	}
	
	/**
	 * 设置是否第一次激活
	 * @param isActivation
	 */
	public void setActivation(boolean isActivation){
		editor.putBoolean(IS_FIRST, isActivation);
		editor.commit();
	}

	/**
	 * 设置是否使用sdk伺服器列表页面
	 * 
	 * @return
	 */
	public boolean getIsUseOurServerList() {
		return this.mSharedPreferences.getBoolean(IS_USE_OUR_SERVER_LIST, true);
	}

	/**
	 * 如果游戏使用我方提供的伺服器，则设置为1；否则设置为0。默认为1。
	 * 
	 * @param isUseOurServerList
	 */
	public void setIsUseOurServerList(boolean isUseOurServerList) {
		this.editor.putBoolean(IS_USE_OUR_SERVER_LIST, isUseOurServerList);
		this.editor.commit();
	}

	/**
	 * 获取SDK版本
	 * 
	 * @return
	 */
	public float getSdkVersion() {
		return this.mSharedPreferences.getFloat(SDK_VERSION, -1);
	}

	/**
	 * 设置SDK版本
	 * 
	 * @param sdkVersion
	 */
	public void setSdkVersion(float sdkVersion) {
		this.editor.putFloat(SDK_VERSION, sdkVersion);
		this.editor.commit();
	}

	/**
	 * 获取语言
	 * 
	 * @return
	 */
	public String getLanguage() {
		return this.mSharedPreferences.getString(LANGUAGE, null);
	}

	/**
	 * 设置语言
	 * 
	 * @param language
	 */
	public void setLanguage(String language) {
		this.editor.putString(LANGUAGE, language);
		this.editor.commit();
	}

	public void removeLanguage() {
		this.editor.remove(LANGUAGE);
		this.editor.commit();
	}

	/**
	 * 是否显示游客登录后，提醒
	 * @return
	 */
	public boolean getIsShowTrialWarnning() {
		return this.mSharedPreferences.getBoolean(IS_TRIAL_SHOW_WRNNING, false);
	}

	/**
	 * 设置游客登录提示
	 * @param isShowTrialWarnning
	 */
	public void setIsShowTrialWarnning(boolean isShowTrialWarnning) {
		this.editor.putBoolean(IS_TRIAL_SHOW_WRNNING, isShowTrialWarnning);
		this.editor.commit();
	}

	/**
	 * 获取参数
	 * @return
	 */
	public String getPluginParams() {
		return this.mSharedPreferences.getString(PARAMS, null);
	}

	/**
	 * 设置参数
	 * @param params
	 */
	public void setPluginParams(String params) {
		this.editor.putString(PARAMS, params);
		this.editor.commit();
	}

	/**
	 * 获取提示url？
	 * @return
	 */
	public String getNoticeUrl() {
		return this.mSharedPreferences.getString("noticeUrl", "");
	}

	public void setNoticeUrl(String noticeUrl) {
		this.editor.putString("noticeUrl", noticeUrl);
		this.editor.commit();
	}

	/**
	 * 是否有谷歌支付渠道
	 * @return
	 */
	public boolean getGPPayEnable() {
		return this.mSharedPreferences.getBoolean("gpPayEnable", true);
	}

	/**
	 * 这是是否有谷歌支付渠道
	 * @param gpPayEnable
	 */
	public void setGPPayEnable(boolean gpPayEnable) {
		this.editor.putBoolean("gpPayEnable", gpPayEnable);
		this.editor.commit();
	}

	public int getGDUrlType() {
		return this.mSharedPreferences.getInt("gdUrlType", 0);
	}

	public void setGDUrlType(int gdUrlType) {
		this.editor.putInt("gdUrlType", gdUrlType);
		this.editor.commit();
	}
}
