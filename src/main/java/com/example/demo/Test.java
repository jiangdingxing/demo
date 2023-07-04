package com.example.demo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Test {

    public final static String KEY = "queryContext";

    public static void main(String[] args) {
        String aaa = "#{" + KEY + "["+(1- 1)+"]}";
        System.out.println(aaa);

    }

    @org.junit.Test
    public void printRealTimeDevice(){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        String now = sdf.format(calendar.getTime());

        calendar.add(Calendar.MINUTE,-1);
        String past = sdf.format(calendar.getTime());
        String sql = "SELECT t5.*,t6.lon_mod,t6.lat_mod  FROM  (SELECT  *  FROM  (SELECT  *  FROM  (SELECT  hphm,count(hphm) as num1  from  (SELECT hphm,gcsj,count(hphm) as num ,sbbh from  zgy_0403_copy1   WHERE  gcsj>'2020-4-3 8:00:00' and  gcsj<'2020-4-3 9:00:00' and  hphm not in('未识别')  group  by  hphm,gcsj,sbbh  ORDER BY hphm, gcsj  \r\n"
                + ") t   GROUP BY  hphm  \r\n"
                + ") t2  WHERE  t2.num1>1) t3  LEFT JOIN   (SELECT hphm,gcsj,count(hphm) as num ,sbbh from  zgy_0403_copy1   WHERE  gcsj>'2020-4-3 8:00:00' and  gcsj<'2020-4-3 9:00:00' and  hphm not in('未识别')  group  by  hphm,gcsj,sbbh  ORDER BY hphm, gcsj  \r\n"
                + ") t4 ON t3.hphm=t4.hphm) t5 LEFT JOIN     device_points_type1_mod2  t6 on  t5.sbbh=t6.sbbh  WHERE    t6.lon_mod IS NOT NULL  and t6.lat_mod IS NOT NULL ";


        System.out.println(sql);

    }


    private static String under2camel(String s,boolean fistCharToUpperCase) {
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


}
