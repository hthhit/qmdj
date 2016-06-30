package com.qm.common.profile;

import com.qm.common.utils.DateUtils;

public class Clock {
	
	private long millis;
	
	public Clock() {
		start();
	}

	public void start() {
		millis = DateUtils.currAbsMillis();
	}
	public long stop() {
		millis = DateUtils.currAbsMillis() - millis;
		return millis;
	}
	
	public long getMillis() {
		return millis;
	}
	
	public String toString() {
		return String.format("==== COST: %dms ====", millis);
	}
}
