package com.algorithm.test;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import com.algorithm.form.CandleForms;
import com.algorithm.form.MAForms;
import com.mybatis.business.StockHistoryRepository;
import com.mybatis.business.StockMaRepository;
import com.mybatis.business.StockRepository;
import com.mybatis.model.Stock;
import com.mybatis.model.StockHistory;
import com.mybatis.model.StockMa;
import com.utility.Utility;

public class MyTest {

	@Test
	public void TestThreeSmallRaise() {
		StockHistory day1 = new StockHistory(1, 1, null, new BigDecimal("1"), new BigDecimal("0.94"), null, null, null, null, null, null, null, null);
		StockHistory day2 = new StockHistory(1, 1, null, new BigDecimal("0.94"), new BigDecimal("0.94"), null, null, null, null, null, null, null, null);
		StockHistory day3 = new StockHistory(1, 1, null, new BigDecimal("0.95"), new BigDecimal("0.96"), null, null, null, null, null, null, null, null);

		List<StockHistory> list = new ArrayList<StockHistory>();
		list.add(day1);
		list.add(day2);
		list.add(day3);

		System.out.println(CandleForms.JustifyTwinPeak(list));
	}

	@Test
	public void TestFunctions() {

		float a = (float) 1.11;
		float b = (float) 2.22;
		System.out.println(a < b);
	}

	@Test
	public void RowBoundTest() {
		try {
			SqlSession sqlSession = Utility.GetSqlSession();

			List<StockHistory> stockHistoryList = new StockHistoryRepository(sqlSession).GetHistoryByStockIdAndDate(1, new SimpleDateFormat("yyyy-MM-dd").parse("2017-01-01"), 5);

			stockHistoryList.forEach(s -> {
				System.out.println(s.getId());
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void TurnPointTest() throws Exception {

		SqlSession sqlSession = Utility.GetSqlSession();
		StockRepository stockRepository = new StockRepository(sqlSession);
		StockHistoryRepository stockHistoryRepository = new StockHistoryRepository(sqlSession);
		StockMaRepository stockMaRepository = new StockMaRepository(sqlSession);
		Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2017-01-01");

		List<Stock> allStocks = stockRepository.GetAllStocks();
		for (Stock stock : allStocks) {

			List<StockHistory> stockHistoryList = stockHistoryRepository.GetHistoryByStockIdAndDate(stock.getId(), startDate, null);
			List<StockMa> stockMaList = stockMaRepository.GetStockMaByStockIdAndDate(stock.getId(), startDate, null);
			System.out.println("Found turnpoint for stock " + stock.getStockCode() + " at: ");
			for (int i = 0; i < stockHistoryList.size() - 1; i++) {
				if (CandleForms.IsTurnPoint(stockHistoryList.get(i), stockMaList.get(i))) {
					System.out.print(new SimpleDateFormat("yyyy-MM-dd").format(stockHistoryList.get(i).getStockDay()));
					System.out.println(" result: " + Utility.VerifySingleDayHypo(stockHistoryList.get(i)).multiply(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP).toString() + "%");
				}
			}
		}
	}

	@Test
	public void LongMaTest() throws Exception {
		
		SqlSession sqlSession = Utility.GetSqlSession();
		StockRepository stockRepository = new StockRepository(sqlSession);
		StockHistoryRepository stockHistoryRepository = new StockHistoryRepository(sqlSession);
		StockMaRepository stockMaRepository = new StockMaRepository(sqlSession);
		Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2017-01-01");
		Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2017-05-01");
		
		List<Stock> allStocks = stockRepository.GetAllStocks();
		for (Stock stock : allStocks) {
			
			List<StockHistory> stockHistoryList = stockHistoryRepository.GetHistoryByStockIdAndDate(stock.getId(), startDate, null);
			List<StockMa> stockMaList = stockMaRepository.GetStockMaByStockIdAndDate(stock.getId(), startDate, endDate);
			System.out.println("Found LongMA for stock " + stock.getStockCode() + " at: ");
			for (StockMa stockMa : stockMaList) {
				
				Optional<StockHistory> stockHistoryOpt = stockHistoryList.stream().filter(h -> h.getId().equals(stockMa.getStockHistoryId())).findFirst();
				if (!stockHistoryOpt.isPresent()) {
					System.out.println("Error: cannot find corresponding StockHistory for MA at " + stockMa.getStockHistoryId());
					continue;
				}
				if (MAForms.IsLongMa(stockMa)) {
					System.out.print(new SimpleDateFormat("yyyy-MM-dd").format(stockHistoryOpt.get().getStockDay()));
					System.out.println(" result: " + Utility.VerifySingleDayHypo(stockHistoryOpt.get()).multiply(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP).toString() + "%");
				}
			}
		}
	}

	@Test
	public void TwinPeakTest() throws Exception {
		
		SqlSession sqlSession = Utility.GetSqlSession();
		StockRepository stockRepository = new StockRepository(sqlSession);
		StockHistoryRepository stockHistoryRepository = new StockHistoryRepository(sqlSession);

		List<Stock> allStocks = stockRepository.GetAllStocks();

		Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2015-01-01");
		
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

				if (CandleForms.JustifyTwinPeak(threeDayHistory)) {
					System.out.println("Find satisfication: " + stock.getStockCode() + " " + stockHistories.get(i).getStockDay());
				}
			}

			// System.out.println("finish stock: " + stock.getStockCode());
		});
	}
}
