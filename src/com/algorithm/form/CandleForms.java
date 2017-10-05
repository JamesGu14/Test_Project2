package com.algorithm.form;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.InvalidParameterException;
import java.util.List;

import com.mybatis.model.StockHistory;
import com.mybatis.model.StockMa;

public class CandleForms {

	/**
	 * 双子峰
	 * 一天大跌，然后连续两天收小阳
	 * @param threeDayHistory
	 * @return
	 */
	public static boolean JustifyTwinPeak(List<StockHistory> threeDayHistory) {
		
		if (threeDayHistory.size() != 3) {
			throw new InvalidParameterException("Require 4 days history");
		}
		
		boolean satisfied = true;
		
		StockHistory firstDay = threeDayHistory.get(0);
		
		if (firstDay.getClosePrice().subtract(firstDay.getOpenPrice()).divide(firstDay.getOpenPrice(), 4, RoundingMode.HALF_UP).compareTo(new BigDecimal("-0.05")) > 0) {
			satisfied = false;
		}
		
		for(int i = 1; i < threeDayHistory.size(); i ++) {
			StockHistory today = threeDayHistory.get(i);
			if (today.getClosePrice().subtract(today.getOpenPrice()).compareTo(new BigDecimal(0)) <= 0
				|| today.getClosePrice().subtract(today.getOpenPrice()).divide(today.getOpenPrice(), 4, RoundingMode.HALF_UP).compareTo(new BigDecimal("0.02")) > 0) {
				satisfied = false;
			}
		}
		
		return satisfied;
	}

	/**
	 * 转折点
	 * 1. 在4根MA线以下
	 * 2. 红柱且长下影线
	 * 
	 */
	public static boolean TurnPoint(StockHistory stockHistory, StockMa stockMa) {
		
		// 是一个红柱
		if (stockHistory.getClosePrice().compareTo(stockHistory.getOpenPrice()) <= 0) {
			return false;
		}
		
		BigDecimal closePrice = stockHistory.getClosePrice();
		// 最高价(收盘价？)在4根MA之下
		if (stockMa.getMa5().compareTo(closePrice) <= 0) return false;
		if (stockMa.getMa10().compareTo(closePrice) <= 0) return false;
		if (stockMa.getMa20().compareTo(closePrice) <= 0) return false;
		if (stockMa.getMa30().compareTo(closePrice) <= 0) return false;
		
		
		
		return true;
	}
}
