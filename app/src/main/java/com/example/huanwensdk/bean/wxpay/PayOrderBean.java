package com.example.huanwensdk.bean.wxpay;

import com.google.gson.annotations.SerializedName;

/**
 * 
 * @Title:  PayOrderBean.java   
 * @Package bean.wxpay   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: Android_ruan     
 * @date:   2018-4-17 下午2:50:54   
 * @version V1.0
 */
public class PayOrderBean {

	 /**
     * code : 1000
     * data : {"appid":"wx6e0f29b23e120854","noncestr":"3838fd1e3ac74d0ea2a0f3c3876f1ed4","package":"Sign=WXPay","partnerid":"1497321492","prepayid":"wx1714271009608532093866251590677867","sign":"DB997911F24F4174C3CD712B470D9804","timestamp":"1523946430"}
     * message : 恭喜您，成功了！
     * orderid : wp41411897657542802607ITD
     */

    private String code;
    private String data;
    private String message;
    private String orderid;
    private Data order;

    public Data getOrder() {
		return order;
	}

	public void setOrder(Data order) {
		this.order = order;
	}

	public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }
    
    

    @Override
	public String toString() {
		return "PayOrderBean [code=" + code + ", data=" + data + ", message="
				+ message + ", orderid=" + orderid + "]";
	}



	public class Data{


        /**
         * appid : wx6e0f29b23e120854
         * noncestr : 3838fd1e3ac74d0ea2a0f3c3876f1ed4
         * package : Sign=WXPay
         * partnerid : 1497321492
         * prepayid : wx1714271009608532093866251590677867
         * sign : DB997911F24F4174C3CD712B470D9804
         * timestamp : 1523946430
         */

        private String appid;
        private String noncestr;
        @SerializedName("package")
        private String packageX;
        private String partnerid;
        private String prepayid;
        private String sign;
        private String timestamp;

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public String getPackageX() {
            return packageX;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

		@Override
		public String toString() {
			return "Data [appid=" + appid + ", noncestr=" + noncestr
					+ ", packageX=" + packageX + ", partnerid=" + partnerid
					+ ", prepayid=" + prepayid + ", sign=" + sign
					+ ", timestamp=" + timestamp + "]";
		}
        
    }
	
}
