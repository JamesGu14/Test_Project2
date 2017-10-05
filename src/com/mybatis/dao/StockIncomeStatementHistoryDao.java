package com.mybatis.dao;

import com.mybatis.model.StockIncomeStatementHistory;
import com.mybatis.model.StockIncomeStatementHistoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface StockIncomeStatementHistoryDao {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stock_income_statement_history
     *
     * @mbggenerated Sun Sep 17 15:15:42 CST 2017
     */
    int countByExample(StockIncomeStatementHistoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stock_income_statement_history
     *
     * @mbggenerated Sun Sep 17 15:15:42 CST 2017
     */
    int deleteByExample(StockIncomeStatementHistoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stock_income_statement_history
     *
     * @mbggenerated Sun Sep 17 15:15:42 CST 2017
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stock_income_statement_history
     *
     * @mbggenerated Sun Sep 17 15:15:42 CST 2017
     */
    int insert(StockIncomeStatementHistory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stock_income_statement_history
     *
     * @mbggenerated Sun Sep 17 15:15:42 CST 2017
     */
    int insertSelective(StockIncomeStatementHistory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stock_income_statement_history
     *
     * @mbggenerated Sun Sep 17 15:15:42 CST 2017
     */
    List<StockIncomeStatementHistory> selectByExample(StockIncomeStatementHistoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stock_income_statement_history
     *
     * @mbggenerated Sun Sep 17 15:15:42 CST 2017
     */
    StockIncomeStatementHistory selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stock_income_statement_history
     *
     * @mbggenerated Sun Sep 17 15:15:42 CST 2017
     */
    int updateByExampleSelective(@Param("record") StockIncomeStatementHistory record, @Param("example") StockIncomeStatementHistoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stock_income_statement_history
     *
     * @mbggenerated Sun Sep 17 15:15:42 CST 2017
     */
    int updateByExample(@Param("record") StockIncomeStatementHistory record, @Param("example") StockIncomeStatementHistoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stock_income_statement_history
     *
     * @mbggenerated Sun Sep 17 15:15:42 CST 2017
     */
    int updateByPrimaryKeySelective(StockIncomeStatementHistory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stock_income_statement_history
     *
     * @mbggenerated Sun Sep 17 15:15:42 CST 2017
     */
    int updateByPrimaryKey(StockIncomeStatementHistory record);
}