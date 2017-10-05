package com.mybatis.model;

import java.util.Date;

public class StockMacd {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column stockmacd.id
     *
     * @mbggenerated Sun Sep 17 15:15:43 CST 2017
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column stockmacd.stock_id
     *
     * @mbggenerated Sun Sep 17 15:15:43 CST 2017
     */
    private Integer stockId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column stockmacd.stock_day
     *
     * @mbggenerated Sun Sep 17 15:15:43 CST 2017
     */
    private Date stockDay;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column stockmacd.ema12
     *
     * @mbggenerated Sun Sep 17 15:15:43 CST 2017
     */
    private Float ema12;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column stockmacd.ema26
     *
     * @mbggenerated Sun Sep 17 15:15:43 CST 2017
     */
    private Float ema26;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column stockmacd.diff
     *
     * @mbggenerated Sun Sep 17 15:15:43 CST 2017
     */
    private Float diff;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column stockmacd.dea
     *
     * @mbggenerated Sun Sep 17 15:15:43 CST 2017
     */
    private Float dea;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column stockmacd.macd
     *
     * @mbggenerated Sun Sep 17 15:15:43 CST 2017
     */
    private Float macd;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column stockmacd.stock_history_id
     *
     * @mbggenerated Sun Sep 17 15:15:43 CST 2017
     */
    private Integer stockHistoryId;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stockmacd
     *
     * @mbggenerated Sun Sep 17 15:15:43 CST 2017
     */
    public StockMacd(Integer id, Integer stockId, Date stockDay, Float ema12, Float ema26, Float diff, Float dea, Float macd, Integer stockHistoryId) {
        this.id = id;
        this.stockId = stockId;
        this.stockDay = stockDay;
        this.ema12 = ema12;
        this.ema26 = ema26;
        this.diff = diff;
        this.dea = dea;
        this.macd = macd;
        this.stockHistoryId = stockHistoryId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stockmacd
     *
     * @mbggenerated Sun Sep 17 15:15:43 CST 2017
     */
    public StockMacd() {
        super();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column stockmacd.id
     *
     * @return the value of stockmacd.id
     *
     * @mbggenerated Sun Sep 17 15:15:43 CST 2017
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column stockmacd.id
     *
     * @param id the value for stockmacd.id
     *
     * @mbggenerated Sun Sep 17 15:15:43 CST 2017
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column stockmacd.stock_id
     *
     * @return the value of stockmacd.stock_id
     *
     * @mbggenerated Sun Sep 17 15:15:43 CST 2017
     */
    public Integer getStockId() {
        return stockId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column stockmacd.stock_id
     *
     * @param stockId the value for stockmacd.stock_id
     *
     * @mbggenerated Sun Sep 17 15:15:43 CST 2017
     */
    public void setStockId(Integer stockId) {
        this.stockId = stockId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column stockmacd.stock_day
     *
     * @return the value of stockmacd.stock_day
     *
     * @mbggenerated Sun Sep 17 15:15:43 CST 2017
     */
    public Date getStockDay() {
        return stockDay;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column stockmacd.stock_day
     *
     * @param stockDay the value for stockmacd.stock_day
     *
     * @mbggenerated Sun Sep 17 15:15:43 CST 2017
     */
    public void setStockDay(Date stockDay) {
        this.stockDay = stockDay;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column stockmacd.ema12
     *
     * @return the value of stockmacd.ema12
     *
     * @mbggenerated Sun Sep 17 15:15:43 CST 2017
     */
    public Float getEma12() {
        return ema12;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column stockmacd.ema12
     *
     * @param ema12 the value for stockmacd.ema12
     *
     * @mbggenerated Sun Sep 17 15:15:43 CST 2017
     */
    public void setEma12(Float ema12) {
        this.ema12 = ema12;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column stockmacd.ema26
     *
     * @return the value of stockmacd.ema26
     *
     * @mbggenerated Sun Sep 17 15:15:43 CST 2017
     */
    public Float getEma26() {
        return ema26;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column stockmacd.ema26
     *
     * @param ema26 the value for stockmacd.ema26
     *
     * @mbggenerated Sun Sep 17 15:15:43 CST 2017
     */
    public void setEma26(Float ema26) {
        this.ema26 = ema26;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column stockmacd.diff
     *
     * @return the value of stockmacd.diff
     *
     * @mbggenerated Sun Sep 17 15:15:43 CST 2017
     */
    public Float getDiff() {
        return diff;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column stockmacd.diff
     *
     * @param diff the value for stockmacd.diff
     *
     * @mbggenerated Sun Sep 17 15:15:43 CST 2017
     */
    public void setDiff(Float diff) {
        this.diff = diff;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column stockmacd.dea
     *
     * @return the value of stockmacd.dea
     *
     * @mbggenerated Sun Sep 17 15:15:43 CST 2017
     */
    public Float getDea() {
        return dea;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column stockmacd.dea
     *
     * @param dea the value for stockmacd.dea
     *
     * @mbggenerated Sun Sep 17 15:15:43 CST 2017
     */
    public void setDea(Float dea) {
        this.dea = dea;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column stockmacd.macd
     *
     * @return the value of stockmacd.macd
     *
     * @mbggenerated Sun Sep 17 15:15:43 CST 2017
     */
    public Float getMacd() {
        return macd;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column stockmacd.macd
     *
     * @param macd the value for stockmacd.macd
     *
     * @mbggenerated Sun Sep 17 15:15:43 CST 2017
     */
    public void setMacd(Float macd) {
        this.macd = macd;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column stockmacd.stock_history_id
     *
     * @return the value of stockmacd.stock_history_id
     *
     * @mbggenerated Sun Sep 17 15:15:43 CST 2017
     */
    public Integer getStockHistoryId() {
        return stockHistoryId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column stockmacd.stock_history_id
     *
     * @param stockHistoryId the value for stockmacd.stock_history_id
     *
     * @mbggenerated Sun Sep 17 15:15:43 CST 2017
     */
    public void setStockHistoryId(Integer stockHistoryId) {
        this.stockHistoryId = stockHistoryId;
    }
}