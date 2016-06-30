package com.qm.kaipan.service;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import com.qm.kaipan.service.utils.ChineseCalendar;

/**
 * Unit test for simple App.
 */
public class HelloWorldTest {

	// @Test
	public void testData() {
		ChineseCalendar cc = new ChineseCalendar(false, 2016, 1, 9);
		System.out.println(cc.getSimpleChineseDateString());
	}

	@Test
	public void testQmA() {
		Date date = new Date();
		long dateNow = date.getTime();
		Calendar nC = Calendar.getInstance();
		nC.setTime(date);
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, nC.get(Calendar.YEAR));
		c.set(Calendar.MONTH, nC.get(Calendar.MONTH));
		c.set(Calendar.DAY_OF_MONTH, nC.get(Calendar.DAY_OF_MONTH) + 1);
		long diff = c.getTimeInMillis() - nC.getTimeInMillis();
		System.out.println(diff);
	}

}
