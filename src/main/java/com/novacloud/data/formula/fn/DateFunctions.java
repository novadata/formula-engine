package com.novacloud.data.formula.fn;

import com.novacloud.data.formula.ExpressionSyntaxException;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 公式引擎支持的自定义函数
 *
 * @author <a href="mailto:zhaoxiaoyong@novacloud.com">zhaoxiaoyong</a>
 * @version Revision: 1.0
 * @date 3/5/2015 1:47 PM
 */
public class DateFunctions {
    public static final String DATE_FORMAT = "yyyyMMdd";
    public static final String DATETIME_FORMAT = "yyyyMMdd hh:mm:ss";
    private static Logger logger = LoggerFactory.getLogger(DateFunctions.class);
    private static final String date_regex_2 = "((\\d{2,4}[-年/\\s]+\\d{1,2}[-月/\\s]*\\d{1,2}[\\s日/]*\\d{1,2}[:时]\\d{1,2}([:分]\\d{1,2})?))|(\\d{2,4}/\\d{1,2}/\\d{1,2}&nbsp;\\d{1,2}[:时]\\d{1,2}([:分]\\d{1,2})?)";
    private static final String date_regex = "(\\d{2,4}[-年/\\s]+\\d{1,2}[-月/\\s]*\\d{1,2}[\\s日/]*[\\s星期(日|天|六五|四|三|二|一)\\s/]*\\d{1,2}[:时]\\d{1,2}([:分]\\d{1,2})?)|(\\d{2,4}[-年./\\s]+\\d{1,2}[-月./\\s]*\\d{1,2}[\\s日/]*)";

    /**
     * 当前日期. 当format为null或""时，默认为yyyyMMdd格式
     *
     * @param format 格式串
     * @return
     */
    @Remark("String TODAY([String format])")
    public static String today(String format) {
        try {
            if (format == null || format.length() < 1) {
                return new DateTime().toString(DATE_FORMAT);
            }
            else {
                return new DateTime().toString(format);
            }
        } catch (Exception e) {
            throw new ExpressionSyntaxException("format error", e);
        }
    }

    /**
     * 以日期值格式返回当前日期和时间. 当format为null或""时，默认为yyyyMMdd  hh:mm:ss格式
     *
     * @param format 格式串
     * @return
     */
    @Remark("String NOW([String format])")
    public static String now(String format) {
        try {
            if (format == null || format.length() < 1) {

                return new DateTime().toString(DATETIME_FORMAT);
            }
            else {
                return new DateTime().toString(format);
            }
        } catch (Exception e) {
            throw new ExpressionSyntaxException("format error", e);
        }
    }


}
