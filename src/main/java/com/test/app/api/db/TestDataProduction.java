package com.test.app.api.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.test.app.api.db.dto.TableColumn;
import com.test.app.api.db.dto.TableInformation;

/**
 * 
 * @ClassName: TestDataProduction
 * @Description: 根据java类型生产测试数据
 * @author lyn
 * @date 2020年3月29日
 *
 */
public class TestDataProduction {
	private static final Logger logger = LoggerFactory.getLogger(TestDataProduction.class);
	// 这个是保存目前所生成的表格名称对应的主键的数据生成数据
	private Map<String, Map<String, List<Object>>> tableMap = null;

	public TestDataProduction() {
		super();
		this.tableMap = new HashMap<>();
	}

	public void clearMap() {
		this.tableMap = null;
	}

	public Map<String, List<Object>> produceDataList(TableInformation tableInformation, int produceSize) {
		Map<String, List<Object>> finalMap = new HashMap<>();
		String tableName = tableInformation.getTableName();
		List<TableColumn> list = tableInformation.getList();
		Map<String, List<Object>> columnMapKeys = new HashMap<>();
		for (TableColumn tableColumn : list) {
			String javaTypeName = tableColumn.getJavaTypeName();
			String columnName = tableColumn.getColumnName();
			Boolean isPrimarykey = tableColumn.getIsPrimarykey();
			if (isPrimarykey) {
				// 主键，生产的数据不重复
				try {
					Set<Object> setData = getJavaTypeRandomDataNoRepeat(javaTypeName, produceSize);
					columnMapKeys.put(columnName, new ArrayList<>(setData));
					finalMap.put(columnName, new ArrayList<>(setData));
					tableMap.put(tableName, columnMapKeys);
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("根据主键仅支持int类型和string类型");
				}
			} else {
				// 不是主键
				List<Object> listData = getJavaTypeRandomData(javaTypeName, produceSize);
				finalMap.put(columnName, listData);
			}
		}
		return finalMap;
	}

	/**
	 * 
	 * @Title: getJavaTypeRandomDataNoRepeat
	 * @Description: 根据主键仅支持int类型和string类型
	 * @param javaType
	 * @param produceSize
	 * @return
	 * @throws Exception
	 */
	public Set<Object> getJavaTypeRandomDataNoRepeat(String javaType, int produceSize) throws Exception {
		Set<Object> setData = new HashSet<>();
		if (javaType.equalsIgnoreCase("int")) {
			while (true) {
				int randomForIntegerBounded = RandomData.getRandomForIntegerBounded(1, produceSize * 2);
				setData.add(randomForIntegerBounded);
				if (setData.size() == produceSize) {
					return setData;
				}
			}
		} else if (javaType.equalsIgnoreCase("String")) {
			while (true) {
				String randomUUIDString = RandomData.getRandomUUIDString();
				setData.add(randomUUIDString);
				if (setData.size() == produceSize) {
					return setData;
				}
			}
		} else {
			logger.error("根据主键仅支持int类型和string类型");
			throw new Exception("根据主键仅支持int类型和string类型");
		}
	}

	public List<Object> getJavaTypeRandomData(String javaType, int produceSize) {
		List<Object> list = new ArrayList<>();
		if (javaType.equalsIgnoreCase("boolean")) {
			while (true) {
				Boolean randomBoolean = RandomData.getRandomBoolean();
				list.add(randomBoolean);
				if (list.size() == produceSize) {
					return list;
				}
			}

		} else if (javaType.equalsIgnoreCase("byte")) {
			while (true) {
				Byte randomByte0To9 = RandomData.getRandomByte0To9();
				list.add(randomByte0To9);
				if (list.size() == produceSize) {
					return list;
				}
			}
		} else if (javaType.equalsIgnoreCase("short")) {
			while (true) {
				Short randomShort = RandomData.getRandomShort();
				list.add(randomShort);
				if (list.size() == produceSize) {
					return list;
				}
			}
		} else if (javaType.equalsIgnoreCase("int")) {
			while (true) {
				int randomForIntegerBounded = RandomData.getRandomForIntegerBounded(1, produceSize);
				list.add(randomForIntegerBounded);
				if (list.size() == produceSize) {
					return list;
				}
			}
		} else if (javaType.equalsIgnoreCase("long")) {
			while (true) {
				long randomForLongBounded = RandomData.getRandomForLongBounded(1, produceSize);
				list.add(randomForLongBounded);
				if (list.size() == produceSize) {
					return list;
				}
			}
		} else if (javaType.equalsIgnoreCase("float")) {
			while (true) {
				float randomForFloat0To1 = RandomData.getRandomForFloat0To1();
				list.add(randomForFloat0To1);
				if (list.size() == produceSize) {
					return list;
				}
			}
		} else if (javaType.equalsIgnoreCase("double")) {
			while (true) {
				double randomForDouble0To1 = RandomData.getRandomForDouble0To1();
				list.add(randomForDouble0To1);
				if (list.size() == produceSize) {
					return list;
				}
			}

		} else if (javaType.equalsIgnoreCase("String")) {
			while (true) {
				String randomUUIDString = RandomData.getRandomUUIDString();
				list.add(randomUUIDString);
				if (list.size() == produceSize) {
					return list;
				}
			}
		} else if (javaType.equalsIgnoreCase("Date")) {
			while (true) {
				String date = RandomData.getDate();
				list.add(date);
				if (list.size() == produceSize) {
					return list;
				}
			}
		} else if (javaType.equalsIgnoreCase("Blod")) {
			logger.error("系统不支持Blod类型的数据");
			new RuntimeException("系统不支持Blod类型的数据");
		} else if (javaType.equalsIgnoreCase("Timestamp")) {
			while (true) {
				String date = RandomData.getDate();
				list.add(date);
				if (list.size() == produceSize) {
					return list;
				}
			}
		}
		return null;
	}

}
