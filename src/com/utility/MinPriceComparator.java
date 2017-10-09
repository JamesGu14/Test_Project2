package com.utility;

import java.util.Comparator;

import com.mybatis.model.StockHistory;

public class MinPriceComparator implements Comparator<StockHistory> {

	@Override
	public int compare(StockHistory s1, StockHistory s2) {
		return s1.getMinPrice().compareTo(s2.getMinPrice());
	}
}