package com.mybatis.business;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.mybatis.dao.StockDao;
import com.mybatis.model.Stock;
import com.mybatis.model.StockExample;

public class StockRepository {

	private StockDao stockDao;

	public StockRepository(SqlSession sqlSession) {
		stockDao = sqlSession.getMapper(StockDao.class);
	}

	public List<Stock> GetAllStocks() {
		List<Stock> allStocks = stockDao.selectByExample(null);
		return allStocks;
	}

	public List<Stock> GetStocksById(List<Integer> stockIds) {
		StockExample stockExample = new StockExample();
		stockExample.createCriteria().andIdIn(stockIds);

		return stockDao.selectByExample(stockExample);
	}

	public List<Stock> GetStockByStockCode(List<String> stockCodes) {
		StockExample stockExample = new StockExample();
		stockExample.createCriteria().andStockCodeIn(stockCodes);

		return stockDao.selectByExample(stockExample);
	}
}
