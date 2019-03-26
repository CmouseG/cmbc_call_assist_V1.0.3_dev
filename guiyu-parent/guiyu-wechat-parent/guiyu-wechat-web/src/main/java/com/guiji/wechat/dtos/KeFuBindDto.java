package com.guiji.wechat.dtos;

public class KeFuBindDto {

    private String action = "new_session";

    private Data data;

    public KeFuBindDto() {
        data = new Data();
    }

    public static KeFuBindDto build(){
        return new KeFuBindDto();
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public KeFuBindDto setDomain(String domain) {
        this.data.setDomain(domain);
        return this;
    }

    public KeFuBindDto setAccount(String account) {
        this.data.setAccount(account);
        return this;
    }

    public KeFuBindDto setOpen_id(String open_id) {
        this.data.setOpen_id(open_id);
        return this;
    }

    public static class Data{

        private String domain;

        private String account;

        private String open_id;

        public String getDomain() {
            return domain;
        }

        public void setDomain(String domain) {
            this.domain = domain;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getOpen_id() {
            return open_id;
        }

        public void setOpen_id(String open_id) {
            this.open_id = open_id;
        }
    }
}
