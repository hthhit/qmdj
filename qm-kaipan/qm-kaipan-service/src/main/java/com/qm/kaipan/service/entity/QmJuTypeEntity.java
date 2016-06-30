package com.qm.kaipan.service.entity;

public class QmJuTypeEntity {
	/**
	 * 阴局
	 */
	public static final int TYPE_LUNAR = 1;
	/**
	 * 阳局
	 */
	public static final int TYPE_SOLAR = 2;
	/**
	 * 起局类型
	 */
	private int type;
	/**
	 * 起局局数
	 */
	private int number;
	/**
	 * 起局描述
	 */
	private String desc;

	public QmJuTypeEntity(int type, int number) {
		this.type = type;
		this.number = number;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getDesc() {
		if (type == TYPE_LUNAR) {
			this.desc = "阴遁" + this.number + "局";
		} else if (type == TYPE_SOLAR) {
			this.desc = "阳遁" + this.number + "局";
		} else {
			this.desc = "没有对应相关局数";
		}
		return desc;
	}

}
