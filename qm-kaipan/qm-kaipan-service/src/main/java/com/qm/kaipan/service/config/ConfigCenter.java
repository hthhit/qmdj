package com.qm.kaipan.service.config;

import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("configCenter")
public class ConfigCenter extends TimerTask {
	private static final Logger LOGGER = LoggerFactory.getLogger(ConfigCenter.class);

	@PostConstruct
	public void init() {
		Timer t = new Timer();
		t.schedule(this, 0, 1000);
	}

	@Override
	public void run() {
		LOGGER.info("开始加载配置");
	}

}
