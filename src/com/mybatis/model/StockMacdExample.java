package com.mybatis.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class StockMacdExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table stockmacd
     *
     * @mbggenerated Thu Oct 05 17:16:29 CST 2017
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table stockmacd
     *
     * @mbggenerated Thu Oct 05 17:16:29 CST 2017
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table stockmacd
     *
     * @mbggenerated Thu Oct 05 17:16:29 CST 2017
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stockmacd
     *
     * @mbggenerated Thu Oct 05 17:16:29 CST 2017
     */
    public StockMacdExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stockmacd
     *
     * @mbggenerated Thu Oct 05 17:16:29 CST 2017
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stockmacd
     *
     * @mbggenerated Thu Oct 05 17:16:29 CST 2017
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stockmacd
     *
     * @mbggenerated Thu Oct 05 17:16:29 CST 2017
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stockmacd
     *
     * @mbggenerated Thu Oct 05 17:16:29 CST 2017
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stockmacd
     *
     * @mbggenerated Thu Oct 05 17:16:29 CST 2017
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stockmacd
     *
     * @mbggenerated Thu Oct 05 17:16:29 CST 2017
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stockmacd
     *
     * @mbggenerated Thu Oct 05 17:16:29 CST 2017
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stockmacd
     *
     * @mbggenerated Thu Oct 05 17:16:29 CST 2017
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stockmacd
     *
     * @mbggenerated Thu Oct 05 17:16:29 CST 2017
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stockmacd
     *
     * @mbggenerated Thu Oct 05 17:16:29 CST 2017
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table stockmacd
     *
     * @mbggenerated Thu Oct 05 17:16:29 CST 2017
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        protected void addCriterionForJDBCDate(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()), property);
        }

        protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andStockIdIsNull() {
            addCriterion("stock_id is null");
            return (Criteria) this;
        }

        public Criteria andStockIdIsNotNull() {
            addCriterion("stock_id is not null");
            return (Criteria) this;
        }

        public Criteria andStockIdEqualTo(Integer value) {
            addCriterion("stock_id =", value, "stockId");
            return (Criteria) this;
        }

        public Criteria andStockIdNotEqualTo(Integer value) {
            addCriterion("stock_id <>", value, "stockId");
            return (Criteria) this;
        }

        public Criteria andStockIdGreaterThan(Integer value) {
            addCriterion("stock_id >", value, "stockId");
            return (Criteria) this;
        }

        public Criteria andStockIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("stock_id >=", value, "stockId");
            return (Criteria) this;
        }

        public Criteria andStockIdLessThan(Integer value) {
            addCriterion("stock_id <", value, "stockId");
            return (Criteria) this;
        }

        public Criteria andStockIdLessThanOrEqualTo(Integer value) {
            addCriterion("stock_id <=", value, "stockId");
            return (Criteria) this;
        }

        public Criteria andStockIdIn(List<Integer> values) {
            addCriterion("stock_id in", values, "stockId");
            return (Criteria) this;
        }

        public Criteria andStockIdNotIn(List<Integer> values) {
            addCriterion("stock_id not in", values, "stockId");
            return (Criteria) this;
        }

        public Criteria andStockIdBetween(Integer value1, Integer value2) {
            addCriterion("stock_id between", value1, value2, "stockId");
            return (Criteria) this;
        }

        public Criteria andStockIdNotBetween(Integer value1, Integer value2) {
            addCriterion("stock_id not between", value1, value2, "stockId");
            return (Criteria) this;
        }

        public Criteria andStockDayIsNull() {
            addCriterion("stock_day is null");
            return (Criteria) this;
        }

        public Criteria andStockDayIsNotNull() {
            addCriterion("stock_day is not null");
            return (Criteria) this;
        }

        public Criteria andStockDayEqualTo(Date value) {
            addCriterionForJDBCDate("stock_day =", value, "stockDay");
            return (Criteria) this;
        }

        public Criteria andStockDayNotEqualTo(Date value) {
            addCriterionForJDBCDate("stock_day <>", value, "stockDay");
            return (Criteria) this;
        }

        public Criteria andStockDayGreaterThan(Date value) {
            addCriterionForJDBCDate("stock_day >", value, "stockDay");
            return (Criteria) this;
        }

        public Criteria andStockDayGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("stock_day >=", value, "stockDay");
            return (Criteria) this;
        }

        public Criteria andStockDayLessThan(Date value) {
            addCriterionForJDBCDate("stock_day <", value, "stockDay");
            return (Criteria) this;
        }

        public Criteria andStockDayLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("stock_day <=", value, "stockDay");
            return (Criteria) this;
        }

        public Criteria andStockDayIn(List<Date> values) {
            addCriterionForJDBCDate("stock_day in", values, "stockDay");
            return (Criteria) this;
        }

        public Criteria andStockDayNotIn(List<Date> values) {
            addCriterionForJDBCDate("stock_day not in", values, "stockDay");
            return (Criteria) this;
        }

        public Criteria andStockDayBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("stock_day between", value1, value2, "stockDay");
            return (Criteria) this;
        }

        public Criteria andStockDayNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("stock_day not between", value1, value2, "stockDay");
            return (Criteria) this;
        }

        public Criteria andEma12IsNull() {
            addCriterion("ema12 is null");
            return (Criteria) this;
        }

        public Criteria andEma12IsNotNull() {
            addCriterion("ema12 is not null");
            return (Criteria) this;
        }

        public Criteria andEma12EqualTo(Float value) {
            addCriterion("ema12 =", value, "ema12");
            return (Criteria) this;
        }

        public Criteria andEma12NotEqualTo(Float value) {
            addCriterion("ema12 <>", value, "ema12");
            return (Criteria) this;
        }

        public Criteria andEma12GreaterThan(Float value) {
            addCriterion("ema12 >", value, "ema12");
            return (Criteria) this;
        }

        public Criteria andEma12GreaterThanOrEqualTo(Float value) {
            addCriterion("ema12 >=", value, "ema12");
            return (Criteria) this;
        }

        public Criteria andEma12LessThan(Float value) {
            addCriterion("ema12 <", value, "ema12");
            return (Criteria) this;
        }

        public Criteria andEma12LessThanOrEqualTo(Float value) {
            addCriterion("ema12 <=", value, "ema12");
            return (Criteria) this;
        }

        public Criteria andEma12In(List<Float> values) {
            addCriterion("ema12 in", values, "ema12");
            return (Criteria) this;
        }

        public Criteria andEma12NotIn(List<Float> values) {
            addCriterion("ema12 not in", values, "ema12");
            return (Criteria) this;
        }

        public Criteria andEma12Between(Float value1, Float value2) {
            addCriterion("ema12 between", value1, value2, "ema12");
            return (Criteria) this;
        }

        public Criteria andEma12NotBetween(Float value1, Float value2) {
            addCriterion("ema12 not between", value1, value2, "ema12");
            return (Criteria) this;
        }

        public Criteria andEma26IsNull() {
            addCriterion("ema26 is null");
            return (Criteria) this;
        }

        public Criteria andEma26IsNotNull() {
            addCriterion("ema26 is not null");
            return (Criteria) this;
        }

        public Criteria andEma26EqualTo(Float value) {
            addCriterion("ema26 =", value, "ema26");
            return (Criteria) this;
        }

        public Criteria andEma26NotEqualTo(Float value) {
            addCriterion("ema26 <>", value, "ema26");
            return (Criteria) this;
        }

        public Criteria andEma26GreaterThan(Float value) {
            addCriterion("ema26 >", value, "ema26");
            return (Criteria) this;
        }

        public Criteria andEma26GreaterThanOrEqualTo(Float value) {
            addCriterion("ema26 >=", value, "ema26");
            return (Criteria) this;
        }

        public Criteria andEma26LessThan(Float value) {
            addCriterion("ema26 <", value, "ema26");
            return (Criteria) this;
        }

        public Criteria andEma26LessThanOrEqualTo(Float value) {
            addCriterion("ema26 <=", value, "ema26");
            return (Criteria) this;
        }

        public Criteria andEma26In(List<Float> values) {
            addCriterion("ema26 in", values, "ema26");
            return (Criteria) this;
        }

        public Criteria andEma26NotIn(List<Float> values) {
            addCriterion("ema26 not in", values, "ema26");
            return (Criteria) this;
        }

        public Criteria andEma26Between(Float value1, Float value2) {
            addCriterion("ema26 between", value1, value2, "ema26");
            return (Criteria) this;
        }

        public Criteria andEma26NotBetween(Float value1, Float value2) {
            addCriterion("ema26 not between", value1, value2, "ema26");
            return (Criteria) this;
        }

        public Criteria andDiffIsNull() {
            addCriterion("diff is null");
            return (Criteria) this;
        }

        public Criteria andDiffIsNotNull() {
            addCriterion("diff is not null");
            return (Criteria) this;
        }

        public Criteria andDiffEqualTo(Float value) {
            addCriterion("diff =", value, "diff");
            return (Criteria) this;
        }

        public Criteria andDiffNotEqualTo(Float value) {
            addCriterion("diff <>", value, "diff");
            return (Criteria) this;
        }

        public Criteria andDiffGreaterThan(Float value) {
            addCriterion("diff >", value, "diff");
            return (Criteria) this;
        }

        public Criteria andDiffGreaterThanOrEqualTo(Float value) {
            addCriterion("diff >=", value, "diff");
            return (Criteria) this;
        }

        public Criteria andDiffLessThan(Float value) {
            addCriterion("diff <", value, "diff");
            return (Criteria) this;
        }

        public Criteria andDiffLessThanOrEqualTo(Float value) {
            addCriterion("diff <=", value, "diff");
            return (Criteria) this;
        }

        public Criteria andDiffIn(List<Float> values) {
            addCriterion("diff in", values, "diff");
            return (Criteria) this;
        }

        public Criteria andDiffNotIn(List<Float> values) {
            addCriterion("diff not in", values, "diff");
            return (Criteria) this;
        }

        public Criteria andDiffBetween(Float value1, Float value2) {
            addCriterion("diff between", value1, value2, "diff");
            return (Criteria) this;
        }

        public Criteria andDiffNotBetween(Float value1, Float value2) {
            addCriterion("diff not between", value1, value2, "diff");
            return (Criteria) this;
        }

        public Criteria andDeaIsNull() {
            addCriterion("dea is null");
            return (Criteria) this;
        }

        public Criteria andDeaIsNotNull() {
            addCriterion("dea is not null");
            return (Criteria) this;
        }

        public Criteria andDeaEqualTo(Float value) {
            addCriterion("dea =", value, "dea");
            return (Criteria) this;
        }

        public Criteria andDeaNotEqualTo(Float value) {
            addCriterion("dea <>", value, "dea");
            return (Criteria) this;
        }

        public Criteria andDeaGreaterThan(Float value) {
            addCriterion("dea >", value, "dea");
            return (Criteria) this;
        }

        public Criteria andDeaGreaterThanOrEqualTo(Float value) {
            addCriterion("dea >=", value, "dea");
            return (Criteria) this;
        }

        public Criteria andDeaLessThan(Float value) {
            addCriterion("dea <", value, "dea");
            return (Criteria) this;
        }

        public Criteria andDeaLessThanOrEqualTo(Float value) {
            addCriterion("dea <=", value, "dea");
            return (Criteria) this;
        }

        public Criteria andDeaIn(List<Float> values) {
            addCriterion("dea in", values, "dea");
            return (Criteria) this;
        }

        public Criteria andDeaNotIn(List<Float> values) {
            addCriterion("dea not in", values, "dea");
            return (Criteria) this;
        }

        public Criteria andDeaBetween(Float value1, Float value2) {
            addCriterion("dea between", value1, value2, "dea");
            return (Criteria) this;
        }

        public Criteria andDeaNotBetween(Float value1, Float value2) {
            addCriterion("dea not between", value1, value2, "dea");
            return (Criteria) this;
        }

        public Criteria andMacdIsNull() {
            addCriterion("macd is null");
            return (Criteria) this;
        }

        public Criteria andMacdIsNotNull() {
            addCriterion("macd is not null");
            return (Criteria) this;
        }

        public Criteria andMacdEqualTo(Float value) {
            addCriterion("macd =", value, "macd");
            return (Criteria) this;
        }

        public Criteria andMacdNotEqualTo(Float value) {
            addCriterion("macd <>", value, "macd");
            return (Criteria) this;
        }

        public Criteria andMacdGreaterThan(Float value) {
            addCriterion("macd >", value, "macd");
            return (Criteria) this;
        }

        public Criteria andMacdGreaterThanOrEqualTo(Float value) {
            addCriterion("macd >=", value, "macd");
            return (Criteria) this;
        }

        public Criteria andMacdLessThan(Float value) {
            addCriterion("macd <", value, "macd");
            return (Criteria) this;
        }

        public Criteria andMacdLessThanOrEqualTo(Float value) {
            addCriterion("macd <=", value, "macd");
            return (Criteria) this;
        }

        public Criteria andMacdIn(List<Float> values) {
            addCriterion("macd in", values, "macd");
            return (Criteria) this;
        }

        public Criteria andMacdNotIn(List<Float> values) {
            addCriterion("macd not in", values, "macd");
            return (Criteria) this;
        }

        public Criteria andMacdBetween(Float value1, Float value2) {
            addCriterion("macd between", value1, value2, "macd");
            return (Criteria) this;
        }

        public Criteria andMacdNotBetween(Float value1, Float value2) {
            addCriterion("macd not between", value1, value2, "macd");
            return (Criteria) this;
        }

        public Criteria andStockHistoryIdIsNull() {
            addCriterion("stock_history_id is null");
            return (Criteria) this;
        }

        public Criteria andStockHistoryIdIsNotNull() {
            addCriterion("stock_history_id is not null");
            return (Criteria) this;
        }

        public Criteria andStockHistoryIdEqualTo(Integer value) {
            addCriterion("stock_history_id =", value, "stockHistoryId");
            return (Criteria) this;
        }

        public Criteria andStockHistoryIdNotEqualTo(Integer value) {
            addCriterion("stock_history_id <>", value, "stockHistoryId");
            return (Criteria) this;
        }

        public Criteria andStockHistoryIdGreaterThan(Integer value) {
            addCriterion("stock_history_id >", value, "stockHistoryId");
            return (Criteria) this;
        }

        public Criteria andStockHistoryIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("stock_history_id >=", value, "stockHistoryId");
            return (Criteria) this;
        }

        public Criteria andStockHistoryIdLessThan(Integer value) {
            addCriterion("stock_history_id <", value, "stockHistoryId");
            return (Criteria) this;
        }

        public Criteria andStockHistoryIdLessThanOrEqualTo(Integer value) {
            addCriterion("stock_history_id <=", value, "stockHistoryId");
            return (Criteria) this;
        }

        public Criteria andStockHistoryIdIn(List<Integer> values) {
            addCriterion("stock_history_id in", values, "stockHistoryId");
            return (Criteria) this;
        }

        public Criteria andStockHistoryIdNotIn(List<Integer> values) {
            addCriterion("stock_history_id not in", values, "stockHistoryId");
            return (Criteria) this;
        }

        public Criteria andStockHistoryIdBetween(Integer value1, Integer value2) {
            addCriterion("stock_history_id between", value1, value2, "stockHistoryId");
            return (Criteria) this;
        }

        public Criteria andStockHistoryIdNotBetween(Integer value1, Integer value2) {
            addCriterion("stock_history_id not between", value1, value2, "stockHistoryId");
            return (Criteria) this;
        }

        public Criteria andIsGoldencrossIsNull() {
            addCriterion("is_goldencross is null");
            return (Criteria) this;
        }

        public Criteria andIsGoldencrossIsNotNull() {
            addCriterion("is_goldencross is not null");
            return (Criteria) this;
        }

        public Criteria andIsGoldencrossEqualTo(Boolean value) {
            addCriterion("is_goldencross =", value, "isGoldencross");
            return (Criteria) this;
        }

        public Criteria andIsGoldencrossNotEqualTo(Boolean value) {
            addCriterion("is_goldencross <>", value, "isGoldencross");
            return (Criteria) this;
        }

        public Criteria andIsGoldencrossGreaterThan(Boolean value) {
            addCriterion("is_goldencross >", value, "isGoldencross");
            return (Criteria) this;
        }

        public Criteria andIsGoldencrossGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_goldencross >=", value, "isGoldencross");
            return (Criteria) this;
        }

        public Criteria andIsGoldencrossLessThan(Boolean value) {
            addCriterion("is_goldencross <", value, "isGoldencross");
            return (Criteria) this;
        }

        public Criteria andIsGoldencrossLessThanOrEqualTo(Boolean value) {
            addCriterion("is_goldencross <=", value, "isGoldencross");
            return (Criteria) this;
        }

        public Criteria andIsGoldencrossIn(List<Boolean> values) {
            addCriterion("is_goldencross in", values, "isGoldencross");
            return (Criteria) this;
        }

        public Criteria andIsGoldencrossNotIn(List<Boolean> values) {
            addCriterion("is_goldencross not in", values, "isGoldencross");
            return (Criteria) this;
        }

        public Criteria andIsGoldencrossBetween(Boolean value1, Boolean value2) {
            addCriterion("is_goldencross between", value1, value2, "isGoldencross");
            return (Criteria) this;
        }

        public Criteria andIsGoldencrossNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_goldencross not between", value1, value2, "isGoldencross");
            return (Criteria) this;
        }

        public Criteria andIsDeadcrossIsNull() {
            addCriterion("is_deadcross is null");
            return (Criteria) this;
        }

        public Criteria andIsDeadcrossIsNotNull() {
            addCriterion("is_deadcross is not null");
            return (Criteria) this;
        }

        public Criteria andIsDeadcrossEqualTo(Boolean value) {
            addCriterion("is_deadcross =", value, "isDeadcross");
            return (Criteria) this;
        }

        public Criteria andIsDeadcrossNotEqualTo(Boolean value) {
            addCriterion("is_deadcross <>", value, "isDeadcross");
            return (Criteria) this;
        }

        public Criteria andIsDeadcrossGreaterThan(Boolean value) {
            addCriterion("is_deadcross >", value, "isDeadcross");
            return (Criteria) this;
        }

        public Criteria andIsDeadcrossGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_deadcross >=", value, "isDeadcross");
            return (Criteria) this;
        }

        public Criteria andIsDeadcrossLessThan(Boolean value) {
            addCriterion("is_deadcross <", value, "isDeadcross");
            return (Criteria) this;
        }

        public Criteria andIsDeadcrossLessThanOrEqualTo(Boolean value) {
            addCriterion("is_deadcross <=", value, "isDeadcross");
            return (Criteria) this;
        }

        public Criteria andIsDeadcrossIn(List<Boolean> values) {
            addCriterion("is_deadcross in", values, "isDeadcross");
            return (Criteria) this;
        }

        public Criteria andIsDeadcrossNotIn(List<Boolean> values) {
            addCriterion("is_deadcross not in", values, "isDeadcross");
            return (Criteria) this;
        }

        public Criteria andIsDeadcrossBetween(Boolean value1, Boolean value2) {
            addCriterion("is_deadcross between", value1, value2, "isDeadcross");
            return (Criteria) this;
        }

        public Criteria andIsDeadcrossNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_deadcross not between", value1, value2, "isDeadcross");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table stockmacd
     *
     * @mbggenerated do_not_delete_during_merge Thu Oct 05 17:16:29 CST 2017
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table stockmacd
     *
     * @mbggenerated Thu Oct 05 17:16:29 CST 2017
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}