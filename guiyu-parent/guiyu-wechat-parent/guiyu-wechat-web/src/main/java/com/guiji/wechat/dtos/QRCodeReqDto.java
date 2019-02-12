package com.guiji.wechat.dtos;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class QRCodeReqDto implements Serializable {

    private static final long serialVersionUID = 6324136394910494629L;
    /**
     * 该二维码有效时间，以秒为单位。
     * 默认为2分钟
     */
    private long expire_seconds = 600L;

    /**
     * 二维码类型，
     * QR_SCENE为临时的整型参数值
     * QR_STR_SCENE为临时的字符串参数值
     * QR_LIMIT_SCENE为永久的整型参数值
     * QR_LIMIT_STR_SCENE为永久的字符串参数值
     */
    private String action_name = "QR_STR_SCENE";

    /**
     * 二维码详细信息
     */
    private ActionInfo action_info;


    public long getExpire_seconds() {
        return expire_seconds;
    }

    public void setExpire_seconds(long expire_seconds) {
        this.expire_seconds = expire_seconds;
    }

    public String getAction_name() {
        return action_name;
    }

    public void setAction_name(String action_name) {
        this.action_name = action_name;
    }

    public ActionInfo getAction_info() {
        return action_info;
    }

    public void setAction_info(ActionInfo action_info) {
        this.action_info = action_info;
    }

    public void setPenetrateParameter(String penetrateParameter) {
        Scene scene = new Scene(penetrateParameter);
        setAction_info(new ActionInfo(scene));
    }

    public static class ActionInfo {

        private Scene scene;

        public ActionInfo() {
        }

        public ActionInfo(Scene scene) {
            this.scene = scene;
        }

        public Scene getScene() {
            return scene;
        }

        public void setScene(Scene scene) {
            this.scene = scene;
        }
    }

    public static class Scene {
        /**
         * 场景值ID（字符串形式的ID），字符串类型，长度限制为1到64
         */
        private String scene_str;

        public Scene() {
        }

        public Scene(String scene_str) {
            this.scene_str = scene_str;
        }

        public String getScene_str() {
            return scene_str;
        }

        public void setScene_str(String scene_str) {
            this.scene_str = scene_str;
        }
    }
}




