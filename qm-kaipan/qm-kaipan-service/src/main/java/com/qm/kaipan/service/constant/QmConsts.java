package com.qm.kaipan.service.constant;

import java.util.HashMap;
import java.util.Map;

public class QmConsts extends BaseConsts {
	/**
	 * 九星排盘用
	 */
	public static final Map<Integer, String> EIGHT_STAR_PAN = new HashMap<Integer, String>() {
		private static final long serialVersionUID = 1L;
		{
			put(1, "天蓬星");
			put(2, "天任星");
			put(3, "天冲星");
			put(4, "天辅星");
			put(5, "天英星");
			put(6, "天芮星");
			put(7, "天柱星");
			put(8, "天心星");
		}
	};
	/**
	 * 九星计算直符
	 */
	public static final Map<Integer, String> EIGHT_STAR = new HashMap<Integer, String>() {
		private static final long serialVersionUID = 1L;
		{
			put(1, "天蓬星");
			put(2, "天芮星");
			put(3, "天冲星");
			put(4, "天辅星");
			put(5, "天禽星");
			put(6, "天心星");
			put(7, "天柱星");
			put(8, "天任星");
			put(9, "天英星");
		}
	};
	/**
	 * 八门排盘用
	 */
	public static final Map<Integer, String> EIGHT_GATE_PAN = new HashMap<Integer, String>() {
		private static final long serialVersionUID = 1L;
		{
			put(1, "休门");
			put(2, "生门");
			put(3, "伤门");
			put(4, "杜门");
			put(5, "景门");
			put(6, "死门");
			put(7, "惊门");
			put(8, "开门");
		}
	};
	/**
	 * 八门计算直使用
	 */
	public static final Map<Integer, String> EIGHT_GATE = new HashMap<Integer, String>() {
		private static final long serialVersionUID = 1L;
		{
			put(1, "休门");
			put(2, "死门");
			put(3, "伤门");
			put(4, "杜门");
			put(5, "中门");
			put(6, "开门");
			put(7, "惊门");
			put(8, "生门");
			put(9, "景门");
		}
	};
	/**
	 * 八神
	 */
	public static final Map<Integer, String> EIGHT_GOD = new HashMap<Integer, String>() {
		private static final long serialVersionUID = 1L;
		{
			put(1, "直符");
			put(2, "螣蛇");
			put(3, "太阴");
			put(4, "六合");
			put(5, "白虎");
			put(6, "玄武");
			put(7, "九地");
			put(8, "九天");
		}
	};

	/**
	 * 三奇六仪序数
	 */
	public static final Map<Integer, String> THREE_SIX = new HashMap<Integer, String>() {
		private static final long serialVersionUID = 1L;

		{
			put(1, "戊");
			put(2, "己");
			put(3, "庚");
			put(4, "辛");
			put(5, "壬");
			put(6, "癸");
			put(7, "丁");
			put(8, "丙");
			put(9, "乙");
		}
	};

}
