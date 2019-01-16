package com.emate.shop.mapper;

import com.emate.shop.business.model.CountermanScore;
import com.emate.shop.business.model.CountermanScoreExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CountermanScoreMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table counterman_score
     *
     * @mbggenerated Wed Mar 29 12:10:23 CST 2017
     */
    int countByExample(CountermanScoreExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table counterman_score
     *
     * @mbggenerated Wed Mar 29 12:10:23 CST 2017
     */
    int deleteByExample(CountermanScoreExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table counterman_score
     *
     * @mbggenerated Wed Mar 29 12:10:23 CST 2017
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table counterman_score
     *
     * @mbggenerated Wed Mar 29 12:10:23 CST 2017
     */
    int insert(CountermanScore record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table counterman_score
     *
     * @mbggenerated Wed Mar 29 12:10:23 CST 2017
     */
    int insertSelective(CountermanScore record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table counterman_score
     *
     * @mbggenerated Wed Mar 29 12:10:23 CST 2017
     */
    List<CountermanScore> selectByExample(CountermanScoreExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table counterman_score
     *
     * @mbggenerated Wed Mar 29 12:10:23 CST 2017
     */
    CountermanScore selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table counterman_score
     *
     * @mbggenerated Wed Mar 29 12:10:23 CST 2017
     */
    int updateByExampleSelective(@Param("record") CountermanScore record, @Param("example") CountermanScoreExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table counterman_score
     *
     * @mbggenerated Wed Mar 29 12:10:23 CST 2017
     */
    int updateByExample(@Param("record") CountermanScore record, @Param("example") CountermanScoreExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table counterman_score
     *
     * @mbggenerated Wed Mar 29 12:10:23 CST 2017
     */
    int updateByPrimaryKeySelective(CountermanScore record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table counterman_score
     *
     * @mbggenerated Wed Mar 29 12:10:23 CST 2017
     */
    int updateByPrimaryKey(CountermanScore record);
}