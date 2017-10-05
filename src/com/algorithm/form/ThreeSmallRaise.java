package com.algorithm.form;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.InvalidParameterException;
import java.util.List;

import com.mybatis.model.StockHistory;

public class ThreeSmallRaise {

	public static boolean Justify(List<StockHistory> fourDaysHistory) {
		
		if (fourDaysHistory.size() != 4) {
			throw new InvalidParameterException("Require 4 days history");
		}
		
		boolean satisfied = true;
		
		StockHistory firstDay = fourDaysHistory.get(0);
		
		if (firstDay.getClosePrice().subtract(firstDay.getOpenPrice()).divide(firstDay.getOpenPrice(), 4, RoundingMode.HALF_UP).compareTo(new BigDecimal("-0.05")) > 0) {
			satisfied = false;
		}
		
		for(int i = 1; i < 4; i ++) {
			StockHistory today = fourDaysHistory.get(i);
			if (today.getClosePrice().subtract(today.getOpenPrice()).compareTo(new BigDecimal(0)) <= 0
				|| today.getClosePrice().subtract(today.getOpenPrice()).divide(today.getOpenPrice(), 4, RoundingMode.HALF_UP).compareTo(new BigDecimal("0.02")) > 0) {
				satisfied = false;
			}
		}
		
		return satisfied;
	}

}
