package com.test.app.mapper;

import org.apache.ibatis.annotations.Param;

public interface DBOperationMapper {
	public Integer batchInsertSQLBySingleTable(@Param("insertSQL") String insertSQL);
}
