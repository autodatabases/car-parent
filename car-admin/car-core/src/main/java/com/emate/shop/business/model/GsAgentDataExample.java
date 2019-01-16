package com.emate.shop.business.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GsAgentDataExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table gs_agent_data
     *
     * @mbggenerated Tue Jun 27 17:00:40 CST 2017
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table gs_agent_data
     *
     * @mbggenerated Tue Jun 27 17:00:40 CST 2017
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table gs_agent_data
     *
     * @mbggenerated Tue Jun 27 17:00:40 CST 2017
     */
    protected List<Criteria> oredCriteria;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table gs_agent_data
     *
     * @mbggenerated Tue Jun 27 17:00:40 CST 2017
     */
    protected Integer limitStart;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table gs_agent_data
     *
     * @mbggenerated Tue Jun 27 17:00:40 CST 2017
     */
    protected Integer limitEnd;

    public static final String ID_ASC = "id asc";

    public static final String ID_DESC = "id desc";

    public static final String AGENCY_ID_ASC = "agency_id asc";

    public static final String AGENCY_ID_DESC = "agency_id desc";

    public static final String YEAR_ASC = "year asc";

    public static final String YEAR_DESC = "year desc";

    public static final String MONTH_ASC = "month asc";

    public static final String MONTH_DESC = "month desc";

    public static final String PREMIUM_ASC = "premium asc";

    public static final String PREMIUM_DESC = "premium desc";

    public static final String REPLACE_VALUE_ASC = "replace_value asc";

    public static final String REPLACE_VALUE_DESC = "replace_value desc";

    public static final String REPLACE_RATE_ASC = "replace_rate asc";

    public static final String REPLACE_RATE_DESC = "replace_rate desc";

    public static final String LOSS_RATE_ASC = "loss_rate asc";

    public static final String LOSS_RATE_DESC = "loss_rate desc";

    public static final String CREATE_TIME_ASC = "create_time asc";

    public static final String CREATE_TIME_DESC = "create_time desc";

    public static final String UPDATE_TIME_ASC = "update_time asc";

    public static final String UPDATE_TIME_DESC = "update_time desc";

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table gs_agent_data
     *
     * @mbggenerated Tue Jun 27 17:00:40 CST 2017
     */
    public GsAgentDataExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table gs_agent_data
     *
     * @mbggenerated Tue Jun 27 17:00:40 CST 2017
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table gs_agent_data
     *
     * @mbggenerated Tue Jun 27 17:00:40 CST 2017
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table gs_agent_data
     *
     * @mbggenerated Tue Jun 27 17:00:40 CST 2017
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table gs_agent_data
     *
     * @mbggenerated Tue Jun 27 17:00:40 CST 2017
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table gs_agent_data
     *
     * @mbggenerated Tue Jun 27 17:00:40 CST 2017
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table gs_agent_data
     *
     * @mbggenerated Tue Jun 27 17:00:40 CST 2017
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table gs_agent_data
     *
     * @mbggenerated Tue Jun 27 17:00:40 CST 2017
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table gs_agent_data
     *
     * @mbggenerated Tue Jun 27 17:00:40 CST 2017
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table gs_agent_data
     *
     * @mbggenerated Tue Jun 27 17:00:40 CST 2017
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table gs_agent_data
     *
     * @mbggenerated Tue Jun 27 17:00:40 CST 2017
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table gs_agent_data
     *
     * @mbggenerated Tue Jun 27 17:00:40 CST 2017
     */
    public void setLimitStart(Integer limitStart) {
        this.limitStart=limitStart;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table gs_agent_data
     *
     * @mbggenerated Tue Jun 27 17:00:40 CST 2017
     */
    public Integer getLimitStart() {
        return limitStart;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table gs_agent_data
     *
     * @mbggenerated Tue Jun 27 17:00:40 CST 2017
     */
    public void setLimitEnd(Integer limitEnd) {
        this.limitEnd=limitEnd;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table gs_agent_data
     *
     * @mbggenerated Tue Jun 27 17:00:40 CST 2017
     */
    public Integer getLimitEnd() {
        return limitEnd;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table gs_agent_data
     *
     * @mbggenerated Tue Jun 27 17:00:40 CST 2017
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andAgencyIdIsNull() {
            addCriterion("agency_id is null");
            return (Criteria) this;
        }

        public Criteria andAgencyIdIsNotNull() {
            addCriterion("agency_id is not null");
            return (Criteria) this;
        }

        public Criteria andAgencyIdEqualTo(Long value) {
            addCriterion("agency_id =", value, "agencyId");
            return (Criteria) this;
        }

        public Criteria andAgencyIdNotEqualTo(Long value) {
            addCriterion("agency_id <>", value, "agencyId");
            return (Criteria) this;
        }

        public Criteria andAgencyIdGreaterThan(Long value) {
            addCriterion("agency_id >", value, "agencyId");
            return (Criteria) this;
        }

        public Criteria andAgencyIdGreaterThanOrEqualTo(Long value) {
            addCriterion("agency_id >=", value, "agencyId");
            return (Criteria) this;
        }

        public Criteria andAgencyIdLessThan(Long value) {
            addCriterion("agency_id <", value, "agencyId");
            return (Criteria) this;
        }

        public Criteria andAgencyIdLessThanOrEqualTo(Long value) {
            addCriterion("agency_id <=", value, "agencyId");
            return (Criteria) this;
        }

        public Criteria andAgencyIdIn(List<Long> values) {
            addCriterion("agency_id in", values, "agencyId");
            return (Criteria) this;
        }

        public Criteria andAgencyIdNotIn(List<Long> values) {
            addCriterion("agency_id not in", values, "agencyId");
            return (Criteria) this;
        }

        public Criteria andAgencyIdBetween(Long value1, Long value2) {
            addCriterion("agency_id between", value1, value2, "agencyId");
            return (Criteria) this;
        }

        public Criteria andAgencyIdNotBetween(Long value1, Long value2) {
            addCriterion("agency_id not between", value1, value2, "agencyId");
            return (Criteria) this;
        }

        public Criteria andYearIsNull() {
            addCriterion("year is null");
            return (Criteria) this;
        }

        public Criteria andYearIsNotNull() {
            addCriterion("year is not null");
            return (Criteria) this;
        }

        public Criteria andYearEqualTo(String value) {
            addCriterion("year =", value, "year");
            return (Criteria) this;
        }

        public Criteria andYearNotEqualTo(String value) {
            addCriterion("year <>", value, "year");
            return (Criteria) this;
        }

        public Criteria andYearGreaterThan(String value) {
            addCriterion("year >", value, "year");
            return (Criteria) this;
        }

        public Criteria andYearGreaterThanOrEqualTo(String value) {
            addCriterion("year >=", value, "year");
            return (Criteria) this;
        }

        public Criteria andYearLessThan(String value) {
            addCriterion("year <", value, "year");
            return (Criteria) this;
        }

        public Criteria andYearLessThanOrEqualTo(String value) {
            addCriterion("year <=", value, "year");
            return (Criteria) this;
        }

        public Criteria andYearLike(String value) {
            addCriterion("year like", value, "year");
            return (Criteria) this;
        }

        public Criteria andYearNotLike(String value) {
            addCriterion("year not like", value, "year");
            return (Criteria) this;
        }

        public Criteria andYearIn(List<String> values) {
            addCriterion("year in", values, "year");
            return (Criteria) this;
        }

        public Criteria andYearNotIn(List<String> values) {
            addCriterion("year not in", values, "year");
            return (Criteria) this;
        }

        public Criteria andYearBetween(String value1, String value2) {
            addCriterion("year between", value1, value2, "year");
            return (Criteria) this;
        }

        public Criteria andYearNotBetween(String value1, String value2) {
            addCriterion("year not between", value1, value2, "year");
            return (Criteria) this;
        }

        public Criteria andMonthIsNull() {
            addCriterion("month is null");
            return (Criteria) this;
        }

        public Criteria andMonthIsNotNull() {
            addCriterion("month is not null");
            return (Criteria) this;
        }

        public Criteria andMonthEqualTo(String value) {
            addCriterion("month =", value, "month");
            return (Criteria) this;
        }

        public Criteria andMonthNotEqualTo(String value) {
            addCriterion("month <>", value, "month");
            return (Criteria) this;
        }

        public Criteria andMonthGreaterThan(String value) {
            addCriterion("month >", value, "month");
            return (Criteria) this;
        }

        public Criteria andMonthGreaterThanOrEqualTo(String value) {
            addCriterion("month >=", value, "month");
            return (Criteria) this;
        }

        public Criteria andMonthLessThan(String value) {
            addCriterion("month <", value, "month");
            return (Criteria) this;
        }

        public Criteria andMonthLessThanOrEqualTo(String value) {
            addCriterion("month <=", value, "month");
            return (Criteria) this;
        }

        public Criteria andMonthLike(String value) {
            addCriterion("month like", value, "month");
            return (Criteria) this;
        }

        public Criteria andMonthNotLike(String value) {
            addCriterion("month not like", value, "month");
            return (Criteria) this;
        }

        public Criteria andMonthIn(List<String> values) {
            addCriterion("month in", values, "month");
            return (Criteria) this;
        }

        public Criteria andMonthNotIn(List<String> values) {
            addCriterion("month not in", values, "month");
            return (Criteria) this;
        }

        public Criteria andMonthBetween(String value1, String value2) {
            addCriterion("month between", value1, value2, "month");
            return (Criteria) this;
        }

        public Criteria andMonthNotBetween(String value1, String value2) {
            addCriterion("month not between", value1, value2, "month");
            return (Criteria) this;
        }

        public Criteria andPremiumIsNull() {
            addCriterion("premium is null");
            return (Criteria) this;
        }

        public Criteria andPremiumIsNotNull() {
            addCriterion("premium is not null");
            return (Criteria) this;
        }

        public Criteria andPremiumEqualTo(BigDecimal value) {
            addCriterion("premium =", value, "premium");
            return (Criteria) this;
        }

        public Criteria andPremiumNotEqualTo(BigDecimal value) {
            addCriterion("premium <>", value, "premium");
            return (Criteria) this;
        }

        public Criteria andPremiumGreaterThan(BigDecimal value) {
            addCriterion("premium >", value, "premium");
            return (Criteria) this;
        }

        public Criteria andPremiumGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("premium >=", value, "premium");
            return (Criteria) this;
        }

        public Criteria andPremiumLessThan(BigDecimal value) {
            addCriterion("premium <", value, "premium");
            return (Criteria) this;
        }

        public Criteria andPremiumLessThanOrEqualTo(BigDecimal value) {
            addCriterion("premium <=", value, "premium");
            return (Criteria) this;
        }

        public Criteria andPremiumIn(List<BigDecimal> values) {
            addCriterion("premium in", values, "premium");
            return (Criteria) this;
        }

        public Criteria andPremiumNotIn(List<BigDecimal> values) {
            addCriterion("premium not in", values, "premium");
            return (Criteria) this;
        }

        public Criteria andPremiumBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("premium between", value1, value2, "premium");
            return (Criteria) this;
        }

        public Criteria andPremiumNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("premium not between", value1, value2, "premium");
            return (Criteria) this;
        }

        public Criteria andReplaceValueIsNull() {
            addCriterion("replace_value is null");
            return (Criteria) this;
        }

        public Criteria andReplaceValueIsNotNull() {
            addCriterion("replace_value is not null");
            return (Criteria) this;
        }

        public Criteria andReplaceValueEqualTo(BigDecimal value) {
            addCriterion("replace_value =", value, "replaceValue");
            return (Criteria) this;
        }

        public Criteria andReplaceValueNotEqualTo(BigDecimal value) {
            addCriterion("replace_value <>", value, "replaceValue");
            return (Criteria) this;
        }

        public Criteria andReplaceValueGreaterThan(BigDecimal value) {
            addCriterion("replace_value >", value, "replaceValue");
            return (Criteria) this;
        }

        public Criteria andReplaceValueGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("replace_value >=", value, "replaceValue");
            return (Criteria) this;
        }

        public Criteria andReplaceValueLessThan(BigDecimal value) {
            addCriterion("replace_value <", value, "replaceValue");
            return (Criteria) this;
        }

        public Criteria andReplaceValueLessThanOrEqualTo(BigDecimal value) {
            addCriterion("replace_value <=", value, "replaceValue");
            return (Criteria) this;
        }

        public Criteria andReplaceValueIn(List<BigDecimal> values) {
            addCriterion("replace_value in", values, "replaceValue");
            return (Criteria) this;
        }

        public Criteria andReplaceValueNotIn(List<BigDecimal> values) {
            addCriterion("replace_value not in", values, "replaceValue");
            return (Criteria) this;
        }

        public Criteria andReplaceValueBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("replace_value between", value1, value2, "replaceValue");
            return (Criteria) this;
        }

        public Criteria andReplaceValueNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("replace_value not between", value1, value2, "replaceValue");
            return (Criteria) this;
        }

        public Criteria andReplaceRateIsNull() {
            addCriterion("replace_rate is null");
            return (Criteria) this;
        }

        public Criteria andReplaceRateIsNotNull() {
            addCriterion("replace_rate is not null");
            return (Criteria) this;
        }

        public Criteria andReplaceRateEqualTo(BigDecimal value) {
            addCriterion("replace_rate =", value, "replaceRate");
            return (Criteria) this;
        }

        public Criteria andReplaceRateNotEqualTo(BigDecimal value) {
            addCriterion("replace_rate <>", value, "replaceRate");
            return (Criteria) this;
        }

        public Criteria andReplaceRateGreaterThan(BigDecimal value) {
            addCriterion("replace_rate >", value, "replaceRate");
            return (Criteria) this;
        }

        public Criteria andReplaceRateGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("replace_rate >=", value, "replaceRate");
            return (Criteria) this;
        }

        public Criteria andReplaceRateLessThan(BigDecimal value) {
            addCriterion("replace_rate <", value, "replaceRate");
            return (Criteria) this;
        }

        public Criteria andReplaceRateLessThanOrEqualTo(BigDecimal value) {
            addCriterion("replace_rate <=", value, "replaceRate");
            return (Criteria) this;
        }

        public Criteria andReplaceRateIn(List<BigDecimal> values) {
            addCriterion("replace_rate in", values, "replaceRate");
            return (Criteria) this;
        }

        public Criteria andReplaceRateNotIn(List<BigDecimal> values) {
            addCriterion("replace_rate not in", values, "replaceRate");
            return (Criteria) this;
        }

        public Criteria andReplaceRateBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("replace_rate between", value1, value2, "replaceRate");
            return (Criteria) this;
        }

        public Criteria andReplaceRateNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("replace_rate not between", value1, value2, "replaceRate");
            return (Criteria) this;
        }

        public Criteria andLossRateIsNull() {
            addCriterion("loss_rate is null");
            return (Criteria) this;
        }

        public Criteria andLossRateIsNotNull() {
            addCriterion("loss_rate is not null");
            return (Criteria) this;
        }

        public Criteria andLossRateEqualTo(BigDecimal value) {
            addCriterion("loss_rate =", value, "lossRate");
            return (Criteria) this;
        }

        public Criteria andLossRateNotEqualTo(BigDecimal value) {
            addCriterion("loss_rate <>", value, "lossRate");
            return (Criteria) this;
        }

        public Criteria andLossRateGreaterThan(BigDecimal value) {
            addCriterion("loss_rate >", value, "lossRate");
            return (Criteria) this;
        }

        public Criteria andLossRateGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("loss_rate >=", value, "lossRate");
            return (Criteria) this;
        }

        public Criteria andLossRateLessThan(BigDecimal value) {
            addCriterion("loss_rate <", value, "lossRate");
            return (Criteria) this;
        }

        public Criteria andLossRateLessThanOrEqualTo(BigDecimal value) {
            addCriterion("loss_rate <=", value, "lossRate");
            return (Criteria) this;
        }

        public Criteria andLossRateIn(List<BigDecimal> values) {
            addCriterion("loss_rate in", values, "lossRate");
            return (Criteria) this;
        }

        public Criteria andLossRateNotIn(List<BigDecimal> values) {
            addCriterion("loss_rate not in", values, "lossRate");
            return (Criteria) this;
        }

        public Criteria andLossRateBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("loss_rate between", value1, value2, "lossRate");
            return (Criteria) this;
        }

        public Criteria andLossRateNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("loss_rate not between", value1, value2, "lossRate");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table gs_agent_data
     *
     * @mbggenerated do_not_delete_during_merge Tue Jun 27 17:00:40 CST 2017
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table gs_agent_data
     *
     * @mbggenerated Tue Jun 27 17:00:40 CST 2017
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}