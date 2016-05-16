/**
 * Created by DixonShen on 2016/5/16.
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexTest {
    public static void main(String[] args) {
        String line = "This order was placed for QT3000! OK?";
        String pattern = "(.*)(\\d+)(.*)";

        Pattern r = Pattern.compile(pattern);

        Matcher m = r.matcher(line);
        if(m.find()){
            System.out.println("Found Value: " + m.group(0) );
            System.out.println("Found Value: " + m.group(1) );
            System.out.println("Found Value: " + m.group(2) );
        }
        else{
            System.out.print("NO MATCH");
        }
    }


}
