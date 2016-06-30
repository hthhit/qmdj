package com.qm.kaipan.service.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.qm.kaipan.service.entity.FourStem;
import com.qm.kaipan.service.entity.StemNode;

public class LunarCalendar extends GregorianCalendar {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(LunarCalendar.class);
	private final int[] lunarInfo = { 0x4bd8, 0x4ae0, 0xa570, 0x54d5, 0xd260, 0xd950, 0x5554, 0x56af, 0x9ad0, 0x55d2,
			0x4ae0, 0xa5b6, 0xa4d0, 0xd250, 0xd295, 0xb54f, 0xd6a0, 0xada2, 0x95b0, 0x4977, 0x497f, 0xa4b0, 0xb4b5,
			0x6a50, 0x6d40, 0xab54, 0x2b6f, 0x9570, 0x52f2, 0x4970, 0x6566, 0xd4a0, 0xea50, 0x6a95, 0x5adf, 0x2b60,
			0x86e3, 0x92ef, 0xc8d7, 0xc95f, 0xd4a0, 0xd8a6, 0xb55f, 0x56a0, 0xa5b4, 0x25df, 0x92d0, 0xd2b2, 0xa950,
			0xb557, 0x6ca0, 0xb550, 0x5355, 0x4daf, 0xa5b0, 0x4573, 0x52bf, 0xa9a8, 0xe950, 0x6aa0, 0xaea6, 0xab50,
			0x4b60, 0xaae4, 0xa570, 0x5260, 0xf263, 0xd950, 0x5b57, 0x56a0, 0x96d0, 0x4dd5, 0x4ad0, 0xa4d0, 0xd4d4,
			0xd250, 0xd558, 0xb540, 0xb6a0, 0x95a6, 0x95bf, 0x49b0, 0xa974, 0xa4b0, 0xb27a, 0x6a50, 0x6d40, 0xaf46,
			0xab60, 0x9570, 0x4af5, 0x4970, 0x64b0, 0x74a3, 0xea50, 0x6b58, 0x5ac0, 0xab60, 0x96d5, 0x92e0, 0xc960,
			0xd954, 0xd4a0, 0xda50, 0x7552, 0x56a0, 0xabb7, 0x25d0, 0x92d0, 0xcab5, 0xa950, 0xb4a0, 0xbaa4, 0xad50,
			0x55d9, 0x4ba0, 0xa5b0, 0x5176, 0x52bf, 0xa930, 0x7954, 0x6aa0, 0xad50, 0x5b52, 0x4b60, 0xa6e6, 0xa4e0,
			0xd260, 0xea65, 0xd530, 0x5aa0, 0x76a3, 0x96d0, 0x4afb, 0x4ad0, 0xa4d0, 0xd0b6, 0xd25f, 0xd520, 0xdd45,
			0xb5a0, 0x56d0, 0x55b2, 0x49b0, 0xa577, 0xa4b0, 0xaa50, 0xb255, 0x6d2f, 0xada0, 0x4b63, 0x937f, 0x49f8,
			0x4970, 0x64b0, 0x68a6, 0xea5f, 0x6b20, 0xa6c4, 0xaaef, 0x92e0, 0xd2e3, 0xc960, 0xd557, 0xd4a0, 0xda50,
			0x5d55, 0x56a0, 0xa6d0, 0x55d4, 0x52d0, 0xa9b8, 0xa950, 0xb4a0, 0xb6a6, 0xad50, 0x55a0, 0xaba4, 0xa5b0,
			0x52b0, 0xb273, 0x6930, 0x7337, 0x6aa0, 0xad50, 0x4b55, 0x4b6f, 0xa570, 0x54e4, 0xd260, 0xe968, 0xd520,
			0xdaa0, 0x6aa6, 0x56df, 0x4ae0, 0xa9d4, 0xa4d0, 0xd150, 0xf252, 0xd520 };
	private final int[] solarTermInfo = { 0, 21208, 42467, 63836, 85337, 107014, 128867, 150921, 173149, 195551, 218072,
			240693, 263343, 285989, 308563, 331033, 353350, 375494, 397447, 419210, 440795, 462224, 483532, 504758 };

	public LunarCalendar() {
		Date nowD = new Date();
		long TimeInMillis = nowD.getTime();
		init(TimeInMillis);
	}

	public LunarCalendar(long TimeInMillis) {
		init(TimeInMillis);
	}

	/** 阳历的日历 */
	private Calendar solar;
	/** 阳历年 */
	private int solarYear;
	/** 阳历月 */
	private int solarMonth;
	/** 阳历天 */
	private int solarDay;
	/** 阳历时 */
	private int solarHour;
	/** 阳历分 */
	private int solarMinute;
	/** 农历年 */
	private int lunarYear;
	/** 农历月 */
	private int lunarMonth;
	/** 农历日 */
	private int lunarDay;
	/** 农历时 */
	private int lunarHour;
	/** 农历分 */
	private int lunarMinute;
	/** 干支历年 */
	private int cyclicalYear;
	/** 干支历月 */
	private int cyclicalMonth;
	/** 干支历日 */
	private int cyclicalDay;
	/** 干支历时 */
	private int cyclicalTiananHour;
	private int cyclicalDeqiHour;

	private boolean isLeapYear;
	private boolean isLeap;
	private int maxDayInMonth = 29;

	public static void main(String[] args) {
		Date date = new Date();
		LunarCalendar calendar = new LunarCalendar(date.getTime());
		System.out.println(calendar.getLunarYear() + " " + calendar.getLunarMonth() + " " + calendar.getLunarDay());
		System.out.println(calendar.getTiananY() + "," + calendar.getDeqiY() + ":" + calendar.getTiananM() + ","
				+ calendar.getDeqiM() + ":" + calendar.getTiananD() + "," + calendar.getDeqiD());
		System.out.println("农历" + calendar.getLunarDateString() + ",干支年" + calendar.getCyclicalDateString());
		System.out.println(JSON.toJSONString(calendar.getFourStems()));
	}

	/**
	 * 获取四柱
	 * 
	 * @return
	 */
	public FourStem getFourStems() {
		int yearH = getTiananY() + 1;
		int yearE = getDeqiY() + 1;
		int monthH = getTiananM() + 1;
		int monthE = getDeqiM() + 1;
		int dayH = getTiananD() + 1;
		int dayE = getDeqiD() + 1;
		int hourH = getTiananH() + 1;
		int hourE = getDeqiH() + 1;
		StemNode nodeY = new StemNode(yearH, yearE);
		StemNode nodeM = new StemNode(monthH, monthE);
		StemNode nodeD = new StemNode(dayH, dayE);
		StemNode nodeH = new StemNode(hourH, hourE);
		return new FourStem(nodeY, nodeM, nodeD, nodeH);
	}

	private void init(long TimeInMillis) {
		this.solar = Calendar.getInstance();
		this.solar.setTimeInMillis(TimeInMillis);
		Calendar baseDate = new GregorianCalendar(1900, 0, 31);
		long offset = (TimeInMillis - baseDate.getTimeInMillis()) / 86400000;
		// 按农历年递减每年的农历天数，确定农历年份
		this.lunarYear = 1900;
		int daysInLunarYear = this.getLunarYearDays(this.lunarYear);
		while (this.lunarYear < 2100 && offset >= daysInLunarYear) {
			offset -= daysInLunarYear;
			daysInLunarYear = this.getLunarYearDays(++this.lunarYear);
		}
		// 农历年数字

		// 按农历月递减每月的农历天数，确定农历月份
		int lunarMonth = 1;
		// 所在农历年闰哪个月,若没有返回0
		int leapMonth = this.getLunarLeapMonth(this.lunarYear);
		// 是否闰年
		this.isLeapYear = leapMonth > 0;
		// 闰月是否递减
		boolean leapDec = false;
		boolean isLeap = false;
		int daysInLunarMonth = 0;
		while (lunarMonth < 13 && offset > 0) {
			if (isLeap && leapDec) { // 如果是闰年,并且是闰月
				// 所在农历年闰月的天数
				daysInLunarMonth = this.getLunarLeapDays(this.lunarYear);
				leapDec = false;
			} else {
				// 所在农历年指定月的天数
				daysInLunarMonth = this.getLunarMonthDays(this.lunarYear, lunarMonth);
			}
			if (offset < daysInLunarMonth) {
				break;
			}
			offset -= daysInLunarMonth;

			if (leapMonth == lunarMonth && isLeap == false) {
				// 下个月是闰月
				leapDec = true;
				isLeap = true;
			} else {
				// 月份递增
				lunarMonth++;
			}
		}
		this.maxDayInMonth = daysInLunarMonth;
		// 农历月数字
		this.lunarMonth = lunarMonth;
		// 是否闰月
		this.isLeap = (lunarMonth == leapMonth && isLeap);
		// 农历日数字
		this.lunarDay = (int) offset + 1;
		// 取得干支历
		this.getCyclicalData();
	}

	/**
	 * 取干支历 不是历年，历月干支，而是中国的从立春节气开始的节月，是中国的太阳十二宫，阳历的。
	 *
	 * @param cncaData
	 *            日历对象(Tcnca)
	 */
	private void getCyclicalData() {
		this.solarYear = this.solar.get(Calendar.YEAR);
		this.solarMonth = this.solar.get(Calendar.MONTH);
		this.solarDay = this.solar.get(Calendar.DAY_OF_MONTH);
		this.solarHour = this.solar.get(Calendar.HOUR_OF_DAY);
		// 干支历
		int cyclicalYear = 0;
		int cyclicalMonth = 0;
		int cyclicalDay = 0;

		// 干支年 1900年立春後为庚子年(60进制36)
		int term2 = this.getSolarTermDay(solarYear, 2); // 立春日期
		// 依节气调整二月分的年柱, 以立春为界
		if (solarMonth < 1 || (solarMonth == 1 && solarDay < term2)) {
			cyclicalYear = (solarYear - 1900 + 36 - 1) % 60;
		} else {
			cyclicalYear = (solarYear - 1900 + 36) % 60;
		}

		// 干支月 1900年1月小寒以前为 丙子月(60进制12)
		int firstNode = this.getSolarTermDay(solarYear, solarMonth * 2); // 传回当月「节」为几日开始
		// 依节气月柱, 以「节」为界
		if (solarDay < firstNode) {
			cyclicalMonth = ((solarYear - 1900) * 12 + solarMonth + 12) % 60;
		} else {
			cyclicalMonth = ((solarYear - 1900) * 12 + solarMonth + 13) % 60;
		}

		// 当月一日与 1900/1/1 相差天数
		// 1900/1/1与 1970/1/1 相差25567日, 1900/1/1 日柱为甲戌日(60进制10)
		cyclicalDay = (int) (this.UTC(solarYear, solarMonth, solarDay, 0, 0, 0) / 86400000 + 25567 + 10) % 60;
		this.cyclicalYear = cyclicalYear;
		this.cyclicalMonth = cyclicalMonth;
		this.cyclicalDay = cyclicalDay;

		int dayTianan = getTianan(getTiananD()) + 1;
		int hourDeqi = getCommEarthHour(solarHour);
		int hourTianan = (dayTianan * 2 + hourDeqi - 2) % 10;
		if (hourTianan == 0) {
			hourTianan = 10;
		}

		this.cyclicalTiananHour = hourTianan - 1;
		this.cyclicalDeqiHour = hourDeqi - 1;
		this.initFourStem(solarYear, solarMonth, solarDay, solarHour);
	}

	/**
	 * 返回公历年节气的日期
	 *
	 * @param solarYear
	 *            指定公历年份(数字)
	 * @param index
	 *            指定节气序号(数字,0从小寒算起)
	 * @return 日期(数字,所在月份的第几天)
	 */
	private int getSolarTermDay(int solarYear, int index) {
		return this.getUTCDay(getSolarTermCalendar(solarYear, index));
	}

	/**
	 * 返回公历年节气的日期
	 *
	 * @param solarYear
	 *            指定公历年份(数字)
	 * @param index
	 *            指定节气序号(数字,0从小寒算起)
	 * @return 日期(数字,所在月份的第几天)
	 */
	public Date getSolarTermCalendar(int solarYear, int index) {
		long l = (long) 31556925974.7 * (solarYear - 1900) + solarTermInfo[index] * 60000L;
		l = l + this.UTC(1900, 0, 6, 2, 5, 0);
		return new Date(l);
	}

	/**
	 * 返回农历年正常月份的总天数
	 *
	 * @param lunarYear
	 *            指定农历年份(数字)
	 * @param lunarMonth
	 *            指定农历月份(数字)
	 * @return 该农历年闰月的月份(数字,没闰返回0)
	 */
	private int getLunarMonthDays(int lunarYear, int lunarMonth) {
		// 数据表中,每个农历年用16bit来表示,
		// 前12bit分别表示12个月份的大小月,最后4bit表示闰月
		int daysInLunarMonth = ((this.lunarInfo[lunarYear - 1900] & (0x10000 >> lunarMonth)) != 0) ? 30 : 29;
		return daysInLunarMonth;
	}

	/** 转化全球标准时间 */
	private GregorianCalendar utcCal = null;

	private synchronized void makeUTCCalendar() {
		if (this.utcCal == null) {
			this.utcCal = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
		}
	}

	/**
	 * 取 Date 对象中用全球标准时间 (UTC) 表示的日期
	 *
	 * @param date
	 *            指定日期
	 * @return UTC 全球标准时间 (UTC) 表示的日期
	 */
	public synchronized int getUTCDay(Date date) {
		this.makeUTCCalendar();
		synchronized (utcCal) {
			utcCal.clear();
			utcCal.setTimeInMillis(date.getTime());
			return utcCal.get(Calendar.DAY_OF_MONTH);
		}
	}

	/**
	 * 返回农历年的总天数
	 *
	 * @param lunarYear
	 *            指定农历年份(数字)
	 * @return 该农历年的总天数(数字)
	 */
	private int getLunarYearDays(int lunarYear) {
		// 按小月计算,农历年最少有12 * 29 = 348天
		int daysInLunarYear = 348;
		// 数据表中,每个农历年用16bit来表示,
		// 前12bit分别表示12个月份的大小月,最后4bit表示闰月
		// 每个大月累加一天
		for (int i = 0x8000; i > 0x8; i >>= 1) {
			daysInLunarYear += ((this.lunarInfo[lunarYear - 1900] & i) != 0) ? 1 : 0;
		}
		// 加上闰月天数
		daysInLunarYear += this.getLunarLeapDays(lunarYear);

		return daysInLunarYear;
	}

	/**
	 * 返回农历年闰月的天数
	 *
	 * @param lunarYear
	 *            指定农历年份(数字)
	 * @return 该农历年闰月的天数(数字)
	 */
	private int getLunarLeapDays(int lunarYear) {
		// 下一年最后4bit为1111,返回30(大月)
		// 下一年最后4bit不为1111,返回29(小月)
		// 若该年没有闰月,返回0
		return this.getLunarLeapMonth(lunarYear) > 0 ? ((this.lunarInfo[lunarYear - 1899] & 0xf) == 0xf ? 30 : 29) : 0;
	}

	/**
	 * 返回农历年闰月月份
	 *
	 * @param lunarYear
	 *            指定农历年份(数字)
	 * @return 该农历年闰月的月份(数字,没闰返回0)
	 */
	private int getLunarLeapMonth(int lunarYear) {
		// 数据表中,每个农历年用16bit来表示,
		// 前12bit分别表示12个月份的大小月,最后4bit表示闰月
		// 若4bit全为1或全为0,表示没闰, 否则4bit的值为闰月月份
		int leapMonth = this.lunarInfo[lunarYear - 1900] & 0xf;
		leapMonth = (leapMonth == 0xf ? 0 : leapMonth);
		return leapMonth;
	}

	/**
	 * 返回全球标准时间 (UTC) (或 GMT) 的 1970 年 1 月 1 日到所指定日期之间所间隔的毫秒数。
	 *
	 * @param y
	 *            指定年份
	 * @param m
	 *            指定月份
	 * @param d
	 *            指定日期
	 * @param h
	 *            指定小时
	 * @param min
	 *            指定分钟
	 * @param sec
	 *            指定秒数
	 * @return 全球标准时间 (UTC) (或 GMT) 的 1970 年 1 月 1 日到所指定日期之间所间隔的毫秒数
	 */
	public synchronized long UTC(int y, int m, int d, int h, int min, int sec) {
		this.makeUTCCalendar();
		synchronized (utcCal) {
			utcCal.clear();
			utcCal.set(y, m, d, h, min, sec);
			return utcCal.getTimeInMillis();
		}
	}

	public final String[] lunarString1 = { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九" };
	public final String[] lunarString2 = { "初", "十", "廿", "卅", "正", "腊", "冬", "闰" };
	public final String[] Tianan = { "甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸" };
	public final String[] Deqi = { "子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌", "亥" };

	/**
	 * 农历年是否是闰月
	 *
	 * @return 农历年是否是闰月
	 */
	public boolean isLeap() {
		return isLeap;
	}

	public int getLunarDay() {
		return this.lunarDay;
	}

	/**
	 * 返回农历日期字符串
	 *
	 * @return 农历日期字符串
	 */
	public String getLunarDayString() {
		return this.getLunarDayString(this.lunarDay);
	}

	public int getLunarMonth() {
		return this.lunarMonth;
	}

	/**
	 * 返回农历日期字符串
	 *
	 * @return 农历日期字符串
	 */
	public String getLunarMonthString() {
		return (this.isLeap() ? "闰" : "") + this.getLunarMonthString(this.lunarMonth);
	}

	public int getLunarYear() {
		return this.lunarYear;
	}

	/**
	 * 年份天干
	 *
	 * @return 年份天干
	 */
	public int getTiananY() {
		return this.getTianan(this.cyclicalYear);
	}

	/**
	 * 月份天干
	 *
	 * @return 月份天干
	 */
	public int getTiananM() {
		return this.getTianan(this.cyclicalMonth);
	}

	/**
	 * 日期天干
	 *
	 * @return 日期天干
	 */
	public int getTiananD() {
		return this.getTianan(this.cyclicalDay);
	}

	public int getTiananH() {
		return this.cyclicalTiananHour;
	}

	/**
	 * 年份地支
	 *
	 * @return 年分地支
	 */
	public int getDeqiY() {
		return this.getDeqi(this.cyclicalYear);
	}

	/**
	 * 月份地支
	 *
	 * @return 月份地支
	 */
	public int getDeqiM() {
		return this.getDeqi(this.cyclicalMonth);
	}

	/**
	 * 日期地支
	 *
	 * @return 日期地支
	 */
	public int getDeqiD() {
		return this.getDeqi(this.cyclicalDay);
	}

	/**
	 * 小时地支
	 * 
	 * @return
	 */
	public int getDeqiH() {
		return this.cyclicalDeqiHour;
	}

	/**
	 * 取得干支历字符串
	 *
	 * @return 干支历字符串(例:甲子年甲子月甲子日)
	 */
	public String getCyclicalDateString() {
		return this.getCyclicaYear() + "年" + this.getCyclicaMonth() + "月" + this.getCyclicaDay() + "日"
				+ this.getCyclicaHour() + "时";
	}

	/**
	 * 返回农历表示字符串
	 *
	 * @return 农历字符串(例:甲子年正月初三)
	 */
	public String getLunarDateString() {
		return this.getLunarYearString() + "年" + this.getLunarMonthString() + "月" + this.getLunarDayString() + "日";
	}

	/**
	 * 返回农历日期字符串
	 *
	 * @return 农历日期字符串
	 */
	public String getLunarYearString() {
		return this.getLunarYearString(this.lunarYear);
	}

	/**
	 * 取得干支年字符串
	 *
	 * @return 干支年字符串
	 */
	public String getCyclicaYear() {
		return this.getCyclicalString(this.cyclicalYear);
	}

	/**
	 * 取得干支月字符串
	 *
	 * @return 干支月字符串
	 */
	public String getCyclicaMonth() {
		return this.getCyclicalString(this.cyclicalMonth);
	}

	/**
	 * 取得干支日字符串
	 *
	 * @return 干支日字符串
	 */
	public String getCyclicaDay() {
		return this.getCyclicalString(this.cyclicalDay);
	}

	public String getCyclicaHour() {
		return this.Tianan[this.cyclicalTiananHour] + this.Deqi[this.cyclicalDeqiHour];
	}

	/**
	 * 返回指定数字的农历年份表示字符串
	 *
	 * @param lunarYear
	 *            农历年份(数字,0为甲子)
	 * @return 农历年份字符串
	 */
	private String getLunarYearString(int lunarYear) {
		return this.getCyclicalString(lunarYear - 1900 + 36);
	}

	/**
	 * 干支字符串
	 *
	 * @param cyclicalNumber
	 *            指定干支位置(数字,0为甲子)
	 * @return 干支字符串
	 */
	private String getCyclicalString(int cyclicalNumber) {
		return this.Tianan[this.getTianan(cyclicalNumber)] + this.Deqi[this.getDeqi(cyclicalNumber)];
	}

	/**
	 * 获得天干
	 *
	 * @param cyclicalNumber
	 * @return 天干 (数字)
	 */
	private int getTianan(int cyclicalNumber) {
		return cyclicalNumber % 10;
	}

	/**
	 * 获得地支
	 *
	 * @param cyclicalNumber
	 * @return 地支 (数字)
	 */
	private int getDeqi(int cyclicalNumber) {
		return cyclicalNumber % 12;
	}

	// /**
	// * 返回指定数字的农历年份表示字符串
	// *
	// * @param lunarYear
	// * 农历年份(数字,0为甲子)
	// * @return 农历年份字符串
	// */
	/**
	 * 返回指定数字的农历月份表示字符串
	 *
	 * @param lunarMonth
	 *            农历月份(数字)
	 * @return 农历月份字符串 (例:正)
	 */
	private String getLunarMonthString(int lunarMonth) {
		String lunarMonthString = "";
		if (lunarMonth == 1) {
			lunarMonthString = this.lunarString2[4];
		} else {
			if (lunarMonth > 9) {
				lunarMonthString += this.lunarString2[1];
			}
			if (lunarMonth % 10 > 0) {
				lunarMonthString += this.lunarString1[lunarMonth % 10];
			}
		}
		return lunarMonthString;
	}

	/**
	 * 返回指定数字的农历日表示字符串
	 *
	 * @param lunarDay
	 *            农历日(数字)
	 * @return 农历日字符串 (例: 廿一)
	 */
	private String getLunarDayString(int lunarDay) {
		if (lunarDay < 1 || lunarDay > 30) {
			return "";
		}
		int i1 = lunarDay / 10;
		int i2 = lunarDay % 10;
		String c1 = this.lunarString2[i1];
		String c2 = this.lunarString1[i2];
		if (lunarDay < 11) {
			c1 = this.lunarString2[0];
		}
		if (i2 == 0) {
			c2 = this.lunarString2[1];
		}
		return c1 + c2;
	}

	/**
	 * 2016-05-16目前还有问题
	 */
	/*********** 另外的算法获取天干地支 ****************************************/
	private int heavenYear;
	private int earthYear;
	private int heavenMonth;
	private int earthMonth;
	private int heavenDay;
	private int earthDay;
	private int heavenHour;
	private int earthHour;

	private void initFourStem(int year, int month, int day, int hour) {
		initHeavenYear(year);
		initEarthYear(year);

		initHeavenMonth();
		initEarthMonth();

		initHeavenDay(year, month, day);
		initEarthDay(year, month, day);

		initHeavenHour();
		initEarthHour(hour);
	}

	/**
	 * 公元后 获取干支年 mod((n-3)/10)
	 * 
	 * @param year
	 */
	private void initHeavenYear(int year) {
		int dy = year - 3;
		int mdy = dy % 10;
		if (mdy <= 0) {
			mdy = 10;
		}
		this.heavenYear = mdy;
	}

	/**
	 * 计算年地支
	 * 
	 * @param year
	 * @return
	 */
	private void initEarthYear(int year) {
		int dy = year - 3;
		int py = dy % 12;
		if (py <= 0) {
			py = 12;
		}
		this.earthYear = py;
	}

	/**
	 * 计算天干月柱 年干*2+月数
	 * 
	 * @return
	 */
	private void initHeavenMonth() {
		int yH = this.heavenYear;
		int lM = this.lunarMonth;
		int mH = yH * 2 + lM;
		int mRH = mH % 10;
		if (mRH == 0) {
			mRH = 10;
		}
		this.heavenMonth = mRH;
	}

	/**
	 * 计算地支月柱 地支是固定不变的
	 * 
	 * @return
	 */

	private void initEarthMonth() {
		this.earthMonth = this.lunarMonth;
	}

	private static final String[] EARTH_MONTH = { "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌", "亥", "子", "丑" };

	/**
	 * 获取日天干 G=4C+[C/4]+5y+[y/4]+[3*(M+1)/5]+d-3 C是世纪数减1 y是年份后两位 M是月份 d是日数
	 * 若遇到1月和2月按上一年的13月和14月处理
	 * 
	 * @return
	 */
	private void initHeavenDay(int year, int month, int day) {
		if (month == 1 || month == 2) {
			year = year - 1;
			month = 12 + month;
		}
		int yC = year / 100;// 获取世纪数
		int yY = year % 100;// 取年份后两位
		int c2 = yC / 4;
		int y4 = yY / 4;
		int m3 = 3 * (month + 1) / 5;
		int sG = 5 * (yC + yY) + c2 + y4 + m3 + day - 3 - yC;
		sG = sG % 10;
		if (sG <= 0) {
			sG = 10;
		}
		this.heavenDay = sG;
	}

	/**
	 * 获取日地支 Z=8C+[20/4]+4y+[y/4]+[3*(M+1)/5]+d+7=i 奇数月i=0 偶数月i=6
	 * 
	 * @return
	 */
	private void initEarthDay(int year, int month, int day) {
		if (month == 1 || month == 2) {
			year = year - 1;
			month = 12 + month;
		}
		int yC = year / 100;// 获取世纪数
		int yY = year % 100;// 取年份后两位
		int c2 = yC / 4;
		int y4 = yY / 4;
		int m3 = 3 * (month + 1) / 5;
		int i = 0;
		if (month % 2 == 0) {
			i = 6;
		}
		int sZ = 8 * yC + c2 + 5 * yY + y4 + m3 + day + 7 + i;

		sZ = sZ % 12;
		if (sZ <= 0) {
			sZ = 12;
		}
		this.earthDay = sZ;
	}

	private void initEarthHour(int hour) {
		int eh = getCommEarthHour(hour);
		this.earthHour = eh;
	}

	/**
	 * 获取时支 是固定的
	 * 
	 * @return
	 */
	private int getCommEarthHour(int hour) {
		int eH = -1;
		if (hour == 23 || hour == 0) {
			eH = 1;
		} else if (hour == 2 || hour == 1) {
			eH = 2;
		} else if (hour == 4 || hour == 3) {
			eH = 3;
		} else if (hour == 6 || hour == 5) {
			eH = 4;
		} else if (hour == 8 || hour == 7) {
			eH = 5;
		} else if (hour == 10 || hour == 9) {
			eH = 6;
		} else if (hour == 12 || hour == 11) {
			eH = 7;
		} else if (hour == 14 || hour == 13) {
			eH = 8;
		} else if (hour == 16 || hour == 15) {
			eH = 9;
		} else if (hour == 18 || hour == 17) {
			eH = 10;
		} else if (hour == 20 || hour == 19) {
			eH = 11;
		} else if (hour == 22 || hour == 21) {
			eH = 12;
		}
		return eH;
	}

	/**
	 * 获取时干 日干*2+时支数-2 时干数超过10需要减10 支取各位数0
	 * 
	 * @return
	 */
	private void initHeavenHour() {
		int dG = this.heavenDay;
		int hZ = this.earthHour;
		int hG = 2 * dG + hZ - 2;
		int rHg = hG % 10;
		if (rHg == 0) {
			rHg = 10;
		}
		this.heavenHour = rHg;
	}

	public int getHeavenYear() {
		return this.heavenYear;
	}

	public String getHeavenYearStr() {
		return Tianan[getHeavenYear() - 1];
	}

	public int getEarthYear() {
		return earthYear;
	}

	public String getEarthYearStr() {
		return Deqi[getEarthYear() - 1];
	}

	public int getHeavenMonth() {
		return heavenMonth;
	}

	public String getHeavenMonthStr() {
		return Tianan[getHeavenMonth() - 1];
	}

	public int getEarthMonth() {
		return getLunarMonth();
	}

	public String getEarthMonthStr() {
		return EARTH_MONTH[getEarthMonth() - 1];
	}

	public int getHeavenDay() {
		return heavenDay;
	}

	public String getHeavenDayStr() {
		return Tianan[getHeavenDay() - 1];
	}

	public int getEarthDay() {
		return earthDay;
	}

	public String getEarthDayStr() {
		return Deqi[getEarthDay() - 1];
	}

	public int getHeavenHour() {
		return heavenHour;
	}

	public String getHeavenHourStr() {
		return Tianan[getHeavenHour() - 1];
	}

	public int getEarthHour() {
		return earthHour;
	}

	public String getEarthHourStr() {
		return Deqi[getEarthHour() - 1];
	}

}
