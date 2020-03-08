package com.test.app.api.db;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * 
 * @ClassName: RandomData
 * @Description: 根据java类型产生随机数
 * @author lyn
 * @date 2020年3月29日
 *
 */
public class RandomData {

	public static final Integer TYPE_UPPERCASE = 1;
	public static final Integer TYPE_LOWERCASE = 2;
	public static final Integer TYPE_NUMBER = 3;
	private final static int CHINESE_CHAR_RANGE = 0x9FA5 - 0x4E00 + 1;

	public static void main(String[] args) throws Exception {
		String randomChar1 = getRandomString(10, RandomData.TYPE_LOWERCASE);
		String randomChar2 = getRandomString(10, RandomData.TYPE_NUMBER);
		String randomChar3 = getRandomString(10, RandomData.TYPE_UPPERCASE);
		System.out.println(randomChar1);
		System.out.println(randomChar2);
		System.out.println(randomChar3);
		String randomChineseString = getRandomChineseByUnicode(10);
		System.out.println(randomChineseString);
		String randomChineseByGB2312 = getRandomChineseByGB2312(10);
		System.out.println(randomChineseByGB2312);
		System.out.println(getRandomSex());
		String date = getDate();
		System.out.println(date);
		Date date2 = getDate("2012-03-11 23:23:00", "2015-04-22 23:23:00");
		System.out.println(date2.toString());
		String randomUUIDString = getRandomUUIDString();
		System.out.println(randomUUIDString);
		Boolean randomBoolean = getRandomBoolean();
		System.out.println(randomBoolean);
		Byte randomByte0To9 = getRandomByte0To9();
		System.out.println(randomByte0To9);
		Short randomShort = getRandomShort();
		System.out.println(randomShort);
		long randomForLongBounded = getRandomForLongBounded(0, 80000);
		System.out.println(randomForLongBounded);
		float randomForFloat0To1 = getRandomForFloat0To1();
		System.out.println(randomForFloat0To1);
		double randomForDouble0To1 = getRandomForDouble0To1();
		System.out.println(randomForDouble0To1);
	}

	/**
	 * 
	 * @Title: getRandomForDouble0To1
	 * @Description: 生成0.0d-1.0d之间的Double随机数
	 * @return
	 */
	public static double getRandomForDouble0To1() {
		double generatorDouble = new Random().nextDouble();
		return generatorDouble;
	}

	/**
	 * 
	 * @Title: getRandomForFloat0To1
	 * @Description: 随机数Float的生成生成0.0-1.0之间的Float随机数
	 * @return
	 */
	public static float getRandomForFloat0To1() {
		float floatUnbounded = new Random().nextFloat();
		return floatUnbounded;
	}

	/**
	 * 
	 * @Title: getRandomForLongBounded
	 * @Description: 使用Random生成有边界的Long
	 * @param min
	 * @param max
	 * @return
	 */
	public static long getRandomForLongBounded(long min, long max) {
		long rangeLong = min + (((long) (new Random().nextDouble() * (max - min))));
		return rangeLong;
	}

	/**
	 * 
	 * @Title: getRandomShort
	 * @Description: 获取short类型的数据随机数
	 * @return
	 */
	public static Short getRandomShort() {
		Random random = new Random();
		int nextInt = random.nextInt(32767);
		Short short1 = (short) nextInt;
		return short1;
	}

	/**
	 * 
	 * @Title: getRandomByte
	 * @Description: 获取byte类型的数据在0-9之间，包括0和9
	 * @return
	 */
	public static Byte getRandomByte0To9() {
		Random random = new Random();
		int nextInt = random.nextInt(10);
		byte parseByte = Byte.parseByte(nextInt + "");
		return parseByte;
	}

	/**
	 * 
	 * @Title: getRandomString
	 * @Description: 根据类型得到随机指定长度的字符串
	 * @param ln
	 *            指定字符串长度
	 * @param type
	 *            指定字符串类型：TYPE_UPPERCASE=1;TYPE_LOWERCASE=2;TYPE_NUMBER=3;
	 * @return
	 */
	public static String getRandomString(int ln, int type) {
		String result = "";
		Random rd = new Random();
		switch (type) {
		case 1:
			for (int i = 0; i < ln; i++) {
				result += (char) (rd.nextInt(26) + 65);
			}
			break;
		case 2:
			for (int i = 0; i < ln; i++) {
				result += (char) (rd.nextInt(26) + 97);
			}
			break;
		case 3:
			for (int i = 0; i < ln; i++) {
				result += rd.nextInt(10);
			}
			break;
		default:
			break;
		}
		return result;
	}

	/**
	 * 
	 * @Title: getRandomChineseByUnicode
	 * @Description: 获取指定个数的随机汉字汉字(根据Unicode编码)
	 * @param length
	 * @return
	 */
	public static String getRandomChineseByUnicode(int length) {
		Random random = new Random();
		String result = "";
		for (int i = 0; i < length; i++) {
			result += (char) (random.nextInt(CHINESE_CHAR_RANGE));
		}
		return result;
	}

	/**
	 * 
	 * @Title: getRandomChineseByGB2312
	 * @Description: 获取指定个数的随机汉字汉字(根据GB2312区位码编辑)
	 * @param length
	 * @return
	 * @throws Exception
	 */
	public static String getRandomChineseByGB2312(int length) throws Exception {
		String str = "";
		int highPos, lowPos;
		long seed = new Date().getTime();
		Random random = new Random(seed);
		for (int i = 0; i < length; i++) {
			highPos = (176 + Math.abs(random.nextInt(39)));
			lowPos = 161 + Math.abs(random.nextInt(93));
			byte[] b = new byte[2];
			b[0] = (new Integer(highPos)).byteValue();
			b[1] = (new Integer(lowPos)).byteValue();
			str += new String(b, "GB2312");
		}
		return str;
	}

	/**
	 * 
	 * @Title: getRandomSex
	 * @Description: 获取性别，返回男或女
	 * @return
	 */
	public static String getRandomSex() {
		Random random = new Random();
		String sex = random.nextBoolean() ? "男" : "女";
		return sex;
	}

	/**
	 * 
	 * @Title: getRandomBoolean
	 * @Description: 获取随机数为布尔值
	 * @return
	 */
	public static Boolean getRandomBoolean() {
		Random random = new Random();
		boolean nextBoolean = random.nextBoolean();
		return nextBoolean;
	}

	/**
	 * 
	 * @Title: getDate
	 * @Description: 获取在开始和结束时间内的时间date
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static Date getDate(String beginDate, String endDate) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date start = format.parse(beginDate);// 构造开始日期
			Date end = format.parse(endDate);// 构造结束日期
			// getTime()表示返回自 1970 年 1 月 1 日 00:00:00 GMT 以来此 Date 对象表示的毫秒数。
			if (start.getTime() >= end.getTime()) {
				return null;
			}
			long date = random(start.getTime(), end.getTime());
			return new Date(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static long random(long begin, long end) {
		long rtn = begin + (long) (Math.random() * (end - begin));
		// 如果返回的是开始时间和结束时间，则递归调用本函数查找随机值
		if (rtn == begin || rtn == end) {
			return random(begin, end);
		}
		return rtn;
	}

	/**
	 * 
	 * @Title: getDate
	 * @Description: 获取当前的时间
	 * @return
	 */
	public static String getDate() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return simpleDateFormat.format(new Date());
	}

	/**
	 * 
	 * @Title: getRandomForIntegerUnbounded
	 * @Description: 随机数生成无边界的Int
	 * @return
	 */
	public static int getRandomForIntegerUnbounded() {
		int intUnbounded = new Random().nextInt();
		return intUnbounded;
	}

	/**
	 * 
	 * @Title: getRandomForIntegerBounded
	 * @Description: 生成有边界的Int
	 * @param min
	 * @param max
	 * @return
	 */
	public static int getRandomForIntegerBounded(int min, int max) {
		int intBounded = min + ((int) (new Random().nextFloat() * (max - min)));
		return intBounded;
	}

	/**
	 * 
	 * @Title: getRandomUUIDString
	 * @Description: 生成uuid随机字符串
	 * @return
	 */
	public static String getRandomUUIDString() {
		String replace = UUID.randomUUID().toString().replace("-", "");
		return replace;
	}

}
