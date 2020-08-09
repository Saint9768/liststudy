package com.saint.pachong.util;

import com.saint.pachong.pojo.Book;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Saint
 * @version 1.0
 * @createTime 2020-08-09 9:56
 */
@Component
public class HtmlParseUtil {
    public static void main(String[] args) throws IOException {
        parseJD("多线程").forEach(l -> System.out.println(l));
    }

    public static List<Book> parseJD(String keywords) throws IOException {
        List<Book> list = new ArrayList<>();
        //1.获取请求
        String url = "http://search.jd.com/Search?keyword=" + keywords;

        //2. 解析网页,document就是返回的浏览的document对象
        Document document = Jsoup.parse(new URL(url), 30000);

        // 3.所有你在JS中可以使用的方法，在这里都可以使用
        Element element = document.getElementById("J_goodsList");

        // 4.获取所有的li元素
        Elements elements = element.getElementsByTag("li");

        //获取元素中的内容，这里el  就是每一个li标签
        for (Element el : elements) {
            // 关于这种图片特别多的网站，所有的图片都是延迟加载的
            String img = el.getElementsByTag("img").eq(0).attr("data-lazy-img");
            //获取p-price class中的第一个元素
            String price = el.getElementsByClass("p-price").eq(0).text();
            String title = el.getElementsByClass("p-name").eq(0).text();
            Book book = new Book();
            book.setImg(img);
            book.setPrice(price);
            book.setTitle(title);
            list.add(book);
        }
        return list;
    }
}
