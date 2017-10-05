package com.algorithm.form;

import com.mybatis.model.StockMacd;

public class MacdForms {
	
	public static boolean IsGoldenCross(StockMacd yesterdayMacd, StockMacd todayMacd) {
		
		if (yesterdayMacd.getDiff() < yesterdayMacd.getDea() && todayMacd.getDiff() > todayMacd.getDea()) {
			return true;
		}
		return false;
	}
	
	public static boolean IsDeadCross(StockMacd yesterdayMacd, StockMacd todayMacd) {
		
		if (yesterdayMacd.getDiff() >= yesterdayMacd.getDea() && todayMacd.getDiff() < todayMacd.getDea()) {
			return true;
		}
		return false;
	}
}
