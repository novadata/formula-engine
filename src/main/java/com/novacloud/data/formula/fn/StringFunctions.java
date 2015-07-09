package com.novacloud.data.formula.fn;

import com.novacloud.data.formula.ExpressionSyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 */
public class StringFunctions extends JSObject {
    private static final Logger logger = LoggerFactory.getLogger(StringFunctions.class);

    public static String fromCharCode(int charCode) {
        return new String(new int[]{charCode}, 0, 1);
    }

    /**
     * 按照正则表达式提取匹配的第一组子串。
     *
     * @param text
     * @param regex
     * @return
     */
    public static String regexExtract(String text, String regex) {
        try {
            Pattern pattern = Pattern.compile(regex, Pattern.DOTALL
                    | Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
            Matcher matcher = pattern.matcher(text);
            if (matcher.find()) {
                return matcher.group(0).trim();
            }
            else {
                return "";
            }
        } catch (Exception e) {
            logger.warn("extract error", e);
            throw new ExpressionSyntaxException("regex error", e);
        }
    }


    public static char charAt(String text, int p) {
        return text.charAt(p);
    }

    public static int charCodeAt(String text, int p) {
        return text.charAt(p);
    }

    public static int len(String text) {
        return text.length();
    }

    public static String mid(String text, int start, int length) {
        return text.substring(start, start + length);
    }

    public static String left(String text, int length) {
        if (length <= 0) {
            return "";
        }
        return text.substring(0, length);
    }

    public static String right(String text, int length) {
        if (length <= 0) {
            return "";
        }
        int startIndex = text.length() - length;
        return text.substring(startIndex < 0 ? 0 : startIndex, text.length());
    }

    @Remark("String CONCAT(arg1,String arg2,[String arg3])")
    public static String concat(String text, String arg2, String arg3) {
        StringBuilder buf = new StringBuilder(text);
        buf.append(arg2);
        if (arg3 != null) {
            buf.append(arg3);
        }
        return buf.toString();
    }

    @Remark("int INDEXOF(String text,String searchvalue,[int fromIndex]")
    public static int indexOf(String text, String searchvalue, int fromIndex) {
        return text.indexOf(searchvalue, fromIndex);
    }

    @Remark("int LASTINDEXOF(String text,String searchvalue,[int fromIndex]")
    public static int lastIndexOf(String text, String searchvalue, int fromIndex) {
        return text.lastIndexOf(searchvalue, fromIndex);
    }

    public static String regexReplace(String text, String regex, String replacement) {
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
        return pattern.matcher(text).replaceAll(replacement);
    }

    public static String replace(String text, String search_for, String replacement) {
        return text.replace(search_for, replacement);
    }

    @Remark("boolean REGEXMATCH(String text,String regex)")
    public static boolean regexMatch(String text, String regex) {
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.MULTILINE);
        Matcher match = pattern.matcher(text);
        return match.find();
    }


    // 15.5.4.14 String.prototype.split(separator, limit)
    @Remark("String[] SPLIT(String text,String separator,[int limit])")
    public static String[] split(String text, String separator, int limit) {
        return Pattern.compile(Pattern.quote(String.valueOf(separator))).split(text, limit);

    }
     /**
     *
     *
     * @param text
     * @param start
     * @param end
     * @return
     */
    @Remark("String SUBSTR(String text,int start,[int end]")
    public static String substr(String text, int start, int end) {
        if (end == 0) {
            end = text.length();
        }
            return text.substring(start, end);
    }

    @Remark("String TOLOWERCASE(String text,[String locale])")
    public static String toLowerCase(String text,String locale) {
        Locale l = Locale.getDefault();
        if (locale != null && locale.length()>1) {
            l = new Locale(locale);
        }
        return text.toLowerCase(l);
    }


    @Remark("String TOUPPERCASE(String text,[String locale])")
    public static String toUpperCase(String text,String locale) {
        Locale l = Locale.getDefault();
         if (locale != null && locale.length()>1) {
             l = new Locale(locale);
         }
         return text.toUpperCase(l);
    }
}
