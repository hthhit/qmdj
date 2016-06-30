package com.qm.kaipan.service.entity;

import java.util.Calendar;
import java.util.Date;

/**
 * 四柱
 * 
 */
public class FourStem {
	private StemNode yearNode;
	private StemNode monthNode;
	private StemNode dayNode;
	private StemNode hourNode;

	public FourStem() {
	}
	public FourStem(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
	}

	public FourStem(int hYear, int eYear, int hMonth, int eMonth, int hDay, int eDay, int hHour, int eHour) {
		this.yearNode = new StemNode(hYear, eYear);
		this.monthNode = new StemNode(hMonth, eMonth);
		this.dayNode = new StemNode(hDay, eDay);
		this.hourNode = new StemNode(hHour, eHour);
	}

	public FourStem(StemNode yearNode, StemNode monthNode, StemNode dayNode, StemNode hourNode) {
		this.yearNode = yearNode;
		this.monthNode = monthNode;
		this.dayNode = dayNode;
		this.hourNode = hourNode;
	}

	public StemNode getYearNode() {
		return yearNode;
	}

	public void setYearNode(StemNode yearNode) {
		this.yearNode = yearNode;
	}

	public StemNode getMonthNode() {
		return monthNode;
	}

	public void setMonthNode(StemNode monthNode) {
		this.monthNode = monthNode;
	}

	public StemNode getDayNode() {
		return dayNode;
	}

	public void setDayNode(StemNode dayNode) {
		this.dayNode = dayNode;
	}

	public StemNode getHourNode() {
		return hourNode;
	}

	public void setHourNode(StemNode hourNode) {
		this.hourNode = hourNode;
	}

}
