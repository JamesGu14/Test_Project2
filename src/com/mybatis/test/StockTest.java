package com.mybatis.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import com.mybatis.dao.StockDao;
import com.mybatis.model.Stock;
import com.mybatis.model.StockExample;

public class StockTest {

	private SqlSession GetSqlSession() throws IOException {
		InputStream inputStream = Resources.getResourceAsStream("resource.xml");
		SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);

		return factory.openSession(true);
	}

	@Test
	public void GetTest() throws IOException {
		SqlSession sqlSession = GetSqlSession();
		StockDao stockDao = sqlSession.getMapper(StockDao.class);
		
		StockExample stockExample = new StockExample();
		StockExample.Criteria stockCriteria = stockExample.createCriteria();
		stockCriteria.andIdEqualTo(10);
		
		List<Stock> stockList = stockDao.selectByExample(stockExample);
		
		for (Stock stock : stockList) {
			System.out.println(stock.getStockName());
			System.out.println(stock.getStockCode());
		}
//		Stock stock = stockDao.getById(1);
//
//		System.out.println(stock.getId());
//		System.out.println(stock.getStockName());
//		System.out.println(stock.getPinyin());
		sqlSession.close();
	}

}
