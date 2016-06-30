package com.qm.kaipan.web.api.view;

import org.codehaus.jackson.map.annotate.JsonSerialize;

public class JsonView {

	public int code;
	public String msg;
	public int Object;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getObject() {
		return Object;
	}

	public void setObject(int object) {
		Object = object;
	}

	public static JsonView setReturn(int code, String msg) {
		JsonView jv = new JsonView();
		jv.setCode(code);
		jv.setMsg(msg);
		return jv;
	}

}
