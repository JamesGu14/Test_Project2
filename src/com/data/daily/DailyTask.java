package com.data.daily;

import com.data.calculate.StockKdjCal;
import com.data.calculate.StockMaCal;
import com.data.calculate.StockMacdCal;
import com.data.dataimport.StockHistoryImporter;
import com.data.dataimport.StockImporter;

public class DailyTask {
	
	private static void ImportStocks() {
		StockImporter stockImporter = new StockImporter();
		stockImporter.Import();
	}
	
	private static void ImportStockHistory() {
		new StockHistoryImporter();
	}
	
	private static void CalMa() {
		new StockMaCal();
	}
	
	private static void CalMacd() {
		new StockMacdCal();
	}
	
	private static void CalKdj() {
		new StockKdjCal();
	}
	
	private static void DataImportBundle() {
		// Step 1. Import stock list from Aliyun API call
		// ImportStocks();
		
//		// Step 2. Import stock history from csv files from Tongdaxin
//		ImportStockHistory();
//		
//		// Step 3. Calculate MA indicator
//		CalMa();
//		
//		// Step 4. Calculate MACD indicator
//		CalMacd();
//		
//		// Step 5. Calculate KDJ indicator
//		CalKdj();
	}
	
	public static void main(String[] args) {
		
		DataImportBundle();
		
		// Step 6. Analyze bundle
		
	}
}
