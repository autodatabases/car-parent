package com.emate.shop.common;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.util.Objects;

import javax.xml.bind.JAXBContext;

import com.emate.shop.exception.ParameterException;

public class DocumentHelper {

    public static String toDocument(Object obj) {
        if (Objects.isNull(obj)) {
            return null;
        }
        try (OutputStream outputStream = new ByteArrayOutputStream();) {
            JAXBContext.newInstance(obj.getClass()).createMarshaller().marshal(obj, outputStream);
            return outputStream.toString();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ParameterException("toDocument error", e);
        }
    }

    public static <T> T fromDocument(String document, Class<T> clazz) {
        if (Objects.isNull(document) || document.isEmpty()) {
            return null;
        }
        try {
            return clazz
                    .cast(JAXBContext.newInstance(clazz).createUnmarshaller().unmarshal(new StringReader(document)));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("fromDocument error", e);
        }
    }
}
