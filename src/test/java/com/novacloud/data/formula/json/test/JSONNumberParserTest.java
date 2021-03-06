package com.novacloud.data.formula.json.test;


import com.novacloud.data.formula.impl.ExpressionFactoryImpl;
import org.junit.Assert;
import org.junit.Test;

public class JSONNumberParserTest {

	@Test
	public void testNumber(){
		doTestNumber((123.4+11),"(123.4+11)");
		doTestNumber((123.4+11.12),"(123.4+11.12)");
		doTestNumber((123.4+11.12E2),"(123.4+11.12E2)");
		doTestNumber(0.23,"0.23");
		doTestNumber(223,"2.23E2");
		doTestNumber(1234,"1234.intValue()");
		doTestNumber(0,"0.0");
		doTestNumber(2,"2.23.intValue()");
		doTestNumber(22.3,"2.23E1");
		doTestNumber(0.223,"2.23E-1");
		doTestNumber(1,"1");
		doTestNumber(0,"0");
		doTestNumber(1.23,"1.23");
		doTestNumber(223,"2.23E2");
		doTestNumber(0x23,"0x23");
		doTestNumber(0x23,"0x23.intValue()");
		System.out.println(Double.parseDouble("2.3E4"));
	}

	public void doTestNumber(Object expected,String json){
		Assert.assertEquals(expected, ExpressionFactoryImpl.getInstance().create(json).evaluate());
		Assert.assertEquals(expected, ExpressionFactoryImpl.getInstance().create(json+" ").evaluate());
	}
}
