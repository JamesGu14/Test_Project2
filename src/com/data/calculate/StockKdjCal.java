package com.data.calculate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.ibatis.session.SqlSession;

import com.mybatis.business.StockHistoryRepository;
import com.mybatis.business.StockKdjRepository;
import com.mybatis.business.StockRepository;
import com.mybatis.model.Stock;
import com.mybatis.model.StockHistory;
import com.mybatis.model.StockHistoryExample;
import com.mybatis.model.StockKdj;
import com.utility.Utility;

public class StockKdjCal {

	private SqlSession sqlSession;
	private StockRepository stockRepository;
	private StockKdjRepository stockKdjRepository;
	private StockHistoryRepository stockHistoryRepository;
	private Date startCalDate;
	private List<Stock> allStocks;

	public StockKdjCal() {

		try {
			sqlSession = Utility.GetSqlSession();
			stockRepository = new StockRepository(sqlSession);
			stockKdjRepository = new StockKdjRepository(sqlSession);
			stockHistoryRepository = new StockHistoryRepository(sqlSession);
			startCalDate = Utility.ConvertToDate(Utility.GetPropByKey("config/dataimporter.properties", "kdj_start_date"), "-");

			allStocks = stockRepository.GetAllStocks();

			startCalculate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}

	}

	private void startCalculate() throws Exception {

		System.out.println("Start calculating kdj");

		int index = 1;
		for (Stock stock : allStocks) {

			System.out.print("(" + (index++) + "/" + allStocks.size() + " - " + Math.round(100 * index / allStocks.size()) + "%) Start calculating KDJ for stock: " + stock.getStockCode() + " ");

			int currentStockId = stock.getId();

			// Step 1. Get all stock history for selected stock
			StockHistoryExample stockHistoryExample = new StockHistoryExample();
			stockHistoryExample.createCriteria().andStockIdEqualTo(currentStockId);
			stockHistoryExample.setOrderByClause("stock_day");
			List<StockHistory> allHistory = stockHistoryRepository.getStockHistoryDao().selectByExample(stockHistoryExample);

			// Step 2. Get all stock kdj for selected stock
			List<StockKdj> existingKdj = stockKdjRepository.GetStockKdjByStockid(currentStockId);

			CalculateForSingleStock(currentStockId, new ArrayList<StockHistory>(allHistory), new ArrayList<StockKdj>(existingKdj));
		}
	}

	private void CalculateForSingleStock(int stockId, ArrayList<StockHistory> allHistory, ArrayList<StockKdj> existingKdjList) throws Exception {

		if (allHistory.isEmpty()) {
			return;
		}
		List<StockKdj> needUpdateList = new ArrayList<StockKdj>();
		List<StockKdj> needInsertList = new ArrayList<StockKdj>();

		int startIndex = 0;
		int firstDayStockHistoryId = allHistory.get(0).getId();

		// Step 3. Find start point
		Optional<StockHistory> startHistory = allHistory.stream().filter(h -> h.getStockDay().compareTo(startCalDate) == 0).findFirst();

		if (startHistory.isPresent()) {
			startIndex = allHistory.indexOf(startHistory.get());
		}

		// Step 4. Check if current stock has first day KDJ
		// If not, add the first day
		if (existingKdjList.stream().noneMatch(m -> m.getStockHistoryId() == firstDayStockHistoryId)) {

			StockHistory stockFirstDay = allHistory.get(0);
			BigDecimal firstDayMaxPrice = stockFirstDay.getMaxPrice();
			BigDecimal firstDayMinPrice = stockFirstDay.getMinPrice();

			StockKdj firstDayKdj = new StockKdj();
			firstDayKdj.setStockId(stockId);
			firstDayKdj.setStockHistoryId(stockFirstDay.getId());
			firstDayKdj.setStockDate(stockFirstDay.getStockDay());

			if (firstDayMaxPrice.compareTo(firstDayMinPrice) == 0) {
				firstDayKdj.setRsv((float) 0);
			} else {
				firstDayKdj.setRsv(stockFirstDay.getClosePrice().subtract(stockFirstDay.getMinPrice()).multiply((new BigDecimal(100)))
						.divide((stockFirstDay.getMaxPrice().subtract(stockFirstDay.getMinPrice())), 4, RoundingMode.HALF_UP).floatValue());
			}

			firstDayKdj.setK(firstDayKdj.getRsv());
			firstDayKdj.setD(firstDayKdj.getRsv());
			firstDayKdj.setJ(firstDayKdj.getD() * 3 - firstDayKdj.getK() * 2);
			existingKdjList.add(firstDayKdj);
			needInsertList.add(firstDayKdj);
		}

		// Step 5. Start calculate KDJ indicator base on stock_history
		for (int i = startIndex; i < allHistory.size(); i++) {
			
			// If startPoint is the first day. skip it.
			if (i == 0) {
				continue;
			}
			
			StockHistory todayHistory = allHistory.get(i);
			StockHistory yesterdayHistory = allHistory.get(i - 1);
			Date stockDate = todayHistory.getStockDay();
			int yStockHistoryId = yesterdayHistory.getId();
			int stockHistoryId = todayHistory.getId();
			
			Optional<StockKdj> existingYesterdayKdjOpt = existingKdjList.stream().filter(m -> m.getStockHistoryId() == yStockHistoryId).findFirst();
			Optional<StockKdj> existingTodayKdjOpt = existingKdjList.stream().filter(m -> m.getStockHistoryId() == stockHistoryId).findFirst();

			if (!existingYesterdayKdjOpt.isPresent()) {
				throw new Exception("Yesterday kdj does not exist for stock: " + stockId);
			}
			StockKdj existingYesterdayKdj = existingYesterdayKdjOpt.get();
			
			// 获取最近9天的最低、最高价
            int earlyDateInt = Math.max(0, i - 8);
            // int days = Math.min(i + 1, 9);
            List<StockHistory> nineDayStock = new ArrayList<StockHistory>(allHistory.subList(earlyDateInt, i + 1));
            BigDecimal highest = Collections.max(nineDayStock, new MaxPriceComparator()).getMaxPrice();
            BigDecimal lowest = Collections.min(nineDayStock, new MinPriceComparator()).getMinPrice();
            
            float rsv = todayHistory.getClosePrice().subtract(lowest).multiply(new BigDecimal(100))
            		.divide(highest.subtract(lowest), 4, RoundingMode.HALF_UP).floatValue();
            	
            float k = new BigDecimal((rsv + 2 * existingYesterdayKdj.getK()) / 3).setScale(4, RoundingMode.HALF_UP).floatValue();
            float d = new BigDecimal((k + 2 * existingYesterdayKdj.getD()) / 3).setScale(4, RoundingMode.HALF_UP).floatValue();
            float j = new BigDecimal((3 * k - 2 * d)).setScale(4, RoundingMode.HALF_UP).floatValue();
			
			StockKdj newDayKdj = new StockKdj();
			newDayKdj.setK(k);
			newDayKdj.setD(d);
			newDayKdj.setJ(j);
			newDayKdj.setRsv(rsv);
			newDayKdj.setStockDate(stockDate);
			newDayKdj.setStockId(stockId);
			newDayKdj.setStockHistoryId(stockHistoryId);
			
			if (existingTodayKdjOpt.isPresent()) {
				// if today exist, check if same as calculated, update if not
				StockKdj existingTodayKdj = existingTodayKdjOpt.get();
				StockKdj updatedStockKdj = CompareStockKdj(existingTodayKdj, newDayKdj);

				if (updatedStockKdj != null) {
					// need update
					needUpdateList.add(updatedStockKdj);
					existingKdjList.set(existingKdjList.indexOf(existingTodayKdj), updatedStockKdj);
				}
			} else {
				existingKdjList.add(newDayKdj);
				needInsertList.add(newDayKdj);
			}
		}
		
		for (StockKdj stockKdj : needUpdateList) {
			stockKdjRepository.getStockKdjDao().updateByPrimaryKey(stockKdj);
		}

		for (StockKdj stockKdj : needInsertList) {
			stockKdjRepository.getStockKdjDao().insert(stockKdj);
		}

		System.out.println("StockId: " + stockId + " updated: " + needUpdateList.size() + "; insert: " + needInsertList.size());
	}
	
	private StockKdj CompareStockKdj(StockKdj existingTodayKdj, StockKdj newDayKdj) {
		
		boolean equaled = true;
		
		// K
		float k1 = existingTodayKdj.getK();
		float k2 = newDayKdj.getK();
		float compareK = Math.abs(k1 - k2);
		if (compareK > (float) 0.1) {
			existingTodayKdj.setK(newDayKdj.getK());
			equaled = false;
		}
		
		// D
		float d1 = existingTodayKdj.getD();
		float d2 = newDayKdj.getD();
		float compareD = Math.abs(d1 - d2);
		if (compareD > (float) 0.1) {
			existingTodayKdj.setD(newDayKdj.getD());
			equaled = false;
		}
		
		// J
		float j1 = existingTodayKdj.getJ();
		float j2 = newDayKdj.getJ();
		float compareJ = Math.abs(j1 - j2);
		if (compareJ > (float) 0.1) {
			existingTodayKdj.setJ(newDayKdj.getJ());
			equaled = false;
		}
		
		// RSV
		float rsv1 = existingTodayKdj.getRsv();
		float rsv2 = newDayKdj.getRsv();
		float compareRsv = Math.abs(rsv1 - rsv2);
		if (compareRsv > (float) 0.1) {
			existingTodayKdj.setRsv(newDayKdj.getRsv());
			equaled = false;
		}
		
		if (equaled) {
			return null;
		}
		
		return existingTodayKdj;
	}

	private class MaxPriceComparator implements Comparator<StockHistory> {

		@Override
		public int compare(StockHistory s1, StockHistory s2) {
			return s1.getMaxPrice().compareTo(s2.getMaxPrice());
		}
	}
	
	private class MinPriceComparator implements Comparator<StockHistory> {

		@Override
		public int compare(StockHistory s1, StockHistory s2) {
			return s1.getMinPrice().compareTo(s2.getMinPrice());
		}
	}
}









