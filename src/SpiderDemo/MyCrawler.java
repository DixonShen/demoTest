package SpiderDemo;

import org.htmlparser.filters.LinkStringFilter;
import org.htmlparser.filters.StringFilter;
import org.htmlparser.lexer.Stream;

import java.util.Set;

/**
 * Created by DixonShen on 2016/7/11.
 */
public class MyCrawler {

    /**
     * 使用种子初始化url队列
     * @param seeds
     */
    private void initCrawlerWithSeeds(String[] seeds) {
        for (int i = 0; i < seeds.length; i++)
            LinkQueue.addUnvisitedUrl(seeds[i]);
    }

    /**
     * 抓取过程
     * @param seeds
     */
    public void crawling(String[] seeds){
        // 定义过滤器，提取以http://www.baidu.com开头的链接
        LinkStringFilter filter = new LinkStringFilter("qichacha.com"){
            public boolean accept(String url){
                if (url.startsWith("http://www.qichacha.com"))
                    return true;
                else
                    return false;
            }
        };
        //初始化url队列
        initCrawlerWithSeeds(seeds);
        //循环条件：待抓取的链接不空且抓取的网页不多于1000
        while (!LinkQueue.unVisitedUrlsEmpty()
                && LinkQueue.getVisitedUrlNum() <= 1000){
            //队头url出队列
            String visitUrl = (String) LinkQueue.unVisitedUrlDeQueue();
            if (visitUrl == null)
                continue;
            HttpGetUtils downloader = new HttpGetUtils();
            downloader.downloadFile(visitUrl);
            LinkQueue.addVisitedUrl(visitUrl);
            Set<String> links = HtmlParserTool.extractLinks(visitUrl,filter);

            for (String link : links){
                LinkQueue.addUnvisitedUrl(link);
            }
        }
    }

    public static void main(String[] args) {
        MyCrawler crawler = new MyCrawler();
        crawler.crawling(new String[] {"http://www.qichacha.com"});
    }
}
