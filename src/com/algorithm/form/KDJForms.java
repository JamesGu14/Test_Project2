package com.algorithm.form;

import com.mybatis.model.StockKdj;

public class KDJForms {

	public static boolean IsGoldenCross(StockKdj yesterdayKdj, StockKdj todayKdj) {
		if (yesterdayKdj.getK() > yesterdayKdj.getJ() && yesterdayKdj.getD() > yesterdayKdj.getJ() && todayKdj.getK() < todayKdj.getJ() && todayKdj.getD() < todayKdj.getJ()) {
			return true;
		}
		return false;
	}

	public static boolean IsDeadCross(StockKdj yesterdayKdj, StockKdj todayKdj) {
		if (yesterdayKdj.getK() < yesterdayKdj.getJ() && yesterdayKdj.getD() < yesterdayKdj.getJ() && todayKdj.getK() > todayKdj.getJ() && todayKdj.getD() > todayKdj.getJ()) {
			return true;
		}
		return false;
	}

}
