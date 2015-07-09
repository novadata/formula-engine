package com.novacloud.data.formula.impl.test;

import com.novacloud.data.formula.json.JSONEncoder;
import org.junit.Assert;
import org.junit.Test;
import com.novacloud.data.formula.impl.ExpressionFactoryImpl;

public class ELParseTest {
	private final ExpressionFactoryImpl ef = new ExpressionFactoryImpl();
	@Test
	public void testMethod(){
		Object el = ef.parse("object.test(123)");
		el = JSONEncoder.encode(el);
		Assert.assertEquals("[97,[96,[-2,\"object\"],[-1,\"test\"]],[64,[-3],[-1,123]]]", el);
	}

	@Test
	public void test3op(){
		System.out.println(JSONEncoder.encode( ef.parse("0?0?5:7:3")));
		System.out.println(JSONEncoder.encode( ef.parse("0?(0?5:7):3")));
	}

}
