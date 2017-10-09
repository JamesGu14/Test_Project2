package com.utility;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.mybatis.business.StockHistoryRepository;
import com.mybatis.model.StockHistory;
import com.mybatis.model.StockHistoryExample;

public class Utility {

	public static SqlSession GetSqlSession() throws IOException {
		InputStream inputStream = Resources.getResourceAsStream("resource.xml");
		SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);

		return factory.openSession(true);
	}

	@SuppressWarnings("deprecation")
	public static Date ConvertToDate(String dateStr, String splitter) throws Exception {
		if(!dateStr.contains(splitter) || dateStr.split(splitter).length != 3) {
			throw new Exception("Invalid date format string");
		}
		return new Date(Integer.parseInt(dateStr.split(splitter)[0]) - 1900, Integer.parseInt(dateStr.split(splitter)[1]) - 1, Integer.parseInt(dateStr.split(splitter)[2]));
	}
	
	public static String GetPropByKey(String propFile, String key) throws IOException {
		String value = "";	
		
		Properties prop = new Properties();
		InputStream propIn = new BufferedInputStream(new FileInputStream(propFile));
		prop.load(propIn); /// 加载属性列表
		value = prop.getProperty(key);
		propIn.close();
		
		return value;
	}

	// 验证那天之后涨没涨
	public static BigDecimal VerifySingleDayHypo(StockHistory stockHistory) {
		SqlSession sqlSession = null;
		try {
			sqlSession = Utility.GetSqlSession();
			StockHistoryRepository stockHistoryRepository = new StockHistoryRepository(sqlSession);
			
			StockHistoryExample example = new StockHistoryExample();
			example.createCriteria().andStockIdEqualTo(stockHistory.getStockId()).andStockDayGreaterThan(stockHistory.getStockDay());
			List<StockHistory> nextTenDays = stockHistoryRepository.getStockHistoryDao().selectByExampleWithRowbounds(example, new RowBounds(0, 10));
			
			BigDecimal maxPrice = Collections.max(nextTenDays, new MaxPriceComparator()).getMaxPrice();
			return maxPrice.subtract(nextTenDays.get(0).getOpenPrice()).divide(nextTenDays.get(0).getOpenPrice(), 4, RoundingMode.HALF_UP);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
		
		return null;
	}
}
