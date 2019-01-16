package com.emate.shop.mapper;

import com.emate.shop.business.model.OilRechargeCode;
import com.emate.shop.business.model.OilRechargeCodeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OilRechargeCodeMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oil_recharge_code
     *
     * @mbggenerated Mon Apr 16 17:14:11 CST 2018
     */
    int countByExample(OilRechargeCodeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oil_recharge_code
     *
     * @mbggenerated Mon Apr 16 17:14:11 CST 2018
     */
    int deleteByExample(OilRechargeCodeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oil_recharge_code
     *
     * @mbggenerated Mon Apr 16 17:14:11 CST 2018
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oil_recharge_code
     *
     * @mbggenerated Mon Apr 16 17:14:11 CST 2018
     */
    int insert(OilRechargeCode record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oil_recharge_code
     *
     * @mbggenerated Mon Apr 16 17:14:11 CST 2018
     */
    int insertSelective(OilRechargeCode record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oil_recharge_code
     *
     * @mbggenerated Mon Apr 16 17:14:11 CST 2018
     */
    List<OilRechargeCode> selectByExample(OilRechargeCodeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oil_recharge_code
     *
     * @mbggenerated Mon Apr 16 17:14:11 CST 2018
     */
    OilRechargeCode selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oil_recharge_code
     *
     * @mbggenerated Mon Apr 16 17:14:11 CST 2018
     */
    int updateByExampleSelective(@Param("record") OilRechargeCode record, @Param("example") OilRechargeCodeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oil_recharge_code
     *
     * @mbggenerated Mon Apr 16 17:14:11 CST 2018
     */
    int updateByExample(@Param("record") OilRechargeCode record, @Param("example") OilRechargeCodeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oil_recharge_code
     *
     * @mbggenerated Mon Apr 16 17:14:11 CST 2018
     */
    int updateByPrimaryKeySelective(OilRechargeCode record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oil_recharge_code
     *
     * @mbggenerated Mon Apr 16 17:14:11 CST 2018
     */
    int updateByPrimaryKey(OilRechargeCode record);
}