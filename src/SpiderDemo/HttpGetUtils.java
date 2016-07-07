package SpiderDemo;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by DixonShen on 2016/7/6.
 */
public class HttpGetUtils {

    /**
     * get 方法
     * @param url
     * @return
     */
    public static String get(String url){
        String result = "";
        try{
            //获取httpclient实例
            CloseableHttpClient httpclient = HttpClients.createDefault();
            //获取方法实例 GET
            HttpGet httpGet = new HttpGet(url);
            //执行方法得到响应
            CloseableHttpResponse response = httpclient.execute(httpGet);
            try{
                //如果执行正确而且返回值正确，即可解析
                if (response != null
                        && response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                    System.out.println(response.getStatusLine());
                    HttpEntity entity = response.getEntity();
                    //从输入流中解析结果
                    result = readResponse(entity,"utf-8");
                }
            }finally {
                httpclient.close();
                response.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     * stream读取内容，可以传入字符格式
     * @param resEntity
     * @param charset
     * @return
     */
    private static String readResponse(HttpEntity resEntity, String charset){
        StringBuffer res = new StringBuffer();
        BufferedReader reader = null;
        try {
            if (resEntity == null){
                return null;
            }

            reader = new BufferedReader(new InputStreamReader(
                    resEntity.getContent(),charset));
            String line = null;

            while ((line = reader.readLine()) != null){
                res.append(line);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                if (reader != null){
                    reader.close();
                }
            }catch (IOException e){

            }
        }
        return res.toString();
    }

    public static void main(String[] args) {
        String url = "http://www.qichacha.com/";
        System.out.println(get(url));
    }
}
