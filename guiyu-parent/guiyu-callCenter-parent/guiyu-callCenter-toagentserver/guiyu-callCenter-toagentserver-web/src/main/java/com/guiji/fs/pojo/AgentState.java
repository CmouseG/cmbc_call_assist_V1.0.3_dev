package com.guiji.fs.pojo;

/**
 * 座席状态
 */
public enum AgentState {
    /**
     * 离线
     */
    Offline("Offline"),

    /**
     * 签入状态(已登入)，随时可以接听电话
     */
    CheckIn("Waiting"),

    /**
     * 签出状态（未登出），不再接听电话
     */
    CheckOut("Checkout"),

    /**
     * 通话中
     */
    InCall("In a queue call"),

    /**
     * 振铃中
     */
    InProgress("Receiving"),

    /**
     * 小休，停止接听电话
     */
    OnBreak("On Break"),

    /**
     * 其他状态
     */
    None("None");

    private String val;

    AgentState(String val){
        this.val = val;
    }

    /**
     * 根据字符串，获取State
     * @return
     */
    public static AgentState getState(String val){
        for (AgentState state : AgentState.values()) {
            if(state.val.equals(val)){
                return state;
            }
        }

        return AgentState.None;
    }

    public static void main(String[] args) {
        System.out.println(AgentState.getState("On Break"));
        System.out.println(AgentState.getState("Receiving"));
        System.out.println(AgentState.getState("In a queue call"));
        System.out.println(AgentState.getState("offline"));
    }
}
