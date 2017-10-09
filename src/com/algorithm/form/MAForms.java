package com.algorithm.form;

import com.mybatis.model.StockMa;

public class MAForms {

	/**
	 * 均线多头排列，通常为3天以上
	 * 
	 * @param maList
	 * @return
	 */
	public static boolean IsLongMa(StockMa ma) {

		if(ma.getMa20Raisedays() <= 3) return false;
		if(ma.getMa30Raisedays() <= 3) return false;
		
		if (ma.getMa30().compareTo(ma.getMa20()) >= 0 || ma.getMa20().compareTo(ma.getMa10()) >= 0 || ma.getMa10().compareTo(ma.getMa5()) >= 0) {
			return false;
		}
		return true;
	}
}
