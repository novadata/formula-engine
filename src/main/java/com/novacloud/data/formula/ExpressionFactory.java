package com.novacloud.data.formula;

import com.novacloud.data.formula.impl.ExpressionFactoryImpl;

import java.lang.reflect.Method;
import java.util.Locale;
import java.util.Map;

/**
 * @see ExpressionFactoryImpl
 */
 public interface ExpressionFactory {
	/**
	 * 从中间代码或者直接的表达式文本解析成表达式对象
	 * @param el
	 * @return
	 */
	 abstract Expression create(Object el);
	/**
	 * 将表达式解析成中间状态
	 * @param expression
	 * @return
	 */
	 abstract Object parse(String expression);
	/**
	 * 添加表达式引擎内置变量
	 * @param name
	 * @param value
	 */
	 abstract void addVar(String name, Object value);
	/**
	 * 从对象(Map/JavaBean)构造一个表达式上下文
	 * @param <T>
	 * @param context
	 * @return
	 */
	 abstract <T>T wrapAsContext(Object context);

	/**
	 * add global function
	 * @param var
	 * @param value
	 * @param category
	 */
	 void addGlobalFn(String var, Method value,Category category);

	/**
	 * Get Global Function Map using default locale:zh
	 * @return key: function name,value,function object
	 */
	 Map<String, Function> getGlobalFnMap();
	/**
	 * key: function name,value,function object
	 * @param locale The function's remark and example will be different with different locale.default locale:zh
	 *
	 * @return key: function name, value:Function object
	 */
	 Map<String, Function> getGlobalFnMap(Locale locale);
}