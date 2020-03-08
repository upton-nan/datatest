package com.test.app.api.db;

import java.util.List;
import java.util.Map;

import com.test.app.api.db.dto.TableColumn;
import com.test.app.api.db.dto.TableInformation;

public class SqlScriptProduce {

	public String createSqlScript(TableInformation tableInformation, TestDataProduction testDataProduction) {
		Integer dataSize = 20;
		// key是字段名，list是字段数据
		Map<String, List<Object>> produceDataListMap = testDataProduction.produceDataList(tableInformation, dataSize);
		StringBuffer prefix = getPrefix(tableInformation);
		List<TableColumn> list = tableInformation.getList();
		for (int i = 0; i < dataSize; i++) {
			StringBuffer record = new StringBuffer();
			record.append("(");
			for (TableColumn tableColumn : list) {
				if (record.length() != 1) {
					record.append(",");
				}
				String columnDataStr=getSqlTypeColumnStr(tableColumn,produceDataListMap,i);
				record.append(columnDataStr);
			}
			record.append(")");
			record.append(",");
			prefix.append(record);
			prefix.append("\n");
		}
		prefix.setLength(prefix.length() - 2);
		prefix.append(";");
		prefix.append("\n");
		return prefix.toString();
	}

	/**
	 * 
	 * @Title: getSqlTypeColumnStr
	 * @Description: 获取字段的数据信息所对应的类型格式添加到sql脚本
	 * @param tableColumn
	 * @param produceDataListMap
	 * @return
	 */
	private String getSqlTypeColumnStr(TableColumn tableColumn, Map<String, List<Object>> produceDataListMap,Integer index) {
		String sqlType = tableColumn.getSqlTypeName();
		String columnName = tableColumn.getColumnName();
		List<Object> list = produceDataListMap.get(columnName);
		Object dataObject = list.get(index);
		if (sqlType.equalsIgnoreCase("bit")||sqlType.equalsIgnoreCase("tinyint")
				||sqlType.equalsIgnoreCase("smallint")
				||sqlType.equalsIgnoreCase("int")
				||sqlType.equalsIgnoreCase("bigint")
				||sqlType.equalsIgnoreCase("float")||sqlType.equalsIgnoreCase("decimal") || sqlType.equalsIgnoreCase("numeric")
				|| sqlType.equalsIgnoreCase("real") || sqlType.equalsIgnoreCase("money")
				|| sqlType.equalsIgnoreCase("smallmoney")) {
			//不用加引号
			return dataObject.toString();
		} else if (sqlType.equalsIgnoreCase("varchar") || sqlType.equalsIgnoreCase("char")
				|| sqlType.equalsIgnoreCase("nvarchar") || sqlType.equalsIgnoreCase("nchar")
				|| sqlType.equalsIgnoreCase("text")
				||sqlType.equalsIgnoreCase("datetime") 
				|| sqlType.equalsIgnoreCase("date")
				||sqlType.equalsIgnoreCase("image")
				||sqlType.equalsIgnoreCase("timestamp")) {
			//需要加引号
			return "'" + dataObject.toString() + "'";
		}
		return null;
	}

	/**
	 * 
	 * @Title: getPrefix
	 * @Description: 获取sql插入前缀部分insert
	 * @param tableInformation
	 * @return
	 */
	private StringBuffer getPrefix(TableInformation tableInformation) {
		String tableName = tableInformation.getTableName();
		List<TableColumn> list = tableInformation.getList();
		StringBuffer tableColumns = getTableColumns(list);
		StringBuffer sb = new StringBuffer();
		sb.append("insert into ").append(tableName).append("(").append(tableColumns).append(")").append(" values")
				.append("\n");
		return sb;
	}

	/**
	 * 
	 * @Title: getTableColumns
	 * @Description: 获取表格字段名称拼接
	 * @param list
	 * @return
	 */
	private StringBuffer getTableColumns(List<TableColumn> list) {
		StringBuffer sb = new StringBuffer();
		for (TableColumn tableColumn : list) {
			if (sb.length() != 0) {
				sb.append(",");
			}
			sb.append(tableColumn.getColumnName());
		}
		return sb;
	}

}
