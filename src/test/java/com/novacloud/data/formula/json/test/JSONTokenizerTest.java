package com.novacloud.data.formula.json.test;


import com.novacloud.data.formula.impl.ExpressionFactoryImpl;
import org.junit.Test;

public class JSONTokenizerTest {

	@Test
	public void testFloat(){
		doTest("67E+12-34");
		doTest("067");
		doTest("0x67E12");
		doTest("67E+1");
		doTest("67E-1");
		doTest("67E+12");
		doTest("67E-12");
		//new JSONTokenizer("0xfff2ed /19.5e-2+ 2 +19.5E-2").parse();
		doTest("0xfff2ed /19.5e-2+ 2 +19.5E-2");
		doTest("(19E2)");
		doTest("(0xCCFF%2)+(0676/(19.5E-2)-(19.5E-2))*(0676/(19.5E-2)-(19.5E-2))");
		System.out.println(10);
		doTest("0676/(19.5E-2)-(19.5E-2)");
		
	}

	private void doTest(String el) {
		Float actual = ((Number) ExpressionFactoryImpl.getInstance().create(el).evaluate("")).floatValue();
		System.out.println("acture"+actual);

	}

	private int toInt(Float expected) {
		return new Float(expected*1000).intValue();
	}
}
