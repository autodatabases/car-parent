package com.emate.shop.business.model;

public class Regions {
    /**
     * 
     * @Table regions.id
     */
    private Integer id;

    /**
     * 父ID，自关联
     * @Table regions.parentId
     */
    private Integer parentid;

    /**
     * 地区名称
     * @Table regions.regionName
     */
    private String regionname;

    /**
     * 父路径
     * @Table regions.parentPath
     */
    private String parentpath;

    /**
     * 首字母
     * @Table regions.firstLetter
     */
    private String firstletter;

    /**
     * 省级 regiontype
     */
    public static final java.lang.Byte REGIONTYPE_1 = 1;
    /**
     * 市级 regiontype
     */
    public static final java.lang.Byte REGIONTYPE_2 = 2;
    /**
     * 区级 regiontype
     */
    public static final java.lang.Byte REGIONTYPE_3 = 3;
    /**
     * 类型：1，省级，2，市级，3，区级
     * @Table regions.regionType
     */
    private Byte regiontype;

    /**
     * 
     * @Table regions.agencyId
     */
    private Short agencyid;

    /**
     * 市场级别
     * @Table regions.shippingId
     */
    private Integer shippingid;

    /**
     * 是否可见
     * @Table regions.visible
     */
    private Integer visible;

    /**
     * 
     * @Table regions.rowId
     */
    private String rowid;

    /**
    * 获取 
    * @return id
    */
    public Integer getId() {
        return id;
    }

    /**
    * 设置 
    * @param id
    */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
    * 获取 父ID，自关联
    * @return parentid
    */
    public Integer getParentid() {
        return parentid;
    }

    /**
    * 设置 父ID，自关联
    * @param parentid
    */
    public void setParentid(Integer parentid) {
        this.parentid = parentid;
    }

    /**
    * 获取 地区名称
    * @return regionname
    */
    public String getRegionname() {
        return regionname;
    }

    /**
    * 设置 地区名称
    * @param regionname
    */
    public void setRegionname(String regionname) {
        this.regionname = regionname;
    }

    /**
    * 获取 父路径
    * @return parentpath
    */
    public String getParentpath() {
        return parentpath;
    }

    /**
    * 设置 父路径
    * @param parentpath
    */
    public void setParentpath(String parentpath) {
        this.parentpath = parentpath;
    }

    /**
    * 获取 首字母
    * @return firstletter
    */
    public String getFirstletter() {
        return firstletter;
    }

    /**
    * 设置 首字母
    * @param firstletter
    */
    public void setFirstletter(String firstletter) {
        this.firstletter = firstletter;
    }

    /**
    * 获取 类型：1，省级，2，市级，3，区级
    * @return regiontype
    */
    public Byte getRegiontype() {
        return regiontype;
    }

    /**
    * 设置 类型：1，省级，2，市级，3，区级
    * @param regiontype
    */
    public void setRegiontype(Byte regiontype) {
        this.regiontype = regiontype;
    }

    /**
    * 获取 
    * @return agencyid
    */
    public Short getAgencyid() {
        return agencyid;
    }

    /**
    * 设置 
    * @param agencyid
    */
    public void setAgencyid(Short agencyid) {
        this.agencyid = agencyid;
    }

    /**
    * 获取 市场级别
    * @return shippingid
    */
    public Integer getShippingid() {
        return shippingid;
    }

    /**
    * 设置 市场级别
    * @param shippingid
    */
    public void setShippingid(Integer shippingid) {
        this.shippingid = shippingid;
    }

    /**
    * 获取 是否可见
    * @return visible
    */
    public Integer getVisible() {
        return visible;
    }

    /**
    * 设置 是否可见
    * @param visible
    */
    public void setVisible(Integer visible) {
        this.visible = visible;
    }

    /**
    * 获取 
    * @return rowid
    */
    public String getRowid() {
        return rowid;
    }

    /**
    * 设置 
    * @param rowid
    */
    public void setRowid(String rowid) {
        this.rowid = rowid;
    }
}