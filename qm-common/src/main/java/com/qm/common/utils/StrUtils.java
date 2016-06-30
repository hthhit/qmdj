package com.qm.common.utils;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StrUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(StrUtils.class);

	public static final boolean equals(String a, String b) {
		return a == null ? b == null : a.equals(b);
	}

	public static final String minString(String a, String b) {
		return (a == null || (b != null && a.compareTo(b) < 0)) ? b : a;
	}

	public static final String replaceCharAt(String s, int index, char val) {
		if (isEmpty(s) || index < 0 || index >= s.length()) {
			return s;
		}
		StringBuilder sb = new StringBuilder(s);
		sb.deleteCharAt(index);
		sb.insert(index, val);
		return sb.toString();
	}

	public static final boolean testCharAt(String s, int index, char val) {
		if (isEmpty(s) || index < 0 || index >= s.length()) {
			return false;
		}
		return s.charAt(index) == val;
	}

	public static final String concat(Object prefix, Object... objs) {
		StringBuilder sb = new StringBuilder();
		if (prefix != null) {
			sb.append(prefix);
		}
		for (Object o : objs) {
			if (o != null) {
				sb.append(o);
			}
		}
		return sb.toString();
	}

	public static final String concat(List<?> objs) {
		StringBuilder sb = new StringBuilder();
		for (Object o : objs) {
			if (o != null) {
				sb.append(o);
			}
		}
		return sb.toString();
	}

	public static final String concat(Set<?> objs) {
		StringBuilder sb = new StringBuilder();
		for (Object o : objs) {
			if (o != null) {
				sb.append(o);
			}
		}
		return sb.toString();
	}

	public static final String join(Object separator, Object... objs) {
		StringBuilder sb = new StringBuilder();
		boolean isFirst = true;
		for (Object o : objs) {
			if (isFirst) {
				isFirst = false;
			} else {
				sb.append(separator);
			}
			if (o != null) {
				sb.append(o);
			}
		}
		return sb.toString();
	}

	public static final String join(Object separator, List<?> objs) {
		StringBuilder sb = new StringBuilder();
		boolean isFirst = true;
		for (Object o : objs) {
			if (isFirst) {
				isFirst = false;
			} else {
				sb.append(separator);
			}
			if (o != null) {
				sb.append(o);
			}
		}
		return sb.toString();
	}

	public static final String join(Object separator, Set<?> objs) {
		StringBuilder sb = new StringBuilder();
		boolean isFirst = true;
		for (Object o : objs) {
			if (isFirst) {
				isFirst = false;
			} else {
				sb.append(separator);
			}
			if (o != null) {
				sb.append(o);
			}
		}
		return sb.toString();
	}

	public static final String trim(String s) {
		return s == null ? null : s.trim();
	}

	public static final boolean isEmpty(String s) {
		return (s == null || s.isEmpty());
	}

	public static final boolean isAnyEmpty(String... strings) {
		for (String s : strings) {
			if (isEmpty(s)) {
				return true;
			}
		}
		return false;
	}

	public static final boolean isNotEmpty(String s) {
		return (s != null && !s.isEmpty());
	}

	public static final boolean isBlank(String s) {
		return (s == null || s.isEmpty() || s.trim().isEmpty());
	}

	public static final boolean isAnyBlank(String... strings) {
		for (String s : strings) {
			if (isBlank(s)) {
				return true;
			}
		}
		return false;
	}

	public static final boolean isNotBlank(String s) {
		return (s != null && !s.isEmpty() && !s.trim().isEmpty());
	}

	public static final String reverse(String s) {
		StringBuilder sb = new StringBuilder(s);
		return sb.reverse().toString();
	}

	public static final String toString(Object obj) {
		return null == obj ? "" : obj.toString();
	}

	public static final int parseInt(String s, int defVal) {
		if (isEmpty(s)) {
			return defVal;
		}
		try {
			return Integer.parseInt(s);
		} catch (NumberFormatException e) {
			LOGGER.warn("{} parseInt failed: {}", s, e.getMessage());
		}
		return defVal;
	}

	public static final double parseDouble(String s, double defVal) {
		if (isEmpty(s)) {
			return defVal;
		}
		try {
			return Double.parseDouble(s);
		} catch (Exception e) {
			LOGGER.warn("{} parseDouble failed: {}", s, e.getMessage());
		}
		return defVal;
	}

	public static final long parseLong(String s, long defVal) {
		if (isEmpty(s)) {
			return defVal;
		}
		try {
			return Long.parseLong(s);
		} catch (Exception e) {
			LOGGER.warn("{} parseLong failed: {}", s, e.getMessage());
		}
		return defVal;
	}

	public static final int ver2Num(String ver, int defVal) {
		if (isBlank(ver)) {
			return defVal;
		}
		String[] items = ver.split("\\.");
		if (items.length != 3) {
			return defVal;
		}
		int ret = 0;
		for (String i : items) {
			int num = parseInt(i, -1);
			if (num < 0) {
				return defVal;
			}
			ret = (ret << 8) + num;
		}
		return ret;
	}
}
