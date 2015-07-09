package com.novacloud.data.formula.fn;

import com.novacloud.data.formula.ExpressionSyntaxException;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 公式引擎支持的自定义函数
 *
 * @author <a href="mailto:zhaoxiaoyong@novacloud.com">zhaoxiaoyong</a>
 * @version Revision: 1.0
 * @date 3/5/2015 1:47 PM
 */
public class TypeConversionFunctions {
    public static final String DATE_FORMAT = "yyyyMMdd";
    public static final String DATETIME_FORMAT = "yyyyMMdd hh:mm:ss";
    private static final String date_regex_2 = "((\\d{2,4}[-年/\\s]+\\d{1,2}[-月/\\s]*\\d{1,2}[\\s日/]*\\d{1,2}[:时]\\d{1,2}([:分]\\d{1,2})?))|(\\d{2,4}/\\d{1,2}/\\d{1,2}&nbsp;\\d{1,2}[:时]\\d{1,2}([:分]\\d{1,2})?)";
    private static final String date_regex = "(\\d{2,4}[-年/\\s]+\\d{1,2}[-月/\\s]*\\d{1,2}[\\s日/]*[\\s星期(日|天|六五|四|三|二|一)\\s/]*\\d{1,2}[:时]\\d{1,2}([:分]\\d{1,2})?)|(\\d{2,4}[-年./\\s]+\\d{1,2}[-月./\\s]*\\d{1,2}[\\s日/]*)";
    private static final String int_regex = "(\\d+)";
    private static final String float_regex = "(\\d+(\\.\\d+)?)";
    private static Logger logger = LoggerFactory.getLogger(TypeConversionFunctions.class);
    private static Pattern intPattern = Pattern.compile(int_regex);
    private static Pattern floatPattern = Pattern.compile(float_regex);

    public static String Int(String text) {
        final Matcher matcher = intPattern.matcher(text);
        return matcher.find() ? matcher.group() : "";
    }

    public static String Float(String text) {
        final Matcher matcher = floatPattern.matcher(text);
        return matcher.find() ? matcher.group() : "";
    }

    /**
     * 将text转换为日期串. 当pattern为null或""时，自动识别日期；否则，使用pattern格式串来识别日期.
     *
     * @param text
     * @param regex 识别日期用的正则表达式
     * @return
     */
    @Remark("String DATE(String text,[String regex])")
    public static String date(String text, String regex) {
        try {
             Pattern pattern;
             Date findedDate = new Date();
             if (regex == null || regex.length() < 1) {  //auto convert
                 pattern = Pattern.compile(date_regex + "|" + date_regex_2,
                         Pattern.DOTALL | Pattern.CASE_INSENSITIVE
                                 | Pattern.MULTILINE);

             } else {
                 pattern = Pattern.compile(regex,
                         Pattern.DOTALL | Pattern.CASE_INSENSITIVE
                                 | Pattern.MULTILINE);
             }
             Matcher matcher = pattern.matcher(text);
             if (matcher.find()) {
                 findedDate = DateTime.parse(matcher.group(0).trim()).toDate();
             }
             return new DateTime(findedDate.getTime()).toString(DATE_FORMAT);
         } catch (Exception e) {
             throw new ExpressionSyntaxException("regex error", e);
         }
    }
    /**
     * 将text转换为日期时间串. 当pattern为null或""时，自动识别日期；否则，使用pattern格式串来识别日期.
     *
     * @param text
     * @param regex 识别日期时间用的正则表达式
     * @return
     */
    @Remark("String DATETIME(String text,[String regex])")
    public static String datetime(String text, String regex) {
        try {
            Pattern pattern;
            Date findedDate = new Date();
            if (regex == null || regex.length() < 1) {  //auto convert
                pattern = Pattern.compile(date_regex + "|" + date_regex_2,
                        Pattern.DOTALL | Pattern.CASE_INSENSITIVE
                                | Pattern.MULTILINE);

            } else {
                pattern = Pattern.compile(regex,
                        Pattern.DOTALL | Pattern.CASE_INSENSITIVE
                                | Pattern.MULTILINE);
            }
            Matcher matcher = pattern.matcher(text);
            if (matcher.find()) {
                findedDate = DateTime.parse(matcher.group(0).trim()).toDate();
            }
            return new DateTime(findedDate.getTime()).toString(DATETIME_FORMAT);
        } catch (Exception e) {
            throw new ExpressionSyntaxException("regex error", e);
        }
    }

    /**
     * 将对象转换为字符串.如果对象是Date类型，则按照format的格式转换为字符串
     *
     * @param value
     * @param format
     * @return
     */
    @Remark("String STR(Object value,[String format])")
    public static String str(Object value, String format) {
        try {
            if (value instanceof Date) {
                if (format == null || format.length() < 1) {
                    return new DateTime(((Date) value).getTime()).toString(DATE_FORMAT);
                } else {
                    return new DateTime(((Date) value).getTime()).toString(format);
                }
            } else {
                return value == null ? "" : value.toString();
            }
        } catch (Exception e) {
            throw new ExpressionSyntaxException("format error", e);
        }
    }
}
