package com.sumCo.modules.sys.entity;

import java.util.List;

/**
 * @author oplus
 * @Description: TODO(表數據)
 * @date 2017-6-23 15:07
 */
public class SysTable {

	//表的名稱
	private String tableName;
	//表的備註
	private String comments;
	//表的主键
	private SysColumn pk;
	//表的列名(不包含主键)
	private List<SysColumn> columns;
	//類名(第一個字母大寫)，如：sys_user => SysUser
	private String className;
	//類名(第一個字母小寫)，如：sys_user => sysUser
	private String classname;
	
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public SysColumn getPk() {
		return pk;
	}

	public void setPk(SysColumn pk) {
		this.pk = pk;
	}

	public List<SysColumn> getColumns() {
		return columns;
	}

	public void setColumns(List<SysColumn> columns) {
		this.columns = columns;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

}
