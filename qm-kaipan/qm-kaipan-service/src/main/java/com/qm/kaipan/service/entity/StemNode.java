package com.qm.kaipan.service.entity;

import com.qm.kaipan.service.constant.BaseConsts;

public class StemNode {
	/**
	 * 干支
	 */
	NodeRow heavenNode;
	/**
	 * 地址
	 */
	NodeRow earthNode;

	public NodeRow getHeavenNode() {
		return heavenNode;
	}

	public void setHeavenNode(NodeRow heavenNode) {
		this.heavenNode = heavenNode;
	}

	public NodeRow getEarthNode() {
		return earthNode;
	}

	public void setEarthNode(NodeRow earthNode) {
		this.earthNode = earthNode;
	}

	public StemNode(int heavenKey, int earthKey) {
		this.heavenNode = new NodeRow(heavenKey, BaseConsts.HEAVEN_STEM.get(heavenKey));
		this.earthNode = new NodeRow(earthKey, BaseConsts.EARTH_STEM.get(earthKey));
	}

	public StemNode(int heavenKey, String heavenStr, int earthKey, String earthStr) {
		this.heavenNode = new NodeRow(heavenKey, heavenStr);
		this.earthNode = new NodeRow(earthKey, earthStr);

	}

	public StemNode(NodeRow heavenNode, NodeRow earthNode) {
		this.heavenNode = heavenNode;
		this.earthNode = earthNode;
	}
}
