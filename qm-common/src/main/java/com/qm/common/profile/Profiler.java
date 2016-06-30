package com.qm.common.profile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Profiler {

	private static final Logger LOGGER = LoggerFactory.getLogger(Profiler.class);

	public static final Beacon start(String func, String key, String msg) {
		Beacon b = new Beacon(LOGGER, func, key, msg);
		b.start();
		return b;
	}

	public static final void fail(Beacon b) {
		if (b != null) {
			b.fail();
		}
	}

	public static final void end(Beacon b) {
		if (b != null) {
			b.end();
		}
	}

	private Profiler() {
	}
}
