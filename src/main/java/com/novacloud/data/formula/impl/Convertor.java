package com.novacloud.data.formula.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public interface Convertor<T> {
	 Convertor<Object> DEFAULT = new DefaultConvertor();
	 Map<Class<?>, Convertor<?>> DEFAULT_MAP = DefaultConvertor.toMap();

	 T getValue(String value, Class<? extends T> expectedType,
			Object context, String key);
}

class DefaultConvertor implements Convertor<Object> {
	private static final Logger log = LoggerFactory.getLogger(DefaultConvertor.class);

	static Map<Class<?>, Convertor<?>> toMap() {
		Class<?>[] SUPPORTS = { File.class, URL.class, URI.class, Long.TYPE,
				Long.class, Integer.TYPE, Integer.class, Double.TYPE,
				Double.class, Short.TYPE, Short.class, Byte.TYPE, Byte.class,
				Boolean.TYPE, Boolean.class, Character.TYPE, Character.class,
				String.class, Object.class };
		HashMap<Class<?>, Convertor<?>> map = new HashMap<>();
		for (Class<?> type : SUPPORTS) {
			map.put(type, Convertor.DEFAULT);
		}
		return Collections.unmodifiableMap(map);
	}

	public Object getValue(String value, Class<?> expectedType,
			Object context, String key) {
		boolean primitive = expectedType.isPrimitive();
		Class<?> clazz = primitive ? ReflectUtil
				.toWrapper(expectedType) : expectedType;
		try {
			if (value == null) {
				if (primitive) {
					if (Number.class.isAssignableFrom(clazz)) {
						value = "0";
					} else if (clazz == Boolean.class) {
						return false;
					} else if (clazz == Character.class) {
						return (char) 0;
					} else {
						return "";
					}
				} else {
					return null;
				}
			}
			// value != null
			// String|Object
			if (expectedType.isInstance(value)) {
				return value;
			}
			Objects.requireNonNull(value);
			try {// Number,Boolean,File,URI,URL...
				if (clazz == Boolean.class) {
					if(value.length() ==0 ||value.equalsIgnoreCase("false")||value.equals("0")||value.equals("0.0")){
						return false;
					}else{
						return true;
					}
				}
				Constructor<?> constructor = clazz
						.getConstructor(String.class);
				try {
					return constructor.newInstance(value);
				} catch (InvocationTargetException e) {
					if (primitive && e.getTargetException() instanceof NumberFormatException) {
						return getValue("0", expectedType, context, key);
					}
				}
			} catch (NoSuchMethodException e) {
				if (Character.class == clazz) {
					if ( value.length()  == 0) {
						if (primitive) {
							return (char) 0;
						}
					} else {
						return value.charAt(0);
					}
				}
				Class<?> clazz2 = Class.forName(value);
				if (expectedType.isAssignableFrom(clazz2)) {
					return clazz2.newInstance();
				}
			}
		} catch (Exception e) {
			log.warn("",e);
		}
		return null;
	}

}