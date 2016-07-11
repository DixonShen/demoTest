package SpiderDemo;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import javax.xml.ws.http.HTTPException;
import java.io.*;

/**
 * Created by DixonShen on 2016/7/6.
 * 根据得到的url，爬取网页内容，下载到本地保存
 */
public class HttpGetUtils {

    /**
     *下载url指向的网页
     * @param url
     * @return
     */
    public static String downloadFile(String url) {
        String result = "";
        String filePath = null;
        try {
            //生成httpclient对象
            CloseableHttpClient httpclient = HttpClients.createDefault();
//            //设置http连接超时 5s
//            httpclient.getConnectionManager();
            //获取方法实例 GET
            HttpGet httpGet = new HttpGet(url);
            //执行方法得到响应
            CloseableHttpResponse response = httpclient.execute(httpGet);
            try {
                //如果执行正确而且返回值正确，即可解析
                if (response != null
                        && response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    System.out.println(response.getStatusLine());
                    HttpEntity entity = response.getEntity();
                    //从输入流中解析结果
                    result = readResponse(entity, "utf-8");
                    filePath = "f:\\spider\\"
                            + getFileNameByUrl(url,
                            response.getFirstHeader("Content-Type").getValue());
                    saveToLocal(result.getBytes(),filePath);
                } else {
                    System.err.println("Method failed: "
                            + response.getStatusLine());
                    filePath = null;
                }
            } finally {
                httpGet.releaseConnection();
            }
        } catch (Exception e) {
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
    private static String readResponse(HttpEntity resEntity, String charset) {
        StringBuffer res = new StringBuffer();
        BufferedReader reader = null;
        try {
            if (resEntity == null) {
                return null;
            }

            reader = new BufferedReader(new InputStreamReader(
                    resEntity.getContent(), charset));
            String line = null;

            while ((line = reader.readLine()) != null) {
                res.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e){

            }
        }
        return res.toString();
    }

    /**
     * 根据url和网页类型生成需要保存的网页的文件名  去除掉url中的非文件名字符
     * @param url
     * @param contentType
     * @return
     */
    public static String getFileNameByUrl(String url, String contentType){
        //remove http://
        url = url.substring(7);
        //text/html类型
        if (contentType.indexOf("html") != -1){
            url = url.replaceAll("[\\?/:*|<>\"]","_") + ".html";
            return url;
        }
        //如application/pdf类型
        else {
            return url.replaceAll("[\\?/:*|<>\"]","_") + "."
                    + contentType.substring(contentType.lastIndexOf("/" + 1));
        }
    }

    /**
     * 保存网页字节数组到本地文件  filePath为要保存的文件的相对地址
     * @param data
     * @param filePath
     */
    public static void saveToLocal(byte[] data, String filePath){
        try {
            DataOutputStream out = new DataOutputStream(new FileOutputStream(
                    new File(filePath)));
            for (int i=0; i<data.length;i++)
                out.write(data[i]);
            out.flush();
            out.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

//    public static void main(String[] args) {
//        String url = "http://www.qichacha.com/";
//        System.out.println(get(url));
//    }
}
