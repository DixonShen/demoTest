package SpiderDemo;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.LinkRegexFilter;
import org.htmlparser.filters.LinkStringFilter;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by DixonShen on 2016/7/7.
 * 定义HtmlParserTool类，
 * 用来获得网页中的超链接（包括a标签，frame中的src等等），
 * 即为了得到子节点的URL
 */
public class HtmlParserTool {

    //获取一个网站上的链接，filter用来过滤链接
    public static Set<String> extractLinks(String url, LinkRegexFilter filter){
        Set<String> links = new HashSet<String>();
        try {
            Parser parser = new Parser(url);
            //过滤<frame>标签的filter，用来提取frame标签里的src属性所表示的链接
            NodeFilter frameFilter = new NodeFilter() {
                @Override
                public boolean accept(Node node) {
                    if (node.getText().startsWith("frame src="))
                        return true;
                    else
                        return false;
                }
            };
            //OrFilter用来设置过滤<a>标签，和<frame>标签
            OrFilter linkFilter = new OrFilter(new NodeClassFilter(
                    LinkTag.class), frameFilter);
            //得到所有经过过滤的标签
            NodeList list = parser.extractAllNodesThatMatch(linkFilter);
            for (int i=0; i<list.size(); i++){
                Node tag = list.elementAt(i);
                if (tag instanceof LinkTag){       //<a>标签
                    LinkTag link = (LinkTag) tag;
                    String linkUrl = link.getLink();
                    if (filter.accept(link))
                        links.add(linkUrl);
                }else {                           //<frame>标签
                    //提取frame标签里src属性的链接如 <frame src="test.html"/>
                    String frame = tag.getText();
                    int start = frame.indexOf("src=");
                    frame = frame.substring(start);
                    int end = frame.indexOf(" ");
                    if (end == -1){
                        end = frame.indexOf(">");
                    }

                }
            }
        }catch (ParserException e){
            e.printStackTrace();
        }
        return links;
    }

}
