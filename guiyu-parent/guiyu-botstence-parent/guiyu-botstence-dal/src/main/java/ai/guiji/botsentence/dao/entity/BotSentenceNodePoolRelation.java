package ai.guiji.botsentence.dao.entity;

import java.io.Serializable;
import java.util.Date;

public class BotSentenceNodePoolRelation implements Serializable {
    private String poolRelationId;

    private String nodeId;

    private String botsentenceId;

    private String processId;

    private Date crtTime;

    private String crtUser;

    private Date lstUpdateTime;

    private String lstUpdateUser;

    private static final long serialVersionUID = 1L;

    public String getPoolRelationId() {
        return poolRelationId;
    }

    public void setPoolRelationId(String poolRelationId) {
        this.poolRelationId = poolRelationId == null ? null : poolRelationId.trim();
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId == null ? null : nodeId.trim();
    }

    public String getBotsentenceId() {
        return botsentenceId;
    }

    public void setBotsentenceId(String botsentenceId) {
        this.botsentenceId = botsentenceId == null ? null : botsentenceId.trim();
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId == null ? null : processId.trim();
    }

    public Date getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(Date crtTime) {
        this.crtTime = crtTime;
    }

    public String getCrtUser() {
        return crtUser;
    }

    public void setCrtUser(String crtUser) {
        this.crtUser = crtUser == null ? null : crtUser.trim();
    }

    public Date getLstUpdateTime() {
        return lstUpdateTime;
    }

    public void setLstUpdateTime(Date lstUpdateTime) {
        this.lstUpdateTime = lstUpdateTime;
    }

    public String getLstUpdateUser() {
        return lstUpdateUser;
    }

    public void setLstUpdateUser(String lstUpdateUser) {
        this.lstUpdateUser = lstUpdateUser == null ? null : lstUpdateUser.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", poolRelationId=").append(poolRelationId);
        sb.append(", nodeId=").append(nodeId);
        sb.append(", botsentenceId=").append(botsentenceId);
        sb.append(", processId=").append(processId);
        sb.append(", crtTime=").append(crtTime);
        sb.append(", crtUser=").append(crtUser);
        sb.append(", lstUpdateTime=").append(lstUpdateTime);
        sb.append(", lstUpdateUser=").append(lstUpdateUser);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}