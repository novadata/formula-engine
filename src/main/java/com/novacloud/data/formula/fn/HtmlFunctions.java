package com.novacloud.data.formula.fn;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Html Functions
 *
 * @author <a href="mailto:zhaoxiaoyong@novacloud.com">zhaoxiaoyong</a>
 * @date 2015-6-4 下午1:57:33
 * @version Revision: 1.0
 */
public class HtmlFunctions extends JSObject {
    private static final Logger logger = LoggerFactory.getLogger(HtmlFunctions.class);

    public static String attr(String text,String attr){
        try {
            final Document document = Jsoup.parseBodyFragment(text);
            return document.body().child(0).attr(attr);
        } catch (Exception e) {
            return "";
        }
    }
    public static String tagName(String text){
         try {
             final Document document = Jsoup.parseBodyFragment(text);
             return document.body().child(0).tagName();
         } catch (Exception e) {
             return "";
         }
     }
    public static String text(String text){
         try {
             final Document document = Jsoup.parseBodyFragment(text);
             return document.body().child(0).text();
         } catch (Exception e) {
             return "";
         }
     }
    public static String ownText(String text){
         try {
             final Document document = Jsoup.parseBodyFragment(text);
             return document.body().child(0).ownText();
         } catch (Exception e) {
             return "";
         }
     }
    public static String innerHtml(String text){
         try {
             final Document document = Jsoup.parseBodyFragment(text);
             return document.body().child(0).html();
         } catch (Exception e) {
             return "";
         }
     }
    public static String child(String text,int index){
         try {
             final Document document = Jsoup.parseBodyFragment(text);
             final Element child = document.body().child(0).child(index);
             return child==null ?"":child.outerHtml();
         } catch (Exception e) {
             return "";
         }
     }
    public static String sibling(String text,int index){
         try {
             final Document document = Jsoup.parseBodyFragment(text);
             final Element element = document.body().child(index);
             return element==null?"":element.outerHtml();
         } catch (Exception e) {
             return "";
         }
     }
    public static String querySelector(String text,String selector){
         try {
             final Document document = Jsoup.parseBodyFragment(text);
             final Element first = document.body().select(selector).first();
             return first==null?"":first.outerHtml();
         } catch (Exception e) {
             return "";
         }
     }
    public static String querySelectorAll(String text,String selector){
         try {
             final Document document = Jsoup.parseBodyFragment(text);
             final Elements elements = document.body().select(selector);
             return elements.outerHtml();
         } catch (Exception e) {
             return "";
         }
     }
}
