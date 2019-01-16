package com.emate.shop.rpc.dto;

import java.util.List;

public class RpcRequest implements java.io.Serializable {

    private static final long serialVersionUID = -8643757230663618215L;

    //    private Class<?>          serviceClazz;

    private String            serviceClazz;

    //    private Method            serviceMethod;

    private String            methedName;

    //    private Class<?>[]        methedParameterTypes;

    private String[]          methedParameterTypes;

    private List<Object>      args;

    public List<Object> getArgs() {
        return args;
    }

    public void setArgs(List<Object> args) {
        this.args = args;
    }

    //    public Class<?> getServiceClazz() {
    //        return serviceClazz;
    //    }
    //
    //    public void setServiceClazz(Class<?> serviceClazz) {
    //        this.serviceClazz = serviceClazz;
    //    }

    public String getMethedName() {
        return methedName;
    }

    public String getServiceClazz() {
        return serviceClazz;
    }

    public void setServiceClazz(String serviceClazz) {
        this.serviceClazz = serviceClazz;
    }

    public void setMethedName(String methedName) {
        this.methedName = methedName;
    }

    public String[] getMethedParameterTypes() {
        return methedParameterTypes;
    }

    public void setMethedParameterTypes(String[] methedParameterTypes) {
        this.methedParameterTypes = methedParameterTypes;
    }

    //    public Method getServiceMethod() {
    //        return serviceMethod;
    //    }
    //
    //    public void setServiceMethod(Method serviceMethod) {
    //        this.serviceMethod = serviceMethod;
    //    }

}
