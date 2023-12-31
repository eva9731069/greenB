package com.sumCo.modules.sys.entity;

/**
 * @author oplus
 * @Description: TODO(列的屬性)
 * @date 2017-6-23 15:07
 */
public class SysColumn {

	//列名
    private String columnName;
    //列名類型
    private String dataType;
    //列名備註
    private String comments;
    //屬性名稱(第一個字母大寫)，如：user_name => UserName
    private String attrName;
    //屬性名稱(第一個字母小寫)，如：user_name => userName
    private String attrname;
    //屬性類型
    private String attrType;
    //auto_increment
    private String extra;
    
	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getAttrname() {
		return attrname;
	}

	public void setAttrname(String attrname) {
		this.attrname = attrname;
	}

	public String getAttrName() {
		return attrName;
	}

	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}

	public String getAttrType() {
		return attrType;
	}

	public void setAttrType(String attrType) {
		this.attrType = attrType;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

}
