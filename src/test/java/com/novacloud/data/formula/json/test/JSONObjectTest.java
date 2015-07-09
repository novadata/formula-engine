package com.novacloud.data.formula.json.test;

import java.util.List;

import org.junit.Test;
import com.novacloud.data.formula.json.JSONDecoder;


public class JSONObjectTest {

	public static class JavaBean{
	  String text = "a";
	  List<Long> list;
	  int i;
	  JavaBean inner;
	}
	public static class Wrapper{
		List<JavaBean> values;
	}
	@Test
	public void test() {
		JSONDecoder d = new JSONDecoder(false);
	
		Wrapper w = d.decode("{values:[{text:'b',list:[1,2,3],i:3,inner:{i:2}}]}", Wrapper.class);
		System.out.println(w);
System.out.println(w.values.get(0));
//		JavaBean o = d.decode("{text:'b',list:[1,2,3],i:3,inner:{i:2}}", JavaBean.class);
//		System.out.println(o.text);
//		System.out.println(o.list);
//		System.out.println(o.list.get(0).getClass());
//		System.out.println(o.inner.i);
//		System.out.println(JavaBean.class.isMemberClass());
//
//		System.out.println(JSONObjectTest.class.isMemberClass());
//
//		Object o2 = d.decode("[{text:'b',list:[1,2,3],i:3,inner:{i:2}}]", List.class);
//		System.out.println(o2.getClass());
		
	}

	
}
