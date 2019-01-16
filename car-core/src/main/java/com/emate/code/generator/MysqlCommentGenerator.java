package com.emate.code.generator;

import java.util.Objects;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.internal.DefaultCommentGenerator;

public class MysqlCommentGenerator extends DefaultCommentGenerator {

//    public static File                                          file          = new File(
//            "../car-admin/src/main/webapp/resources/js/DD.js");

//    public static Map<String, Map<String, Map<String, String>>> jsonMap       = new HashMap<String, Map<String, Map<String, String>>>();
//
//    public static Map<String, Map<String, String>>              jsonMap2      = new HashMap<String, Map<String, String>>();
//
//    public static Map<String, String>                           jsonMap3      = new HashMap<String, String>();

//    public static String                                        lastTableName = null;

    @Override
    public void addFieldComment(Field field,
            IntrospectedTable introspectedTable,
            IntrospectedColumn introspectedColumn) {

        String comment = introspectedColumn.getRemarks();
//        String tableName = StringHelper.changeDatabaseStrToObj(
//                introspectedTable.getFullyQualifiedTable()
//                        .getFullyQualifiedTableNameAtRuntime());
        if (Objects.nonNull(comment)) {
            if (comment.matches("^[\\s\\S]*[\\d]+[，][\\s\\S]*$")) {
                String[] pairs = comment.split("[：]")[1].split("[，]");
                for (int i = 0; i < pairs.length / 2; i++) {
                    String value = pairs[2 * i];
                    String comm = pairs[2 * i + 1];
                    field.addJavaDocLine("/**");
                    field.addJavaDocLine(" * " + comm + " "
                            + introspectedColumn.getJavaProperty());
                    field.addJavaDocLine(" */");
                    field.addJavaDocLine("public static final "
                            + introspectedColumn.getFullyQualifiedJavaType()
                            + " "
                            + introspectedColumn.getActualColumnName()
                                    .toUpperCase()
                            + "_" + value + " = " + value + ";");
                   // jsonMap3.put(value, comm);
                }
//                if (lastTableName != null && !tableName.equals(lastTableName)) {
//                    jsonMap2.clear();
//                }
//                jsonMap2.put(
//                        StringHelper.changeDatabaseStrToObj(
//                                introspectedColumn.getActualColumnName()),
//                        copyMap(jsonMap3));
//                lastTableName = tableName;
//                jsonMap3.clear();
//                if (jsonMap.containsKey(tableName)) {
//                    jsonMap.get(tableName).putAll(jsonMap2);
//                } else {
//                    Map<String, Map<String, String>> temp = new HashMap<String, Map<String, String>>();
//                    temp.putAll(jsonMap2);
//                    jsonMap.put(tableName, temp);
//                }

//                try {
//                    FileUtils.write(file,
//                            "var DD = " + JacksonHelper.toJSON(jsonMap),
//                            "utf-8");
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
            }
        }
        field.addJavaDocLine("/**");
        field.addJavaDocLine(" * " + introspectedColumn.getRemarks());
        field.addJavaDocLine(
                " * @Table " + introspectedTable.getFullyQualifiedTable() + "."
                        + introspectedColumn.getActualColumnName());
        field.addJavaDocLine(" */");
    }

    @Override
    public void addSetterComment(Method method,
            IntrospectedTable introspectedTable,
            IntrospectedColumn introspectedColumn) {
        method.addJavaDocLine("/**");
        method.addJavaDocLine("* 设置 " + introspectedColumn.getRemarks());
        method.addJavaDocLine(
                "* @param " + introspectedColumn.getJavaProperty());
        method.addJavaDocLine("*/");
    }

    @Override
    public void addGetterComment(Method method,
            IntrospectedTable introspectedTable,
            IntrospectedColumn introspectedColumn) {
        method.addJavaDocLine("/**");
        method.addJavaDocLine("* 获取 " + introspectedColumn.getRemarks());
        method.addJavaDocLine(
                "* @return " + introspectedColumn.getJavaProperty());
        method.addJavaDocLine("*/");

    }

//    private static Map<String, String> copyMap(Map<String, String> jsonMap3) {
//        Map<String, String> temp = new HashMap<String, String>();
//        for (Map.Entry<String, String> entry : jsonMap3.entrySet()) {
//            temp.put(entry.getKey(), entry.getValue());
//        }
//        return temp;
//    }
}
