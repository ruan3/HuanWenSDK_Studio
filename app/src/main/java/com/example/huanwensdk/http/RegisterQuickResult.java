package com.example.huanwensdk.http;

public class RegisterQuickResult {
	  /**
     * id : 15217728162288
     * state : {"code":1002,"msg":"推广渠道不存在"}
     * data : {"user_id":null,"account":null,"password":null,"logintime":1521772816}
     */

    private String id;
    private StateBean state;
    private DataBean data;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public StateBean getState() {
        return state;
    }

    public void setState(StateBean state) {
        this.state = state;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class StateBean {
        /**
         * code : 1002
         * msg : 推广渠道不存在
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

        @Override
        public String toString() {
            return "StateBean{" +
                    "code=" + code +
                    ", msg='" + msg + '\'' +
                    '}';
        }
    }

    public static class DataBean {
        /**
         * user_id : null
         * account : null
         * password : null
         * logintime : 1521772816
         */

        private Object user_id;
        private Object account;
        private Object password;
        private int logintime;

        public Object getUser_id() {
            return user_id;
        }

        public void setUser_id(Object user_id) {
            this.user_id = user_id;
        }

        public Object getAccount() {
            return account;
        }

        public void setAccount(Object account) {
            this.account = account;
        }

        public Object getPassword() {
            return password;
        }

        public void setPassword(Object password) {
            this.password = password;
        }

        public int getLogintime() {
            return logintime;
        }

        public void setLogintime(int logintime) {
            this.logintime = logintime;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "user_id=" + user_id +
                    ", account=" + account +
                    ", password=" + password +
                    ", logintime=" + logintime +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "test{" +
                "id='" + id + '\'' +
                ", state=" + state +
                ", data=" + data +
                '}';
    }
	
}
