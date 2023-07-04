package com.example.demo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PgSQlGenerateEntityUtil {


    public static void main(String[] args) {
        PgSQlGenerateEntityUtil pgGen = new PgSQlGenerateEntityUtil();
        //PgSQlGenerateEntityUtil.basePath=""; //指定生成的位置,默认是当前工程
        //pgGen.needEntityHelper=true;
        //生成的包名
        PgSQlGenerateEntityUtil.packageOutPath = "com.cennavi.trafficsystem.roadsituation.web.model";
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

    //是否创建BaseBO
    private boolean needBaseBO = true;

    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    // 数据库配置信息
    private static final String JDBC_URL = "jdbc:postgresql://192.168.219.223:9432/vector_all_database";
    public static final String jdbc_user = "postgres";
    public static final String jdbc_pwd = "superman@2019";
    private static final String DRIVER = "org.postgresql.Driver";

    //指定实体生成所在包的路径
    private static String basePath = new File("").getAbsolutePath();
    //指定包名
    private static String packageOutPath = "com.cennavi.trafficsystem.roadsituation.web.model";
    //作者名字
    private static final String authorName = "jiangDingXing";
    //指定需要生成的表的表名，全部生成设置为null
    private String[] generateTables = {"AOI","BLDG_P","BUI_S","GRE_S","GREB_S","LAK_S","POI","RAI_L","RES_S",
        "ROA_S","SEA_S","TRA_CL","XZQH_L","XZQH_P","XZQH_S"};

    //主键
    private static String pk;

    private PgSQlGenerateEntityUtil() {
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
    /**
     * 功能：解析字段数据，生成实体类的所有内容
     * @return
     */
    private String parse() {
        StringBuffer sb = new StringBuffer();
        sb.append("package " + packageOutPath + ";\r\n");
        sb.append("\r\n");
        // 判断是否导入工具包
        if (needUtil) {
            sb.append("import java.util.Date;\r\n");
        }
        if (needSql) {
            sb.append("import java.sql.*;\r\n");
        }

        for (int i = 0; i < colNames.length; i++) {
            String hasbd = sqlType2JavaType(colTypes[i]);
            if(hasbd =="BigDecimal" || "BigDecimal".equals(hasbd)) {needBigDecimal=true;}
        }
        if(needBigDecimal) {
            sb.append("import java.math.BigDecimal;\r\n");
        }
        // 注释部分
        sb.append("/**\r\n");
        sb.append(" * <ul>\r\n");
        sb.append(" * <li>table name:  " + tableName + "</li>\r\n");
        sb.append(" * <li>table comment:  " + (tableComment==null?"":tableComment) + "</li>\r\n");
        sb.append(" * <li>author name: " + authorName + "</li>\r\n");
        sb.append(" * <li>create time: " + SDF.format(new Date()) + "</li>\r\n");
        sb.append(" * </ul>\r\n");
        sb.append(" */ \r\n");
        // 实体部分
        String classExtends = "";

        if(needEntityHelper) {
            classExtends=" extends EntityHelper";
        }
        //适配新余项目
        if(needBaseBO) {
            classExtends=" extends BaseBO";
        }

        sb.append("public class " + under2camel(tableName, true) + classExtends + "{\r\n\r\n");

        if(needBaseBO) {
            processBaseBO(sb);
        }
        //实体属性
        processAllAttrs(sb);
        sb.append("\r\n");
        // 构造函数
        processConstructor(sb);//
        //get set方法
        processAllMethod(sb);
        processToString(sb);
        if(needEntityHelper) {
            processEntityHelper(sb,pk);
        }
        sb.append("}\r\n");
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
     * BaseBO
     * @param sb
     */
    private void processBaseBO(StringBuffer sb) {
        //适配新余项目
        sb.append("\tprivate static final String MAPPER = \"maindb/"+under2camel(tableName, true)+"Mapper.xml\"; ");
        sb.append("\r\n");
        sb.append("\t@Override\r\n");
        sb.append("\tpublic String getMapper() {\r\n");
        sb.append("\t\treturn MAPPER;\r\n");
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
            sb.append(under2camel(colNames[i],false) + "=\" + "
                    + under2camel(colNames[i],false)).append(" + \r\n");
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
            p.append(sqlType2JavaType(colTypes[i])+" "+under2camel(colNames[i],false));
            if(i!=colNames.length-1) {
                p.append(",");
            }
            v.append("\t\tthis."+under2camel(colNames[i],false)+"="+under2camel(colNames[i],false)+";\r\n");
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
            sb.append("\tpublic void set" + initCap(under2camel(colNames[i],false)) + "(" + sqlType2JavaType(colTypes[i]) + " "
                    + under2camel(colNames[i],false) + "){\r\n");
            sb.append("\t\tthis." + under2camel(colNames[i],false) + "=" + under2camel(colNames[i],false) + ";\r\n");
            sb.append("\t}\r\n");
            sb.append("\tpublic " + sqlType2JavaType(colTypes[i]) + " get" + initCap(under2camel(colNames[i],false)) + "(){\r\n");
            sb.append("\t\treturn " + under2camel(colNames[i],false) + ";\r\n");
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
                returnType =  "Double";
                break;
            case "text":
            case "character varying":
            case "character":
            case "varchar":
            case "TEXT":
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
            case "geometry":
                returnType =  "Object";
                break;
            case "numeric":
            case "decimal":
                returnType =  "BigDecimal";
                break;
            case "timestamp":
            case "date":
                returnType =  "Date";
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
        String dirName = basePath + "/src/main/java/" + packageOutPath.replace(".", "/");
        File dir = new File(dirName);
        if (!dir.exists()) {dir.mkdirs();System.out.println("mkdirs dir 【" + dirName + "】");}
        return dirName;
    }
    /**
     * 功能：生成EntityHelper
     */
    private void EntityHelper() {
        String dirName = PgSQlGenerateEntityUtil.pkgDirName();
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
     * 功能：生成BaseBO
     */
    private void BaseBO() {
        String dirName = PgSQlGenerateEntityUtil.pkgDirName();
        String javaPath = dirName + "/BaseBO.java";
        try {
            StringBuffer sb = new StringBuffer();
            sb.append("package " + packageOutPath + ";\r\n");
            sb.append("\r\n");
            sb.append("public abstract class BaseBO{\r\n\r\n");
            sb.append("\tpublic abstract String getMapper();\r\n");
            sb.append("\r\n");
            sb.append("}\r\n");
            FileWriter fw = new FileWriter(javaPath);
            PrintWriter pw = new PrintWriter(fw);
            pw.println(sb.toString());
            pw.flush();
            if (pw != null){pw.close();}
            System.out.println("create class 【BaseBO】");
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
            //从元数据中获取到所有的表名
            ResultSet rs = db.getTables(null, null, null, new String[] { "TABLE" });
            //String generateTable = generateTables[0];

            while (rs.next()) {
                for(int i=0;i<generateTables.length;i++){
                    if (generateTables[i].equals(rs.getString(3))) {
                        tableNames.add(rs.getString(3));//表名}
                        tableComments.add(rs.getString(5));//表注释
                    }
                }
            }
            if (generateTables == null||generateTables.length==0) {
                throw new RuntimeException("请在generateTables数组中指定需要生成的表名！！！");
            }else if (tableNames==null||tableNames.size()==0){
                throw new RuntimeException("数据库中不存在指定的表！！！");
            }

            if(needEntityHelper) {
                EntityHelper();
            }
            if(needBaseBO) {
                BaseBO();
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
                String dirName = PgSQlGenerateEntityUtil.pkgDirName();
                String javaPath = dirName + "/" + under2camel(tableName, true) + ".java";
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
