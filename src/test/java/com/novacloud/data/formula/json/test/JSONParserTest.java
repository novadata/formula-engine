package com.novacloud.data.formula.json.test;


import com.novacloud.data.formula.json.JSONDecoder;
import org.junit.Before;
import org.junit.Test;

public class JSONParserTest {

	@Test
	public void testComment(){
		doParser("/* / */{\"a\":1}");
		doParser("{\"a\":1}//111");
	}

	@Test
	public void testObject(){
		doParser("{\"a\":1}");
		doParser("{\'a\':1}//111");
	}

	public void doParser(String json){
		JSONDecoder.decode(json);
	}
}
