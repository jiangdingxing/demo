package com.example.demo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @author jiangDingXing
 * @date 2023-03-30 15:39
 * @description:
 */
public class MysqlEntityUtil {

    //表名[为空时输出`TAB_PRE`前缀的所有表]
    private static final String[] TAB_NAMES = {};

    //表前缀[为空时输出库中所有表]
    private static final String[] TAB_PRE = {"sec_","t_"};

    //生成实体类类名时是否去除表前缀
    private static final boolean REMOVE_TAB_PRE = false;

    //输出实体类的文件夹路径[为空时根据`PACKAGE_PATH`输出]
    private static final String PATH = "";

    //输出实体类的包 (若为空实体类将不添加package)
    private static final String PACKAGE_PATH = "com.cennavi.safeproduction.web.pojo.entity";

    //需要导入的包 (java.util.Date 会自动判断 不需要添加)
    private static final String[] IMPORTS = {
            "com.baomidou.mybatisplus.annotation.TableName",
            "com.baomidou.mybatisplus.annotation.TableField",
            "lombok.Data"
    };

    //类注释 占位符:[$year$:年, $month$:月, $day$:日, $hour$:时, $minute$:分, $second$:秒, $week$:星期, $tableName$:表名, $className$:类名(大驼峰表名), $tableComment$:表注释]
    private static final String[] CLASS_COMMENT = {
            "$tableComment$",
            "",
            "@author jiangDingXing",
            "@date $year$/$month$/$day$ $hour$:$minute$"
    };

    //类注解 占位符:[$tableName$:表名, $className$:类名(大驼峰表名), $tableComment$:表注释]
    private static final String[] CLASS_ANNOTATION = {
            "@Data",
            "@TableName(\"$tableName$\")"
    };

    //变量注释 占位符:[$fieldName$:字段名, $name$:变量名(小驼峰字段名), $fieldType$:字段类型, $type$:变量类型, $fieldComment$:字段注释]
    private static final String[] COMMENT = {
            "$fieldComment$"
    };

    //变量注解 占位符:[$fieldName$:字段名, $name$:变量名(小驼峰字段名), $fieldType$:字段类型, $type$:变量类型, $fieldComment$:字段注释]
    private static final String[] ANNOTATION = {
            "@TableField(\"$fieldName$\")"
    };

    //是否生成getset方法
    private static final boolean ADD_GET_SET = false;

    //数据库连接
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://192.168.100.221:13501/safe_production?useUnicode=true&characterEncoding=utf-8&useSSL=false";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "superman@2019";

    //其他参数
    private static String[] fieldNames;
    private static String[] fieldTypes;
    private static String databaseName;
    private static Connection con;
    private static Statement run;
    private static boolean importDate = false;

    public static void main(String[] args) {
        build();
    }

    /**
     * 开始
     */
    private static void build() {
        try {
            int i = 0;
            //创建数据库连接
            connect();
            //获取所有表名并遍历
            for (String tableName : getAllTables()) {
                boolean build = false;
                //指定表名
                if (notEmpty(TAB_NAMES)) {
                    for (String name : TAB_NAMES) {
                        if (tableName.equals(name.toLowerCase())) {
                            build = true;
                            break;
                        }
                    }
                }
                //指定表名前缀
                else if (notEmpty(TAB_PRE)) {
                    for (String pre : TAB_PRE) {
                        if (tableName.startsWith(pre.toLowerCase())) {
                            build = true;
                            break;
                        }
                    }
                }
                //表名与表名前缀均未指定
                else {
                    build = true;
                }
                if (build) {
                    System.out.println("正在生成表 - " + tableName);
                    //处理表
                    buildTable(tableName);
                    i++;
                }
            }
            System.out.println("生成数量 - " + i);
            System.out.println("结束");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                colseConnect();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 创建连接
     *
     * @throws ClassNotFoundException 类未找到异常
     * @throws SQLException           SQL异常
     */
    private static void connect() throws ClassNotFoundException, SQLException {
        Class.forName(DRIVER);
        con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        run = con.createStatement();
        databaseName = URL.split("\\?")[0].substring(URL.split("\\?")[0].lastIndexOf("/") + 1);
    }

    /**
     * 获取所有表名
     *
     * @return {@link List}<{@link String}>
     * @throws SQLException SQL异常
     */
    private static List<String> getAllTables() throws SQLException {
        List<String> tableNames = new ArrayList<>();
        String sql = "SELECT TABLE_NAME FROM information_schema.tables WHERE table_schema='" + databaseName + "'";
        ResultSet rs = run.executeQuery(sql);
        while (rs.next()) {
            tableNames.add(rs.getString("TABLE_NAME").toLowerCase());
        }
        return tableNames;
    }

    /**
     * 构建表
     *
     * @param tableName 表名
     * @throws SQLException SQL异常
     * @throws IOException  IO异常
     */
    private static void buildTable(String tableName) throws SQLException, IOException {
        String sql = "SELECT * FROM " + tableName + " LIMIT 1";
        ResultSet rs = run.executeQuery(sql);
        ResultSetMetaData rsmd = rs.getMetaData();
        int size = rsmd.getColumnCount();
        fieldNames = new String[size];
        fieldTypes = new String[size];
        for (int i = 0; i < size; i++) {
            fieldNames[i] = rsmd.getColumnName(i + 1);
            fieldTypes[i] = rsmd.getColumnTypeName(i + 1);
        }
        //规范表名
        String className = normTableName(tableName);
        //获取表注释
        String tableComment = getTableComment(tableName);
        //获取字段信息
        StringBuffer fieldInfo = getFieldInfo(tableName);
        //追加getset方法
        if (ADD_GET_SET) {
            appendGetSet(fieldInfo);
        }
        //构建java文件
        buildJavaFile(className, getClassContent(tableName, className, tableComment, fieldInfo));
    }

    /**
     * 获取表注释
     *
     * @param tableName 表名
     * @return {@link String}
     * @throws SQLException SQL异常
     */
    private static String getTableComment(String tableName) throws SQLException {
        String tableComment = "";
        String sql = "SELECT table_comment FROM information_schema.tables WHERE table_schema='" + databaseName + "' AND table_name = '" + tableName + "'";
        ResultSet rs = run.executeQuery(sql);
        while (rs.next()) {
            tableComment = rs.getString("TABLE_COMMENT");
            if (tableComment == null) {
                tableComment = "";
            } else {
                tableComment = tableComment.trim();
            }
        }
        return tableComment;
    }

    /**
     * 获取字段注释
     *
     * @param tableName  表名
     * @param fieldName 字段名
     * @return {@link String}
     * @throws SQLException SQL异常
     */
    private static String getFieldComment(String tableName, String fieldName) throws SQLException {
        String fieldComment = "";
        String sql = "SELECT column_comment FROM information_schema.columns WHERE table_schema='" + databaseName + "' AND TABLE_NAME='" + tableName + "' AND COLUMN_NAME='" + fieldName + "'";

        ResultSet rs = run.executeQuery(sql);
        while (rs.next()) {
            fieldComment = rs.getString("COLUMN_COMMENT");
            if (fieldComment == null) {
                fieldComment = "";
            } else {
                fieldComment = fieldComment.trim();
            }
        }
        return fieldComment;
    }

    /**
     * 获取字段信息
     *
     * @param tableName 表名
     * @return {@link StringBuffer}
     * @throws SQLException SQL异常
     */
    private static StringBuffer getFieldInfo(String tableName) throws SQLException {
        StringBuffer fieldInfo = new StringBuffer();
        for (int i = 0; i < fieldNames.length; i++) {
            //小驼峰
            String name = underlineToHump(fieldNames[i]);
            //类型
            String type = toJavaType(fieldTypes[i]);

            fieldInfo.append("\r\n");
            //变量注释
            String fieldComment = getFieldComment(tableName, fieldNames[i]);
            if (notEmpty(COMMENT)) {
                fieldInfo.append("/**\r\n");
                for (String s : COMMENT) {
                    fieldInfo.append(" * ").append(s
                            .replace("$fieldName$", fieldNames[i])
                            .replace("$name$", name)
                            .replace("$fieldType$", fieldTypes[i])
                            .replace("$type$", type)
                            .replace("$fieldComment$", fieldComment)
                    ).append("\r\n");
                }
                fieldInfo.append(" */\r\n");
            }
            //变量注解
            if (notEmpty(ANNOTATION)) {
                for (String s : ANNOTATION) {
                    fieldInfo.append(s
                            .replace("$fieldName$", fieldNames[i])
                            .replace("$name$", name)
                            .replace("$fieldType$", fieldTypes[i])
                            .replace("$type$", type)
                            .replace("$fieldComment$", fieldComment)
                    ).append("\r\n");
                }
            }
            //变量
            fieldInfo.append("private ").append(type).append(" ").append(name).append(";\r\n");
        }
        return fieldInfo;
    }

    /**
     * 追加getset方法
     *
     * @param fieldInfo 字段信息
     */
    private static void appendGetSet(StringBuffer fieldInfo) {
        for (int i = 0; i < fieldNames.length; i++) {
            fieldInfo.append("\r\n");
            //小驼峰
            String name = underlineToHump(fieldNames[i]);
            //大驼峰
            String capitalName = capitalLetters(name);
            //类型
            String type = toJavaType(fieldTypes[i]);
            fieldInfo.append("public ").append(type).append(" get").append(capitalName).append("() {\r\n");
            fieldInfo.append("return this.").append(name).append(";\r\n");
            fieldInfo.append("}\r\n");
            fieldInfo.append("\r\n");
            fieldInfo.append("public void set").append(capitalName).append("(").append(type).append(" ").append(name).append(") {\r\n");
            fieldInfo.append("this.").append(name).append(" = ").append(name).append(";\r\n");
            fieldInfo.append("}\r\n");
        }
    }

    /**
     * 获取类内容
     *
     * @param tableName    表名
     * @param className    类名
     * @param tableComment 表注释
     * @param fieldInfo    字段信息
     * @return {@link StringBuffer}
     */
    private static StringBuffer getClassContent(String tableName, String className, String tableComment, StringBuffer fieldInfo) {
        StringBuffer classContent = new StringBuffer();
        //包信息
        if (notEmpty(PACKAGE_PATH)) {
            classContent.append("package ").append(PACKAGE_PATH).append(";\r\n");
            classContent.append("\r\n");
        }
        //导包
        classContent.append("import java.io.Serializable;\r\n");
        if (notEmpty(IMPORTS)) {
            for (String s : IMPORTS) {
                classContent.append("import ").append(s).append(";\r\n");
            }
        }
        if (importDate) {
            classContent.append("import java.util.Date;\r\n");
            importDate = false;
        }
        classContent.append("\r\n");
        //类注释
        if (notEmpty(CLASS_COMMENT)) {
            Date date = new Date();
            classContent.append("/**\r\n");
            for (String s : CLASS_COMMENT) {
                classContent.append(" * ").append(s
                        .replace("$year$", new SimpleDateFormat("yyyy").format(date))
                        .replace("$month$", new SimpleDateFormat("MM").format(date))
                        .replace("$day$", new SimpleDateFormat("dd").format(date))
                        .replace("$hour$", new SimpleDateFormat("HH").format(date))
                        .replace("$minute$", new SimpleDateFormat("mm").format(date))
                        .replace("$second$", new SimpleDateFormat("ss").format(date))
                        .replace("$week$", new SimpleDateFormat("EEE").format(date))
                        .replace("$tableName$", tableName)
                        .replace("$className$", className)
                        .replace("$tableComment$", tableComment)
                ).append("\r\n");
            }
            classContent.append(" */\r\n");
        }
        //类注解
        if (notEmpty(CLASS_ANNOTATION)) {
            for (String s : CLASS_ANNOTATION) {
                classContent.append(s
                        .replace("$tableName$", tableName)
                        .replace("$className$", className)
                        .replace("$tableComment$", tableComment)
                ).append("\r\n");
            }
        }
        //类
        classContent.append("public class ").append(className).append(" implements Serializable").append(" {\r\n");
        classContent.append("private static final long serialVersionUID = 1L;\r\n");
        classContent.append(fieldInfo);
        classContent.append("}");
        return classContent;
    }

    /**
     * 构建java文件
     *
     * @param className    类名
     * @param classContent 类内容
     * @throws IOException IO异常
     */
    private static void buildJavaFile(String className, StringBuffer classContent) throws IOException {
        String path = new File("").getAbsolutePath() + "/src/main/java/";
        if (notEmpty(PATH)) {
            if (PATH.endsWith("/") || PATH.endsWith("\\")) {
                path = PATH;
            } else {
                path = PATH + "/";
            }
        } else if (notEmpty(PACKAGE_PATH)) {
            path += PACKAGE_PATH.replace(".", "/") + "/";
        } else {
            path += "entity/dao/";
        }
        new File(path).mkdirs();
        String outputPath = path + className + ".java";
        PrintWriter pw = new PrintWriter(new FileWriter(outputPath));
        for (String s : classContent.toString().split("\r\n")) {
            pw.println(s);
            pw.flush();
        }
        pw.close();
    }

    /**
     * sql数据类型转java数据类型
     *
     * @param sqlType sql数据类型
     * @return {@link String}
     */
    private static String toJavaType(String sqlType) {
        if (sqlType.equalsIgnoreCase("TINYINT")) {
            return "Byte";
        } else if (sqlType.equalsIgnoreCase("SMALLINT")
                || sqlType.equalsIgnoreCase("MEDIUMINT")) {
            return "Short";
        } else if (sqlType.equalsIgnoreCase("INT")
                || sqlType.equalsIgnoreCase("INTEGER")) {
            return "Integer";
        } else if (sqlType.equalsIgnoreCase("BIGINT")) {
            return "Long";
        } else if (sqlType.equalsIgnoreCase("FLOAT")
                || sqlType.equalsIgnoreCase("DOUBLE")) {
            return "Double";
        } else if (sqlType.equalsIgnoreCase("DATE")
                || sqlType.equalsIgnoreCase("TIME")
                || sqlType.equalsIgnoreCase("YEAR")
                || sqlType.equalsIgnoreCase("DATETIME")
                || sqlType.equalsIgnoreCase("TIMESTAMP")) {
            importDate = true;
            return "Date";
        }
        return "String";
    }

    /**
     * 规范表名
     *
     * @param tableName 表名
     * @return {@link String}
     */
    private static String normTableName(String tableName) {
        if (REMOVE_TAB_PRE && notEmpty(TAB_PRE)) {
            //去除表前缀
            for (String pre : TAB_PRE) {
                if (tableName.startsWith(pre.toLowerCase()) && !tableName.equals(pre.toLowerCase())) {
                    tableName = tableName.substring(pre.length());
                    break;
                }
            }
        }
        //下划线转大驼峰
        return capitalLetters(underlineToHump(tableName));
    }

    /**
     * 下划线命名转为小驼峰命名
     *
     * @param tableName 表名
     * @return {@link String}
     */
    private static String underlineToHump(String tableName) {
        StringBuilder result = new StringBuilder();
        String[] p = tableName.split("_");
        for (String s : p) {
            if (result.length() == 0) {
                result.append(s.toLowerCase());
            } else {
                result.append(s.substring(0, 1).toUpperCase()).append(s.substring(1).toLowerCase());
            }
        }
        return result.toString();
    }

    /**
     * 大写首字母
     *
     * @param name 名称
     * @return {@link String}
     */
    private static String capitalLetters(String name) {
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    /**
     * 判断数组不为空
     *
     * @return boolean
     */
    private static <P> boolean notEmpty(P[] arr) {
        return arr != null && arr.length > 0;
    }

    /**
     * 判断字符串不为空
     *
     * @return boolean
     */
    private static boolean notEmpty(String str) {
        return str != null && !str.isEmpty();
    }

    /**
     * 清除链接
     *
     * @throws SQLException SQL异常
     */
    private static void colseConnect() throws SQLException {
        if (con != null) {
            con.close();
            con = null;
        }
        if (run != null) {
            run.close();
            run = null;
        }
    }
}
