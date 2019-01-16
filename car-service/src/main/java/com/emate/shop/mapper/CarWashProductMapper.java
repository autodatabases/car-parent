package com.emate.shop.mapper;

import com.emate.shop.business.model.CarWashProduct;
import com.emate.shop.business.model.CarWashProductExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CarWashProductMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table car_wash_product
     *
     * @mbggenerated Thu Oct 12 15:16:50 CST 2017
     */
    int countByExample(CarWashProductExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table car_wash_product
     *
     * @mbggenerated Thu Oct 12 15:16:50 CST 2017
     */
    int deleteByExample(CarWashProductExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table car_wash_product
     *
     * @mbggenerated Thu Oct 12 15:16:50 CST 2017
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table car_wash_product
     *
     * @mbggenerated Thu Oct 12 15:16:50 CST 2017
     */
    int insert(CarWashProduct record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table car_wash_product
     *
     * @mbggenerated Thu Oct 12 15:16:50 CST 2017
     */
    int insertSelective(CarWashProduct record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table car_wash_product
     *
     * @mbggenerated Thu Oct 12 15:16:50 CST 2017
     */
    List<CarWashProduct> selectByExample(CarWashProductExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table car_wash_product
     *
     * @mbggenerated Thu Oct 12 15:16:50 CST 2017
     */
    CarWashProduct selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table car_wash_product
     *
     * @mbggenerated Thu Oct 12 15:16:50 CST 2017
     */
    int updateByExampleSelective(@Param("record") CarWashProduct record, @Param("example") CarWashProductExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table car_wash_product
     *
     * @mbggenerated Thu Oct 12 15:16:50 CST 2017
     */
    int updateByExample(@Param("record") CarWashProduct record, @Param("example") CarWashProductExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table car_wash_product
     *
     * @mbggenerated Thu Oct 12 15:16:50 CST 2017
     */
    int updateByPrimaryKeySelective(CarWashProduct record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table car_wash_product
     *
     * @mbggenerated Thu Oct 12 15:16:50 CST 2017
     */
    int updateByPrimaryKey(CarWashProduct record);
}