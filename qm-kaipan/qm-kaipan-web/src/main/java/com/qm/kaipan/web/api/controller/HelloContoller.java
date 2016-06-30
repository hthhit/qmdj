package com.qm.kaipan.web.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class HelloContoller {

	protected static final Logger LOGGER = LoggerFactory.getLogger(HelloContoller.class);

	@RequestMapping(value = "/hello")
	@ResponseStatus(value = HttpStatus.OK)
	public void hello() {
		LOGGER.debug("#### hello test: {}");
	}

}
