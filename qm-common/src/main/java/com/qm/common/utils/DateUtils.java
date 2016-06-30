package com.qm.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 时间处理工具类
 */
public class DateUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(DateUtils.class);

	public interface FMT {

		public static final String yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
		public static final String yyyy_MM_dd_HH_mm = "yyyy-MM-dd HH:mm";
		public static final String yyyyMMddHHmmss = "yyyyMMddHHmmss";

		public static final String yyMMddHHmmss = "yyMMddHHmmss";

		public static final String yyyy_MM_dd = "yyyy-MM-dd";

		public static final String yyyyMMdd = "yyyyMMdd";

		public static final String HH_mm = "HH:mm";
		public static final String HH_MM_SS = "HH:mm:ss";
		public static final String HHmm = "HHmm";
		public static final String EEEE = "EEEE";
	}

	public interface TIMESTAMP {
		public static final long SECONDS_MINUTE = 60;
		public static final long SECONDS_HOUR = 60 * SECONDS_MINUTE;
		public static final long SECONDS_DAY = 86400;

		public static final long MILLIS_SECOND = 1000;
		public static final long MILLIS_MINUTE = SECONDS_MINUTE * MILLIS_SECOND;
		public static final long MILLIS_HOUR = SECONDS_HOUR * MILLIS_SECOND;
		public static final long MILLIS_DAY = SECONDS_DAY * MILLIS_SECOND;

		public static final long HOURS_TIMEZONE = 8;
		public static final long SECONDS_TIMEZONE = HOURS_TIMEZONE * 3600;
		public static final long MILLIS_TIMEZONE = SECONDS_TIMEZONE * MILLIS_SECOND;
	}

	public static long getTime(Date date) {
		return date == null ? 0L : date.getTime();
	}

	// 比较两个日期的实际天数差异，移除不足一天的时间
	public static int diffDays(String d0, String d1, String fmt) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(fmt);
			Date date0 = sdf.parse(d0);
			Date date1 = sdf.parse(d1);
			return (int) (Math.abs(date0.getTime() - date1.getTime()) / TIMESTAMP.MILLIS_DAY);
		} catch (Exception e) {
			LOGGER.error("parse date {}/{} by {} failed:", d0, d1, fmt, e);
		}
		return -1;
	}

	/**
	 * 检查字符串是否为某格式的日期
	 */
	public static boolean isDate(String date, String fmt) {
		if (StrUtils.isBlank(date)) {
			return false;
		}
		Date ret = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(fmt);
			ret = sdf.parse(date);
		} catch (Exception e) {
		}
		return ret != null;
	}

	/**
	 * 时间与字符串按格式转换
	 */
	public static String dateToString(Date date, String fmt) {
		if (date == null) {
			return null;
		}
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(fmt, Locale.SIMPLIFIED_CHINESE);
			return sdf.format(date);
		} catch (Exception e) {
			LOGGER.error("format date {} by {} failed:", date, fmt, e);
		}
		return null;
	}

	public static String getWeekDay(Date date) {
		return dateToString(date, FMT.EEEE);
	}

	public static Date stringToDate(String dateStr, String fmt) {
		if (StrUtils.isBlank(dateStr)) {
			return null;
		}
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(fmt, Locale.SIMPLIFIED_CHINESE);
			return sdf.parse(dateStr);
		} catch (Exception e) {
			LOGGER.error("parse date {} by format {} failed:", dateStr, fmt, e);
		}
		return null;
	}

	/**
	 * 获取当前的[毫]秒数：绝对时间、时区时间
	 */
	public static long currAbsMillis() {
		return System.currentTimeMillis();
	}

	public static long currAbsSeconds() {
		return (currAbsMillis() / TIMESTAMP.MILLIS_SECOND);
	}

	public static long currLocalMillis() {
		return absMillis2Local(currAbsMillis());
	}

	public static long absMillis2Local(long abs) {
		return abs + TIMESTAMP.MILLIS_TIMEZONE;
	}

	public static long localMillis2Abs(long local) {
		return local - TIMESTAMP.MILLIS_TIMEZONE;
	}

	/**
	 * 获取当天的起点ms，需要考虑时区的影响，因为unix time是绝对时间，而我们比绝对时间多8个小时
	 */
	public static long todayStartMilli() {
		long currMillis = currLocalMillis();
		return (currMillis - currMillis % TIMESTAMP.MILLIS_DAY);
	}

	/**
	 * 获取当天的剩余ms
	 */
	public static long getTodayRemainMilli() {
		long currMillis = currLocalMillis();
		return (TIMESTAMP.MILLIS_DAY - currMillis % TIMESTAMP.MILLIS_DAY);
	}

	/**
	 * 日期按天位移计算
	 */

	public static Date dateAfterDays(int days) {
		return new Date(currAbsMillis() + days * TIMESTAMP.MILLIS_DAY);
	}

	// 要求按 yyyy-MM-dd 格式存放
	public static String dateAfterDays(String dateStr, int days) {
		Date date = null;
		if (null == dateStr) {
			date = dateAfterDays(days);
		} else {
			date = stringToDate(dateStr, FMT.yyyy_MM_dd);
			date.setTime(date.getTime() + days * TIMESTAMP.MILLIS_DAY);
		}
		return dateToString(date, FMT.yyyy_MM_dd);
	}

	public static Date dateAfterDays(Date date, int days) {
		return null == date ? dateAfterDays(days) : new Date(date.getTime() + days * TIMESTAMP.MILLIS_DAY);
	}
}
