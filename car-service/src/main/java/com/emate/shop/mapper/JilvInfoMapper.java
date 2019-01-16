package com.emate.shop.mapper;

import com.emate.shop.business.model.JilvInfo;
import com.emate.shop.business.model.JilvInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface JilvInfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jilv_info
     *
     * @mbggenerated Thu Sep 22 11:03:44 CST 2016
     */
    int countByExample(JilvInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jilv_info
     *
     * @mbggenerated Thu Sep 22 11:03:44 CST 2016
     */
    int deleteByExample(JilvInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jilv_info
     *
     * @mbggenerated Thu Sep 22 11:03:44 CST 2016
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jilv_info
     *
     * @mbggenerated Thu Sep 22 11:03:44 CST 2016
     */
    int insert(JilvInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jilv_info
     *
     * @mbggenerated Thu Sep 22 11:03:44 CST 2016
     */
    int insertSelective(JilvInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jilv_info
     *
     * @mbggenerated Thu Sep 22 11:03:44 CST 2016
     */
    List<JilvInfo> selectByExample(JilvInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jilv_info
     *
     * @mbggenerated Thu Sep 22 11:03:44 CST 2016
     */
    JilvInfo selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jilv_info
     *
     * @mbggenerated Thu Sep 22 11:03:44 CST 2016
     */
    int updateByExampleSelective(@Param("record") JilvInfo record, @Param("example") JilvInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jilv_info
     *
     * @mbggenerated Thu Sep 22 11:03:44 CST 2016
     */
    int updateByExample(@Param("record") JilvInfo record, @Param("example") JilvInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jilv_info
     *
     * @mbggenerated Thu Sep 22 11:03:44 CST 2016
     */
    int updateByPrimaryKeySelective(JilvInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jilv_info
     *
     * @mbggenerated Thu Sep 22 11:03:44 CST 2016
     */
    int updateByPrimaryKey(JilvInfo record);
}