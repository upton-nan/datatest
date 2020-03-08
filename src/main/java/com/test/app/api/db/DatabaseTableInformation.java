package com.test.app.api.db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.test.app.api.db.dto.TableColumn;
import com.test.app.api.db.dto.TableInformation;

/**
 * 
 * @ClassName: DatabaseTableInformation
 * @Description: 获取数据库表表信息
 * @author lyn
 * @date 2020年3月28日
 *
 */
public class DatabaseTableInformation {
	private final static Logger LOGGER = LoggerFactory.getLogger(DatabaseTableInformation.class);
	private static final String SQL = "SELECT * FROM ";// 数据库操作
	private Connection connection;

	public DatabaseTableInformation(Connection connection) {
		super();
		this.connection = connection;
	}

	/**
	 * 获取数据库下的所有表名
	 */
	public List<String> getTableNames() {
		List<String> tableNames = new ArrayList<>();
		ResultSet rs = null;
		try {
			// 获取数据库的元数据
			DatabaseMetaData db = connection.getMetaData();
			// 从元数据中获取到所有的表名
			rs = db.getTables(null, null, null, new String[] { "TABLE" });
			while (rs.next()) {
				tableNames.add(rs.getString(3));
			}
		} catch (SQLException e) {
			LOGGER.error("getTableNames failure", e);
		}
		return tableNames;
	}

	/**
	 * 获取表中所有字段名称
	 * 
	 * @param tableName
	 *            表名
	 * @return
	 */
	public List<String> getColumnNames(String tableName) {
		List<String> columnNames = new ArrayList<>();
		PreparedStatement pStemt = null;
		String tableSql = SQL + tableName;
		try {
			pStemt = connection.prepareStatement(tableSql);
			// 结果集元数据
			ResultSetMetaData rsmd = pStemt.getMetaData();
			// 表列数
			int size = rsmd.getColumnCount();
			for (int i = 0; i < size; i++) {
				columnNames.add(rsmd.getColumnName(i + 1));
			}
		} catch (SQLException e) {
			LOGGER.error("getColumnNames failure", e);
		}
		return columnNames;
	}
	
	/**
	 * 
	 * @Title: getPrimaryKeys
	 * @Description: 获取某个数据库表的主键信息
	 * @param tableName
	 * @return
	 */
	public List<String> getPrimaryKeys(String tableName) {
		List<String> primaryKeyList = new ArrayList<>();
		try {
			DatabaseMetaData metaData = connection.getMetaData();
			ResultSet primaryKeys = metaData.getPrimaryKeys(null, null, tableName);
			while (primaryKeys.next()) {
				primaryKeyList.add(primaryKeys.getString(4));
			}
		} catch (SQLException e) {
			LOGGER.error("getPrimaryKeys failure", e);
		}
		return primaryKeyList;
	}
	
	

	/**
	 * 获取表中所有字段类型
	 * 
	 * @param tableName
	 * @return
	 */
	public List<String> getColumnTypes(String tableName) {
		List<String> columnTypes = new ArrayList<>();
		PreparedStatement pStemt = null;
		String tableSql = SQL + tableName;
		try {
			pStemt = connection.prepareStatement(tableSql);
			// 结果集元数据
			ResultSetMetaData rsmd = pStemt.getMetaData();
			// 表列数
			int size = rsmd.getColumnCount();
			for (int i = 0; i < size; i++) {
				columnTypes.add(rsmd.getColumnTypeName(i + 1));
			}
		} catch (SQLException e) {
			LOGGER.error("getColumnTypes failure", e);
		}
		return columnTypes;
	}

	/**
	 * 获取表中字段的所有注释
	 * 
	 * @param tableName
	 * @return
	 */
	public List<String> getColumnComments(String tableName) {
		PreparedStatement pStemt = null;
		String tableSql = SQL + tableName;
		List<String> columnComments = new ArrayList<>();// 列名注释集合
		ResultSet rs = null;
		try {
			pStemt = connection.prepareStatement(tableSql);
			rs = pStemt.executeQuery("show full columns from " + tableName);
			while (rs.next()) {
				columnComments.add(rs.getString("Comment"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return columnComments;
	}

	/**
	 * 
	 * @Title: getTableDetails
	 * @Description: 获取所有表单的字段和字段类型还有注释
	 * @return
	 */
	public List<TableInformation> getTableDetails() {
		List<TableInformation> tableInformations = new ArrayList<>();
		List<String> tableNames = getTableNames();
		for (String tableName : tableNames) {
			TableInformation tableInformation = new TableInformation();
			tableInformation.setTableName(tableName);
			List<TableColumn> tableColumns = new ArrayList<>();
			List<String> columnNames = getColumnNames(tableName);
			List<String> columnComments = getColumnComments(tableName);
			List<String> columnTypes = getColumnTypes(tableName);
			List<String> primaryKeys = getPrimaryKeys(tableName);
			int size = columnNames.size();
			for (int j = 0; j < size; j++) {
				TableColumn tableColumn = new TableColumn();
				tableColumn.setColumnName(columnNames.get(j));
				tableColumn.setSqlcolumnNote(columnComments.get(j));
				tableColumn.setSqlTypeName(columnTypes.get(j));
				tableColumn.setJavaTypeName(SQLType2JavaType.sqlType2JavaType(tableColumn.getSqlTypeName()));
				tableColumn.setIsPrimarykey(primaryKeys.contains(tableColumn.getColumnName()));
				tableColumns.add(tableColumn);
			}
			tableInformation.setList(tableColumns);
			tableInformations.add(tableInformation);
		}
		
		return tableInformations;
	}

	/**
	 * 
	 * @Title: getTableDetails
	 * @Description: 获取当个表单的信息，字段名、字段类型、字段注释
	 * @param tableName
	 * @return
	 */
	public TableInformation getTableDetails(String tableName) {
		TableInformation tableInformation = new TableInformation();
		tableInformation.setTableName(tableName);
		List<TableColumn> tableColumns = new ArrayList<>();
		List<String> columnNames = getColumnNames(tableName);
		List<String> columnComments = getColumnComments(tableName);
		List<String> columnTypes = getColumnTypes(tableName);
		List<String> primaryKeys = getPrimaryKeys(tableName);
		int size = columnNames.size();
		for (int j = 0; j < size; j++) {
			TableColumn tableColumn = new TableColumn();
			tableColumn.setColumnName(columnNames.get(j));
			tableColumn.setSqlcolumnNote(columnComments.get(j));
			tableColumn.setSqlTypeName(columnTypes.get(j));
			tableColumn.setJavaTypeName(SQLType2JavaType.sqlType2JavaType(tableColumn.getSqlTypeName()));
			tableColumn.setIsPrimarykey(primaryKeys.contains(tableColumn.getColumnName()));
			tableColumns.add(tableColumn);
		}
		tableInformation.setList(tableColumns);
		return tableInformation;
	}

}
