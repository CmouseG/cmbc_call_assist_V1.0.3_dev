package ai.guiji.botsentence.crm.dao.entity;

import java.io.Serializable;
import java.util.Date;

public class TabTemp implements Serializable {
    private Integer id;

    private String name;

    private String keyStr;

    private String des;

    private Date addtime;

    private Boolean publish;

    private String tradeId;

    private Byte isTts;

    private String file;

    private String options;

    private Integer verify;

    private Integer buildbycustomer;

    private Integer version;

    private Date edittime;

    private String operate;

    private String userLabelRules;

    private byte[] simDict;

    private byte[] selectjson;

    private byte[] simdict;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getKeyStr() {
        return keyStr;
    }

    public void setKeyStr(String keyStr) {
        this.keyStr = keyStr == null ? null : keyStr.trim();
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des == null ? null : des.trim();
    }

    public Date getAddtime() {
        return addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    public Boolean getPublish() {
        return publish;
    }

    public void setPublish(Boolean publish) {
        this.publish = publish;
    }

    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId == null ? null : tradeId.trim();
    }

    public Byte getIsTts() {
        return isTts;
    }

    public void setIsTts(Byte isTts) {
        this.isTts = isTts;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file == null ? null : file.trim();
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options == null ? null : options.trim();
    }

    public Integer getVerify() {
        return verify;
    }

    public void setVerify(Integer verify) {
        this.verify = verify;
    }

    public Integer getBuildbycustomer() {
        return buildbycustomer;
    }

    public void setBuildbycustomer(Integer buildbycustomer) {
        this.buildbycustomer = buildbycustomer;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Date getEdittime() {
        return edittime;
    }

    public void setEdittime(Date edittime) {
        this.edittime = edittime;
    }

    public String getOperate() {
        return operate;
    }

    public void setOperate(String operate) {
        this.operate = operate == null ? null : operate.trim();
    }

    public String getUserLabelRules() {
        return userLabelRules;
    }

    public void setUserLabelRules(String userLabelRules) {
        this.userLabelRules = userLabelRules == null ? null : userLabelRules.trim();
    }

    public byte[] getSimDict() {
        return simDict;
    }

    public void setSimDict(byte[] simDict) {
        this.simDict = simDict;
    }

    public byte[] getSelectjson() {
        return selectjson;
    }

    public void setSelectjson(byte[] selectjson) {
        this.selectjson = selectjson;
    }

    public byte[] getSimdict() {
        return simdict;
    }

    public void setSimdict(byte[] simdict) {
        this.simdict = simdict;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", keyStr=").append(keyStr);
        sb.append(", des=").append(des);
        sb.append(", addtime=").append(addtime);
        sb.append(", publish=").append(publish);
        sb.append(", tradeId=").append(tradeId);
        sb.append(", isTts=").append(isTts);
        sb.append(", file=").append(file);
        sb.append(", options=").append(options);
        sb.append(", verify=").append(verify);
        sb.append(", buildbycustomer=").append(buildbycustomer);
        sb.append(", version=").append(version);
        sb.append(", edittime=").append(edittime);
        sb.append(", operate=").append(operate);
        sb.append(", userLabelRules=").append(userLabelRules);
        sb.append(", simDict=").append(simDict);
        sb.append(", selectjson=").append(selectjson);
        sb.append(", simdict=").append(simdict);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}