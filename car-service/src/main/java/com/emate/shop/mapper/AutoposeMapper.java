package com.emate.shop.mapper;

import com.emate.shop.business.model.Autopose;
import com.emate.shop.business.model.AutoposeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AutoposeMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table autoPose
     *
     * @mbggenerated Fri Aug 05 10:57:37 CST 2016
     */
    int countByExample(AutoposeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table autoPose
     *
     * @mbggenerated Fri Aug 05 10:57:37 CST 2016
     */
    int deleteByExample(AutoposeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table autoPose
     *
     * @mbggenerated Fri Aug 05 10:57:37 CST 2016
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table autoPose
     *
     * @mbggenerated Fri Aug 05 10:57:37 CST 2016
     */
    int insert(Autopose record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table autoPose
     *
     * @mbggenerated Fri Aug 05 10:57:37 CST 2016
     */
    int insertSelective(Autopose record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table autoPose
     *
     * @mbggenerated Fri Aug 05 10:57:37 CST 2016
     */
    List<Autopose> selectByExample(AutoposeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table autoPose
     *
     * @mbggenerated Fri Aug 05 10:57:37 CST 2016
     */
    Autopose selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table autoPose
     *
     * @mbggenerated Fri Aug 05 10:57:37 CST 2016
     */
    int updateByExampleSelective(@Param("record") Autopose record, @Param("example") AutoposeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table autoPose
     *
     * @mbggenerated Fri Aug 05 10:57:37 CST 2016
     */
    int updateByExample(@Param("record") Autopose record, @Param("example") AutoposeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table autoPose
     *
     * @mbggenerated Fri Aug 05 10:57:37 CST 2016
     */
    int updateByPrimaryKeySelective(Autopose record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table autoPose
     *
     * @mbggenerated Fri Aug 05 10:57:37 CST 2016
     */
    int updateByPrimaryKey(Autopose record);
}