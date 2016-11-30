package com.awg.gpe.web.test;

import java.time.LocalDateTime;
import java.time.Month;

import org.junit.Ignore;
import org.junit.Assert;
import org.junit.Test;

import com.awg.gpe.utils.BusinessHours;

@Ignore
public class TestBusinessHours {
	/*
	@Test
	public void testBusinessHours() {
		LocalDateTime startDate = LocalDateTime.of(2016, Month.AUGUST, 30, 8, 0);
		LocalDateTime endDate = startDate.plus(BusinessHours.ofHours(4));
		Assert.assertEquals(LocalDateTime.of(2016, Month.AUGUST, 30, 12, 0), endDate);
	}
	
	@Test
	public void testBetweenDays() {
		LocalDateTime startDate = LocalDateTime.of(2016, Month.AUGUST, 30, 8, 0);
		LocalDateTime endDate = startDate.plus(BusinessHours.ofHours(10));
		Assert.assertEquals(LocalDateTime.of(2016, Month.AUGUST, 31, 10, 0), endDate);
	}
	
	@Test
	public void testBetweenWeekend() {
		LocalDateTime startDate = LocalDateTime.of(2016, Month.SEPTEMBER, 2, 8, 0);
		LocalDateTime endDate = startDate.plus(BusinessHours.ofHours(10));
		Assert.assertEquals(LocalDateTime.of(2016, Month.SEPTEMBER, 5, 10, 0), endDate);
	}
	
	@Test
	public void testBetweenDaysInMinutes() {
		LocalDateTime startDate = LocalDateTime.of(2016, Month.AUGUST, 30, 8, 30);
		LocalDateTime endDate = startDate.plus(BusinessHours.ofHours(8));
		Assert.assertEquals(LocalDateTime.of(2016, Month.AUGUST, 31, 8, 30), endDate);
	}
	
	@Test
	public void testBetweenWeekendInMinutes() {
		LocalDateTime startDate = LocalDateTime.of(2016, Month.SEPTEMBER, 2, 8, 30);
		LocalDateTime endDate = startDate.plus(BusinessHours.ofHours(8));
		Assert.assertEquals(LocalDateTime.of(2016, Month.SEPTEMBER, 5, 8, 30), endDate);
	}
*/
}
