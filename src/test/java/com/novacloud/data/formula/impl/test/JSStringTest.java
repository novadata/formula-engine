package com.novacloud.data.formula.impl.test;

import static org.junit.Assert.assertEquals;

import com.novacloud.data.formula.Expression;
import com.novacloud.data.formula.ExpressionFactory;
import com.novacloud.data.formula.impl.ExpressionFactoryImpl;
import org.junit.Test;


public class JSStringTest {
	final ExpressionFactory factory = new ExpressionFactoryImpl();
	@Test
	public void testString() throws Exception{
		testELValue("\"abcdefg\".indexOf('g')",6);
		testELValue("\"abcdefg\".regexReplace(\"a\", \"1\")","1bcdefg");
		testELValue("'abcdefg'.charAt(1)",'b');
	}
	public void testELValue(String exp,Object value) throws Exception{
		Expression el = factory.create(exp);
		assertEquals(value, el.evaluate());
	}
	
}
