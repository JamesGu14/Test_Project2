package com.algorithm.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.algorithm.form.CandleForm;
import com.mybatis.model.StockHistory;

public class Test {
	
	@org.junit.Test
	public void TestThreeSmallRaise() {
		StockHistory day1 = new StockHistory(1, 1, null, new BigDecimal("1"), new BigDecimal("0.94"), null, null, null, null, null, null, null, null);
		StockHistory day2 = new StockHistory(1, 1, null, new BigDecimal("0.94"), new BigDecimal("0.94"), null, null, null, null, null, null, null, null);
		StockHistory day3 = new StockHistory(1, 1, null, new BigDecimal("0.95"), new BigDecimal("0.96"), null, null, null, null, null, null, null, null);
		
		List<StockHistory> list = new ArrayList<StockHistory>();
		list.add(day1);
		list.add(day2);
		list.add(day3);
		
		System.out.println(CandleForm.JustifyTwinPeak(list));
	}

	@org.junit.Test
	public void MyTest() {
		
		float a = (float)1.11;
		float b = (float)2.22;
		System.out.println(a < b);
	}
}
