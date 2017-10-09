package com.mybatis.business;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;

import com.mybatis.dao.StockHistoryDao;
import com.mybatis.model.StockHistory;
import com.mybatis.model.StockHistoryExample;

public class StockHistoryRepository {
	
	private StockHistoryDao stockHistoryDao;
	
	public StockHistoryDao getStockHistoryDao() {
		return stockHistoryDao;
	}

	public StockHistoryRepository(SqlSession sqlSession) {
		stockHistoryDao = sqlSession.getMapper(StockHistoryDao.class);
	}
	
	public List<StockHistory> GetHistoryByStockId(int stockId) {
		StockHistoryExample stockHistoryExample = new StockHistoryExample();
		stockHistoryExample.createCriteria().andStockIdEqualTo(stockId);
		stockHistoryExample.setOrderByClause("stock_day");
		
		return stockHistoryDao.selectByExample(stockHistoryExample);
	}
	
	public List<StockHistory> GetHistoryByStockIdAndDate(int stockId, Date startDate, Date endDate) {
		StockHistoryExample stockHistoryExample = new StockHistoryExample();
		if (endDate != null) {
			stockHistoryExample.createCriteria().andStockIdEqualTo(stockId).andStockDayBetween(startDate, endDate);
		} else {
			stockHistoryExample.createCriteria().andStockIdEqualTo(stockId).andStockDayGreaterThanOrEqualTo(startDate);
		}
		
		return stockHistoryDao.selectByExample(stockHistoryExample);
	}

	public List<StockHistory> GetHistoryByStockIdAndDate(int stockId, Date startDate, int days) {
		StockHistoryExample stockHistoryExample = new StockHistoryExample();
		stockHistoryExample.createCriteria().andStockIdEqualTo(stockId).andStockDayGreaterThanOrEqualTo(startDate);
		stockHistoryExample.setOrderByClause("stock_day");
		RowBounds rowBounds = new RowBounds(0, days);
		return stockHistoryDao.selectByExampleWithRowbounds(stockHistoryExample, rowBounds);
	}
}
