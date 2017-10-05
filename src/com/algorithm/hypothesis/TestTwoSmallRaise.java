package com.algorithm.hypothesis;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.algorithm.form.CandleForm;
import com.mybatis.business.StockHistoryRepository;
import com.mybatis.business.StockRepository;
import com.mybatis.model.Stock;
import com.mybatis.model.StockHistory;
import com.utility.Utility;

public class TestTwoSmallRaise {
	private SqlSession sqlSession;
	private StockRepository stockRepository;
	private StockHistoryRepository stockHistoryRepository;

	private List<Stock> allStocks;

	
	public TestTwoSmallRaise() {
		try {
			sqlSession = Utility.GetSqlSession();
			stockRepository = new StockRepository(sqlSession);
			stockHistoryRepository = new StockHistoryRepository(sqlSession);

			allStocks = stockRepository.GetAllStocks();

			Start(new SimpleDateFormat("yyyy-MM-dd").parse("2015-01-01"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
	}

	private void Start(Date startDate) {

		System.out.println("Start finding...");
		// Step 1. Loop all stocks
		allStocks.forEach(stock -> {

			// Step 2. Get all stock_history for selected stock
			List<StockHistory> stockHistories = stockHistoryRepository.GetHistoryByStockIdAndDate(stock.getId(), startDate, null);

			// Step 4. 大阴线后，三连小阳
			int startIndex = 8;
			int endIndex = stockHistories.size();

			for (int i = startIndex; i < endIndex; i++) {

				List<StockHistory> threeDayHistory = stockHistories.subList(i - 2, i + 1);

				if (CandleForm.JustifyTwinPeak(threeDayHistory)) {
					System.out.println("Find satisfication: " + stock.getStockCode() + " " + stockHistories.get(i).getStockDay());
				}
			}

			// System.out.println("finish stock: " + stock.getStockCode());
		});

	}

	public static void main(String[] args) {
		new TestTwoSmallRaise();
	}

}
