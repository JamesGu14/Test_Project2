package com.mybatis.business;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.mybatis.dao.StockTradingDateDao;
import com.mybatis.model.StockTradingDate;

public class StockTradingDateRepository {

	private StockTradingDateDao stockTradingDateDao;

	public StockTradingDateDao getStockTradingDateDao() {
		return stockTradingDateDao;
	}

	public StockTradingDateRepository(SqlSession sqlSession) {
		stockTradingDateDao = sqlSession.getMapper(StockTradingDateDao.class);
	}

	public List<StockTradingDate> GetAllStockTradingDate() {
		return stockTradingDateDao.selectByExample(null);
	}
}
