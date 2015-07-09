package com.novacloud.data.formula.impl.test;

import com.novacloud.data.formula.ExpressionFactory;
import com.novacloud.data.formula.impl.ExpressionFactoryImpl;

import org.junit.Assert;
import org.junit.Test;

public class CompareTest {
	final ExpressionFactory ef = ExpressionFactoryImpl.getInstance();
	@Test
	public void test(){
		String el = "NaN==NaN";
		System.out.println(ef.create(el).evaluate());
	}
	
	@Test
	public void testNumberCompare(){
		test("2>1",true);
		test("2>=1",true);
		test("2<1",false);
		test("2<=1",false);
		test("2==1",false);
		

		test("1>2",false);
		test("1>=2",false);
		test("1<2",true);
		test("1<=2",true);
		test("1==2",false);

		test("1>1",false);
		test("1>=1",true);
		test("1<1",false);
		test("1<=1",true);
		test("1==1",true);

		test("NaN==NaN",false);
		test("NaN!=NaN",true);
		test("Infinity>=NaN",false);
		//[1>1
		//,1&lt;2,
		//1>=1,
		//1&lt;=0,
		//1>=NaN,
		//NaN==NaN,Infinity>Infinity,Infinity>NaN
		
	}

	private void test(String el, boolean expected) {
		boolean actual = (Boolean)ef.create(el).evaluate();
		Assert.assertEquals(el, expected, actual);
	}

}
