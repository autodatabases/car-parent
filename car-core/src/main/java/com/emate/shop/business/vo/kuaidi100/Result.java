package com.emate.shop.business.vo.kuaidi100;

import java.util.ArrayList;

import com.emate.shop.common.JacksonHelper;

public class Result {

    private String                message   = "";

    //    @JsonIgnore
    private String                nu        = "";

    //    @JsonIgnore
    private String                ischeck   = "0";

    //    @JsonIgnore
    private String                com       = "";

    private String                status    = "0";

    //    @JsonIgnore
    private ArrayList<ResultItem> data      = new ArrayList<ResultItem>();

    //    @JsonIgnore
    private String                state     = "0";

    //    @JsonIgnore
    private String                condition = "";

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNu() {
        return nu;
    }

    public void setNu(String nu) {
        this.nu = nu;
    }

    public String getCom() {
        return com;
    }

    public void setCom(String com) {
        this.com = com;
    }

    public ArrayList<ResultItem> getData() {
        return data;
    }

    public void setData(ArrayList<ResultItem> data) {
        this.data = data;
    }

    public String getIscheck() {
        return ischeck;
    }

    public void setIscheck(String ischeck) {
        this.ischeck = ischeck;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    @Override
    public String toString() {
        return JacksonHelper.toJSON(this);
    }
}
