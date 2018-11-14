package com.example.huanwensdk.utils;

import java.io.File;

import android.os.Environment;
import android.os.Handler;

public class Constant {
	
	public static final String SKD_VERSION = "1.0";
	
//	public static final String header="http://interface.sdkse.huanwenyx.com/";
//	public static final String header="http://interface.sdk.huanwenyx.com/";

	public static final String header="http://wuziqi.fangame.com.tw/";

	//隐私政策url
	public static final String PRIVACY_URL = "http://www.galaxystargame.com/privacy_policy.html";
	
	//试玩地址
//	public static final String HW_LOGIN_TRIAL = "http://login.fangame.com.tw/user/tourLogin";
	
	public static final String HW_LOGIN_TRIAL = header+"interface.php?c=IHwGame&m=ITourLogin";
	
	//注册地址
//	public static final String HW_REGISTER_URL = "http://login.fangame.com.tw/user/regist";
	public static final String HW_REGISTER_URL = header+"interface.php?c=IHwGame&m=IRegister";
	
	//登录
//	public static final String HW_LOGIN_URL ="http://login.fangame.com.tw/user/phEmailLogin";
	public static final String HW_LOGIN_URL = header+"interface.php?c=IHwGame&m=ILogin";
	
	//忘记密码
//	public static final String HW_FORGET_SEND_EMAIL = "http://login.fangame.com.tw/user/forgetPwdMail";
	public static final String HW_FORGET_SEND_EMAIL = header+"interface.php?c=IHwGame&m=IForgetPaw";
	
	//验证邮箱
//	public static final String HW_FORGET_VALID_EMAIL = "http://login.fangame.com.tw/user/validmail";
	public static final String HW_FORGET_VALID_EMAIL = header+"interface.php?c=IHwGame&m=IForgetPawSuccess";
	
	//重设密码
//	public static final String HW_RESET_PASSWORD = "http://login.fangame.com.tw/user/forgotPwd";
	public static final String HW_RESET_PASSWORD = header+"interface.php?c=IHwGame&m=IForgetPawUpdate";
	
	//试玩账号找回
//	public static final String HW_CHECK_UID = "http://login.fangame.com.tw/user/checkTouristPwd";
	public static final String HW_CHECK_UID = header+"interface.php?c=IHwGame&m=ICheckTouristPwd";
	
	//绑定邮箱账号
//	public static final String HW_BING_EMAIL = "http://login.fangame.com.tw/user/forgotNEmailBind";
	public static final String HW_BING_EMAIL = header+"interface.php?c=IHwGame&m=IBinding";
	
	//自动登录
//	public static final String HW_LOGIN_FREE = "http://login.fangame.com.tw/user/freeLogin";
	public static final String HW_LOGIN_FREE = header+"interface.php?c=IHwGame&m=IFreeLogin";
	
	//检查服务器
	public static final String HW_CHECK_SERVER = "http://base.fangame.com.tw/server/check";
	
	//微信支付appid
	public static final String WX_APP_ID = "wx56eaa654117c394f";//wxb4ba3c02aa476ea1   wx43971aff0a6a58d2   银河星的wx6e0f29b23e120854

	//支付列表
//	public static final String HW_PAY_LIST = "http://base.fangame.com.tw/item/get/itemList";
	public static final String HW_PAY_LIST = header+"interface.php?c=IHwItems";
	
//	public static final String HW_GET_WECHAT_ORDER = "http://iap.fangame.com.tw/wechat/order";
	public static final String HW_GET_WECHAT_ORDER = header+"interface.php?c=IHwOrder&m=WxOrder";
	
//	public static final String HW_GET_ALI_ORDER = "http://iap.fangame.com.tw/aliPay/order";
	public static final String HW_GET_ALI_ORDER = header+"interface.php?c=IHwOrder&m=AlipayOrder";
	
	//激活接口
	public static final String HW_ACTIVATION = header+"interface.php?c=IHwGame&m=IActivation";
	
	//支付双向验证
	public static final String HW_CHECK_PAY = header+"interface.php?c=IHwShip";
	
	//检查平台币是否够支付
	public static final String HW_CHECK_COIN = header+"interface.php?c=IHwPoint&m=gamePoint";
	
	public static final String HW_PAY_WITH_COIN = header+"interface.php?c=IHwPoint&m=gamePointExchange";
	
	//登录绑定接口
	public static final String HW_LOGIN_BIND_EMAIL = "http://login.fangame.com.tw/user/emailBind";
	
	//获取支付方式列表
//	public static final String HW_GET_PAY_LIST = "http://base.fangame.com.tw/payChannel/getGamePayChannel";
	public static final String HW_GET_PAY_LIST = header+"interface.php?c=IHwGamePayChannel";
	
//	public static final String HW_GET_ITEM_LIST = "http://base.fangame.com.tw/item/get/itemList";
	
	//谷歌订单信息
//	public static final String HW_GET_GOOGLE_ORDER = "http://iap.fangame.com.tw/google/order";
	
	public static final String HW_GET_GOOGLE_ORDER = header+"interface.php?c=IHwOrder&m=GoogleOrder";
	
	//谷歌双验证
//	public static final String HW_PAY = "http://iap.fangame.com.tw/google/pay";
	public static final String HW_PAY = header+"interface.php?c=IHwGoogleShip";
	
	//获取订单
//	public static final String GET_STORES = "http://base.fangame.com.tw/data/getStoreList";
	
	public static final String WX_APP_SECRET = "dc3f46cf31563eceaa89c61fcd7f3d22";
	
	public static final String GET_STORES = header+"IHwService/orderInfo";
	
	//第三方登录接口
	public static final String Third_Party_Login = header+"interface.php?c=IHwGame&m=ISignInWith";
	
	//保存角色
	public static final String HW_SAVE_ROLE = header+"interface.php?c=IHwRole";
	public static final String HW_APP_KEY = "HWGAME#_DR*)HW";

	//PayPal的clientId
	public static final String PAYPAL_CLIENT_ID = "AYrfroV0vE1CzCrozLQX9prp5KOe9UKLYK_00vtR3znN8jYwiHPkg8dlbwOpBxaRNQck3KzqUlhsKcWx";

	public static String GAME_FILE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "fangame" + File.separator;
}
