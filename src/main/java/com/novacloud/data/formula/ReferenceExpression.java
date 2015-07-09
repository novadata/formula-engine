package com.novacloud.data.formula;

public interface ReferenceExpression{
	/**
	 * 根据传入的变量上下文，执行表达式
	 * @param context 变量表
	 * @return
	 */
	 Reference prepare(Object context);
	/**
	 * 返回表达式的源代码
	 * @return
	 */
	 String toString();
}