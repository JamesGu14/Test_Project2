package com.algorithm.hypothesis;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

import org.apache.ibatis.session.SqlSession;

import com.mybatis.business.StockHistoryRepository;
import com.mybatis.business.StockKdjRepository;
import com.mybatis.business.StockMaRepository;
import com.mybatis.business.StockMacdRepository;
import com.mybatis.business.StockRepository;
import com.mybatis.model.Stock;
import com.mybatis.model.StockHistory;
import com.mybatis.model.StockMa;
import com.utility.Utility;

public class Hypo1 {

	private SqlSession sqlSession;
	private StockRepository stockRepository;
	private StockHistoryRepository stockHistoryRepository;
	private StockKdjRepository stockKdjRepository;
	private StockMaRepository stockMaRepository;
	private StockMacdRepository stockMacdRepository;

	private List<Stock> allStocks;

	public Hypo1() {
		try {
			sqlSession = Utility.GetSqlSession();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
		
		stockRepository = new StockRepository(sqlSession);
		stockHistoryRepository = new StockHistoryRepository(sqlSession);
//		stockKdjRepository = new StockKdjRepository();
		stockMaRepository = new StockMaRepository(sqlSession);
//		stockMacdRepository = new StockMacdRepository();

		allStocks = stockRepository.GetAllStocks();

		Start();
	}

	private void Start() {

		System.out.println("Start finding...");
		// Step 1. Loop all stocks
		allStocks.forEach(stock -> {
			
			System.out.print("start stock: " + stock.getStockCode());
			
			// Step 2. Get all stock_history for selected stock
			List<StockHistory> stockHistories = stockHistoryRepository.GetHistoryByStockId(stock.getId());

			// Step 3. Get all ma5 for selected stock
			List<StockMa> stockMas = stockMaRepository.GetStockMaByStockId(stock.getId());

			// Step 4. 5日线向下，三连小阳
			int startIndex = 8;
			int endIndex = stockHistories.size();

			for (int i = startIndex; i < endIndex; i++) {

				boolean satisfied = true;

				List<StockHistory> threeDayHistory = stockHistories.subList(i - 2, i + 1);
				if (threeDayHistory.size() != 3) {
					System.out.println("Error: " + stock.getStockCode() + " - three day size not 3");
					continue;
				}
				for (StockHistory day : threeDayHistory) {

					// Check if everyday ma5 drops and candle line raises less than 2%
					if (day.getClosePrice().subtract(day.getOpenPrice()).compareTo(new BigDecimal("0")) < 0 
							|| day.getClosePrice().subtract(day.getOpenPrice()).divide(day.getOpenPrice(), 4, RoundingMode.HALF_UP).compareTo(new BigDecimal("0.02")) > 0) {
						satisfied = false;
						break;
					}

					// Check if MA 5 drops all three days
					Optional<StockMa> stockMaOpt = stockMas.stream().filter(m -> m.getStockHistoryId().equals(day.getId())).findFirst();
					if (!stockMaOpt.isPresent()) {
						System.out.println("Data Error: stock Ma does not exist");
						break;
					}

					StockMa stockMa = stockMaOpt.get();
					if (stockMa.getMa20Raisedays() > 0) {
						satisfied = false;
						break;
					}
				}

				if (satisfied) {
					System.out.println("Find satisfication: " + stock.getStockCode() + " " + stockHistories.get(i).getStockDay());
				}
			}
			
			System.out.println("finish stock: " + stock.getStockCode());
		});
	}

	public static void main(String[] args) {
		new Hypo1();
	}

}
