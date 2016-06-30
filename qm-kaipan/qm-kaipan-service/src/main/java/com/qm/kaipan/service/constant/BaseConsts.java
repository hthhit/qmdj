package com.qm.kaipan.service.constant;

import java.util.HashMap;
import java.util.Map;

public class BaseConsts {
	/**
	 * 10天干
	 */
	public static final Map<Integer, String> HEAVEN_STEM = new HashMap<Integer, String>() {
		private static final long serialVersionUID = -2544197918463890638L;

		{
			put(1, "甲");
			put(2, "乙");
			put(3, "丙");
			put(4, "丁");
			put(5, "戊");
			put(6, "己");
			put(7, "庚");
			put(8, "辛");
			put(9, "壬");
			put(10, "癸");
		}
	};
	/**
	 * 12地支常量
	 */
	public static final Map<Integer, String> EARTH_STEM = new HashMap<Integer, String>() {
		private static final long serialVersionUID = -7945952544136817050L;

		{
			put(1, "子");
			put(2, "丑");
			put(3, "寅");
			put(4, "卯");
			put(5, "辰");
			put(6, "巳");
			put(7, "午");
			put(8, "未");
			put(9, "申");
			put(10, "酉");
			put(11, "戌");
			put(12, "亥");
		}
	};
}
