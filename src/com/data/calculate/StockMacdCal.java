package com.data.calculate;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.ibatis.session.SqlSession;

import com.algorithm.form.MacdForms;
import com.mybatis.business.StockHistoryRepository;
import com.mybatis.business.StockMacdRepository;
import com.mybatis.business.StockRepository;
import com.mybatis.model.Stock;
import com.mybatis.model.StockHistory;
import com.mybatis.model.StockHistoryExample;
import com.mybatis.model.StockMa;
import com.mybatis.model.StockMacd;
import com.utility.Utility;

public class StockMacdCal {

	private SqlSession sqlSession;
	private StockRepository stockRepository;
	private StockMacdRepository stockMacdRepository;
	private StockHistoryRepository stockHistoryRepository;

	private Date startCalDate;
	private List<Stock> allStocks;

	public StockMacdCal() {

		try {
			startCalDate = Utility.ConvertToDate(Utility.GetPropByKey("config/dataimporter.properties", "macd_start_date"), "-");
			sqlSession = Utility.GetSqlSession();
			stockRepository = new StockRepository(sqlSession);
			stockMacdRepository = new StockMacdRepository(sqlSession);
			stockHistoryRepository = new StockHistoryRepository(sqlSession);

			allStocks = stockRepository.GetAllStocks();

			StartCalculate();
			CalculateGoldenDead();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
	}

	private void CalculateGoldenDead() {
		System.out.println("Start calculating Golden Cross and Dead Cross");
		int index = 1;
		for (Stock stock : allStocks) {
			System.out.print("(" + (index++) + "/" + allStocks.size() + ") Start calculating MACD Golden and Dead cross for stock: " + stock.getStockCode() + " ");
			int currentStockId = stock.getId();
			
			List<StockMacd> allMacd = stockMacdRepository.GetStockMacdByStockId(currentStockId);
			List<StockMacd> needUpdateList = new ArrayList<StockMacd>();

			for (int j = 1; j < allMacd.size(); j++) {
				StockMacd yesterday = allMacd.get(j - 1);
				StockMacd today = allMacd.get(j);
				
				if (MacdForms.IsGoldenCross(yesterday, today)) {
					today.setIsGoldencross(true);
					needUpdateList.add(today);
				}
				
				if (MacdForms.IsDeadCross(yesterday, today)) {
					today.setIsDeadcross(true);
					needUpdateList.add(today);
				}
			}
			
			System.out.println("Need update count: " + needUpdateList.size());
			needUpdateList.forEach(macd -> {
				stockMacdRepository.getStockMacdDao().updateByPrimaryKey(macd);
			});
		}
	}

	private void StartCalculate() throws Exception {

		System.out.println("Start calculating macd");

		int index = 1;
		for (Stock stock : allStocks) {

			System.out.print("(" + (index++) + "/" + allStocks.size() + ") Start calculating MACD for stock: " + stock.getStockCode() + " ");

			int currentStockId = stock.getId();

			// Step 1. Get all stock history for selected stock
			StockHistoryExample stockHistoryExample = new StockHistoryExample();
			stockHistoryExample.createCriteria().andStockIdEqualTo(currentStockId);
			stockHistoryExample.setOrderByClause("stock_day");
			List<StockHistory> allHistory = stockHistoryRepository.getStockHistoryDao().selectByExample(stockHistoryExample);

			// Step 2. Get all stock macd for selected stock
			List<StockMacd> existingMacd = stockMacdRepository.GetStockMacdByStockId(currentStockId);

			CalculateForSingleStock(currentStockId, new ArrayList<StockHistory>(allHistory), new ArrayList<StockMacd>(existingMacd));
		}
	}

	private void CalculateForSingleStock(int stockId, ArrayList<StockHistory> allHistory, ArrayList<StockMacd> existingMacdList) throws Exception {

		if (allHistory.isEmpty()) {
			return;
		}

		List<StockMacd> needUpdateList = new ArrayList<StockMacd>();
		List<StockMacd> needInsertList = new ArrayList<StockMacd>();

		int startIndex = 0;
		int firstDayStockHistoryId = allHistory.get(0).getId();

		// Step 3. Find start point
		Optional<StockHistory> startHistory = allHistory.stream().filter(h -> h.getStockDay().compareTo(startCalDate) == 0).findFirst();

		if (startHistory.isPresent()) {
			startIndex = allHistory.indexOf(startHistory.get());
		}

		// Step 4. Check if current stock has first day MACD
		// If not, add the first day

		if (existingMacdList.stream().noneMatch(m -> m.getStockHistoryId() == firstDayStockHistoryId)) {

			StockHistory stockFirstDay = allHistory.get(0);
			StockMacd firstDayMacd = new StockMacd(null, stockFirstDay.getStockId(), stockFirstDay.getStockDay(), stockFirstDay.getClosePrice().floatValue(), stockFirstDay.getClosePrice().floatValue(), (float) 0, (float) 0, (float) 0,
					stockFirstDay.getId(), false, false);

			// stockMacdDao.insert(firstDayMacd);
			existingMacdList.add(firstDayMacd);
			needInsertList.add(firstDayMacd);
		}

		// Step 5. Start calculate MACD indicator base on stock_history
		for (int i = startIndex; i < allHistory.size(); i++) {

			// If startPoint is the first day. skip it.
			if (i == 0) {
				continue;
			}

			StockHistory todayHistory = allHistory.get(i);
			Date stockDate = todayHistory.getStockDay();
			int yStockHistoryId = allHistory.get(i - 1).getId();
			int stockHistoryId = allHistory.get(i).getId();

			Optional<StockMacd> existingYesterdayMacdOpt = existingMacdList.stream().filter(m -> m.getStockHistoryId() == yStockHistoryId).findFirst();
			Optional<StockMacd> existingTodayMacdOpt = existingMacdList.stream().filter(m -> m.getStockHistoryId() == stockHistoryId).findFirst();

			if (!existingYesterdayMacdOpt.isPresent()) {
				throw new Exception("Yesterday macd does not exist for stock: " + stockId);
			}
			StockMacd existingYesterdayMacd = existingYesterdayMacdOpt.get();

			// Calculate
			float ema12 = (existingYesterdayMacd.getEma12() * 11 + todayHistory.getClosePrice().floatValue() * 2) / 13;
			float ema26 = (existingYesterdayMacd.getEma26() * 25 + todayHistory.getClosePrice().floatValue() * 2) / 27;
			float diff = ema12 - ema26;
			float dea = (existingYesterdayMacd.getDea() * 8 + diff * 2) / 10;
			float macd = 2 * (diff - dea);

			StockMacd newDayMacd = new StockMacd();
			newDayMacd.setStockId(stockId);
			newDayMacd.setStockDay(stockDate);
			newDayMacd.setEma12(new BigDecimal(Float.toString(ema12)).setScale(4, BigDecimal.ROUND_HALF_UP).floatValue());
			newDayMacd.setEma26(new BigDecimal(Float.toString(ema26)).setScale(4, BigDecimal.ROUND_HALF_UP).floatValue());
			newDayMacd.setDiff(new BigDecimal(Float.toString(diff)).setScale(4, BigDecimal.ROUND_HALF_UP).floatValue());
			newDayMacd.setDea(new BigDecimal(Float.toString(dea)).setScale(4, BigDecimal.ROUND_HALF_UP).floatValue());
			newDayMacd.setMacd(new BigDecimal(Float.toString(macd)).setScale(4, BigDecimal.ROUND_HALF_UP).floatValue());
			newDayMacd.setStockHistoryId(stockHistoryId);

			if (existingTodayMacdOpt.isPresent()) {
				// if today exist, check if same as calculated, update if not
				StockMacd existingTodayMacd = existingTodayMacdOpt.get();
				StockMacd updatedStockMacd = CompareStockMacd(existingTodayMacd, newDayMacd);

				if (updatedStockMacd != null) {
					// need update
					needUpdateList.add(updatedStockMacd);
					existingMacdList.set(existingMacdList.indexOf(existingTodayMacd), updatedStockMacd);
				}
			} else {
				existingMacdList.add(newDayMacd);
				needInsertList.add(newDayMacd);
			}
		}

		for (StockMacd stockMacd : needUpdateList) {
			stockMacdRepository.getStockMacdDao().updateByPrimaryKey(stockMacd);
		}

		for (StockMacd stockMacd : needInsertList) {
			stockMacdRepository.getStockMacdDao().insert(stockMacd);
		}

		System.out.println("StockId: " + stockId + " updated: " + needUpdateList.size() + "; insert: " + needInsertList.size());
	}

	private StockMacd CompareStockMacd(StockMacd existingTodayMacd, StockMacd newDayMacd) {

		boolean equaled = true;

		// ema12
		float m1 = existingTodayMacd.getEma12();
		float m2 = newDayMacd.getEma12();
		int compareEma12 = Float.compare(m1, m2);
		if (compareEma12 != 0) {
			existingTodayMacd.setEma12(newDayMacd.getEma12());
			equaled = false;
		}

		// ema26
		float n1 = existingTodayMacd.getEma26();
		float n2 = newDayMacd.getEma26();
		int compareEma26 = Float.compare(n1, n2);
		if (compareEma26 != 0) {
			existingTodayMacd.setEma26(newDayMacd.getEma26());
			equaled = false;
		}

		// diff
		float x1 = existingTodayMacd.getDiff();
		float x2 = newDayMacd.getDiff();
		int compareDiff = Float.compare(x1, x2);
		if (compareDiff != 0) {
			existingTodayMacd.setDiff(newDayMacd.getDiff());
			equaled = false;
		}

		// dea
		float y1 = existingTodayMacd.getDea();
		float y2 = newDayMacd.getDea();
		int compareDea = Float.compare(y1, y2);
		if (compareDea != 0) {
			existingTodayMacd.setDea(newDayMacd.getDea());
			equaled = false;
		}

		// macd
		float z1 = existingTodayMacd.getMacd();
		float z2 = newDayMacd.getMacd();
		int compareMacd = Float.compare(z1, z2);
		if (compareMacd != 0) {
			existingTodayMacd.setMacd(newDayMacd.getMacd());
			equaled = false;
		}

		if (equaled) {
			return null;
		}
		return existingTodayMacd;
	}
}
