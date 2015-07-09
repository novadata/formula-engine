package com.novacloud.data.formula.impl.test;

import static org.junit.Assert.assertEquals;

import com.novacloud.data.formula.fn.ECMA262Impl;
import org.junit.Assert;
import org.junit.Test;

public class NumberArithmeticTest {
	@Test
	public void testToNumber() {
		Assert.assertEquals(17, ECMA262Impl.ToNumber("0x11"));
		assertEquals(17, ECMA262Impl.ToNumber("0X11"));
		assertEquals(11, ECMA262Impl.ToNumber("011"));
		assertEquals(-11, ECMA262Impl.ToNumber("-011"));
		assertEquals(-0, ECMA262Impl.ToNumber("-0"));
		assertEquals(-0, ECMA262Impl.ToNumber("0"));
		assertEquals(11, ECMA262Impl.ToNumber("11"));
		assertEquals(10, ECMA262Impl.ToNumber("1E1"));
		assertEquals(10, ECMA262Impl.ToNumber("1e1"));
		assertEquals(1.1, ECMA262Impl.ToNumber("1.1"));
		assertEquals(10, ECMA262Impl.ToNumber("+1e1"));
		assertEquals(-1.1, ECMA262Impl.ToNumber("-1.1"));
		assertEquals(1.1E1, ECMA262Impl.ToNumber("1.1E1"));
		assertEquals(1.1e-1, ECMA262Impl.ToNumber("1.1e-1"));
		assertEquals(1.1E1, ECMA262Impl.ToNumber("+1.1E1"));
		assertEquals(-1.1e-1, ECMA262Impl.ToNumber("-1.1e-1"));
	}

}
