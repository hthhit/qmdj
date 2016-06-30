package com.qm.kaipan.service.entity;

import java.util.Map;

import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class QmResultEntity {
	Map<Integer, NodeRow> earthPan;
	Map<Integer, NodeRow> heavenPan;
	Map<Integer, NodeRow> starPan;
	Map<Integer, NodeRow> godPan;
	Map<Integer, NodeRow> gatePan;

	public Map<Integer, NodeRow> getHeavenPan() {
		return heavenPan;
	}

	public void setHeavenPan(Map<Integer, NodeRow> heavenPan) {
		this.heavenPan = heavenPan;
	}

	public Map<Integer, NodeRow> getEarthPan() {
		return earthPan;
	}

	public void setEarthPan(Map<Integer, NodeRow> earthPan) {
		this.earthPan = earthPan;
	}

	public Map<Integer, NodeRow> getStarPan() {
		return starPan;
	}

	public void setStarPan(Map<Integer, NodeRow> starPan) {
		this.starPan = starPan;
	}

	public Map<Integer, NodeRow> getGodPan() {
		return godPan;
	}

	public void setGodPan(Map<Integer, NodeRow> godPan) {
		this.godPan = godPan;
	}

	public Map<Integer, NodeRow> getGatePan() {
		return gatePan;
	}

	public void setGatePan(Map<Integer, NodeRow> gatePan) {
		this.gatePan = gatePan;
	}

}
