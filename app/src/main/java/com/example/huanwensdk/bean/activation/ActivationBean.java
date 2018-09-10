package com.example.huanwensdk.bean.activation;

/**
 * 
 * @Title:  ActivationBean.java   
 * @Package com.example.huanwensdk.bean.activation   
 * @Description:    激活返回bean   
 * @author: Android_ruan     
 * @date:   2018-4-24 上午10:37:38   
 * @version V1.0
 */
public class ActivationBean {


    /**
     * state : {"code":1002,"msg":"请勿重复激活"}
     * times : 1524536678
     */

    private StateBean state;
    private int times;
    private DataBean data;
    private VersionController version_status;
    private String language;
    
    public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public VersionController getVersion_status() {
		return version_status;
	}

	public void setVersion_status(VersionController version_status) {
		this.version_status = version_status;
	}

	public DataBean getData() {
		return data;
	}

	public void setData(DataBean data) {
		this.data = data;
	}

	public StateBean getState() {
        return state;
    }

    public void setState(StateBean state) {
        this.state = state;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public static class StateBean {
        /**
         * code : 1002
         * msg : 请勿重复激活
         */

        private int code;
        private String msg;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }
    
    public static class DataBean {
        /**
         * code : 1002
         * msg : 请勿重复激活
         */

        private String region;
        public String getRegion() {
			return region;
		}
		public void setRegion(String region) {
			this.region = region;
		}
		public String getCountry() {
			return country;
		}
		public void setCountry(String country) {
			this.country = country;
		}
		public String getCountry_id() {
			return country_id;
		}
		public void setCountry_id(String country_id) {
			this.country_id = country_id;
		}
		private String country;
        private String country_id;

    }
    
    public static class VersionController{
    	
    	String auto_update;//
    	String login_btn_show;//
    	String update_url;//
    	String show_update;//
    	String status;//
    	String update_content;//
		public String getAuto_update() {
			return auto_update;
		}
		public void setAuto_update(String auto_update) {
			this.auto_update = auto_update;
		}
		public String getLogin_btn_show() {
			return login_btn_show;
		}
		public void setLogin_btn_show(String login_btn_show) {
			this.login_btn_show = login_btn_show;
		}
		public String getUpdate_url() {
			return update_url;
		}
		public void setUpdate_url(String update_url) {
			this.update_url = update_url;
		}
		public String getShow_update() {
			return show_update;
		}
		public void setShow_update(String show_update) {
			this.show_update = show_update;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public String getUpdate_content() {
			return update_content;
		}
		public void setUpdate_content(String update_content) {
			this.update_content = update_content;
		}
		@Override
		public String toString() {
			return "VersionController [auto_update=" + auto_update
					+ ", login_btn_show=" + login_btn_show + ", update_url="
					+ update_url + ", show_update=" + show_update + ", status="
					+ status + ", update_content=" + update_content + "]";
		}
    	
    	
    	
    }
	
}
