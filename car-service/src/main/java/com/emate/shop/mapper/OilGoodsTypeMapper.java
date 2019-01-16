package com.emate.shop.mapper;

import com.emate.shop.business.model.OilGoodsType;
import com.emate.shop.business.model.OilGoodsTypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OilGoodsTypeMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oil_goods_type
     *
     * @mbggenerated Tue Jun 26 16:33:35 CST 2018
     */
    int countByExample(OilGoodsTypeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oil_goods_type
     *
     * @mbggenerated Tue Jun 26 16:33:35 CST 2018
     */
    int deleteByExample(OilGoodsTypeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oil_goods_type
     *
     * @mbggenerated Tue Jun 26 16:33:35 CST 2018
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oil_goods_type
     *
     * @mbggenerated Tue Jun 26 16:33:35 CST 2018
     */
    int insert(OilGoodsType record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oil_goods_type
     *
     * @mbggenerated Tue Jun 26 16:33:35 CST 2018
     */
    int insertSelective(OilGoodsType record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oil_goods_type
     *
     * @mbggenerated Tue Jun 26 16:33:35 CST 2018
     */
    List<OilGoodsType> selectByExample(OilGoodsTypeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oil_goods_type
     *
     * @mbggenerated Tue Jun 26 16:33:35 CST 2018
     */
    OilGoodsType selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oil_goods_type
     *
     * @mbggenerated Tue Jun 26 16:33:35 CST 2018
     */
    int updateByExampleSelective(@Param("record") OilGoodsType record, @Param("example") OilGoodsTypeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oil_goods_type
     *
     * @mbggenerated Tue Jun 26 16:33:35 CST 2018
     */
    int updateByExample(@Param("record") OilGoodsType record, @Param("example") OilGoodsTypeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oil_goods_type
     *
     * @mbggenerated Tue Jun 26 16:33:35 CST 2018
     */
    int updateByPrimaryKeySelective(OilGoodsType record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oil_goods_type
     *
     * @mbggenerated Tue Jun 26 16:33:35 CST 2018
     */
    int updateByPrimaryKey(OilGoodsType record);
}