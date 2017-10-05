package com.mybatis.business;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.mybatis.dao.StockMaDao;
import com.mybatis.model.StockMa;
import com.mybatis.model.StockMaExample;

public class StockMaRepository {

	private StockMaDao stockMaDao;

	public StockMaDao getStockMaDao() {
		return stockMaDao;
	}

	public StockMaRepository(SqlSession sqlSession) {
		stockMaDao = sqlSession.getMapper(StockMaDao.class);
	}

	public List<StockMa> GetStockMaByStockId(int stockId) {
		StockMaExample stockMaExample = new StockMaExample();
		stockMaExample.createCriteria().andStockIdEqualTo(stockId);

		return stockMaDao.selectByExample(stockMaExample);
	}
}
