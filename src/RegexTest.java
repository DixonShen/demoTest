/**
 * Created by DixonShen on 2016/5/16.
 */

import java.nio.file.Paths;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;

public class RegexTest {

    public static final String seed1 = "品牌";
    public static final String seed2 = "Apple";

    public static void main(String[] args) {
        String s = "";
        String temp;
        int count = 0;
        try {
            FileReader fr = new FileReader("testJD.html");
            BufferedReader br = new BufferedReader(fr);
            while ((temp = br.readLine()) != null) {
                s += temp;
            }
            br.close();
            System.out.println(s);
            String InitialPattern = ">" + seed1 + "<.+>" + seed2 + "<";
            System.out.println(InitialPattern);
            Pattern p = Pattern.compile(InitialPattern);
            Matcher m = p.matcher(s);
            while (m.find()) {
                count++;
                System.out.println(m.group());
            }
            System.out.println(count);
        } catch (IOException e) {
            System.out.println("Exception");
        }

    }

}
