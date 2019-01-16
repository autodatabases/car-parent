package com.emate.shop.mapper;

import com.emate.shop.business.model.AutoPartRelation;
import com.emate.shop.business.model.AutoPartRelationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AutoPartRelationMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auto_part_relation
     *
     * @mbggenerated Tue Oct 11 11:57:39 CST 2016
     */
    int countByExample(AutoPartRelationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auto_part_relation
     *
     * @mbggenerated Tue Oct 11 11:57:39 CST 2016
     */
    int deleteByExample(AutoPartRelationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auto_part_relation
     *
     * @mbggenerated Tue Oct 11 11:57:39 CST 2016
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auto_part_relation
     *
     * @mbggenerated Tue Oct 11 11:57:39 CST 2016
     */
    int insert(AutoPartRelation record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auto_part_relation
     *
     * @mbggenerated Tue Oct 11 11:57:39 CST 2016
     */
    int insertSelective(AutoPartRelation record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auto_part_relation
     *
     * @mbggenerated Tue Oct 11 11:57:39 CST 2016
     */
    List<AutoPartRelation> selectByExample(AutoPartRelationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auto_part_relation
     *
     * @mbggenerated Tue Oct 11 11:57:39 CST 2016
     */
    AutoPartRelation selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auto_part_relation
     *
     * @mbggenerated Tue Oct 11 11:57:39 CST 2016
     */
    int updateByExampleSelective(@Param("record") AutoPartRelation record, @Param("example") AutoPartRelationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auto_part_relation
     *
     * @mbggenerated Tue Oct 11 11:57:39 CST 2016
     */
    int updateByExample(@Param("record") AutoPartRelation record, @Param("example") AutoPartRelationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auto_part_relation
     *
     * @mbggenerated Tue Oct 11 11:57:39 CST 2016
     */
    int updateByPrimaryKeySelective(AutoPartRelation record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auto_part_relation
     *
     * @mbggenerated Tue Oct 11 11:57:39 CST 2016
     */
    int updateByPrimaryKey(AutoPartRelation record);
}