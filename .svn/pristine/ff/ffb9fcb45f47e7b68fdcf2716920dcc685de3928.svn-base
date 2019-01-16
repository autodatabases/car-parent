package com.emate.shop.rpc.dto;

import java.beans.IntrospectionException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.DumperOptions.FlowStyle;
import org.yaml.snakeyaml.DumperOptions.LineBreak;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.nodes.NodeTuple;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Representer;

import com.emate.shop.encrypt.EncrypAES;

public class RpcDtoHelper {

    public static Yaml getYaml(boolean pretty) {
        if (pretty) {
            DumperOptions options = new DumperOptions();
            options.setLineBreak(LineBreak.getPlatformLineBreak());
            options.setDefaultFlowStyle(FlowStyle.FLOW);
            options.setPrettyFlow(true);
            options.setAllowReadOnlyProperties(true);
            return new org.yaml.snakeyaml.Yaml(new SkipNullRepresenter(),
                    options);
        }
        return new org.yaml.snakeyaml.Yaml(new SkipNullRepresenter());
    }

    public static Yaml getYaml() {
        return getYaml(false);
    }

    static class SkipNullRepresenter extends Representer {

        @Override
        protected NodeTuple representJavaBeanProperty(Object javaBean,
                Property property, Object propertyValue, Tag customTag) {
            if (propertyValue == null) {
                return null;
            }
            return super.representJavaBeanProperty(javaBean, property,
                    propertyValue, customTag);
        }

        @Override
        protected Set<Property> getProperties(Class<? extends Object> type)
                throws IntrospectionException {
            if (HttpServletRequest.class.isAssignableFrom(type)
                    || HttpServletResponse.class.isAssignableFrom(type)
                    || Method.class.isAssignableFrom(type)
                    || Class.class.isAssignableFrom(type)) {
                return new TreeSet<>();
            }
            return super.getProperties(type);
        }

    }

    public static final String PARAMETER_KEY = "parameterKey4RemoteServiceRequest";

    public static String object2Yaml(Object object) {
        return getYaml().dump(object);
    }

    public static String object2PrettyYaml(Object object) {
        return getYaml(true).dump(object);
    }

    public static Object yaml2Object(String yaml) {
        return getYaml().load(yaml);
    }

    public static String encrypt(String src) {
        try {
            byte[] bytes = EncrypAES.getIns().Encrytor(src);
            return new String(bytes, "utf-8");
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static String decrypt(String aes) {
        try {
            byte[] bytes = EncrypAES.getIns().Decryptor(aes.getBytes("utf-8"));
            return new String(bytes, "UTF-8");
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static String object2EncryptAES(Object object) {
//        try {
            return getYaml().dump(object);
          //  byte[] bytes = EncrypAES.getIns().Encrytor(xml);
           // return new String(bytes, "utf-8");
//        } catch (Throwable e) {
//            throw new RuntimeException(e);
//        }
    }

    public static Object encryptAES2Object(String encryptAES) {
//        try {
//            byte[] bytes = EncrypAES.getIns()
//                    .Decryptor(encryptAES.getBytes("utf-8"));
//            return YAML_TRANS.load(new String(bytes, "UTF-8"));
        	return getYaml().load(encryptAES);
//        } catch (Throwable e) {
//            throw new RuntimeException(e);
//        }
    }
    
    public static void main(String[] args) throws UnsupportedEncodingException {
    	//System.out.println(decrypt("1yXHlHdkpSiO0OToBjL5S9nWtGWBiW9K"));
    	System.out.println(URLEncoder.encode(encrypt("2497_"+System.currentTimeMillis()),"utf-8"));
	}

}
