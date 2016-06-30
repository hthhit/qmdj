package com.qm.common.utils;

import java.util.regex.Pattern;

public class RegUtils {

	public static final Pattern DATE_PATTERN = Pattern.compile("^201[5-9]-[01]\\d-[0-3]\\d$");

	public static final Pattern PHONE_CH_PATTERN = Pattern.compile("^1[34578]\\d{9}$");

	public static final Pattern MD5_PATTERN = Pattern.compile("^[\\wa-z]{32}$");

	public static final Pattern IDCARD_PATTERN = Pattern
			.compile("^[1-8][0-7]\\d{4}(19|20)\\d{2}[01]\\d[0-3]\\d{4}[\\dxX]$");

	public static final Pattern NUM_PATTERN = Pattern.compile("^-?\\d+(\\.\\d*)?$");

	public static final Pattern DOMAIN_PATTERN = Pattern.compile("http[s]?://[\\w\\.]+\\.daba\\.cn.*");

	public static final Pattern VER_PATTERN = Pattern.compile("^[1-9]\\.\\d+\\.\\d+$");

	public static final Pattern TIME_PATTERN = Pattern.compile("^[0-2]\\d:[0-5]\\d(:[0-5]\\d)?$");

	public static final boolean matches(Pattern pat, String s) {
		if (StrUtils.isBlank(s)) {
			return false;
		}
		return pat.matcher(s).matches();
	}

	// HH:mm or HH:mm:ss
	public static final boolean isTime(String s) {
		return matches(TIME_PATTERN, s);
	}

	public static final boolean isPhoneCH(String s) {
		return matches(PHONE_CH_PATTERN, s);
	}

	public static final boolean isMD5(String s) {
		return matches(MD5_PATTERN, s);
	}

	public static final boolean isDate(String s) {
		return matches(DATE_PATTERN, s);
	}

	public static final boolean isIDCard(String s) {
		return matches(IDCARD_PATTERN, s);
	}

	public static final boolean isNumber(String s) {
		return matches(NUM_PATTERN, s);
	}

	public static final boolean isValidDomain(String url) {
		return matches(DOMAIN_PATTERN, url);
	}

	public static final boolean isValidVersion(String ver) {
		return matches(VER_PATTERN, ver);
	}

	private RegUtils() {
	}
}
