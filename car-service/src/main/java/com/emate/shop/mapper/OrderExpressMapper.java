package com.emate.shop.mapper;

import com.emate.shop.business.model.OrderExpress;
import com.emate.shop.business.model.OrderExpressExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrderExpressMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_express
     *
     * @mbggenerated Mon Dec 12 11:04:32 CST 2016
     */
    int countByExample(OrderExpressExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_express
     *
     * @mbggenerated Mon Dec 12 11:04:32 CST 2016
     */
    int deleteByExample(OrderExpressExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_express
     *
     * @mbggenerated Mon Dec 12 11:04:32 CST 2016
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_express
     *
     * @mbggenerated Mon Dec 12 11:04:32 CST 2016
     */
    int insert(OrderExpress record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_express
     *
     * @mbggenerated Mon Dec 12 11:04:32 CST 2016
     */
    int insertSelective(OrderExpress record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_express
     *
     * @mbggenerated Mon Dec 12 11:04:32 CST 2016
     */
    List<OrderExpress> selectByExample(OrderExpressExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_express
     *
     * @mbggenerated Mon Dec 12 11:04:32 CST 2016
     */
    OrderExpress selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_express
     *
     * @mbggenerated Mon Dec 12 11:04:32 CST 2016
     */
    int updateByExampleSelective(@Param("record") OrderExpress record, @Param("example") OrderExpressExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_express
     *
     * @mbggenerated Mon Dec 12 11:04:32 CST 2016
     */
    int updateByExample(@Param("record") OrderExpress record, @Param("example") OrderExpressExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_express
     *
     * @mbggenerated Mon Dec 12 11:04:32 CST 2016
     */
    int updateByPrimaryKeySelective(OrderExpress record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_express
     *
     * @mbggenerated Mon Dec 12 11:04:32 CST 2016
     */
    int updateByPrimaryKey(OrderExpress record);
}