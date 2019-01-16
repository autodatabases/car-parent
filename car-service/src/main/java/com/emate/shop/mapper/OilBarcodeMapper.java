package com.emate.shop.mapper;

import com.emate.shop.business.model.OilBarcode;
import com.emate.shop.business.model.OilBarcodeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OilBarcodeMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oil_barcode
     *
     * @mbggenerated Thu Sep 22 16:08:30 CST 2016
     */
    int countByExample(OilBarcodeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oil_barcode
     *
     * @mbggenerated Thu Sep 22 16:08:30 CST 2016
     */
    int deleteByExample(OilBarcodeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oil_barcode
     *
     * @mbggenerated Thu Sep 22 16:08:30 CST 2016
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oil_barcode
     *
     * @mbggenerated Thu Sep 22 16:08:30 CST 2016
     */
    int insert(OilBarcode record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oil_barcode
     *
     * @mbggenerated Thu Sep 22 16:08:30 CST 2016
     */
    int insertSelective(OilBarcode record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oil_barcode
     *
     * @mbggenerated Thu Sep 22 16:08:30 CST 2016
     */
    List<OilBarcode> selectByExample(OilBarcodeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oil_barcode
     *
     * @mbggenerated Thu Sep 22 16:08:30 CST 2016
     */
    OilBarcode selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oil_barcode
     *
     * @mbggenerated Thu Sep 22 16:08:30 CST 2016
     */
    int updateByExampleSelective(@Param("record") OilBarcode record, @Param("example") OilBarcodeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oil_barcode
     *
     * @mbggenerated Thu Sep 22 16:08:30 CST 2016
     */
    int updateByExample(@Param("record") OilBarcode record, @Param("example") OilBarcodeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oil_barcode
     *
     * @mbggenerated Thu Sep 22 16:08:30 CST 2016
     */
    int updateByPrimaryKeySelective(OilBarcode record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oil_barcode
     *
     * @mbggenerated Thu Sep 22 16:08:30 CST 2016
     */
    int updateByPrimaryKey(OilBarcode record);
}