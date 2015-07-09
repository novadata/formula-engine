package com.novacloud.data.formula.script;

import com.novacloud.data.formula.ExpressionFactory;
import com.novacloud.data.formula.impl.ExpressionFactoryImpl;

import javax.script.*;
import java.io.Reader;
import java.util.AbstractMap;
import java.util.Map;
import java.util.Set;

/**
 * @see ExpressionFactoryImpl
 */
public class ExpressionEngine extends AbstractScriptEngine {
	private ScriptEngineFactory factory = null;

	public ExpressionEngine(ExpressionEngineFactory factory) {
		this.factory = factory;
	}

	public ExpressionEngine() {
		this.factory = new ExpressionEngineFactory();
	}

	/**
	 * 获得系统默认的表达式工厂(包含ECMA262 标准扩展的表达式工厂,状态(内置对象,运算符扩展)不允许修改)
	 * 
	 * @return
	 */
	public static ExpressionFactory getExpressionFactory() {
		return ExpressionFactoryImpl.getInstance();
	}

	public Bindings createBindings() {
		return new SimpleBindings();
	}

	public Object eval(String script, ScriptContext context)
			throws ScriptException {
		Map<String, Object> proxy = context == this.context? engineProxy:new MapProxy(context);
		return getExpressionFactory().create(script).evaluate(proxy);
	}

	public Object eval(Reader reader, ScriptContext context)
			throws ScriptException {
		return null;
	}

	public ScriptEngineFactory getFactory() {
		return factory;
	}

	private final Map<String, Object> engineProxy = new MapProxy(context){
		public Object get(Object key) {
			return ExpressionEngine.this.get(key.toString());
		}

		public Object put(String key, Object value) {
			ExpressionEngine.this.put(key, value);
			return null;
		}
	};
}
class MapProxy extends AbstractMap<String, Object>{
	private final ScriptContext context;
	MapProxy(ScriptContext context){
		this.context = context;
	}
	public Object get(Object key) {
		return this.context.getAttribute(key.toString());
	}

	public Object put(String key, Object value) {
		this.context.setAttribute(key, value,ScriptContext.ENGINE_SCOPE);
		return null;
	}

	@Override
	public Set<java.util.Map.Entry<String, Object>> entrySet() {
		throw new UnsupportedOperationException();
	}
}