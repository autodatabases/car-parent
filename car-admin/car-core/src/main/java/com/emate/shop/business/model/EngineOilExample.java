package com.emate.shop.business.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EngineOilExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table engine_oil
     *
     * @mbggenerated Wed Sep 21 11:22:36 CST 2016
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table engine_oil
     *
     * @mbggenerated Wed Sep 21 11:22:36 CST 2016
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table engine_oil
     *
     * @mbggenerated Wed Sep 21 11:22:36 CST 2016
     */
    protected List<Criteria> oredCriteria;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table engine_oil
     *
     * @mbggenerated Wed Sep 21 11:22:36 CST 2016
     */
    protected Integer limitStart;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table engine_oil
     *
     * @mbggenerated Wed Sep 21 11:22:36 CST 2016
     */
    protected Integer limitEnd;

    public static final String ID_ASC = "id asc";

    public static final String ID_DESC = "id desc";

    public static final String BRAND_ASC = "brand asc";

    public static final String BRAND_DESC = "brand desc";

    public static final String AUTOLINE_NAME_ASC = "autoLine_name asc";

    public static final String AUTOLINE_NAME_DESC = "autoLine_name desc";

    public static final String ENGINE_DISP_ASC = "engine_disp asc";

    public static final String ENGINE_DISP_DESC = "engine_disp desc";

    public static final String PRODUCT_TIME_ASC = "product_time asc";

    public static final String PRODUCT_TIME_DESC = "product_time desc";

    public static final String TRANS_DESC_ASC = "trans_desc asc";

    public static final String TRANS_DESC_DESC = "trans_desc desc";

    public static final String JIYOU_ASC = "jiyou asc";

    public static final String JIYOU_DESC = "jiyou desc";

    public static final String REMARK_ASC = "remark asc";

    public static final String REMARK_DESC = "remark desc";

    public static final String CREATE_TIME_ASC = "create_time asc";

    public static final String CREATE_TIME_DESC = "create_time desc";

    public static final String UPDATE_TIME_ASC = "update_time asc";

    public static final String UPDATE_TIME_DESC = "update_time desc";

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table engine_oil
     *
     * @mbggenerated Wed Sep 21 11:22:36 CST 2016
     */
    public EngineOilExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table engine_oil
     *
     * @mbggenerated Wed Sep 21 11:22:36 CST 2016
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table engine_oil
     *
     * @mbggenerated Wed Sep 21 11:22:36 CST 2016
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table engine_oil
     *
     * @mbggenerated Wed Sep 21 11:22:36 CST 2016
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table engine_oil
     *
     * @mbggenerated Wed Sep 21 11:22:36 CST 2016
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table engine_oil
     *
     * @mbggenerated Wed Sep 21 11:22:36 CST 2016
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table engine_oil
     *
     * @mbggenerated Wed Sep 21 11:22:36 CST 2016
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table engine_oil
     *
     * @mbggenerated Wed Sep 21 11:22:36 CST 2016
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table engine_oil
     *
     * @mbggenerated Wed Sep 21 11:22:36 CST 2016
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
     * This method corresponds to the database table engine_oil
     *
     * @mbggenerated Wed Sep 21 11:22:36 CST 2016
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table engine_oil
     *
     * @mbggenerated Wed Sep 21 11:22:36 CST 2016
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table engine_oil
     *
     * @mbggenerated Wed Sep 21 11:22:36 CST 2016
     */
    public void setLimitStart(Integer limitStart) {
        this.limitStart=limitStart;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table engine_oil
     *
     * @mbggenerated Wed Sep 21 11:22:36 CST 2016
     */
    public Integer getLimitStart() {
        return limitStart;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table engine_oil
     *
     * @mbggenerated Wed Sep 21 11:22:36 CST 2016
     */
    public void setLimitEnd(Integer limitEnd) {
        this.limitEnd=limitEnd;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table engine_oil
     *
     * @mbggenerated Wed Sep 21 11:22:36 CST 2016
     */
    public Integer getLimitEnd() {
        return limitEnd;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table engine_oil
     *
     * @mbggenerated Wed Sep 21 11:22:36 CST 2016
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

        public Criteria andBrandIsNull() {
            addCriterion("brand is null");
            return (Criteria) this;
        }

        public Criteria andBrandIsNotNull() {
            addCriterion("brand is not null");
            return (Criteria) this;
        }

        public Criteria andBrandEqualTo(String value) {
            addCriterion("brand =", value, "brand");
            return (Criteria) this;
        }

        public Criteria andBrandNotEqualTo(String value) {
            addCriterion("brand <>", value, "brand");
            return (Criteria) this;
        }

        public Criteria andBrandGreaterThan(String value) {
            addCriterion("brand >", value, "brand");
            return (Criteria) this;
        }

        public Criteria andBrandGreaterThanOrEqualTo(String value) {
            addCriterion("brand >=", value, "brand");
            return (Criteria) this;
        }

        public Criteria andBrandLessThan(String value) {
            addCriterion("brand <", value, "brand");
            return (Criteria) this;
        }

        public Criteria andBrandLessThanOrEqualTo(String value) {
            addCriterion("brand <=", value, "brand");
            return (Criteria) this;
        }

        public Criteria andBrandLike(String value) {
            addCriterion("brand like", value, "brand");
            return (Criteria) this;
        }

        public Criteria andBrandNotLike(String value) {
            addCriterion("brand not like", value, "brand");
            return (Criteria) this;
        }

        public Criteria andBrandIn(List<String> values) {
            addCriterion("brand in", values, "brand");
            return (Criteria) this;
        }

        public Criteria andBrandNotIn(List<String> values) {
            addCriterion("brand not in", values, "brand");
            return (Criteria) this;
        }

        public Criteria andBrandBetween(String value1, String value2) {
            addCriterion("brand between", value1, value2, "brand");
            return (Criteria) this;
        }

        public Criteria andBrandNotBetween(String value1, String value2) {
            addCriterion("brand not between", value1, value2, "brand");
            return (Criteria) this;
        }

        public Criteria andAutolineNameIsNull() {
            addCriterion("autoLine_name is null");
            return (Criteria) this;
        }

        public Criteria andAutolineNameIsNotNull() {
            addCriterion("autoLine_name is not null");
            return (Criteria) this;
        }

        public Criteria andAutolineNameEqualTo(String value) {
            addCriterion("autoLine_name =", value, "autolineName");
            return (Criteria) this;
        }

        public Criteria andAutolineNameNotEqualTo(String value) {
            addCriterion("autoLine_name <>", value, "autolineName");
            return (Criteria) this;
        }

        public Criteria andAutolineNameGreaterThan(String value) {
            addCriterion("autoLine_name >", value, "autolineName");
            return (Criteria) this;
        }

        public Criteria andAutolineNameGreaterThanOrEqualTo(String value) {
            addCriterion("autoLine_name >=", value, "autolineName");
            return (Criteria) this;
        }

        public Criteria andAutolineNameLessThan(String value) {
            addCriterion("autoLine_name <", value, "autolineName");
            return (Criteria) this;
        }

        public Criteria andAutolineNameLessThanOrEqualTo(String value) {
            addCriterion("autoLine_name <=", value, "autolineName");
            return (Criteria) this;
        }

        public Criteria andAutolineNameLike(String value) {
            addCriterion("autoLine_name like", value, "autolineName");
            return (Criteria) this;
        }

        public Criteria andAutolineNameNotLike(String value) {
            addCriterion("autoLine_name not like", value, "autolineName");
            return (Criteria) this;
        }

        public Criteria andAutolineNameIn(List<String> values) {
            addCriterion("autoLine_name in", values, "autolineName");
            return (Criteria) this;
        }

        public Criteria andAutolineNameNotIn(List<String> values) {
            addCriterion("autoLine_name not in", values, "autolineName");
            return (Criteria) this;
        }

        public Criteria andAutolineNameBetween(String value1, String value2) {
            addCriterion("autoLine_name between", value1, value2, "autolineName");
            return (Criteria) this;
        }

        public Criteria andAutolineNameNotBetween(String value1, String value2) {
            addCriterion("autoLine_name not between", value1, value2, "autolineName");
            return (Criteria) this;
        }

        public Criteria andEngineDispIsNull() {
            addCriterion("engine_disp is null");
            return (Criteria) this;
        }

        public Criteria andEngineDispIsNotNull() {
            addCriterion("engine_disp is not null");
            return (Criteria) this;
        }

        public Criteria andEngineDispEqualTo(String value) {
            addCriterion("engine_disp =", value, "engineDisp");
            return (Criteria) this;
        }

        public Criteria andEngineDispNotEqualTo(String value) {
            addCriterion("engine_disp <>", value, "engineDisp");
            return (Criteria) this;
        }

        public Criteria andEngineDispGreaterThan(String value) {
            addCriterion("engine_disp >", value, "engineDisp");
            return (Criteria) this;
        }

        public Criteria andEngineDispGreaterThanOrEqualTo(String value) {
            addCriterion("engine_disp >=", value, "engineDisp");
            return (Criteria) this;
        }

        public Criteria andEngineDispLessThan(String value) {
            addCriterion("engine_disp <", value, "engineDisp");
            return (Criteria) this;
        }

        public Criteria andEngineDispLessThanOrEqualTo(String value) {
            addCriterion("engine_disp <=", value, "engineDisp");
            return (Criteria) this;
        }

        public Criteria andEngineDispLike(String value) {
            addCriterion("engine_disp like", value, "engineDisp");
            return (Criteria) this;
        }

        public Criteria andEngineDispNotLike(String value) {
            addCriterion("engine_disp not like", value, "engineDisp");
            return (Criteria) this;
        }

        public Criteria andEngineDispIn(List<String> values) {
            addCriterion("engine_disp in", values, "engineDisp");
            return (Criteria) this;
        }

        public Criteria andEngineDispNotIn(List<String> values) {
            addCriterion("engine_disp not in", values, "engineDisp");
            return (Criteria) this;
        }

        public Criteria andEngineDispBetween(String value1, String value2) {
            addCriterion("engine_disp between", value1, value2, "engineDisp");
            return (Criteria) this;
        }

        public Criteria andEngineDispNotBetween(String value1, String value2) {
            addCriterion("engine_disp not between", value1, value2, "engineDisp");
            return (Criteria) this;
        }

        public Criteria andProductTimeIsNull() {
            addCriterion("product_time is null");
            return (Criteria) this;
        }

        public Criteria andProductTimeIsNotNull() {
            addCriterion("product_time is not null");
            return (Criteria) this;
        }

        public Criteria andProductTimeEqualTo(String value) {
            addCriterion("product_time =", value, "productTime");
            return (Criteria) this;
        }

        public Criteria andProductTimeNotEqualTo(String value) {
            addCriterion("product_time <>", value, "productTime");
            return (Criteria) this;
        }

        public Criteria andProductTimeGreaterThan(String value) {
            addCriterion("product_time >", value, "productTime");
            return (Criteria) this;
        }

        public Criteria andProductTimeGreaterThanOrEqualTo(String value) {
            addCriterion("product_time >=", value, "productTime");
            return (Criteria) this;
        }

        public Criteria andProductTimeLessThan(String value) {
            addCriterion("product_time <", value, "productTime");
            return (Criteria) this;
        }

        public Criteria andProductTimeLessThanOrEqualTo(String value) {
            addCriterion("product_time <=", value, "productTime");
            return (Criteria) this;
        }

        public Criteria andProductTimeLike(String value) {
            addCriterion("product_time like", value, "productTime");
            return (Criteria) this;
        }

        public Criteria andProductTimeNotLike(String value) {
            addCriterion("product_time not like", value, "productTime");
            return (Criteria) this;
        }

        public Criteria andProductTimeIn(List<String> values) {
            addCriterion("product_time in", values, "productTime");
            return (Criteria) this;
        }

        public Criteria andProductTimeNotIn(List<String> values) {
            addCriterion("product_time not in", values, "productTime");
            return (Criteria) this;
        }

        public Criteria andProductTimeBetween(String value1, String value2) {
            addCriterion("product_time between", value1, value2, "productTime");
            return (Criteria) this;
        }

        public Criteria andProductTimeNotBetween(String value1, String value2) {
            addCriterion("product_time not between", value1, value2, "productTime");
            return (Criteria) this;
        }

        public Criteria andTransDescIsNull() {
            addCriterion("trans_desc is null");
            return (Criteria) this;
        }

        public Criteria andTransDescIsNotNull() {
            addCriterion("trans_desc is not null");
            return (Criteria) this;
        }

        public Criteria andTransDescEqualTo(String value) {
            addCriterion("trans_desc =", value, "transDesc");
            return (Criteria) this;
        }

        public Criteria andTransDescNotEqualTo(String value) {
            addCriterion("trans_desc <>", value, "transDesc");
            return (Criteria) this;
        }

        public Criteria andTransDescGreaterThan(String value) {
            addCriterion("trans_desc >", value, "transDesc");
            return (Criteria) this;
        }

        public Criteria andTransDescGreaterThanOrEqualTo(String value) {
            addCriterion("trans_desc >=", value, "transDesc");
            return (Criteria) this;
        }

        public Criteria andTransDescLessThan(String value) {
            addCriterion("trans_desc <", value, "transDesc");
            return (Criteria) this;
        }

        public Criteria andTransDescLessThanOrEqualTo(String value) {
            addCriterion("trans_desc <=", value, "transDesc");
            return (Criteria) this;
        }

        public Criteria andTransDescLike(String value) {
            addCriterion("trans_desc like", value, "transDesc");
            return (Criteria) this;
        }

        public Criteria andTransDescNotLike(String value) {
            addCriterion("trans_desc not like", value, "transDesc");
            return (Criteria) this;
        }

        public Criteria andTransDescIn(List<String> values) {
            addCriterion("trans_desc in", values, "transDesc");
            return (Criteria) this;
        }

        public Criteria andTransDescNotIn(List<String> values) {
            addCriterion("trans_desc not in", values, "transDesc");
            return (Criteria) this;
        }

        public Criteria andTransDescBetween(String value1, String value2) {
            addCriterion("trans_desc between", value1, value2, "transDesc");
            return (Criteria) this;
        }

        public Criteria andTransDescNotBetween(String value1, String value2) {
            addCriterion("trans_desc not between", value1, value2, "transDesc");
            return (Criteria) this;
        }

        public Criteria andJiyouIsNull() {
            addCriterion("jiyou is null");
            return (Criteria) this;
        }

        public Criteria andJiyouIsNotNull() {
            addCriterion("jiyou is not null");
            return (Criteria) this;
        }

        public Criteria andJiyouEqualTo(String value) {
            addCriterion("jiyou =", value, "jiyou");
            return (Criteria) this;
        }

        public Criteria andJiyouNotEqualTo(String value) {
            addCriterion("jiyou <>", value, "jiyou");
            return (Criteria) this;
        }

        public Criteria andJiyouGreaterThan(String value) {
            addCriterion("jiyou >", value, "jiyou");
            return (Criteria) this;
        }

        public Criteria andJiyouGreaterThanOrEqualTo(String value) {
            addCriterion("jiyou >=", value, "jiyou");
            return (Criteria) this;
        }

        public Criteria andJiyouLessThan(String value) {
            addCriterion("jiyou <", value, "jiyou");
            return (Criteria) this;
        }

        public Criteria andJiyouLessThanOrEqualTo(String value) {
            addCriterion("jiyou <=", value, "jiyou");
            return (Criteria) this;
        }

        public Criteria andJiyouLike(String value) {
            addCriterion("jiyou like", value, "jiyou");
            return (Criteria) this;
        }

        public Criteria andJiyouNotLike(String value) {
            addCriterion("jiyou not like", value, "jiyou");
            return (Criteria) this;
        }

        public Criteria andJiyouIn(List<String> values) {
            addCriterion("jiyou in", values, "jiyou");
            return (Criteria) this;
        }

        public Criteria andJiyouNotIn(List<String> values) {
            addCriterion("jiyou not in", values, "jiyou");
            return (Criteria) this;
        }

        public Criteria andJiyouBetween(String value1, String value2) {
            addCriterion("jiyou between", value1, value2, "jiyou");
            return (Criteria) this;
        }

        public Criteria andJiyouNotBetween(String value1, String value2) {
            addCriterion("jiyou not between", value1, value2, "jiyou");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("remark is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("remark is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("remark =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("remark <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("remark >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("remark >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("remark <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("remark <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("remark like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("remark not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("remark in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("remark not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("remark between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("remark not between", value1, value2, "remark");
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
     * This class corresponds to the database table engine_oil
     *
     * @mbggenerated do_not_delete_during_merge Wed Sep 21 11:22:36 CST 2016
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table engine_oil
     *
     * @mbggenerated Wed Sep 21 11:22:36 CST 2016
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