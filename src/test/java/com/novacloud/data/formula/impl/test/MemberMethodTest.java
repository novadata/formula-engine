package com.novacloud.data.formula.impl.test;

import static org.junit.Assert.assertEquals;

import com.novacloud.data.formula.Expression;
import com.novacloud.data.formula.ExpressionFactory;
import org.junit.Test;
import com.novacloud.data.formula.impl.ExpressionFactoryImpl;


public class MemberMethodTest {
	final ExpressionFactory factory = new ExpressionFactoryImpl();

	@Test
	public void testEncodeURLComponentEL() throws Exception{
		System.out.println( factory.create("(123.4.intValue()+11)").evaluate());
		Expression el = factory.create("(1234).intValue() == 1234");
		Expression el2 = factory.create("(1234.intValue()) == 1234");
		assertEquals(true, el.evaluate());
		assertEquals(true, el2.evaluate());
	}
	
}
