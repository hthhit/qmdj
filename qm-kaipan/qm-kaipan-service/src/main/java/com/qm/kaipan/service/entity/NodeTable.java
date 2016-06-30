package com.qm.kaipan.service.entity;

/**
 * 用来记录关键节点对应的时间
 * 
 * @author yahets
 *
 */
public class NodeTable extends NodeRow {

	public NodeTable() {
	}

	public NodeTable(int key, String name) {
		super(key, name);
	}

	public NodeTable(int key, String name, String dateTime) {
		super(key, name);
		this.dateTime = dateTime;
	}

	/**
	 * 记录节气对应的具体时间点
	 */
	private String dateTime;

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

}
