package com.utility;

import java.util.Comparator;

import com.mybatis.model.StockHistory;

public class MaxPriceComparator implements Comparator<StockHistory> {

	@Override
	public int compare(StockHistory s1, StockHistory s2) {
		return s1.getMaxPrice().compareTo(s2.getMaxPrice());
	}
}