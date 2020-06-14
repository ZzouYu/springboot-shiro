package com.izy.springshiro01.utils;

import com.izy.springshiro01.pojo.JdData;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zouyu
 * @description
 * @date 2020/6/1
 */
public class HtmlParseUtil {
    public  List<?> getData(String keyWord) throws Exception {
        String url = "https://search.jd.com/Search?keyword="+keyWord+"&enc=utf-8";
        Document document = Jsoup.parse(new URL(url), 30000);
        Element element = document.getElementById("J_goodsList");
        Elements elements = element.getElementsByTag("li");
        List<JdData> dataList = new ArrayList<>();
        for (Element element1 : elements) {
            String img = element1.getElementsByTag("img").eq(0).attr("src");
            String title = element1.getElementsByClass("p-name").eq(0).text();
            String price = element1.getElementsByClass("p-price").eq(0).text();
            JdData jdData = new JdData(price, title, img);
            dataList.add(jdData);
        }
        return dataList;
    }
}
