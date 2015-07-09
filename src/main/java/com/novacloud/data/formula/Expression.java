package com.novacloud.data.formula;

public interface Expression{
	/**
	 * 根据传入的变量上下文（map 或者javabean），执行表达式
	 * @param context 变量表
	 * @return
	 */
	 Object evaluate(Object context) throws ExpressionSyntaxException;
	/**
	 * 根据传入的变量上下文（键值数组），执行表达式
	 * @param keyValue 键值对（两个参数代表一个键值对）
	 * @return
	 */
	 Object evaluate(Object... keyValue) throws ExpressionSyntaxException;
}