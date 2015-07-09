package com.novacloud.data.formula;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;

public interface Invocable {

	 Object invoke(Object thiz,Object... args) throws InvocationTargetException, IllegalAccessException, UnsupportedEncodingException;
}