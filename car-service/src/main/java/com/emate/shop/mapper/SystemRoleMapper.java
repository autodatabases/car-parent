package com.emate.shop.mapper;

import com.emate.shop.business.model.SystemRole;
import com.emate.shop.business.model.SystemRoleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SystemRoleMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table system_role
     *
     * @mbggenerated Mon Sep 12 13:47:19 CST 2016
     */
    int countByExample(SystemRoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table system_role
     *
     * @mbggenerated Mon Sep 12 13:47:19 CST 2016
     */
    int deleteByExample(SystemRoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table system_role
     *
     * @mbggenerated Mon Sep 12 13:47:19 CST 2016
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table system_role
     *
     * @mbggenerated Mon Sep 12 13:47:19 CST 2016
     */
    int insert(SystemRole record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table system_role
     *
     * @mbggenerated Mon Sep 12 13:47:19 CST 2016
     */
    int insertSelective(SystemRole record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table system_role
     *
     * @mbggenerated Mon Sep 12 13:47:19 CST 2016
     */
    List<SystemRole> selectByExample(SystemRoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table system_role
     *
     * @mbggenerated Mon Sep 12 13:47:19 CST 2016
     */
    SystemRole selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table system_role
     *
     * @mbggenerated Mon Sep 12 13:47:19 CST 2016
     */
    int updateByExampleSelective(@Param("record") SystemRole record, @Param("example") SystemRoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table system_role
     *
     * @mbggenerated Mon Sep 12 13:47:19 CST 2016
     */
    int updateByExample(@Param("record") SystemRole record, @Param("example") SystemRoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table system_role
     *
     * @mbggenerated Mon Sep 12 13:47:19 CST 2016
     */
    int updateByPrimaryKeySelective(SystemRole record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table system_role
     *
     * @mbggenerated Mon Sep 12 13:47:19 CST 2016
     */
    int updateByPrimaryKey(SystemRole record);
}