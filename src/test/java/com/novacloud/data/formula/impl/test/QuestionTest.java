package com.novacloud.data.formula.impl.test;

import com.novacloud.data.formula.ExpressionFactory;
import org.junit.Test;
import com.novacloud.data.formula.impl.ExpressionFactoryImpl;
public class QuestionTest {
	final ExpressionFactory ef = ExpressionFactoryImpl.getInstance();

	@Test
	public void testRandom() {
		for (int i = 0; i < 1000; i++) {
			test(r3p(0.1,0));
		}
	}

	private String r3p(double gtis3op,int depth) {
		double r = Math.random();
		if (r > gtis3op * depth) {
			return String.valueOf(rb()) + '?' + r3p(gtis3op, depth + 1) + ':' + r3p(gtis3op, depth + 1);
		} else {
			return "" + (int) (r * 10000);
		}
	}

	private boolean rb() {
		return Math.random() > 0.5;
	}

	private void test(String el) {
		System.out.println("test:" + el);
		Number actual = (Number) ef.create(el).evaluate();

	}

}
