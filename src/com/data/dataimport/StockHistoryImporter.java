package com.data.dataimport;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.ibatis.session.SqlSession;

import com.mybatis.business.StockHistoryRepository;
import com.mybatis.business.StockRepository;
import com.mybatis.business.StockTradingDateRepository;
import com.mybatis.model.Stock;
import com.mybatis.model.StockHistory;
import com.mybatis.model.StockTradingDate;
import com.utility.Utility;

public class StockHistoryImporter {

	/**
	 * Import stock histories based on importing files into stock_history table
	 */
	private SqlSession sqlSession;
	private StockRepository stockRepository;
	private StockHistoryRepository stockHistoryRepository;
	private StockTradingDateRepository stockTradingDateRepository;
	private List<Stock> allStocks;
	private List<StockTradingDate> allDates;

	public StockHistoryImporter() {
		try {
			sqlSession = Utility.GetSqlSession();
		} catch (IOException e) {
			e.printStackTrace();
		}
		stockRepository = new StockRepository(sqlSession);
		stockHistoryRepository = new StockHistoryRepository(sqlSession);
		stockTradingDateRepository = new StockTradingDateRepository(sqlSession);
		allStocks = stockRepository.GetAllStocks();
		allDates = stockTradingDateRepository.GetAllStockTradingDate();
		Import();
	}

	private void Import() {
		System.out.println("Stock history importing starts...");

		// Step 1. Prepare source file
		String folderPath = "";
		try {
			folderPath = Utility.GetPropByKey("config/dataimporter.properties", "stock_histories");

			File directory = new File(folderPath);
			File[] directoryListing = directory.listFiles();
			if (directoryListing != null) {
				System.out.println("Total files: " + directoryListing.length);
				for (File file : directoryListing) {
					if (!file.getName().endsWith("txt") || file.getName().contains("imported")) {
						continue;
					}
					File file2 = new File(file.getAbsolutePath().replaceAll(file.getName(), "imported-" + file.getName()));
					boolean isSuccess = ImportForSingleStock(file);
					if (isSuccess) {
						file.renameTo(file2);
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private boolean ImportForSingleStock(File file) {

		List<StockHistory> needInsertStockHistories = new ArrayList<StockHistory>();
		List<StockHistory> needUpdateStockHistories = new ArrayList<StockHistory>();

		String stockCode = file.getName().substring(0, 6);
		Optional<Stock> currentStock = allStocks.stream().filter(s -> s.getStockCode().equals(stockCode)).findFirst();

		// If stock does not exist
		if (!currentStock.isPresent()) {
			System.out.println("===== Error: " + stockCode + " - Stock does not exist");
			return false;
		}

		List<StockHistory> dbHistories = stockHistoryRepository.GetHistoryByStockId(currentStock.get().getId());

		List<StockHistory> fileHistories = new ArrayList<StockHistory>();

		try {
			BufferedReader reader = null;
			reader = new BufferedReader(new FileReader(file));
			String lineContent = null;
			while ((lineContent = reader.readLine()) != null) {
				// Convert to stock entity
				StockHistory dayHistory = MapToDayStock(currentStock.get().getId(), lineContent);
				if (dayHistory != null) {
					fileHistories.add(dayHistory);
				}
			}
			reader.close();
		} catch (Exception exc) {
			exc.printStackTrace();
			return false;
		}

		for (StockHistory dayHistory : fileHistories) {
			int selectedTradingDateId = dayHistory.getTradingDate();
			if (dbHistories.stream().anyMatch(h -> h.getTradingDate() == selectedTradingDateId)) {
				// Compare if value all exist, if not, update
				Optional<StockHistory> existingDay = dbHistories.stream().filter(h -> h.getTradingDate() == selectedTradingDateId).findFirst();
				if (dayHistory.getOpenPrice().compareTo(existingDay.get().getOpenPrice()) == 0 && dayHistory.getClosePrice().compareTo(existingDay.get().getClosePrice()) == 0) {
					continue;
				}
				dayHistory.setId(existingDay.get().getId());
				needUpdateStockHistories.add(dayHistory);
			} else {

				needInsertStockHistories.add(dayHistory);
			}
		}

		boolean insertSuccess = BulkUpdateStockHistory(needUpdateStockHistories);
		boolean updateSuccess = BulkInsertStockHistory(needInsertStockHistories);

		if (insertSuccess && updateSuccess) {
			System.out.println(currentStock.get().getId() + " " + currentStock.get().getStockCode() + " insert:" + needInsertStockHistories.size() + "; update: " + needUpdateStockHistories.size());
			return true;
		}

		return false;
	}

	private boolean BulkInsertStockHistory(List<StockHistory> needInsertStockHistories) {

		for (StockHistory stockHistory : needInsertStockHistories) {
			stockHistoryRepository.getStockHistoryDao().insert(stockHistory);
		}
		return true;
	}

	private boolean BulkUpdateStockHistory(List<StockHistory> needUpdateStockHistories) {

		for (StockHistory stockHistory : needUpdateStockHistories) {
			stockHistoryRepository.getStockHistoryDao().updateByPrimaryKey(stockHistory);
		}
		return true;
	}

	/**
	 * Convert each line of string content to a Stock record
	 * 
	 * @param stockId
	 * @param lineContent
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private StockHistory MapToDayStock(int stockId, String lineContent) {

		// 2000/01/04,17.50,18.55,17.20,18.29,8216000,147325360.00
		String[] strList = lineContent.trim().split(",");
		if (strList.length != 7) {
			return null;
		}
		StockHistory dayHistory = new StockHistory();
		dayHistory.setStockId(stockId);

		// Save stock_day
		try {
			Date stockDay = Utility.ConvertToDate(strList[0], "/");

			// Import date if not exist
			int stockTradingDateId = ImportTradingDate(stockDay);

			dayHistory.setStockDay(stockDay);

			dayHistory.setTradingDate(stockTradingDateId);

		} catch (Exception exc) {
			exc.printStackTrace();
		}

		dayHistory.setOpenPrice(new BigDecimal(strList[1]));
		dayHistory.setMaxPrice(new BigDecimal(strList[2]));
		dayHistory.setMinPrice(new BigDecimal(strList[3]));
		dayHistory.setClosePrice(new BigDecimal(strList[4]));
		dayHistory.setTradeNum(Long.parseLong(strList[5]));
		dayHistory.setTradeMoney(new BigDecimal(strList[6]));

		return dayHistory;
	}

	private int ImportTradingDate(Date stockDay) {

		Optional<StockTradingDate> stockTradingDate = allDates.stream().filter(d -> d.getTradingDate().compareTo(stockDay) == 0).findFirst();
		if (stockTradingDate.isPresent()) {
			return stockTradingDate.get().getId();
		}

		StockTradingDate date = new StockTradingDate();
		date.setTradingDate(stockDay);
		return stockTradingDateRepository.getStockTradingDateDao().insert(date);
	}

}
