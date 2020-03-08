package com.test.app;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.test.app.api.db.DatabaseTableInformation;
import com.test.app.api.db.dto.TableInformation;
import com.test.app.mapper.DBOperationMapper;

/**
 * 
 * @ClassName: MybatisConfigToDB
 * @Description: mybatis直接插入数据
 * @author lyn
 * @date 2020年3月7日
 *
 */
public class MybatisConfigToDB {
	private static final Logger logger = LoggerFactory.getLogger(SimulatorServiceTest.class);

	public SqlSessionFactory getSqlSessionFactory() {
		String resource = "mybatis/mybatis-config.xml";
		InputStream inputStream = null;
		try {
			inputStream = Resources.getResourceAsStream(resource);
			logger.info("加载mybatis的配置文件成功！");
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("加载mybatis的配置文件失败！");
		}
		return new SqlSessionFactoryBuilder().build(inputStream);
	}
	/**
	 * 
	 * @Title: DBAllTableInformation
	 * @Description: 获取数据库中所有表单的字段和字段类型和注释
	 */
	public List<TableInformation> DBAllTableInformation() {
		// 1、获取sqlSessionFactory对象
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		// 2、获取sqlSession对象
		SqlSession openSession = sqlSessionFactory.openSession();
		Connection connection = openSession.getConnection();
		try {
			DatabaseTableInformation databaseTableInformation = new DatabaseTableInformation(connection);
			List<TableInformation> tableDetails = databaseTableInformation.getTableDetails();
			return tableDetails;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			openSession.close();
		}
		return null;
	}
	
	/**
	 * 
	 * @Title: DBTableInformation
	 * @Description: 获取指定表单的信息，字段、类型、注释
	 * @param tableName
	 */
	public TableInformation DBTableInformation(String tableName) {
		// 1、获取sqlSessionFactory对象
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		// 2、获取sqlSession对象
		SqlSession openSession = sqlSessionFactory.openSession();
		Connection connection = openSession.getConnection();
		try {
			DatabaseTableInformation databaseTableInformation = new DatabaseTableInformation(connection);
			TableInformation tableDetails = databaseTableInformation.getTableDetails(tableName);
			return tableDetails;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			openSession.close();
		}
		return null;
	}
	
	

	public void DBMybatisInsertSQL(String insertSQL) {
		// 1、获取sqlSessionFactory对象
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		// 2、获取sqlSession对象
		SqlSession openSession = sqlSessionFactory.openSession();
		try {
			// 3、获取接口的实现类对象
			// 会为接口自动的创建一个代理对象，代理对象去执行增删改查方法
			DBOperationMapper mapper = openSession.getMapper(DBOperationMapper.class);
			try {
				Integer flagNum = mapper.batchInsertSQLBySingleTable(insertSQL);
				if (flagNum != null && flagNum > 0) {
					logger.info("sql数据插入成功");
					openSession.commit();
				} else {
					logger.error("sql数据插入失败");
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("sql数据插入失败");
			}
		} finally {
			openSession.close();
		}
	}

	public static void main(String[] args) {
		MybatisConfigToDB mybatis = new MybatisConfigToDB();
		// mybatis.DBMybatisInsertSQL(
		// "insert into student(id,username,password,phone,email)
		// values(75,'female','male','female','female')");
		List<TableInformation> dbAllTableInformation = mybatis.DBAllTableInformation();
		for (TableInformation tableInformation : dbAllTableInformation) {
			System.out.println(tableInformation);
		}
		System.out.println("----------------------");
		TableInformation dbTableInformation = mybatis.DBTableInformation("tbresult");
		System.out.println(dbTableInformation);
	}

}
