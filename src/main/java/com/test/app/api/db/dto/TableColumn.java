package com.test.app.api.db.dto;

import java.io.Serializable;

public class TableColumn implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//字段名
	private String columnName;
	//数据库类型
	private String sqlTypeName;
	//java类型
	private String javaTypeName;
	//数据库注释
	private String sqlcolumnNote;
	//是否是主键
	private Boolean isPrimarykey;
	
	@Override
	public String toString() {
		return "TableColumn [columnName=" + columnName + ", sqlTypeName=" + sqlTypeName + ", javaTypeName="
				+ javaTypeName + ", sqlcolumnNote=" + sqlcolumnNote + ", isPrimarykey=" + isPrimarykey + "]";
	}
	public Boolean getIsPrimarykey() {
		return isPrimarykey;
	}
	public void setIsPrimarykey(Boolean isPrimarykey) {
		this.isPrimarykey = isPrimarykey;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getSqlTypeName() {
		return sqlTypeName;
	}
	public void setSqlTypeName(String sqlTypeName) {
		this.sqlTypeName = sqlTypeName;
	}
	public String getSqlcolumnNote() {
		return sqlcolumnNote;
	}
	public void setSqlcolumnNote(String sqlcolumnNote) {
		this.sqlcolumnNote = sqlcolumnNote;
	}
	public String getJavaTypeName() {
		return javaTypeName;
	}
	public void setJavaTypeName(String javaTypeName) {
		this.javaTypeName = javaTypeName;
	}
	
	
	
}
