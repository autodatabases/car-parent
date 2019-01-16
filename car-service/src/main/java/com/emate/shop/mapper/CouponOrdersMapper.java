package com.emate.shop.mapper;

import com.emate.shop.business.model.CouponOrders;
import com.emate.shop.business.model.CouponOrdersExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CouponOrdersMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table coupon_orders
     *
     * @mbggenerated Mon Aug 27 17:53:35 CST 2018
     */
    int countByExample(CouponOrdersExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table coupon_orders
     *
     * @mbggenerated Mon Aug 27 17:53:35 CST 2018
     */
    int deleteByExample(CouponOrdersExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table coupon_orders
     *
     * @mbggenerated Mon Aug 27 17:53:35 CST 2018
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table coupon_orders
     *
     * @mbggenerated Mon Aug 27 17:53:35 CST 2018
     */
    int insert(CouponOrders record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table coupon_orders
     *
     * @mbggenerated Mon Aug 27 17:53:35 CST 2018
     */
    int insertSelective(CouponOrders record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table coupon_orders
     *
     * @mbggenerated Mon Aug 27 17:53:35 CST 2018
     */
    List<CouponOrders> selectByExample(CouponOrdersExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table coupon_orders
     *
     * @mbggenerated Mon Aug 27 17:53:35 CST 2018
     */
    CouponOrders selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table coupon_orders
     *
     * @mbggenerated Mon Aug 27 17:53:35 CST 2018
     */
    int updateByExampleSelective(@Param("record") CouponOrders record, @Param("example") CouponOrdersExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table coupon_orders
     *
     * @mbggenerated Mon Aug 27 17:53:35 CST 2018
     */
    int updateByExample(@Param("record") CouponOrders record, @Param("example") CouponOrdersExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table coupon_orders
     *
     * @mbggenerated Mon Aug 27 17:53:35 CST 2018
     */
    int updateByPrimaryKeySelective(CouponOrders record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table coupon_orders
     *
     * @mbggenerated Mon Aug 27 17:53:35 CST 2018
     */
    int updateByPrimaryKey(CouponOrders record);
}