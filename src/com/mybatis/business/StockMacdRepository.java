package com.mybatis.business;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.mybatis.dao.StockMacdDao;
import com.mybatis.model.StockMacd;
import com.mybatis.model.StockMacdExample;

public class StockMacdRepository {
	private StockMacdDao stockMacdDao;

	public StockMacdDao getStockMacdDao() {
		return stockMacdDao;
	}

	public StockMacdRepository(SqlSession sqlSession) {
		stockMacdDao = sqlSession.getMapper(StockMacdDao.class);
	}

	public List<StockMacd> GetStockMacdByStockId(int stockId) {
		StockMacdExample stockMacdExample = new StockMacdExample();
		stockMacdExample.createCriteria().andStockIdEqualTo(stockId);
		stockMacdExample.setOrderByClause("stock_day");

		return stockMacdDao.selectByExample(stockMacdExample);
	}

	public List<StockMacd> GetStockMacdByStockIdAndDate(int stockId, Date startDate, Date endDate) {
		StockMacdExample stockMacdExample = new StockMacdExample();
		if (endDate != null) {
			stockMacdExample.createCriteria().andStockIdEqualTo(stockId).andStockDayBetween(startDate, endDate);
		} else {
			stockMacdExample.createCriteria().andStockIdEqualTo(stockId).andStockDayGreaterThanOrEqualTo(startDate);
		}
		return stockMacdDao.selectByExample(stockMacdExample);
	}
}
