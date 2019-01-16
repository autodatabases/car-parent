package com.emate.shop.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

public class XssFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request0, ServletResponse response0, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) request0;
        HttpServletResponse response = (HttpServletResponse) response0;
        chain.doFilter(new XssHttpServletRequestWrapper(request), response);
    }

    @Override
    public void destroy() {
    }
}

class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {
    public XssHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String[] getParameterValues(String parameter) {
        return XssUtil.cleanXSS4Arr(super.getParameterValues(parameter));
    }

    @Override
    public String getParameter(String parameter) {
        return XssUtil.cleanXSS(super.getParameter(parameter));
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        Map<String, String[]> map = super.getParameterMap();
        if (Objects.isNull(map)) {
            return null;
        }
        Map<String, String[]> mapNew = new HashMap<>();
        map.entrySet().stream().forEach(entry -> {
            mapNew.put(entry.getKey(), XssUtil.cleanXSS4Arr(entry.getValue()));
        });
        return mapNew;
    }

    @Override
    public Object getAttribute(String name) {
        return XssUtil.cleanXSSObject(super.getAttribute(name));
    }

    @Override
    public String getHeader(String name) {
        return XssUtil.cleanXSS(super.getHeader(name));
    }
}

class XssUtil {
    public static String[] cleanXSS4Arr(String[] values) {
        if (Objects.isNull(values)) {
            return null;
        }
        return Arrays.stream(values).map(XssUtil::cleanXSS).collect(Collectors.toList()).toArray(new String[] {});
    }

    public static Object cleanXSSObject(Object object) {
        if (Objects.isNull(object)) {
            return null;
        }
        if (object instanceof String) {
            return cleanXSS((String) object);
        }
        return object;
    }

    public static String cleanXSS(String value) {
        if (value == null) {
            return null;
        }
        value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
        value = value.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");
        value = value.replaceAll("'", "& #39;");
        value = value.replaceAll("eval\\((.*)\\)", "");
        value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
        value = value.replaceAll("script", "");
        return value;
    }

    public static String disCleanXSS(String value) {
        if (value == null) {
            return null;
        }
        value = value.replaceAll("&lt;", "<").replaceAll("&gt;", ">");
        value = value.replaceAll("&#40;", "\\(").replaceAll("&#41;", "\\)");
        value = value.replaceAll("&#39;", "'");
        value = value.replaceAll("\"\"", "[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']");
        return value;
    }
}
