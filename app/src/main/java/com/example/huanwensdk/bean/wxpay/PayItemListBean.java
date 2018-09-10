package com.example.huanwensdk.bean.wxpay;

import java.util.List;

/**
 * 
 * @Title:  PayItemListBean.java   
 * @Package bean.wxpay   
 * @Description:   解释返回的支付列表   
 * @author: Android_ruan     
 * @date:   2018-4-17 上午11:47:14   
 * @version V1.0
 */
public class PayItemListBean {
	  /**
     * code : 1000
     * data : [{"activeDescription":"购买即可获得1000元宝","amount":1000,"currency":"CNY","description":"1000元宝","extraJson":"","gameItemId":"C10","gamecode":"MYZN","givePoints":0,"icon":"","id":90517,"itemKey":"C10","productId":"wechat.myzn.10cny","title":"10元=1000元宝"},{"activeDescription":"","amount":3000,"currency":"CNY","description":"3000元宝","extraJson":"","gameItemId":"C30","gamecode":"MYZN","givePoints":0,"icon":"","id":90737,"itemKey":"C30","productId":"wechat.myzn.30cny","title":"30元=3000元宝"},{"activeDescription":"","amount":5000,"currency":"CNY","description":"5000元宝","extraJson":"","gameItemId":"C50","gamecode":"MYZN","givePoints":0,"icon":"","id":90747,"itemKey":"C50","productId":"wechat.myzn.50cny","title":"50元=5000元宝"},{"activeDescription":"","amount":10000,"currency":"CNY","description":"10000元宝","extraJson":"","gameItemId":"C100","gamecode":"MYZN","givePoints":0,"icon":"","id":90797,"itemKey":"C100","productId":"wechat.myzn.100cny","title":"100元=10000元宝"},{"activeDescription":"","amount":30000,"currency":"CNY","description":"30000元宝","extraJson":"","gameItemId":"C300","gamecode":"MYZN","givePoints":0,"icon":"","id":90767,"itemKey":"C300","productId":"wechat.myzn.300cny","title":"300元=30000元宝"},{"activeDescription":"","amount":50000,"currency":"CNY","description":"50000元宝","extraJson":"","gameItemId":"C500","gamecode":"MYZN","givePoints":0,"icon":"","id":90807,"itemKey":"C500","productId":"wechat.myzn.500cny","title":"500元=50000元宝"},{"activeDescription":"","amount":100000,"currency":"CNY","description":"100000元宝","extraJson":"","gameItemId":"C1000","gamecode":"MYZN","givePoints":0,"icon":"","id":90787,"itemKey":"C1000","productId":"wechat.myzn.1000cny","title":"1000元=100000元宝"},{"activeDescription":"","amount":300000,"currency":"CNY","description":"300000元宝","extraJson":"","gameItemId":"C3000","gamecode":"MYZN","givePoints":0,"icon":"","id":90817,"itemKey":"C3000","productId":"wechat.myzn.3000cny","title":"3000元=300000元宝"}]
     * message : 恭喜您，成功了！
     */

    private String code;
    private String message;
    private String point;
    public String getPoint() {
		return point;
	}

	public void setPoint(String point) {
		this.point = point;
	}



	private List<DataBean> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    
    
    @Override
	public String toString() {
		return "PayItemListBean [code=" + code + ", message=" + message
				+ ", data=" + data + "]";
	}



	public static class DataBean {
        /**
         * activeDescription : 购买即可获得1000元宝
         * amount : 1000
         * currency : CNY
         * description : 1000元宝
         * extraJson : 
         * gameItemId : C10
         * gamecode : MYZN
         * givePoints : 0
         * icon : 
         * id : 90517
         * itemKey : C10
         * productId : wechat.myzn.10cny
         * title : 10元=1000元宝
         */

        private String activeDescription;
        private float amount;
        private String currency;
        private String description;
        private String extraJson;
        private String gameItemId;
        private String gamecode;
        private int givePoints;
        private String icon;
        private int id;
        private String itemKey;
        private String productId;
        private String title;

        public String getActiveDescription() {
            return activeDescription;
        }

        public void setActiveDescription(String activeDescription) {
            this.activeDescription = activeDescription;
        }

        public float getAmount() {
            return amount;
        }

        public void setAmount(float amount) {
            this.amount = amount;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getExtraJson() {
            return extraJson;
        }

        public void setExtraJson(String extraJson) {
            this.extraJson = extraJson;
        }

        public String getGameItemId() {
            return gameItemId;
        }

        public void setGameItemId(String gameItemId) {
            this.gameItemId = gameItemId;
        }

        public String getGamecode() {
            return gamecode;
        }

        public void setGamecode(String gamecode) {
            this.gamecode = gamecode;
        }

        public int getGivePoints() {
            return givePoints;
        }

        public void setGivePoints(int givePoints) {
            this.givePoints = givePoints;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getItemKey() {
            return itemKey;
        }

        public void setItemKey(String itemKey) {
            this.itemKey = itemKey;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

		@Override
		public String toString() {
			return "DataBean [activeDescription=" + activeDescription
					+ ", amount=" + amount + ", currency=" + currency
					+ ", description=" + description + ", extraJson="
					+ extraJson + ", gameItemId=" + gameItemId + ", gamecode="
					+ gamecode + ", givePoints=" + givePoints + ", icon="
					+ icon + ", id=" + id + ", itemKey=" + itemKey
					+ ", productId=" + productId + ", title=" + title + "]";
		}
        
    }
}
