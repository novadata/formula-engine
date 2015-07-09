package com.novacloud.data.formula.json.test;

import com.novacloud.data.formula.json.JSONEncoder;
import org.junit.Test;
import com.novacloud.data.formula.json.JSONDecoder;

public class JSONEnumTest {
	public final A e = A.b;
	public static enum A{
		a,b
	}
	
	@Test
	public void testEnum(){
		String source = JSONEncoder.encode(this);
		System.out.println(source);
		JSONDecoder d = new JSONDecoder(false);
		JSONEnumTest e = d.decode(source, JSONEnumTest.class);
		System.out.println(e.e);
		
		source = "{\"e\":\"a\"}";
		 e = d.decode(source, JSONEnumTest.class);
		System.out.println(e.e);
		source = "{\"e\":2}";
		 e = d.decode(source, JSONEnumTest.class);
		System.out.println(e.e);
	}

}
