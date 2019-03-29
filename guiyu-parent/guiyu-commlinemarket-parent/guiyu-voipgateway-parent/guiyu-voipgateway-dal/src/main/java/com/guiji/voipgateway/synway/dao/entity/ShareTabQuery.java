package com.guiji.voipgateway.synway.dao.entity;

/** 
* @Description: 公共表查询
* @Author: weiyunbo
* @date 2019年1月29日 下午6:07:13 
* @version V1.0  
*/
public class ShareTabQuery {
	private String tableSchema;	//名称空间
	private String tableNamePostfix;	//表名
	
	/**
	 * @return the tableSchema
	 */
	public String getTableSchema() {
		return tableSchema;
	}
	/**
	 * @param tableSchema the tableSchema to set
	 */
	public void setTableSchema(String tableSchema) {
		this.tableSchema = tableSchema;
	}
	/**
	 * @return the tableNamePostfix
	 */
	public String getTableNamePostfix() {
		return tableNamePostfix;
	}
	/**
	 * @param tableNamePostfix the tableNamePostfix to set
	 */
	public void setTableNamePostfix(String tableNamePostfix) {
		this.tableNamePostfix = tableNamePostfix;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ShareTabQuery [tableSchema=" + tableSchema + ", tableNamePostfix=" + tableNamePostfix + "]";
	}
	
}
