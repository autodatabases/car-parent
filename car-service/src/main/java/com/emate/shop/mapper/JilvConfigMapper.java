package com.emate.shop.mapper;

import com.emate.shop.business.model.JilvConfig;
import com.emate.shop.business.model.JilvConfigExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface JilvConfigMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jilv_config
     *
     * @mbggenerated Tue Jun 06 16:42:17 CST 2017
     */
    int countByExample(JilvConfigExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jilv_config
     *
     * @mbggenerated Tue Jun 06 16:42:17 CST 2017
     */
    int deleteByExample(JilvConfigExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jilv_config
     *
     * @mbggenerated Tue Jun 06 16:42:17 CST 2017
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jilv_config
     *
     * @mbggenerated Tue Jun 06 16:42:17 CST 2017
     */
    int insert(JilvConfig record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jilv_config
     *
     * @mbggenerated Tue Jun 06 16:42:17 CST 2017
     */
    int insertSelective(JilvConfig record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jilv_config
     *
     * @mbggenerated Tue Jun 06 16:42:17 CST 2017
     */
    List<JilvConfig> selectByExample(JilvConfigExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jilv_config
     *
     * @mbggenerated Tue Jun 06 16:42:17 CST 2017
     */
    JilvConfig selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jilv_config
     *
     * @mbggenerated Tue Jun 06 16:42:17 CST 2017
     */
    int updateByExampleSelective(@Param("record") JilvConfig record, @Param("example") JilvConfigExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jilv_config
     *
     * @mbggenerated Tue Jun 06 16:42:17 CST 2017
     */
    int updateByExample(@Param("record") JilvConfig record, @Param("example") JilvConfigExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jilv_config
     *
     * @mbggenerated Tue Jun 06 16:42:17 CST 2017
     */
    int updateByPrimaryKeySelective(JilvConfig record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jilv_config
     *
     * @mbggenerated Tue Jun 06 16:42:17 CST 2017
     */
    int updateByPrimaryKey(JilvConfig record);
}