package com.novacloud.data.formula.impl.test;


import com.novacloud.data.formula.Expression;
import com.novacloud.data.formula.Invocable;
import com.novacloud.data.formula.impl.ExpressionFactoryImpl;
import org.junit.Assert;
import org.junit.Test;


public class MemberMethodAddTest {
	final ExpressionFactoryImpl factory = new ExpressionFactoryImpl();

	public Object getStringArray(){
		return new String[]{"1","2"};
	}
	@Test
	public void testAddStaticMethod() throws Exception{
		Expression el = factory.create("stringArray.add2(123)");
		factory.addMethod(Object[].class, "add2",
				new Invocable() {
					public Object invoke(Object thiz, Object... args)  {
						StringBuilder buf = new StringBuilder();
						for(Object o  : (Object[])thiz){
							buf.append(args[0]);
							buf.append(o);
						}
						return buf.toString();
					}
				});
		Assert.assertEquals("12311232", el.evaluate(this));
	}
	
}
