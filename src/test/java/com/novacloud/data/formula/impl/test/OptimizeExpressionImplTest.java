package com.novacloud.data.formula.impl.test;

import com.novacloud.data.formula.Expression;
import com.novacloud.data.formula.ExpressionFactory;
import com.novacloud.data.formula.fn.ECMA262Impl;
import com.novacloud.data.formula.impl.OperationStrategyImpl;
import com.novacloud.data.formula.impl.OptimizeExpressionImpl;
import com.novacloud.data.formula.json.JSONDecoder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import com.novacloud.data.formula.ExpressionToken;
import com.novacloud.data.formula.impl.ExpressionFactoryImpl;

public class OptimizeExpressionImplTest {

	final OperationStrategyImpl strategy = new OperationStrategyImpl(false);
	{
		ECMA262Impl.setup(new ExpressionFactoryImpl(strategy));
	}

	@Test
	public void testCreate() {
		doTest("變量獲取","1","o","{\"o\":\"1\"}");
		doTest("屬性獲取","1","o.a","{\"o\":{\"a\":\"1\"}}");
		doTest("多重屬性獲取","2","o.a.b","{\"o\":{\"a\":{\"b\":\"2\"}}}");
	}

	private void doTest(String msg,Object expected,String el,String context) {
		ExpressionFactory ef = ExpressionFactoryImpl.getInstance();
		Expression exp = OptimizeExpressionImpl.create((ExpressionToken) ef.parse(el), ef, strategy);
		Assert.assertTrue("不需是有效的優化表達式/"+msg,exp instanceof OptimizeExpressionImpl);
		Assert.assertEquals(msg,expected, exp.evaluate(JSONDecoder.decode(context)));
	}

}
