package com.emate.shop.business.model;

public class Autopose {
    /**
     * 唯一标识ID
     * @Table autoPose.id
     */
    private Long id;

    /**
     * 压缩ID
     * @Table autoPose.compreID
     */
    private String compreid;

    /**
     * 厂家
     * @Table autoPose.factoryName
     */
    private String factoryname;

    /**
     * 品牌
     * @Table autoPose.brandName
     */
    private String brandname;

    /**
     * 车系
     * @Table autoPose.autoLineName
     */
    private String autolinename;

    /**
     * 车型
     * @Table autoPose.autoTypeName
     */
    private String autotypename;

    /**
     * 代数
     * @Table autoPose.generationNum
     */
    private String generationnum;

    /**
     * 代号
     * @Table autoPose.codeName
     */
    private String codename;

    /**
     * 最早生产年份
     * @Table autoPose.firstProductTime
     */
    private String firstproducttime;

    /**
     * 最晚停产年份
     * @Table autoPose.lastProductTime
     */
    private String lastproducttime;

    /**
     * 发动机型号
     * @Table autoPose.engineModel
     */
    private String enginemodel;

    /**
     * 排量(升)
     * @Table autoPose.engineDisp
     */
    private String enginedisp;

    /**
     * 燃油类型
     * @Table autoPose.oilType
     */
    private String oiltype;

    /**
     * 最大功率
     * @Table autoPose.maxPower
     */
    private String maxpower;

    /**
     * 变速器描述
     * @Table autoPose.transDesc
     */
    private String transdesc;

    /**
     * 驱动方式
     * @Table autoPose.drivingType
     */
    private String drivingtype;

    /**
     * 蓝标
     * @Table autoPose.blueLabel
     */
    private String bluelabel;

    /**
     * 银标
     * @Table autoPose.silverLabel
     */
    private String silverlabel;

    /**
     * AGM（如此款车型带启停功能）
     * @Table autoPose.agm
     */
    private String agm;

    /**
     * 新增 recaction
     */
    public static final java.lang.Integer RECACTION_0 = 0;
    /**
     * 修改 recaction
     */
    public static final java.lang.Integer RECACTION_1 = 1;
    /**
     * 删除 recaction
     */
    public static final java.lang.Integer RECACTION_2 = 2;
    /**
     * 状态：0，新增，1，修改，2，删除
     * @Table autoPose.recAction
     */
    private Integer recaction;

    /**
     * 汉语拼音
     * @Table autoPose.pinyin
     */
    private String pinyin;

    /**
     * 品牌Logo
     * @Table autoPose.logo
     */
    private String logo;

    /**
    * 获取 唯一标识ID
    * @return id
    */
    public Long getId() {
        return id;
    }

    /**
    * 设置 唯一标识ID
    * @param id
    */
    public void setId(Long id) {
        this.id = id;
    }

    /**
    * 获取 压缩ID
    * @return compreid
    */
    public String getCompreid() {
        return compreid;
    }

    /**
    * 设置 压缩ID
    * @param compreid
    */
    public void setCompreid(String compreid) {
        this.compreid = compreid;
    }

    /**
    * 获取 厂家
    * @return factoryname
    */
    public String getFactoryname() {
        return factoryname;
    }

    /**
    * 设置 厂家
    * @param factoryname
    */
    public void setFactoryname(String factoryname) {
        this.factoryname = factoryname;
    }

    /**
    * 获取 品牌
    * @return brandname
    */
    public String getBrandname() {
        return brandname;
    }

    /**
    * 设置 品牌
    * @param brandname
    */
    public void setBrandname(String brandname) {
        this.brandname = brandname;
    }

    /**
    * 获取 车系
    * @return autolinename
    */
    public String getAutolinename() {
        return autolinename;
    }

    /**
    * 设置 车系
    * @param autolinename
    */
    public void setAutolinename(String autolinename) {
        this.autolinename = autolinename;
    }

    /**
    * 获取 车型
    * @return autotypename
    */
    public String getAutotypename() {
        return autotypename;
    }

    /**
    * 设置 车型
    * @param autotypename
    */
    public void setAutotypename(String autotypename) {
        this.autotypename = autotypename;
    }

    /**
    * 获取 代数
    * @return generationnum
    */
    public String getGenerationnum() {
        return generationnum;
    }

    /**
    * 设置 代数
    * @param generationnum
    */
    public void setGenerationnum(String generationnum) {
        this.generationnum = generationnum;
    }

    /**
    * 获取 代号
    * @return codename
    */
    public String getCodename() {
        return codename;
    }

    /**
    * 设置 代号
    * @param codename
    */
    public void setCodename(String codename) {
        this.codename = codename;
    }

    /**
    * 获取 最早生产年份
    * @return firstproducttime
    */
    public String getFirstproducttime() {
        return firstproducttime;
    }

    /**
    * 设置 最早生产年份
    * @param firstproducttime
    */
    public void setFirstproducttime(String firstproducttime) {
        this.firstproducttime = firstproducttime;
    }

    /**
    * 获取 最晚停产年份
    * @return lastproducttime
    */
    public String getLastproducttime() {
        return lastproducttime;
    }

    /**
    * 设置 最晚停产年份
    * @param lastproducttime
    */
    public void setLastproducttime(String lastproducttime) {
        this.lastproducttime = lastproducttime;
    }

    /**
    * 获取 发动机型号
    * @return enginemodel
    */
    public String getEnginemodel() {
        return enginemodel;
    }

    /**
    * 设置 发动机型号
    * @param enginemodel
    */
    public void setEnginemodel(String enginemodel) {
        this.enginemodel = enginemodel;
    }

    /**
    * 获取 排量(升)
    * @return enginedisp
    */
    public String getEnginedisp() {
        return enginedisp;
    }

    /**
    * 设置 排量(升)
    * @param enginedisp
    */
    public void setEnginedisp(String enginedisp) {
        this.enginedisp = enginedisp;
    }

    /**
    * 获取 燃油类型
    * @return oiltype
    */
    public String getOiltype() {
        return oiltype;
    }

    /**
    * 设置 燃油类型
    * @param oiltype
    */
    public void setOiltype(String oiltype) {
        this.oiltype = oiltype;
    }

    /**
    * 获取 最大功率
    * @return maxpower
    */
    public String getMaxpower() {
        return maxpower;
    }

    /**
    * 设置 最大功率
    * @param maxpower
    */
    public void setMaxpower(String maxpower) {
        this.maxpower = maxpower;
    }

    /**
    * 获取 变速器描述
    * @return transdesc
    */
    public String getTransdesc() {
        return transdesc;
    }

    /**
    * 设置 变速器描述
    * @param transdesc
    */
    public void setTransdesc(String transdesc) {
        this.transdesc = transdesc;
    }

    /**
    * 获取 驱动方式
    * @return drivingtype
    */
    public String getDrivingtype() {
        return drivingtype;
    }

    /**
    * 设置 驱动方式
    * @param drivingtype
    */
    public void setDrivingtype(String drivingtype) {
        this.drivingtype = drivingtype;
    }

    /**
    * 获取 蓝标
    * @return bluelabel
    */
    public String getBluelabel() {
        return bluelabel;
    }

    /**
    * 设置 蓝标
    * @param bluelabel
    */
    public void setBluelabel(String bluelabel) {
        this.bluelabel = bluelabel;
    }

    /**
    * 获取 银标
    * @return silverlabel
    */
    public String getSilverlabel() {
        return silverlabel;
    }

    /**
    * 设置 银标
    * @param silverlabel
    */
    public void setSilverlabel(String silverlabel) {
        this.silverlabel = silverlabel;
    }

    /**
    * 获取 AGM（如此款车型带启停功能）
    * @return agm
    */
    public String getAgm() {
        return agm;
    }

    /**
    * 设置 AGM（如此款车型带启停功能）
    * @param agm
    */
    public void setAgm(String agm) {
        this.agm = agm;
    }

    /**
    * 获取 状态：0，新增，1，修改，2，删除
    * @return recaction
    */
    public Integer getRecaction() {
        return recaction;
    }

    /**
    * 设置 状态：0，新增，1，修改，2，删除
    * @param recaction
    */
    public void setRecaction(Integer recaction) {
        this.recaction = recaction;
    }

    /**
    * 获取 汉语拼音
    * @return pinyin
    */
    public String getPinyin() {
        return pinyin;
    }

    /**
    * 设置 汉语拼音
    * @param pinyin
    */
    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    /**
    * 获取 品牌Logo
    * @return logo
    */
    public String getLogo() {
        return logo;
    }

    /**
    * 设置 品牌Logo
    * @param logo
    */
    public void setLogo(String logo) {
        this.logo = logo;
    }
}