package com.novacloud.data.formula.json.test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import com.novacloud.data.formula.json.JSONEncoder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class JSONEncoderTest {

	@Test
	public void testEncodeObject() throws IOException {
		Assert.assertEquals("{}", JSONEncoder.encode(new Object()));
		StringBuilder out = new StringBuilder();
		new JSONEncoder(null,false,10).encode(new Object(),out);
		assertEquals("{\"class\":\"java.lang.Object\"}", out.toString());
	}

}
