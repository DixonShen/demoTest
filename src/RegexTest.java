/**
 * Created by DixonShen on 2016/5/16.
 */

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;

public class RegexTest {

    public static final String seed1a = "品牌";
    public static final String seed1b = "Apple";
    public static final String seed2a = "上市年份";
    public static final String seed2b = "2015年";
    public static final String seed3a = "重力感应";
    public static final String seed3b = "支持";
    public static final String seed4a = "CPU品牌";
    public static final String seed4b = "苹果";

    public static void main(String[] args) {
        String s = "";
        String temp;
        String pattern;
        int count = 0;
        try {
            //读入待解析html源码
            FileReader fr = new FileReader("testJD.html");
            BufferedReader br = new BufferedReader(fr);
            while ((temp = br.readLine()) != null) {
                s += temp;
            }
            br.close();
            System.out.println(s.length());
            String InitialPattern = seed1a + "<.{1,200}>" + seed1b;
//            String InitialPattern = seed1;
            System.out.println(InitialPattern);
            Pattern p0 = Pattern.compile(InitialPattern);
            s = s.replaceAll("\\s*", "");
//            System.out.println(s);
            Matcher m0 = p0.matcher(s);
            while (m0.find()) {
                count++;
                temp = m0.group();
                System.out.println(temp);
                System.out.println(temp.length());
            }
            System.out.println(count);
            count = 0;
//            temp = m0.group();
            temp = temp.replaceAll(">" + seed1a, "");
            temp = temp.replaceAll(seed1b + "<", "");
            pattern = temp;
            Pattern p1 = Pattern.compile(pattern);
            System.out.println(pattern);
            Matcher m1 = p1.matcher(s);
            while (m1.find()) {
                count++;
                System.out.println(m1.group());
                System.out.println(m1.start() + " " + m1.end());
                System.out.println(count);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return;
        }

    }

}
