package com.mybatis.dao;

import com.mybatis.model.HypothesisResult;
import com.mybatis.model.HypothesisResultExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HypothesisResultDao {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hypothesis_result
     *
     * @mbggenerated Sun Sep 17 15:15:42 CST 2017
     */
    int countByExample(HypothesisResultExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hypothesis_result
     *
     * @mbggenerated Sun Sep 17 15:15:42 CST 2017
     */
    int deleteByExample(HypothesisResultExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hypothesis_result
     *
     * @mbggenerated Sun Sep 17 15:15:42 CST 2017
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hypothesis_result
     *
     * @mbggenerated Sun Sep 17 15:15:42 CST 2017
     */
    int insert(HypothesisResult record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hypothesis_result
     *
     * @mbggenerated Sun Sep 17 15:15:42 CST 2017
     */
    int insertSelective(HypothesisResult record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hypothesis_result
     *
     * @mbggenerated Sun Sep 17 15:15:42 CST 2017
     */
    List<HypothesisResult> selectByExampleWithBLOBs(HypothesisResultExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hypothesis_result
     *
     * @mbggenerated Sun Sep 17 15:15:42 CST 2017
     */
    List<HypothesisResult> selectByExample(HypothesisResultExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hypothesis_result
     *
     * @mbggenerated Sun Sep 17 15:15:42 CST 2017
     */
    HypothesisResult selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hypothesis_result
     *
     * @mbggenerated Sun Sep 17 15:15:42 CST 2017
     */
    int updateByExampleSelective(@Param("record") HypothesisResult record, @Param("example") HypothesisResultExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hypothesis_result
     *
     * @mbggenerated Sun Sep 17 15:15:42 CST 2017
     */
    int updateByExampleWithBLOBs(@Param("record") HypothesisResult record, @Param("example") HypothesisResultExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hypothesis_result
     *
     * @mbggenerated Sun Sep 17 15:15:42 CST 2017
     */
    int updateByExample(@Param("record") HypothesisResult record, @Param("example") HypothesisResultExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hypothesis_result
     *
     * @mbggenerated Sun Sep 17 15:15:42 CST 2017
     */
    int updateByPrimaryKeySelective(HypothesisResult record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hypothesis_result
     *
     * @mbggenerated Sun Sep 17 15:15:42 CST 2017
     */
    int updateByPrimaryKeyWithBLOBs(HypothesisResult record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hypothesis_result
     *
     * @mbggenerated Sun Sep 17 15:15:42 CST 2017
     */
    int updateByPrimaryKey(HypothesisResult record);
}