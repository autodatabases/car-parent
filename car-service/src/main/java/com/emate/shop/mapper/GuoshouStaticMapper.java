package com.emate.shop.mapper;

import com.emate.shop.business.model.GuoshouStatic;
import com.emate.shop.business.model.GuoshouStaticExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GuoshouStaticMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table guoshou_static
     *
     * @mbggenerated Thu Jun 22 15:09:34 CST 2017
     */
    int countByExample(GuoshouStaticExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table guoshou_static
     *
     * @mbggenerated Thu Jun 22 15:09:34 CST 2017
     */
    int deleteByExample(GuoshouStaticExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table guoshou_static
     *
     * @mbggenerated Thu Jun 22 15:09:34 CST 2017
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table guoshou_static
     *
     * @mbggenerated Thu Jun 22 15:09:34 CST 2017
     */
    int insert(GuoshouStatic record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table guoshou_static
     *
     * @mbggenerated Thu Jun 22 15:09:34 CST 2017
     */
    int insertSelective(GuoshouStatic record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table guoshou_static
     *
     * @mbggenerated Thu Jun 22 15:09:34 CST 2017
     */
    List<GuoshouStatic> selectByExample(GuoshouStaticExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table guoshou_static
     *
     * @mbggenerated Thu Jun 22 15:09:34 CST 2017
     */
    GuoshouStatic selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table guoshou_static
     *
     * @mbggenerated Thu Jun 22 15:09:34 CST 2017
     */
    int updateByExampleSelective(@Param("record") GuoshouStatic record, @Param("example") GuoshouStaticExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table guoshou_static
     *
     * @mbggenerated Thu Jun 22 15:09:34 CST 2017
     */
    int updateByExample(@Param("record") GuoshouStatic record, @Param("example") GuoshouStaticExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table guoshou_static
     *
     * @mbggenerated Thu Jun 22 15:09:34 CST 2017
     */
    int updateByPrimaryKeySelective(GuoshouStatic record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table guoshou_static
     *
     * @mbggenerated Thu Jun 22 15:09:34 CST 2017
     */
    int updateByPrimaryKey(GuoshouStatic record);
}