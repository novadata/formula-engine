package com.novacloud.data.formula.impl;

import com.novacloud.data.formula.Expression;
import com.novacloud.data.formula.ExpressionFactory;
import com.novacloud.data.formula.ExpressionToken;
import com.novacloud.data.formula.OperationStrategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class OptimizeExpressionImpl extends ExpressionImpl {
	protected final String name;

	public OptimizeExpressionImpl(ExpressionToken expression,ExpressionFactory factory,
			OperationStrategy calculater, String name) {
		super(expression, factory,calculater);
		this.name = name;
	}

	@Override
	public List<String> getVars() {
		return Arrays.asList(name);
	}

	@Override
	public Object evaluate(Object context) {
		Map<String, Object> contextMap = factory.wrapAsContext(context);
		return compute(contextMap);
	}

	protected Object compute(Map<String, Object> valueStack) {
		return strategy.getVar(valueStack,name);
	}
	/**
	 * 生成内部优化的表达式
	 * @param el
	 * @param calculater
	 * @return
	 */
	public static Expression create(final ExpressionToken el,ExpressionFactory factory,
			OperationStrategy calculater) {
		if (el.getType() == ExpressionToken.VALUE_VAR) {
			return new OptimizeExpressionImpl(el,factory, calculater, 
					(String)el.getParam());
		}else if (el.getType() == TokenImpl.OP_GET_STATIC) {
					ArrayList<Object> props = new ArrayList<>();
			ExpressionToken current = el;
			String baseName;
			while(true) {
				if(current.getType() == TokenImpl.OP_GET_STATIC){
					props.add(current.getParam());
				}else{
					if(current.getType() == ExpressionToken.VALUE_VAR){
						baseName = (String)current.getParam();
						break;
					}else{
						return null;
					}
				}
				current = current.getLeft();
			}
			final Object[] properties = props.toArray();
			switch (properties.length) {
			case 1:
				return new PropertyImpl(el,factory, calculater, 
						baseName,properties[0]);
			default:
				return new PropertiesImpl(el, factory,calculater, 
						baseName,properties);
			}

		}
		return null;
	}


}

class PropertyImpl extends OptimizeExpressionImpl {
	private final Object key;

	public PropertyImpl(ExpressionToken expression,ExpressionFactory factory,
			OperationStrategy calculater, String name, Object key) {
		super(expression,factory, calculater, name);
		this.key = key;
	}

	protected Object compute(Map<String, Object> valueStack) {
		Object base = strategy.getVar(valueStack, name);
		return ReflectUtil.getValue(base, key);
	}
}

class PropertiesImpl extends PropertyImpl {
	private final Object[] keys;

	public PropertiesImpl(ExpressionToken expression,ExpressionFactory factory,
			OperationStrategy calculater, String name, Object[] keys) {
		super(expression,factory, calculater, name, null);
		this.keys=Arrays.copyOf(keys,keys.length);
	}

	protected Object compute(Map<String, Object> valueStack) {
		Object base = strategy.getVar(valueStack, name);
		int i = keys.length;
		while (i-- > 0) {
			base = ReflectUtil.getValue(base, keys[i]);
		}
		return base;
	}
}
