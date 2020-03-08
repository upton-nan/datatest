package com.test.app;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.test.app.api.db.SqlScriptProduce;
import com.test.app.api.db.TestDataProduction;
import com.test.app.api.db.dto.TableInformation;
import com.test.app.util.FileUtils;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;

public class SQLDataServiceTest {
	private static final Logger logger = LoggerFactory.getLogger(SQLDataServiceTest.class);

	public static void main(String[] args) {
		new SQLDataServiceTest();
	}

	SQLDataServiceTest() {
		// 获取主程序运行时所在目录
		URL url = getClass().getProtectionDomain().getCodeSource().getLocation();
		String resourceDir = null;
		try {
			resourceDir = URLDecoder.decode(url.getPath(), "UTF-8");
		} catch (UnsupportedEncodingException uee) {
			uee.printStackTrace();
		}
		logger.debug("取得主程序运行目录: {}", resourceDir);

		// 按照自定义配置文件配置SLF4J
		loadLogbackConfig();
		// 按照配置文件生成数据
		String sqlScriptStr = "";
		try {
			// 先查找数据库表格
			MybatisConfigToDB configToDB = new MybatisConfigToDB();
			SqlScriptProduce sqlScriptProduce = new SqlScriptProduce();
			List<TableInformation> dbAllTableInformation = configToDB.DBAllTableInformation();
			// 先根据表信息生成数据
			TestDataProduction testDataProduction = new TestDataProduction();
			for (TableInformation tableInformation : dbAllTableInformation) {
				sqlScriptStr += sqlScriptProduce.createSqlScript(tableInformation, testDataProduction);
			}
			logger.info("成功生成SQL Script数据。");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("未知异常。");
		}
		logger.info("开始将数据插入数据库中");
		String[] tableSQLArr = sqlScriptStr.split(";");
		for (String tableInsertSQL : tableSQLArr) {
			if (tableInsertSQL != null && !tableInsertSQL.trim().isEmpty()) {
				MybatisConfigToDB dbInsert = new MybatisConfigToDB();
				dbInsert.DBMybatisInsertSQL(tableInsertSQL);
			}
		}
		logger.info("数据插入数据库中结束");
		// 打印至控制台
		// System.out.println(sqlScript);

		// 输出至文件
		try {
			String sqlScriptPath = writeFile(sqlScriptStr);
			logger.info("成功生成SQL Script文件： {}。", sqlScriptPath);
		} catch (IOException ioe) {
			ioe.printStackTrace();
			logger.error("生成SQL Script文件时发生异常。");
		}

	}

	/**
	 * Method Name: loadLogbackConfig Description: 读取自定义的SLF4J的配置文件，并以之配置SLF4J
	 * 
	 * @since JDK 1.8.0
	 */
	private void loadLogbackConfig() {
		String projectRunPath = getProjectRunPath();
		File logbackConfig = new File(projectRunPath + "conf/logback.xml");
		if (logbackConfig.exists()) {
			LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
			JoranConfigurator configurator = new JoranConfigurator();
			configurator.setContext(context);
			context.reset();
			try {
				configurator.doConfigure(logbackConfig);
			} catch (JoranException je) {
				// StatusPrinter will handle this
			}
		}
	}

	/**
	 * Method Name: writeFile Description: 将文件写入指定.sql文件
	 * 
	 * @param sql
	 * @return 所生成SQL Script文件[.sql]的绝对路径
	 * @throws IOException
	 * @since JDK 1.8.0
	 */
	private String writeFile(String sql) throws IOException {
		String tablename = "default";
		if (sql.startsWith("insert into ") && sql.indexOf("(") != -1) {
			tablename = sql.substring(12, sql.indexOf("("));
		}
		File sqlScript = new File("src/main/resources/resultSQL/" + tablename + ".sql");
		FileUtils.write(sqlScript, sql, "UTF-8");
		return sqlScript.getAbsolutePath();
	}

	private String getProjectRunPath() {
		// 获取主程序运行时所在目录
		URL url = getClass().getProtectionDomain().getCodeSource().getLocation();
		String resourceDir = null;
		try {
			resourceDir = URLDecoder.decode(url.getPath(), "UTF-8");
		} catch (UnsupportedEncodingException uee) {
			uee.printStackTrace();
		}
		return resourceDir;
	}
}
