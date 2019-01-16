package com.emate.shop.mapper;

import com.emate.shop.business.model.Counterman;
import com.emate.shop.business.model.CountermanExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CountermanMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table counterman
     *
     * @mbggenerated Mon Jul 24 14:02:47 CST 2017
     */
    int countByExample(CountermanExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table counterman
     *
     * @mbggenerated Mon Jul 24 14:02:47 CST 2017
     */
    int deleteByExample(CountermanExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table counterman
     *
     * @mbggenerated Mon Jul 24 14:02:47 CST 2017
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table counterman
     *
     * @mbggenerated Mon Jul 24 14:02:47 CST 2017
     */
    int insert(Counterman record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table counterman
     *
     * @mbggenerated Mon Jul 24 14:02:47 CST 2017
     */
    int insertSelective(Counterman record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table counterman
     *
     * @mbggenerated Mon Jul 24 14:02:47 CST 2017
     */
    List<Counterman> selectByExample(CountermanExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table counterman
     *
     * @mbggenerated Mon Jul 24 14:02:47 CST 2017
     */
    Counterman selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table counterman
     *
     * @mbggenerated Mon Jul 24 14:02:47 CST 2017
     */
    int updateByExampleSelective(@Param("record") Counterman record, @Param("example") CountermanExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table counterman
     *
     * @mbggenerated Mon Jul 24 14:02:47 CST 2017
     */
    int updateByExample(@Param("record") Counterman record, @Param("example") CountermanExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table counterman
     *
     * @mbggenerated Mon Jul 24 14:02:47 CST 2017
     */
    int updateByPrimaryKeySelective(Counterman record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table counterman
     *
     * @mbggenerated Mon Jul 24 14:02:47 CST 2017
     */
    int updateByPrimaryKey(Counterman record);
}