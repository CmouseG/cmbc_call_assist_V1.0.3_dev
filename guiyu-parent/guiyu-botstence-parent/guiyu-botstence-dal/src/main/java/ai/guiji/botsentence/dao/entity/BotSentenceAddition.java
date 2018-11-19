package ai.guiji.botsentence.dao.entity;

import java.io.Serializable;

public class BotSentenceAddition implements Serializable {
    private String processId;

    private String templateJson;

    private String weightsTxt;

    private String optionsJson;

    private String stopwordsTxt;

    private String userdictTxt;

    private String simTxt;

    private static final long serialVersionUID = 1L;

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId == null ? null : processId.trim();
    }

    public String getTemplateJson() {
        return templateJson;
    }

    public void setTemplateJson(String templateJson) {
        this.templateJson = templateJson == null ? null : templateJson.trim();
    }

    public String getWeightsTxt() {
        return weightsTxt;
    }

    public void setWeightsTxt(String weightsTxt) {
        this.weightsTxt = weightsTxt == null ? null : weightsTxt.trim();
    }

    public String getOptionsJson() {
        return optionsJson;
    }

    public void setOptionsJson(String optionsJson) {
        this.optionsJson = optionsJson == null ? null : optionsJson.trim();
    }

    public String getStopwordsTxt() {
        return stopwordsTxt;
    }

    public void setStopwordsTxt(String stopwordsTxt) {
        this.stopwordsTxt = stopwordsTxt == null ? null : stopwordsTxt.trim();
    }

    public String getUserdictTxt() {
        return userdictTxt;
    }

    public void setUserdictTxt(String userdictTxt) {
        this.userdictTxt = userdictTxt == null ? null : userdictTxt.trim();
    }

    public String getSimTxt() {
        return simTxt;
    }

    public void setSimTxt(String simTxt) {
        this.simTxt = simTxt == null ? null : simTxt.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", processId=").append(processId);
        sb.append(", templateJson=").append(templateJson);
        sb.append(", weightsTxt=").append(weightsTxt);
        sb.append(", optionsJson=").append(optionsJson);
        sb.append(", stopwordsTxt=").append(stopwordsTxt);
        sb.append(", userdictTxt=").append(userdictTxt);
        sb.append(", simTxt=").append(simTxt);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}