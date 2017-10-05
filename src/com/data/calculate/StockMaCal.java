package com.data.calculate;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.ibatis.session.SqlSession;

import com.mybatis.business.StockHistoryRepository;
import com.mybatis.business.StockMaRepository;
import com.mybatis.business.StockRepository;
import com.mybatis.model.Stock;
import com.mybatis.model.StockHistory;
import com.mybatis.model.StockHistoryExample;
import com.mybatis.model.StockMa;
import com.utility.Utility;

public class StockMaCal {

	private SqlSession sqlSession;
	private StockMaRepository stockMaRepository;
	private StockRepository stockRepository;
	private StockHistoryRepository stockHistoryRepository;
	// private StockTradingDateDao stockTradingDateDao;
	private Date startCalDate;
	private List<Stock> allStocks;

	public StockMaCal() {

		try {
			// Prepare work
			startCalDate = Utility.ConvertToDate(Utility.GetPropByKey("config/dataimporter.properties", "ma_start_date"), "-");
			sqlSession = Utility.GetSqlSession();
			stockMaRepository = new StockMaRepository(sqlSession);
			stockRepository = new StockRepository(sqlSession);
			stockHistoryRepository = new StockHistoryRepository(sqlSession);

			allStocks = stockRepository.GetAllStocks();
			// allDates = CalCommon.GetAllStockTradingDate(stockTradingDateDao);

			// TODO: Get it back when running real daily task
			StartCalculate();

			// Calculate consecutive raising/drop days
			StartCalculateRaiseDrop();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
	}

	private void StartCalculate() {

		System.out.println("Start calculating MA... ");

		int index = 1;
		for (Stock stock : allStocks) {
			System.out.print("(" + (index++) + "/" + allStocks.size() + ") Start calculating MA for stock: " + stock.getStockCode() + " ");

			int currentStockId = stock.getId();

			// Step 1. Get all stock history for selected stock
			StockHistoryExample stockHistoryExample = new StockHistoryExample();
			stockHistoryExample.createCriteria().andStockIdEqualTo(currentStockId);
			stockHistoryExample.setOrderByClause("stock_day");
			List<StockHistory> allHistory = stockHistoryRepository.getStockHistoryDao().selectByExample(stockHistoryExample);

			// Step 2. Get all stock ma for selected stock
			List<StockMa> existingMa = stockMaRepository.GetStockMaByStockId(currentStockId);

			CalculateForSingleStock(currentStockId, new ArrayList<StockHistory>(allHistory), new ArrayList<StockMa>(existingMa));
		}
	}

	private void StartCalculateRaiseDrop() throws Exception {

		System.out.println("Start calculating raise drop days...");

		int index = 1;
		for (Stock stock : allStocks) {
			System.out.print("(" + (index++) + "/" + allStocks.size() + ") Start calculating MA Raise Drop for stock: " + stock.getStockCode() + " ");

			int currentStockId = stock.getId();

			List<StockMa> existingMa = stockMaRepository.GetStockMaByStockId(currentStockId);
			CalculateRaiseDropSingleStock(currentStockId, new ArrayList<StockMa>(existingMa));
		}
	}

	private void CalculateRaiseDropSingleStock(int currentStockId, ArrayList<StockMa> existingMaList) throws Exception {

		// If no ma records, return
		if (existingMaList.size() == 0) {
			return;
		}

		List<StockMa> needUpdateList = new ArrayList<StockMa>();

		// Step 1. Set default value for first MA record
		StockMa firstMa = existingMaList.get(0);
		if (firstMa.getMa20Raisedays() == null && firstMa.getMa30Raisedays() == null && firstMa.getMa20Dropdays() == null && firstMa.getMa30Dropdays() == null) {
			// Need update
			firstMa.setMa20Raisedays(0);
			firstMa.setMa20Dropdays(0);
			firstMa.setMa30Raisedays(0);
			firstMa.setMa30Dropdays(0);
			needUpdateList.add(firstMa);

			existingMaList.set(0, firstMa);
		}

		if (existingMaList.size() > 1) {
			// array contains more then 1 element

			for (int i = 1; i < existingMaList.size(); i++) {
				StockMa yesterdayMa = existingMaList.get(i - 1);
				StockMa todayMa = existingMaList.get(i);

				int yMa20Raise = yesterdayMa.getMa20Raisedays();
				int yMa20Drop = yesterdayMa.getMa20Dropdays();
				int yMa30Raise = yesterdayMa.getMa30Raisedays();
				int yMa30Drop = yesterdayMa.getMa30Dropdays();

				if (yMa20Raise > 0 && yMa20Drop > 0) {
					throw new Exception("MA20 record has issue at: " + yesterdayMa.getStockDate().toString());
				}
				if (yMa30Raise > 0 && yMa30Drop > 0) {
					throw new Exception("MA30 record has issue at: " + yesterdayMa.getStockDate().toString());
				}

				boolean needUpdate = false;

				int today20Raise = todayMa.getMa20().subtract(yesterdayMa.getMa20()).compareTo(new BigDecimal(0));
				if (today20Raise > 0) {
					// Raised
					if (todayMa.getMa20Raisedays() == null || todayMa.getMa20Raisedays() != yMa20Raise + 1 || todayMa.getMa20Dropdays() != 0) {
						todayMa.setMa20Raisedays(yMa20Raise + 1);
						todayMa.setMa20Dropdays(0);
						needUpdate = true;
					}
				} else if (today20Raise == 0) {
					// Do Nothing
					if (todayMa.getMa20Raisedays() == null || (yMa20Raise > 0 && todayMa.getMa20Raisedays() != yMa20Raise + 1) || (yMa20Drop > 0 && todayMa.getMa20Dropdays() != yMa20Drop + 1)) {

						if (yMa20Raise > 0) {
							todayMa.setMa20Raisedays(yMa20Raise + 1);
							todayMa.setMa20Dropdays(yMa20Drop);
						} else if (yMa20Drop > 0) {
							todayMa.setMa20Raisedays(yMa20Raise);
							todayMa.setMa20Dropdays(yMa20Drop + 1);
						} else {
							todayMa.setMa20Raisedays(0);
							todayMa.setMa20Dropdays(0);
						}

						needUpdate = true;
					}
				} else {
					// Drop
					if (todayMa.getMa20Raisedays() == null || todayMa.getMa20Raisedays() != 0 || todayMa.getMa20Dropdays() != yMa20Drop + 1) {
						todayMa.setMa20Raisedays(0);
						todayMa.setMa20Dropdays(yesterdayMa.getMa20Dropdays() + 1);
						needUpdate = true;
					}
				}

				int today30Raise = todayMa.getMa30().subtract(yesterdayMa.getMa30()).compareTo(new BigDecimal(0));
				if (today30Raise > 0) {
					// Raised
					if (todayMa.getMa30Raisedays() == null || todayMa.getMa30Raisedays() != yMa30Raise + 1 || todayMa.getMa30Dropdays() != 0) {
						todayMa.setMa30Raisedays(yMa30Raise + 1);
						todayMa.setMa30Dropdays(0);
						needUpdate = true;
					}
				} else if (today30Raise == 0) {
					// Do Nothing
					if (todayMa.getMa30Raisedays() == null || (yMa30Raise > 0 && todayMa.getMa30Raisedays() != yMa30Raise + 1) || (yMa30Drop > 0 && todayMa.getMa30Dropdays() != yMa30Drop + 1)) {

						if (yMa30Raise > 0) {
							todayMa.setMa30Raisedays(yMa30Raise + 1);
							todayMa.setMa30Dropdays(yMa30Drop);
						} else if (yMa30Drop > 0) {
							todayMa.setMa30Raisedays(yMa30Raise);
							todayMa.setMa30Dropdays(yMa30Drop + 1);
						} else {
							todayMa.setMa30Raisedays(0);
							todayMa.setMa30Dropdays(0);
						}

						needUpdate = true;
					}
				} else {
					// Drop
					if (todayMa.getMa30Raisedays() == null || todayMa.getMa30Raisedays() != 0 || todayMa.getMa30Dropdays() != yMa30Drop + 1) {
						todayMa.setMa30Raisedays(0);
						todayMa.setMa30Dropdays(yesterdayMa.getMa30Dropdays() + 1);
						needUpdate = true;
					}
				}

				if (needUpdate) {
					existingMaList.set(i, todayMa);
					needUpdateList.add(todayMa);
				}
			}

			// Update list
			for (StockMa stockMa : needUpdateList) {
				stockMaRepository.getStockMaDao().updateByPrimaryKey(stockMa);
			}
			System.out.println("Updated: " + needUpdateList.size() + " records.");
		}
	}

	private boolean CalculateForSingleStock(int stockId, ArrayList<StockHistory> allHistory, ArrayList<StockMa> existingMaList) {

		// List<StockMa> calculatedResult = new ArrayList<StockMa>();

		List<StockMa> needUpdateList = new ArrayList<StockMa>();
		List<StockMa> needInsertList = new ArrayList<StockMa>();

		int startIndex = 0;
		int fiveDaysPointer = 0;
		int tenDaysPointer = 0;
		int twentyDaysPointer = 0;
		int thirtyDaysPointer = 0;
		int sixtyDaysPointer = 0;
		int oneTwentyDaysPointer = 0;

		// Step 3. Find start point
		Optional<StockHistory> startHistory = allHistory.stream().filter(h -> h.getStockDay().compareTo(startCalDate) == 0).findFirst();

		if (startHistory.isPresent()) {
			startIndex = allHistory.indexOf(startHistory.get());
		}

		// Step 4. Calculate ma base on stock_history
		for (int i = startIndex; i < allHistory.size(); i++) {

			Date stockDate = allHistory.get(i).getStockDay();
			int stockHistoryId = allHistory.get(i).getId();

			// Update pointers
			fiveDaysPointer = Math.max(i - 4, 0);
			tenDaysPointer = Math.max(i - 9, 0);
			twentyDaysPointer = Math.max(i - 19, 0);
			thirtyDaysPointer = Math.max(i - 29, 0);
			sixtyDaysPointer = Math.max(i - 59, 0);
			oneTwentyDaysPointer = Math.max(i - 119, 0);

			int toPointer = i + 1;

			Optional<StockMa> existingMaOpt = existingMaList.stream().filter(m -> m.getStockHistoryId() == stockHistoryId).findFirst();

			BigDecimal fiveMa = CalAvg(new ArrayList<StockHistory>(allHistory.subList(fiveDaysPointer, toPointer)));
			BigDecimal tenMa = CalAvg(new ArrayList<StockHistory>(allHistory.subList(tenDaysPointer, toPointer)));
			BigDecimal twentyMa = CalAvg(new ArrayList<StockHistory>(allHistory.subList(twentyDaysPointer, toPointer)));
			BigDecimal thirtyMa = CalAvg(new ArrayList<StockHistory>(allHistory.subList(thirtyDaysPointer, toPointer)));
			BigDecimal sixtyMa = CalAvg(new ArrayList<StockHistory>(allHistory.subList(sixtyDaysPointer, toPointer)));
			BigDecimal oneTwentyMa = CalAvg(new ArrayList<StockHistory>(allHistory.subList(oneTwentyDaysPointer, toPointer)));

			StockMa newStockMa = new StockMa(null, stockId, stockDate, fiveMa, tenMa, twentyMa, thirtyMa, sixtyMa, oneTwentyMa, null, null, null, null, stockHistoryId);

			// Step 5. compare with db existing
			if (existingMaOpt.isPresent()) {

				// check if need update
				StockMa existingMa = existingMaOpt.get();
				StockMa updatedStockMa = CompareStockMa(existingMa, newStockMa);
				if (updatedStockMa != null) {
					// need update
					needUpdateList.add(updatedStockMa);
				}
			} else {
				// need insert
				needInsertList.add(newStockMa);
			}
		}

		// Step 6. Update & Insert records
		for (StockMa stockMa : needUpdateList) {
			stockMaRepository.getStockMaDao().updateByPrimaryKey(stockMa);
		}

		for (StockMa stockMa : needInsertList) {
			stockMaRepository.getStockMaDao().insert(stockMa);
		}

		System.out.println("StockId: " + stockId + " updated: " + needUpdateList.size() + "; insert: " + needInsertList.size());
		return true;
	}

	// returns null if all values are correct

	private StockMa CompareStockMa(StockMa existingMa, StockMa newStockMa) {

		boolean equaled = true;

		// MA5
		if (existingMa.getMa5().compareTo(newStockMa.getMa5()) != 0) {
			equaled = false;
			existingMa.setMa5(newStockMa.getMa5());
		}

		// MA10
		if (existingMa.getMa10().compareTo(newStockMa.getMa10()) != 0) {
			equaled = false;
			existingMa.setMa10(newStockMa.getMa10());
		}

		// MA20
		if (existingMa.getMa20().compareTo(newStockMa.getMa20()) != 0) {
			equaled = false;
			existingMa.setMa20(newStockMa.getMa20());
		}

		// MA30
		if (existingMa.getMa30().compareTo(newStockMa.getMa30()) != 0) {
			equaled = false;
			existingMa.setMa30(newStockMa.getMa30());
		}

		// MA60
		if (existingMa.getMa60().compareTo(newStockMa.getMa60()) != 0) {
			equaled = false;
			existingMa.setMa60(newStockMa.getMa60());
		}

		// MA120
		if (existingMa.getMa120().compareTo(newStockMa.getMa120()) != 0) {
			equaled = false;
			existingMa.setMa120(newStockMa.getMa120());
		}

		if (equaled) {
			return null;
		}
		return existingMa;
	}

	private BigDecimal CalAvg(ArrayList<StockHistory> subList) {

		BigDecimal sum = new BigDecimal(0);
		for (StockHistory stockHistory : subList) {

			sum = sum.add(stockHistory.getClosePrice());
		}

		// MathContext mc = new MathContext(2, RoundingMode.HALF_DOWN);

		return sum.divide(new BigDecimal(subList.size()), 2, RoundingMode.HALF_UP);
	}
}
