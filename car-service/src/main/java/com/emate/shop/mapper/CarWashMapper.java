package com.emate.shop.mapper;

import com.emate.shop.business.model.CarWash;
import com.emate.shop.business.model.CarWashExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CarWashMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table car_wash
     *
     * @mbggenerated Tue Apr 11 15:38:39 CST 2017
     */
    int countByExample(CarWashExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table car_wash
     *
     * @mbggenerated Tue Apr 11 15:38:39 CST 2017
     */
    int deleteByExample(CarWashExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table car_wash
     *
     * @mbggenerated Tue Apr 11 15:38:39 CST 2017
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table car_wash
     *
     * @mbggenerated Tue Apr 11 15:38:39 CST 2017
     */
    int insert(CarWash record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table car_wash
     *
     * @mbggenerated Tue Apr 11 15:38:39 CST 2017
     */
    int insertSelective(CarWash record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table car_wash
     *
     * @mbggenerated Tue Apr 11 15:38:39 CST 2017
     */
    List<CarWash> selectByExample(CarWashExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table car_wash
     *
     * @mbggenerated Tue Apr 11 15:38:39 CST 2017
     */
    CarWash selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table car_wash
     *
     * @mbggenerated Tue Apr 11 15:38:39 CST 2017
     */
    int updateByExampleSelective(@Param("record") CarWash record, @Param("example") CarWashExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table car_wash
     *
     * @mbggenerated Tue Apr 11 15:38:39 CST 2017
     */
    int updateByExample(@Param("record") CarWash record, @Param("example") CarWashExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table car_wash
     *
     * @mbggenerated Tue Apr 11 15:38:39 CST 2017
     */
    int updateByPrimaryKeySelective(CarWash record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table car_wash
     *
     * @mbggenerated Tue Apr 11 15:38:39 CST 2017
     */
    int updateByPrimaryKey(CarWash record);
}