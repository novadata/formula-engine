package com.novacloud.data.formula.impl.test;

import static org.junit.Assert.assertEquals;

import java.util.LinkedHashMap;
import java.util.Map;

import com.novacloud.data.formula.Expression;
import com.novacloud.data.formula.ExpressionFactory;
import com.novacloud.data.formula.Invocable;
import com.novacloud.data.formula.impl.ExpressionFactoryImpl;
import org.junit.Test;

public class ECMA262FunctionTest {
	final ExpressionFactory factory = new ExpressionFactoryImpl();
	final Invocable encodeURI = (Invocable)factory.create("encodeURI").evaluate("");
	final Invocable decodeURI = (Invocable)factory.create("decodeURI").evaluate("");
	final Invocable encodeURIComponent = (Invocable)factory.create("encodeURIComponent").evaluate("");
	final Invocable decodeURIComponent = (Invocable)factory.create("decodeURIComponent").evaluate("");


	@Test
	public void testEncodeURLComponentEL() throws Exception {
		Expression el = factory.create("encodeURIComponent('金大为')");
		String encoded = (String) el.evaluate();
		assertEquals("%E9%87%91%E5%A4%A7%E4%B8%BA", encoded);

		el = factory
				.create("decodeURIComponent('a%E9%87%91%E5%A4%A7%E4%B8%BA')");
		assertEquals("a金大为", el.evaluate());
	}

	@Test
	public void testEncodeURLComponent() throws Exception {
		Map<String, String> encodeMap = new LinkedHashMap<>();
		// encodeMap.put("+", "%2B");
		encodeMap.put("金+大+为", "%E9%87%91%2B%E5%A4%A7%2B%E4%B8%BA");
		encodeMap.put("http://www.xidea.org/a b c 金 大 为",
				"http%3A%2F%2Fwww.xidea.org%2Fa%20b%20c%20%E9%87%91%20%E5%A4%A7%20%E4%B8%BA"
						.replace("%20", "+"));
		for (String key : encodeMap.keySet()) {
			String value = encodeMap.get(key);
			assertEquals(value, encodeURIComponent.invoke(null, key));
			assertEquals(key, decodeURIComponent.invoke(null, value));
		}
		// System.out.println(encodeURIComponent.invoke("1 2"));
		// System.out.println(decodeURIComponent.invoke("1+2"));
	}

	@Test
	public void testEncodeURL() throws Exception {
		Map<String, String> encodeMap = new LinkedHashMap<>();
		// encodeMap.put("+", "%2B");
		encodeMap.put("http://www.xidea.org/金+大+为",
				"http://www.xidea.org/%E9%87%91+%E5%A4%A7+%E4%B8%BA");
		encodeMap.put("http://www.xidea.org/a?金=大&为=2",
				"http://www.xidea.org/a?%E9%87%91=%E5%A4%A7&%E4%B8%BA=2"
						.replace("%20", "+"));
		for (String key : encodeMap.keySet()) {
			String value = encodeMap.get(key);
			assertEquals(value, encodeURI.invoke(null, key));
			assertEquals(key, decodeURI.invoke(null, value));
		}
	}

	@Test
	public void testNumber() throws Exception {
		// Expression el = factory.createEL("'金大为'.substring(1,2)");
		Expression el = factory.create("parseInt(1.23)");
		assertEquals(1, el.evaluate());
	}

	@Test
	public void testIsNaN() throws Exception {
		// Expression el = factory.createEL("'金大为'.substring(1,2)");
		Expression el = factory.create("isNaN(0/0)");
		assertEquals(true, el.evaluate());
	}

	@Test
	public void testFP() throws Exception {
		// Expression el = factory.createEL("'金大为'.substring(1,2)");
		Expression el = factory.create("(true?isNaN:isFinite)(0/0)");
		assertEquals(true, el.evaluate());
		el = factory.create("(false?isNaN:isFinite)(0/0)");
		assertEquals(false, el.evaluate());
	}

}
