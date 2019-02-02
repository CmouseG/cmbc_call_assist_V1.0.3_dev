package com.guiji.notice.entity;

import java.io.Serializable;

public class SettingIntent implements Serializable {

    private static final long serialVersionUID = 1L;

    private String intent;
    private Boolean selected;

    public String getIntent() {
        return intent;
    }

    public void setIntent(String intent) {
        this.intent = intent;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }
}
