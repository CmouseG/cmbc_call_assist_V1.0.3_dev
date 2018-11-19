package ai.guiji.botsentence.vo;

public class AutoDeployExecuteVo {
	
	private String code;
	private String module="cfgs";
	private String version;
	private String path;
	private String equipment="nj";
	private int pre_download=-3;
	private String command;
	
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getEquipment() {
		return equipment;
	}
	public void setEquipment(String equipment) {
		this.equipment = equipment;
	}
	public int getPre_download() {
		return pre_download;
	}
	public void setPre_download(int pre_download) {
		this.pre_download = pre_download;
	}
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = "python up_cfgs.py "+command;
	}

}
