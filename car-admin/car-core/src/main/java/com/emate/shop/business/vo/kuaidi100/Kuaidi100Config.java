/**
 * 
 */
package com.emate.shop.business.vo.kuaidi100;

/**
 * @file Kuaidi100Config.java
 * @author kevin
 * @mail kevin@emateglobal.com
 * @since 2016年8月17日
 */
public class Kuaidi100Config {

    private String callbackUrl;

    private String pollUrl;

    private String pollKey;

    private String pollSalt;

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public String getPollUrl() {
        return pollUrl;
    }

    public void setPollUrl(String pollUrl) {
        this.pollUrl = pollUrl;
    }

    public String getPollKey() {
        return pollKey;
    }

    public void setPollKey(String pollKey) {
        this.pollKey = pollKey;
    }

    public String getPollSalt() {
        return pollSalt;
    }

    public void setPollSalt(String pollSalt) {
        this.pollSalt = pollSalt;
    }
}
