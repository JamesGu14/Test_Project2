package com.algorithm.form;

import java.util.List;

import com.mybatis.model.StockMa;

public class MAForms {

	/**
	 * 均线多头排列，通常为3天以上
	 * 
	 * @param maList
	 * @return
	 */
	public static boolean IsLongMa(List<StockMa> maList) {

		for (StockMa ma : maList) {
			if (ma.getMa30().compareTo(ma.getMa20()) < 0 || ma.getMa20().compareTo(ma.getMa10()) < 0 || ma.getMa10().compareTo(ma.getMa5()) < 0) {
				return false;
			}
		}
		return true;
	}
}
