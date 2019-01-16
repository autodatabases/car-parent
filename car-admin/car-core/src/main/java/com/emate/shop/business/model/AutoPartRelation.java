package com.emate.shop.business.model;

public class AutoPartRelation {
    /**
     * 
     * @Table auto_part_relation.id
     */
    private Long id;

    /**
     * 车型id
     * @Table auto_part_relation.auto_id
     */
    private Long autoId;

    /**
     * 汽车配件类型
     * @Table auto_part_relation.part_type
     */
    private String partType;

    /**
     * 
     * @Table auto_part_relation.part_type_id
     */
    private Integer partTypeId;

    /**
     * 匹配配件id1多个id用，分隔
     * @Table auto_part_relation.match_id
     */
    private String matchId;

    /**
    * 获取 
    * @return id
    */
    public Long getId() {
        return id;
    }

    /**
    * 设置 
    * @param id
    */
    public void setId(Long id) {
        this.id = id;
    }

    /**
    * 获取 车型id
    * @return autoId
    */
    public Long getAutoId() {
        return autoId;
    }

    /**
    * 设置 车型id
    * @param autoId
    */
    public void setAutoId(Long autoId) {
        this.autoId = autoId;
    }

    /**
    * 获取 汽车配件类型
    * @return partType
    */
    public String getPartType() {
        return partType;
    }

    /**
    * 设置 汽车配件类型
    * @param partType
    */
    public void setPartType(String partType) {
        this.partType = partType;
    }

    /**
    * 获取 
    * @return partTypeId
    */
    public Integer getPartTypeId() {
        return partTypeId;
    }

    /**
    * 设置 
    * @param partTypeId
    */
    public void setPartTypeId(Integer partTypeId) {
        this.partTypeId = partTypeId;
    }

    /**
    * 获取 匹配配件id1多个id用，分隔
    * @return matchId
    */
    public String getMatchId() {
        return matchId;
    }

    /**
    * 设置 匹配配件id1多个id用，分隔
    * @param matchId
    */
    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }
}