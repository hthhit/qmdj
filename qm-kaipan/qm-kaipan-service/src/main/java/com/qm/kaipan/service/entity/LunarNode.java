package com.qm.kaipan.service.entity;

public class LunarNode extends NodeRow {
	/**
	 * 1表示大月 2表示小月 3表示润月
	 */
	private int monthType;
	private String monthTypeName;
	private String dateTime;

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public int getMonthType() {
		return monthType;
	}

	public void setMonthType(int monthType) {
		setMonthTypeName(monthType);
		this.monthType = monthType;
	}

	public String getMonthTypeName() {
		return monthTypeName;
	}

	public void setMonthTypeName(int monthType) {
		if (1 == monthType) {
			this.monthTypeName = "大";
		} else if (2 == monthType) {
			this.monthTypeName = "小";
		} else if (3 == monthType) {
			this.monthTypeName = "润";
		} else {
			this.monthTypeName = "未识别";
		}
	}

	public void setMonthTypeName(String monthTypeName) {
		this.monthTypeName = monthTypeName;
	}

}
