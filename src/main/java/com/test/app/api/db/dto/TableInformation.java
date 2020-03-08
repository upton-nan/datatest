package com.test.app.api.db.dto;

import java.util.List;

public class TableInformation {
	private String tableName;
	private List<TableColumn> list;
	@SuppressWarnings("unused")
	private Integer columnSize;
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public List<TableColumn> getList() {
		return list;
	}
	public void setList(List<TableColumn> list) {
		this.list = list;
	}
	public Integer getColumnSize() {
		if (list==null) {
			return 0;
		}
		return list.size();
	}
	@Override
	public String toString() {
		return "TableInformation [tableName=" + tableName + ", list=" + list + ", columnSize=" + getColumnSize() + "]";
	}
	
	
}
