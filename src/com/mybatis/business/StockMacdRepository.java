package com.mybatis.business;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.mybatis.dao.StockMacdDao;
import com.mybatis.model.StockMacd;
import com.mybatis.model.StockMacdExample;
import com.utility.Utility;

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

		return stockMacdDao.selectByExample(stockMacdExample);
	}
}
