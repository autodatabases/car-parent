package com.emate.shop.business.model;

public class Autobrand {
    /**
     * 汉语拼音
     * @Table autoBrand.pinyin
     */
    private String pinyin;

    /**
     * 品牌Logo
     * @Table autoBrand.logo
     */
    private String logo;

    /**
     * 品牌
     * @Table autoBrand.brandName
     */
    private String brandname;

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
}