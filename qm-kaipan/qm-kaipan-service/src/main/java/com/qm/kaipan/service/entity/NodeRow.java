package com.qm.kaipan.service.entity;

public class NodeRow {
	/**
	 * 记录算法中的对应序数
	 */
	private int key;
	/**
	 * 记录对应的值
	 */
	private String name;

	public NodeRow(int key, String name) {
		this.key = key;
		this.name = name;
	}

	public NodeRow() {
		super();
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
