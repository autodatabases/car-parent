package com.emate.shop.mapper;

import com.emate.shop.business.model.SurveyCenterBranch;
import com.emate.shop.business.model.SurveyCenterBranchExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SurveyCenterBranchMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table survey_center_branch
     *
     * @mbggenerated Fri May 11 10:12:44 CST 2018
     */
    int countByExample(SurveyCenterBranchExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table survey_center_branch
     *
     * @mbggenerated Fri May 11 10:12:44 CST 2018
     */
    int deleteByExample(SurveyCenterBranchExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table survey_center_branch
     *
     * @mbggenerated Fri May 11 10:12:44 CST 2018
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table survey_center_branch
     *
     * @mbggenerated Fri May 11 10:12:44 CST 2018
     */
    int insert(SurveyCenterBranch record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table survey_center_branch
     *
     * @mbggenerated Fri May 11 10:12:44 CST 2018
     */
    int insertSelective(SurveyCenterBranch record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table survey_center_branch
     *
     * @mbggenerated Fri May 11 10:12:44 CST 2018
     */
    List<SurveyCenterBranch> selectByExample(SurveyCenterBranchExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table survey_center_branch
     *
     * @mbggenerated Fri May 11 10:12:44 CST 2018
     */
    SurveyCenterBranch selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table survey_center_branch
     *
     * @mbggenerated Fri May 11 10:12:44 CST 2018
     */
    int updateByExampleSelective(@Param("record") SurveyCenterBranch record, @Param("example") SurveyCenterBranchExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table survey_center_branch
     *
     * @mbggenerated Fri May 11 10:12:44 CST 2018
     */
    int updateByExample(@Param("record") SurveyCenterBranch record, @Param("example") SurveyCenterBranchExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table survey_center_branch
     *
     * @mbggenerated Fri May 11 10:12:44 CST 2018
     */
    int updateByPrimaryKeySelective(SurveyCenterBranch record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table survey_center_branch
     *
     * @mbggenerated Fri May 11 10:12:44 CST 2018
     */
    int updateByPrimaryKey(SurveyCenterBranch record);
}