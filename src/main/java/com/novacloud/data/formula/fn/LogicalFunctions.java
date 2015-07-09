package com.novacloud.data.formula.fn;

import com.novacloud.data.formula.ExpressionSyntaxException;

/**
 * Created by zhaoxy on 5/11/15.
 */
public class LogicalFunctions {
    /**
     * 当逻辑表达式的值为 TRUE 时返回一个值truePart，而当其为 FALSE 时返回另一个值falsePart
     *
     * @param condition
     * @return
     */
    @Remark("String IIF(boolean condition, String truePart, String falsePart)")
    public static String iif(boolean condition, String truePart, String falsePart) {
        try {
            return condition?truePart:falsePart;
        } catch (Exception e) {
            throw new ExpressionSyntaxException("condition  error", e);
        }
    }
}
