package com.data.dataimport;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.ibatis.session.SqlSession;

import com.google.gson.Gson;
import com.mybatis.dao.StockDao;
import com.mybatis.model.Stock;
import com.utility.Utility;

public class StockImporter {

	/**
	 * Import all stocks into stock table
	 */
	private SqlSession sqlSession;

	public StockImporter() {
		try {
			// Step 1. Prepare sql session
			sqlSession = Utility.GetSqlSession();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void Import() {
		System.out.println("Importing starts...");
		
		List<Stock> needUpdateStocks = new ArrayList<Stock>();
		List<Stock> needInsertStocks = new ArrayList<Stock>();
		// New Step 1. Fetch from API
		List<Stock> allStocks = new ArrayList<Stock>();
		String shMarket = "sh";
		String szMarket = "sz";
		int shPage = 28;
		int szPage = 42;
		for (int i = 1; i <= shPage; i++) {
			List<Stock> resultSet = FetchApi(shMarket, i + "");

			if (resultSet == null) {
				StringBuilder stringBuilder = new StringBuilder("No result at: ");
				stringBuilder.append("market ").append(shMarket).append("; page: ").append(i).append(";");
				System.out.println(stringBuilder.toString());
				continue;
			}
			allStocks.addAll(resultSet);
			System.out.println("fetch finishes for: " + shMarket + " page: " + i + "; add new: " + resultSet.size());
		}

		for (int j = 1; j <= szPage; j++) {
			List<Stock> resultSet = FetchApi(szMarket, j + "");

			if (resultSet == null) {
				StringBuilder stringBuilder = new StringBuilder("No result at: ");
				stringBuilder.append("market ").append(szMarket).append("; page:").append(j).append(";");
				System.out.println(stringBuilder.toString());
				continue;
			}

			allStocks.addAll(resultSet);
			System.out.println("fetch finishes for: " + szMarket + " page: " + j + "; add new: " + resultSet.size());
		}

		// Step 2. Get all existing stocks from db
		StockDao stockDao = sqlSession.getMapper(StockDao.class);
		List<Stock> existingStockList = stockDao.selectByExample(null);

		// Step 3. Compare lists and save those not existing records.
		for (Stock stock : allStocks) {
			Optional<Stock> result = existingStockList.stream().filter(s -> s.getStockCode().equals(stock.getStockCode())).findFirst();
			if (result.isPresent()) {
				// If stock exist in db
				Stock dbStock = result.get();
				if (dbStock.getStockName() == "placeholder" || StringUtils.isEmpty(dbStock.getPinyin())) {
					dbStock.setStockName(stock.getStockName());
					dbStock.setPinyin(stock.getPinyin());
					needUpdateStocks.add(dbStock);
				}
			} else {
				// If stock does not exist in db
				Stock newStock = new Stock();
				newStock.setIsdelisted(false);
				newStock.setPinyin(stock.getPinyin());
				newStock.setStockCode(stock.getStockCode());
				newStock.setStockName(stock.getStockName());
				needInsertStocks.add(newStock);
			}
		}

		// Step 4. Bulk update and insert stocks
		// System.out.println("Need insert stocks: " + needInsertStocks.size());
		// System.out.println("Need update stocks: " + needUpdateStocks.size());
		for (Stock stock : needUpdateStocks) {
			stockDao.updateByPrimaryKey(stock);
		}

		for (Stock stock : needInsertStocks) {
			stockDao.insert(stock);
		}
	}

	private List<Stock> FetchApi(String market, String page) {

		List<Stock> resultSet = new ArrayList<Stock>();

		try {
			URI uri = new URIBuilder().setScheme("https").setHost("ali-stock.showapi.com").setPath("/stocklist").setParameter("market", market).setParameter("page", page).build();

			HttpClient client = HttpClientBuilder.create().build();
			HttpGet request = new HttpGet(uri);
			String appcode = "9a5e597e74eb4ed89ea42f30a8c2d4ab";
			request.addHeader("Authorization", "APPCODE " + appcode);
			HttpResponse response = client.execute(request);

			// System.out.println(response.getEntity().getContent().toString());

			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			// System.out.println(result.toString());

			Gson g = new Gson();
			StockResultJsonObj p = g.fromJson(result.toString(), StockResultJsonObj.class);

			List<StockImportClass> importList = p.getShowapi_res_body().getContentlist();

			for (StockImportClass stockImportClass : importList) {
				Stock newStock = new Stock(null, stockImportClass.getName(), stockImportClass.getCode(), true, stockImportClass.getPinyin());
				resultSet.add(newStock);
			}

			return resultSet;
			// 获取response的body
			// System.out.println(EntityUtils.toString(response.getEntity()));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private class StockResultJsonObj {
		private ShowApiResBody showapi_res_body;

		public ShowApiResBody getShowapi_res_body() {
			return showapi_res_body;
		}

		public void setShowapi_res_body(ShowApiResBody showapi_res_body) {
			this.showapi_res_body = showapi_res_body;
		}

	}

	private class ShowApiResBody {
		private List<StockImportClass> contentlist;

		public List<StockImportClass> getContentlist() {
			return contentlist;
		}

		public void setContentlist(List<StockImportClass> contentlist) {
			this.contentlist = contentlist;
		}
	}

	private class StockImportClass {
		private String stockType;
		private String name;
		private String code;
		private String pinyin;
		private String listing_date;

		public String getStockType() {
			return stockType;
		}

		public void setStockType(String stockType) {
			this.stockType = stockType;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getPinyin() {
			return pinyin;
		}

		public void setPinyin(String pinyin) {
			this.pinyin = pinyin;
		}

		public String getListing_date() {
			return listing_date;
		}

		public void setListing_date(String listing_date) {
			this.listing_date = listing_date;
		}

	}
}
