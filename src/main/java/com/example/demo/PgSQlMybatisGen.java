package com.example.demo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class PgSQlMybatisGen {


    public static void main(String[] args) {
        PgSQlMybatisGen pgGen = new PgSQlMybatisGen();
        //PgSQlGenerateEntityUtil.basePath=""; //指定生成的位置,默认是当前工程
        //pgGen.needEntityHelper=true;
        pgGen.generate();
        System.out.println("generate Entity to classes successful!");

    }

    private boolean ifCamel = true;//表字段是否驼峰命名

    //表名
    private String tableName;
    //表注释
    private String tableComment;
    //列名数组
    private String[] colNames;
    //列名类型数组
    private String[] colTypes;
    //列名大小数组
    private int[] colSizes;
    //列名注释
    private Map colNamesComment = new HashMap();
    //是否需要导入包java.util.*
    private boolean needUtil = false;
    //是否需要导入包java.sql.*
    private boolean needSql = false;
    //是否需要导入包java.math.BigDecimal
    private boolean needBigDecimal = false;
    //是否创建EntityHelper
    private boolean needEntityHelper = false;
    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    // 数据库配置信息
    private static final String JDBC_URL = "jdbc:postgresql://113.108.157.29:8099/ktpi";
    public static final String jdbc_user = "postgres";
    public static final String jdbc_pwd = "Superman@2019";
    private static final String DRIVER = "org.postgresql.Driver";

    //指定实体生成所在包的路径
    private static String basePath = new File("").getAbsolutePath();
    //生成指定包名
    private static String packageOutPath = "mapper";
    //xml的namespace类包路径
    private static String classPackageOutPath = "com.cennavi.trafficsystem.roadsituation.web.model";
    //作者名字
    private static final String authorName = "jiangdingxing";
    //指定需要生成的表的表名，全部生成设置为null
    private String[] generateTables = {"m_traffic_road_video_equipment"};
    //主键
    private static String pk;

    private PgSQlMybatisGen() {
    }
    /**
     * 功能：查询表信息的sql
     * @param tableName
     * @return comment 表的注释，type字段类型，name字段名字，notnull是否为空
     */
    private static String SQL(String tableName) {
        if(tableName==null || "".equals(tableName)) {return null;}
        String sql = "SELECT col_description(a.attrelid,a.attnum) as comment,"
                + "format_type(a.atttypid,a.atttypmod) as type,a.attname as name, a.attnotnull as notnull,a.atttypmod-4 as fields_length "
                + "FROM pg_class as c,pg_attribute as a where c.relname = '"+tableName+"' "
                + "and a.attrelid = c.oid and a.attnum>0";
        return sql;

    }
    /**
     * 功能：获取主键的sql
     * @param tableName
     * @return pk_name:主键名  | colname：主键对应字段名 | typename:主键类型
     */
    private static String PK_SQL(String tableName) {
        if(tableName==null || "".equals(tableName)) {return null;}
        String pksql = "select pg_constraint.conname as pk_name,pg_attribute.attname as colname,pg_type.typname as typename from pg_constraint  inner join pg_class on pg_constraint.conrelid = pg_class.oid inner join pg_attribute on pg_attribute.attrelid = pg_class.oid and  pg_attribute.attnum = pg_constraint.conkey[1] inner join pg_type on pg_type.oid = pg_attribute.atttypid where pg_class.relname = '"+tableName+"' and pg_constraint.contype='p'";
        return pksql;
    }

    private String parseJdbcType(String jdbcType) {

        if (jdbcType.indexOf("character") >= 0) {
            return "VARCHAR";
        }
        if(jdbcType.indexOf("geometry") >= 0){
            return "VARCHAR";
        }
        if(jdbcType.indexOf("text") >= 0){
            return "VARCHAR";
        }
        if (jdbcType.indexOf("timestamp") >= 0) {
            return "TIMESTAMP";
        }
        if (jdbcType.indexOf("smallint") >= 0) {
            return "INTEGER";
        }
         return jdbcType.toUpperCase();
    }

    /**
     * 功能：解析字段数据，生成实体类的所有内容
     * @return
     */
    private String parse() {
        StringBuffer sb = new StringBuffer();

        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n");
        sb.append("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\r\n");
        sb.append("<mapper namespace=\""+classPackageOutPath+"."+under2camel(tableName, true)+"\">\r\n");

        //resultMap
        sb.append("\r\n\t<resultMap id=\"BaseResultMap\" type=\""+classPackageOutPath+"."+ under2camel(tableName, true)+"\">\r\n");
        for (int i = 0; i < colNames.length; i++) {
            String colName = colNames[i];
            String fieldRegular = under2camel(colName, false);
            sb.append("\t\t<result property=\""+fieldRegular+"\" column=\""+colName+"\" jdbcType = \"" +parseJdbcType(colTypes[i])+  "\" />\r\n");
            System.out.println("\t\t<result property=\""+fieldRegular+"\" column=\""+colName+"\" jdbcType = \"" +parseJdbcType(colTypes[i])+  "\" />\r\n");
        }
  /*      sb.append("\t\t<result property=\"startName\" column=\"b$START_NAME\"  />\r\n");*/
        sb.append("\t</resultMap>\r\n\r\n");


        System.out.println("*********************querySql*******************");

        sb.append("\t<sql id=\"querySql\">\r\n\t\tSELECT");
        for (int i=0;i<colNames.length;i++) {
            String fieldRegular = "\""+colNames[i]+"\"";
            if (i==0)
                sb.append("\r\n\t\t\ta."+fieldRegular+", \r\n");
            else if(i==colNames.length-1)
                sb.append("\t\t\ta."+fieldRegular+" \r\n");
            else
                sb.append("\t\t\ta."+fieldRegular+", \r\n");
            System.out.println(fieldRegular);
        }
        sb.append("\t\tFROM\r\n");
        sb.append("\t</sql>\r\n");

        System.out.println("**********************FromSql*******************");

        sb.append("\r\n\t<sql id=\"FromSql\">");
        sb.append("\r\n\t\t\t"+tableName+" a \r\n");
        sb.append("\t</sql>\r\n");

        System.out.println("**********************insert********************");

        sb.append("\r\n\t<insert id=\"insert\" parameterType=\"java.util.HashMap\">");
        sb.append("\r\n\t\tINSERT INTO "+tableName+"");
        sb.append("\r\n\t\t(");
        sb.append("\r\n\t\t<foreach item=\"value\" index=\"key\" collection=\"entity\" separator=\",\">");
        sb.append("\r\n\t\t\t${key}");
        sb.append("\r\n\t\t</foreach>");
        sb.append("\r\n\t\t)VALUES(");
        sb.append("\r\n\t\t<foreach item=\"value\" index=\"key\" collection=\"entity\" separator=\",\">");
        sb.append("\r\n\t\t\t#{value}");
        sb.append("\r\n\t\t</foreach>");
        sb.append("\r\n\t\t)");
        sb.append("\r\n\t </insert>");

        System.out.println("**********************update********************");
        sb.append("\r\n");

        sb.append("\r\n\t<update id=\"update\" parameterType=\"java.util.HashMap\">");
        sb.append("\r\n\t\tupdate "+tableName+"");
        sb.append("\r\n\t\t<set>");
        sb.append("\r\n\t\t<foreach item=\"value\" index=\"key\" collection=\"entity\" separator=\",\">");
        sb.append("\r\n\t\t\t<if test=\"key!='ID'  and value!=null\">${key}= #{value}</if>");
        sb.append("\r\n\t\t</foreach>");
        sb.append("\r\n\t\t</set>");
        sb.append("\r\n\t\t\t where \"ID\" = #{id}");
        sb.append("\r\n\t </update>");

        System.out.println("**********************queryList********************");
        sb.append("\r\n");

        sb.append("\r\n\t<select id=\"queryList\" parameterType=\"java.util.Map\" resultMap=\"BaseResultMap\">");
        sb.append("\r\n\t\t<include refid=\"querySql\"/>");
        sb.append("\r\n\t\t<include refid=\"FromSql\"/>");
        sb.append("\r\n\t\t<if test=\"queryFilterSql !='' and queryFilterSql!=null\">");
        sb.append("\r\n\t\t\tWHERE ${queryFilterSql}");
        sb.append("\r\n\t\t </if>");
        sb.append("\r\n\t\t<if test=\"sortSql !='' and sortSql!=null\">");
        sb.append("\r\n\t\t\t order by ${sortSql}");
        sb.append("\r\n\t\t </if>");
        sb.append("\r\n\t</select>");

        System.out.println("**********************querySize********************");
        sb.append("\r\n");

        sb.append("\r\n\t<select id=\"querySize\" parameterType=\"java.util.Map\" resultType=\"java.lang.Integer\">");
        sb.append("\r\n\t\tSELECT");
        sb.append("\r\n\t\t\tcount(*)");
        sb.append("\r\n\t\tFROM");
        sb.append("\r\n\t\t\t<include refid=\"FromSql\"/>");
        sb.append("\r\n\t\t<if test=\"queryFilterSql !='' and queryFilterSql!=null\">");
        sb.append("\r\n\t\t\t WHERE ${queryFilterSql}");
        sb.append("\r\n\t\t </if>");
        sb.append("\r\n\t</select>");

        System.out.println("**********************findById*********************");
        sb.append("\r\n");

        sb.append("\r\n\t<select id=\"findById\" parameterType=\"java.lang.String\"");
        sb.append("\r\n\t\tresultType=\""+classPackageOutPath+"."+ under2camel(tableName, true)+"\">");
        sb.append("\r\n\t\t\tSELECT");
        sb.append("\r\n\t\t\t *");
        sb.append("\r\n\t\t\tFROM");
        sb.append("\r\n\t\t\t"+tableName+"");
        sb.append("\r\n\t\tWHERE \"ID\" = #{id}");
        sb.append("\r\n\t</select>");

        System.out.println("**********************deleteById*******************");
        sb.append("\r\n");

//        sb.append("\r\n\t<delete id=\"deleteById\">");
//        sb.append("\r\n\t\tDELETE FROM "+tableName+" WHERE \"ID\"=#{id}");
//        sb.append("\r\n\t</delete>");

        sb.append("\r\n");
        sb.append("\r\n</mapper>");

        return sb.toString();
    }

    /**
     * @param sb
     * @description 生成所有成员变量及注释
     */
    private void processAllAttrs(StringBuffer sb) {
        for (int i = 0; i < colNames.length; i++) {
            if(colNamesComment.get(colNames[i])!=null &&!"".equals(colNamesComment.get(colNames[i]))) {
                sb.append("\t/*"+colNamesComment.get(colNames[i])+"*/\r\n");
            }
            if (ifCamel) {
                sb.append("\tprivate " + sqlType2JavaType(colTypes[i]) + " " + under2camel(colNames[i],false) + ";\r\n");
            } else {
                sb.append("\tprivate " + sqlType2JavaType(colTypes[i]) + " " + colNames[i] + ";\r\n");
            }
        }
    }
    /**
     * EntityHelper
     * @param sb
     * @param pk
     */
    private void processEntityHelper(StringBuffer sb,String pk) {
        sb.append("\t@Override\r\n");
        sb.append("\tpublic String getPrimaryKey() {\r\n");
        sb.append("\t\treturn \""+pk+"\";\r\n");
        sb.append("\t}\r\n");
    }
    /**
     * 重写toString()方法
     * @param sb
     */
    private void processToString(StringBuffer sb) {
        sb.append("\t@Override\r\n\tpublic String toString() {\r\n");
        sb.append("\t\treturn \"" +tableName + "[\" + \r\n");
        for (int i = 0; i < colNames.length; i++) {
            if (i != 0)
                sb.append("\t\t\t\", ");
            if (i == 0)
                sb.append("\t\t\t\"");
            sb.append(colNames[i] + "=\" + "
                    + colNames[i]).append(" + \r\n");
            if (i == colNames.length - 1) {
                sb.append("\t\t\t\"]\";\r\n");
            }
        }
        sb.append("\t}\r\n");
    }
    /**
     * 构造函数
     * @param sb
     */
    private void processConstructor(StringBuffer sb) {
        StringBuffer p = new StringBuffer();
        StringBuffer v = new StringBuffer();
        for(int i = 0; i < colNames.length; i++) {
            p.append(sqlType2JavaType(colTypes[i])+" "+colNames[i]);
            if(i!=colNames.length-1) {
                p.append(",");
            }
            v.append("\t\tthis."+colNames[i]+"="+colNames[i]+";\r\n");
        }
        //无参数构造函数
        sb.append("\tpublic "+under2camel(tableName,true)+"() {\r\n");
        sb.append("\t\tsuper();\r\n");
        sb.append("\t}\r\n");
        //带参构造函数
        sb.append("\tpublic "+under2camel(tableName,true)+"("+p.toString()+") {\r\n");
        sb.append(v.toString());
        sb.append("\t}\r\n");
    }

    /**
     * @param sb
     * @description 生成所有get/set方法
     */
    private void processAllMethod(StringBuffer sb) {
        for (int i = 0; i < colNames.length; i++) {
            sb.append("\tpublic void set" + initCap(colNames[i]) + "(" + sqlType2JavaType(colTypes[i]) + " "
                    + colNames[i] + "){\r\n");
            sb.append("\t\tthis." + colNames[i] + "=" + colNames[i] + ";\r\n");
            sb.append("\t}\r\n");
            sb.append("\tpublic " + sqlType2JavaType(colTypes[i]) + " get" + initCap(colNames[i]) + "(){\r\n");
            sb.append("\t\treturn " + colNames[i] + ";\r\n");
            sb.append("\t}\r\n");
        }
    }

    /**
     * 功能：将传入字符串的首字母转成大写
     * @param str 传入字符串
     * @return
     */
    private String initCap(String str) {
        char[] ch = str.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z')
            ch[0] = (char) (ch[0] - 32);
        return new String(ch);
    }

    /**
     * 功能：下划线命名转驼峰命名
     * @param s
     * @param fistCharToUpperCase 首字母是否大写
     * @author 呐喊
     * @return
     */
    private String under2camel(String s,boolean fistCharToUpperCase) {
        String separator = "_";
        String under="";
        s = s.toLowerCase().replace(separator, " ");
        String sarr[]=s.split(" ");
        for(int i=0;i<sarr.length;i++)
        {
            String w=sarr[i].substring(0,1).toUpperCase()+sarr[i].substring(1);
            under +=w;
        }
        if(!fistCharToUpperCase) {
            under = under.substring(0,1).toLowerCase()+under.substring(1);
        }
        return under;
    }

    /**
     * 功能：查找pgsql字段类型所对应的Java类型
     * @return
     */
    private String sqlType2JavaType(String sqlType) {
        if(sqlType.indexOf("(")>0) {
            sqlType = sqlType.substring(0, sqlType.indexOf("("));
        }
        String returnType=null;
        switch (sqlType) {
            case "boolean":
            case "bit":
                returnType =  "boolean";
                break;
            case "bytea":
                returnType =  "byte";
                break;
            case "bigint":
            case "int8":
            case "bigserial":
                returnType =  "Long";
                break;
            case "integer":
            case "int2":
            case "int4":
            case "smallint":
            case "serial":
                returnType =  "Integer";
                break;
            case "real":
            case "double precision":
                returnType =  "double";
                break;
            case "text":
            case "character varying":
            case "character":
            case "varchar":
            case "char":
            case "uuid":
            case "json":
            case "xml":
            case "money":
            case "timestamp without time zone":
            case "timestamp with time zone":
            case "time without time zone":
            case "time with time zone":
                returnType =  "String";
                break;
            case "inet":
            case "cidr":
            case "macaddr":
            case "macaddr8":
            case "box":
            case "circle":
            case "interval":
            case "line":
            case "lseg":
            case "path":
            case "point":
            case "polygon":
            case "varbit":
                returnType =  "Object";
                break;
            case "numeric":
            case "decimal":
                returnType =  "BigDecimal";
                break;
            default:
                break;
        }
        return returnType;
    }
    /**
     * 功能：获取并创建实体所在的路径目录
     * @return
     */
    private static String pkgDirName() {
        String dirName = basePath + "/src/main/resources/" + packageOutPath.replace(".", "/");
        File dir = new File(dirName);
        if (!dir.exists()) {dir.mkdirs();System.out.println("mkdirs dir 【" + dirName + "】");}
        return dirName;
    }
    /**
     * 功能：生成EntityHelper
     */
    private void EntityHelper() {
        String dirName = PgSQlMybatisGen.pkgDirName();
        String javaPath = dirName + "/EntityHelper.java";
        try {
            StringBuffer sb = new StringBuffer();
            sb.append("package " + packageOutPath + ";\r\n");
            sb.append("\r\n");
            sb.append("public abstract class EntityHelper{\r\n\r\n");
            sb.append("\tpublic abstract String getPrimaryKey();\r\n");
            sb.append("\r\n");
            sb.append("}\r\n");
            FileWriter fw = new FileWriter(javaPath);
            PrintWriter pw = new PrintWriter(fw);
            pw.println(sb.toString());
            pw.flush();
            if (pw != null){pw.close();}
            System.out.println("create class 【EntityHelper】");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    /**
     * 功能： 生成实体类的主方法
     */
    private void generate(){
        Connection conn=null;
        try {
            //数据库的连接
            PreparedStatement pStemt = null;
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(JDBC_URL, jdbc_user, jdbc_pwd);
            if(conn!=null) {
                System.out.println("connect database success..."+conn);
            }else {
                System.out.println("connect database failed^_^");
                return;
            }
            //获取数据库的元数据
            DatabaseMetaData db = conn.getMetaData();
            //是否有指定生成表，有指定则直接用指定表，没有则全表生成
            List<String> tableNames = new ArrayList<>();
            List<String> tableComments = new ArrayList<>();
            /*if (generateTables == null) {*/

            //从元数据中获取到所有的表名
            ResultSet rs = db.getTables(null, null, null, new String[] { "TABLE" });
            //String generateTable = generateTables[0];

            while (rs.next()) {
                for(int i=0;i<generateTables.length;i++) {
                    if (generateTables[i].equals(rs.getString(3))) {
                        tableNames.add(rs.getString(3));//表名}
                        tableComments.add(rs.getString(5));//表注释
                    }
                }
            }
            if (generateTables == null||generateTables.length==0) {
                throw new RuntimeException("请在generateTables数组中指定需要生成的表名！！！");
            }else if (tableNames==null||tableNames.size()==0){
                throw new RuntimeException("数据库中不存在指定的表！！！->");
            }

            String tableSql;
            PrintWriter out = null;
            for (int j = 0; j < tableNames.size(); j++) {
                tableName = tableNames.get(j);
                tableComment = tableComments.get(j);
                tableSql = SQL(tableName);
                pStemt = conn.prepareStatement(tableSql, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                ResultSet rstb = pStemt.executeQuery();
                rstb.last();
                int rowCount = rstb.getRow();
                rstb.beforeFirst();
                colNames = new String[rowCount];
                colTypes = new String[rowCount];
                colSizes = new int[rowCount];

                int idx = 0;
                while(rstb.next()) {
                    colNames[idx] = rstb.getString("name");
                    colTypes[idx] = rstb.getString("type");
                    colSizes[idx] = rstb.getInt("fields_length");
                    colNamesComment.put(rstb.getString("name"), rstb.getString("comment"));
                    idx++;
                }
                //获取主键
                pStemt = conn.prepareStatement(PK_SQL(tableName));
                ResultSet rspk = pStemt.executeQuery();
                while(rspk.next()) {
                    pk=rspk.getString("colname");
                }
                //解析生成实体java文件的所有内容
                String content = parse();
                //输出生成文件
                String dirName = PgSQlMybatisGen.pkgDirName();
                String javaPath = dirName + "/" + under2camel(tableName, true) + "Mapper.xml";
                FileWriter fw = new FileWriter(javaPath);
                out = new PrintWriter(fw);
                out.println(content);
                out.flush();
                System.out.println("create class 【" + tableName + "】");
            }
            if (out != null) {
                out.close();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            if(conn!=null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

}
