package com.novacloud.data.formula.impl.test;

import com.novacloud.data.formula.ExpressionToken;
import com.novacloud.data.formula.Invocable;
import com.novacloud.data.formula.impl.ExpressionFactoryImpl;
import com.novacloud.data.formula.impl.OperationStrategyImpl;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

import static org.junit.Assert.fail;

public class TokenGen {
	@SuppressWarnings("unchecked")
	@Test public void test() throws UnsupportedEncodingException{
		String[] code = "2C%22%B4%CE%BB%FA%BB%E1%A3%BA%A3%A9%3C%2F".split("%");
		byte[] bts  =new byte[code.length];
		for (int i = 0; i < bts.length; i++) {
			bts[i] = (byte)Integer.parseInt(code[i], 16);
		}
		System.out.println(new String(bts,"GBK"));
		
	}
	@SuppressWarnings("unchecked")
	@Test
	public void genFunctionsSupport()throws Exception {
		ExpressionFactoryImpl exp = new ExpressionFactoryImpl();
		OperationStrategyImpl osi = (OperationStrategyImpl) exp.getStrategy();
		System.out.println(new TreeSet<>(osi.getGlobalMap().keySet()));
		Field field = OperationStrategyImpl.class.getDeclaredField("classMethodMap");
		field.setAccessible(true);
		Map<Class<?>, Map<String, Invocable>>  classMethodMap = (Map<Class<?>, Map<String, Invocable>> ) field.get(osi);
		for(Map<String,Invocable> methodMap:classMethodMap.values()){
				System.out.print(methodMap);
		}
	}
	@Test
	public void genExpressionTokens()throws Exception {
		Field[] fields = ExpressionToken.class.getFields();
		HashMap<String, Object> values = new HashMap<>();
		StringBuilder php = new StringBuilder();
		StringBuilder js = new StringBuilder();
		for(Field f : fields){
			String name = f.getName();
			Object value = f.get(null);
			if(values.containsValue(value)){
				fail("常量值重复"+name+"=" +value);
			}else{
				values.put(name,value);
			}
			js.append("var ").append(name).append("= ").append(value).append(";\n");
			php.append("define('LITE_").append(name).append("',").append(value).append(");\n");
		}
		System.out.println(js);
		System.out.println();
		System.out.println();
		System.out.println(php);
	}

}
