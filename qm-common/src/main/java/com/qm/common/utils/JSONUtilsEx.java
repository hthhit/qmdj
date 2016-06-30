package com.qm.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

public class JSONUtilsEx {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(JSONUtilsEx.class);

	private JSONUtilsEx() {}
	
	public static final int getIntValue(JSONObject jo, String key, int defVal) {
		try {
			return jo.getIntValue(key);
		} catch (Exception e) {
			LOGGER.error("JSONObject getIntValue {} failed:", key, e);
		}
		return defVal;
	}
	
	public static final long getLongValue(JSONObject jo, String key, long defVal) {
		try {
			return jo.getLongValue(key);
		} catch (Exception e) {
			LOGGER.error("JSONObject getLongValue {} failed:", key, e);
		}
		return defVal;
	}

	public static final double getDoubleValue(JSONObject jo, String key, double defVal) {
		try {
			return jo.getDoubleValue(key);
		} catch (Exception e) {
			LOGGER.error("JSONObject getDoubleValue {} failed:", key, e);
		}
		return defVal;
	}
}
