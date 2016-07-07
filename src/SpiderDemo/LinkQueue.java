package SpiderDemo;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

/**
 * Created by DixonShen on 2016/7/7.
 * 定义已访问队列，待访问队列和爬取得URL的哈希表，
 * 包括出队列，入队列，判断队列是否空等操作
 */
public class LinkQueue {
    //已访问的url集合
    private static Set visitedUrl = new HashSet();
    //待访问的URL集合
    private static Queue unVisitedUrl = new PriorityQueue(); //PriorityQueue待了解

    //获得URL队列
    public static Queue getUnVisitedUrl(){
        return unVisitedUrl;
    }

    //添加到访问过的URL队列中
    public static void addVisitedUrl(String url){
        visitedUrl.add(url);
    }

    //移除访问过的URL
    public static void removeVisitedUrl(String url){
        visitedUrl.remove(url);
    }

    //未访问过的url出队列
    public static Object unVisitedUrlDeQueue(){
        return unVisitedUrl.poll();
    }

    //保证每个url只被访问一次
    public static void addUnvisitedUrl(String url){
        if (url != null && !url.trim().equals("") &&
                !visitedUrl.contains(url) &&
                !unVisitedUrl.contains(url))
            unVisitedUrl.add(url);
    }

    //获得已访问的URL数目
    public static int getVisitedUrlNum(){
        return visitedUrl.size();
    }

    //判断未访问的URL队列中是否为空
    public static boolean unVisitedUrlsEmpty(){
        return unVisitedUrl.isEmpty();
    }
}
