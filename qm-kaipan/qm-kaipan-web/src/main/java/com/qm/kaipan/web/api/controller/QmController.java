package com.qm.kaipan.web.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qm.kaipan.web.api.view.JsonView;

@Controller
@RequestMapping(value = "/qm")
public class QmController extends BaseController {
	@RequestMapping(value = "/pan")
	@ResponseBody
	public JsonView queryQmPan() {
		return JsonView.setReturn(0, "成功");
	}
}
