package com.qm.kaipan.service.entity;

import org.codehaus.jackson.map.annotate.JsonSerialize;

public class JsonResult {
	public interface Code {
		/**
		 * 0表示成功
		 */
		public static final int CODE_SUCCESS = 0;
		/**
		 * 异常 1001-2000
		 */
		public static final int CODE_EXCEPTION = 1001;
		/**
		 * 查询错误 20001-3000
		 */
		public static final int CODE_FAILED = 2001;

	}

	public interface Msg {
		public static final String  MSG_SUCCESS="请求成功";
	}

	private int resultCode;
	private String resultMsg;
	private Object obj;

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public int getResultCode() {
		return resultCode;
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

	public static JsonResult setJsonResult(int code, String msg) {
		JsonResult jsonResult = new JsonResult();
		jsonResult.setResultCode(code);
		jsonResult.setResultMsg(msg);
		return jsonResult;
	}

	public static JsonResult setJsonResult(int code, String msg, Object obj) {
		JsonResult jsonResult = new JsonResult();
		jsonResult.setResultCode(code);
		jsonResult.setResultMsg(msg);
		jsonResult.setObj(obj);
		return jsonResult;
	}

}
