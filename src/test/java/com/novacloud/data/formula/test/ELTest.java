package com.novacloud.data.formula.test;

import com.novacloud.data.formula.ExpressionFactory;
import com.novacloud.data.formula.Function;
import com.novacloud.data.formula.impl.ExpressionFactoryImpl;
import com.novacloud.data.formula.impl.ExpressionParser;
import com.novacloud.data.formula.json.JSONDecoder;
import com.novacloud.data.formula.json.JSONEncoder;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class ELTest {
	private static ExpressionFactory optimizedFactory = new ExpressionFactoryImpl();
	private static ExpressionFactory noneOptimizedFactory = new ExpressionFactoryImpl(){

		public Object parse(String el) {
			ExpressionParser ep = new ExpressionParser(el);
			ep.setAliasMap(aliseMap);
			//tokens = ((TokenImpl) tokens).optimize(strategy, Collections.EMPTY_MAP);
			return ep.parseEL();
		}
	};

	private static final JSONEncoder encoder = new JSONEncoder(JSONEncoder.W3C_DATE_TIME_FORMAT, true, 128){
		public void print(Object object,StringBuilder out){
			if(object instanceof Number){
				Number n = (Number) object;
				if(Float.isInfinite(n.floatValue())){
					out.append("null");
					//this.print("Infinite", out);
				}else if(Float.isNaN(n.floatValue())){
					out.append("null");
					//this.print("NaN", out);
				}else if(n.doubleValue() == 0){
					out.append("0");
				}else{
					super.print(object, out);
				}
			}else{
				super.print(object, out);
			}
		}
	};





	private static String normalizeJSON(String result,boolean jsonStringResult) {
		try {
//			System.out.println(result);
			if(jsonStringResult){
				String raw = JSONDecoder.decode((String) JSONDecoder.decode(result));
				raw = encoder.encode(raw,new StringBuilder()).toString();
				return encoder.encode(raw,new StringBuilder()).toString();
			}
			result = encoder.encode(JSONDecoder.decode(result),new StringBuilder()).toString();
		} catch (Exception ignored) {
		}
		return result;
	}
	@Test
	public void testIgnoreCase(){
		ExpressionFactoryImpl factory = new ExpressionFactoryImpl();
		String formula=  "CONCAT('a','b')";
		Object actual = factory.create(formula).evaluate(this);
		assertEquals("ab",actual);

		formula=  "concat('a','b')";
		 actual = factory.create(formula).evaluate(this);
		assertEquals("ab",actual);

		formula=  "ConCat('a','b')";
		actual = factory.create(formula).evaluate(this);
		assertEquals("ab",actual);
		HashMap context = new HashMap();
		context.put("this","hello");


		formula=  "ConCat(this,'a','b')";
		actual = factory.create(formula).evaluate(context);
		assertEquals("helloab",actual);

	}
	@Test
	public void testOptArgs(){

		ExpressionFactoryImpl factory = new ExpressionFactoryImpl();
		String formula=  "concat('a','b')";
		Object actual = factory.create(formula).evaluate(this);
				assertEquals("ab",actual);
		formula=  "concat('a','b','c')";
		 actual = factory.create(formula).evaluate(this);
		assertEquals("abc",actual);
		formula=  "indexOf('abc','b')";
		actual = factory.create(formula).evaluate(this);
		assertEquals(1,actual);
		formula=  "indexOf('abc','b',2)";
		actual = factory.create(formula).evaluate(this);
			assertEquals(-1,actual);
	}
	@Test
	public void testReturnArray() {

		ExpressionFactoryImpl factory = new ExpressionFactoryImpl();
		String formula = "split('a,b,c',',')";
		Object actual = factory.create(formula).evaluate();
		assertArrayEquals(new String[]{"a","b","c"}, ( String[])actual);
	}




	@Test
	public void testGlobalFnMap(){
		ExpressionFactoryImpl factory = new ExpressionFactoryImpl();
		HashMap context = new HashMap();
		Object evaluate = factory.create("now()").evaluate(context );
//		ExpressionImpl el = new ExpressionImpl("6*length+length('a')");
//		List<String> vars = el.getVars();
//		for (String var : vars) {
//			System.err.println(var);
//		}

		context.put("unit", factory.create("3").evaluate(context));
		context.put("size",factory.create("5*unit+charAt(\"ab\",1)").evaluate(context));

		context.put("this","hello");
		Map<String, Function> fnCategoryMap = factory.getGlobalFnMap(Locale.CHINESE);
		for (String s : fnCategoryMap.keySet()) {
			System.err.printf("%s, %s \n sigagure:%s\nremark:%s \nexample:%s \n----------\n",s,fnCategoryMap.get(s).getCategory()
					,fnCategoryMap.get(s).getSignature()					,fnCategoryMap.get(s).getRemark()
,fnCategoryMap.get(s).getExample()

			);

		}
		 evaluate = factory.create("this+size").evaluate(context );
		Assert.assertEquals("hello15b",evaluate);
	}


}
