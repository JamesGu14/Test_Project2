package com.mybatis.business;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.mybatis.dao.StockKdjDao;
import com.mybatis.model.StockKdj;
import com.mybatis.model.StockKdjExample;

public class StockKdjRepository {

	private StockKdjDao stockKdjDao;

	public StockKdjDao getStockKdjDao() {
		return stockKdjDao;
	}

	public StockKdjRepository(SqlSession sqlSession) {
		stockKdjDao = sqlSession.getMapper(StockKdjDao.class);
	}

	public List<StockKdj> GetStockKdjByStockid(int stockId) {
		StockKdjExample stockKdjExample = new StockKdjExample();
		stockKdjExample.createCriteria().andStockIdEqualTo(stockId);
		List<StockKdj> stockKdjList = stockKdjDao.selectByExample(stockKdjExample);

		return stockKdjList;
	}
}
