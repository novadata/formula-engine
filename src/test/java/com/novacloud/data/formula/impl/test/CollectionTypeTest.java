package com.novacloud.data.formula.impl.test;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.novacloud.data.formula.impl.ExpressionImpl;
import com.novacloud.data.formula.impl.ReflectUtil;
import org.junit.Assert;
import org.junit.Test;
import com.novacloud.data.formula.Reference;


public class CollectionTypeTest {
	public ArrayList<? extends Collection<Double>> collectionList = new ArrayList<>();
	public StringList<? extends Integer> stringList2 = new StringList<>();
	public StringList2<Integer,? extends String> stringList3 = new StringList2<>();

//	public Collection<String> stringMap = new ArrayList<String>();
	public StringMap<String, Boolean> stringMap2 = new StringMap<>();

	public static class StringList<T> extends ArrayList<String> implements
			Collection<String> {
		private static final long serialVersionUID = 1L;
	}

	public static class NumberList<T> extends ArrayList<Number> {
		private static final long serialVersionUID = 1L;
	}

	public static class StringList2<K, T> extends StringList<K> {
		private static final long serialVersionUID = 1L;
	}

	public static class StringList3<K3, T, K> extends StringList2<K3, T> {
		private static final long serialVersionUID = 1L;
	}

	public static class StringMap<K, T> extends HashMap<Boolean, String> {
		private static final long serialVersionUID = 1L;
	}


	@Test
	public void testGetListType() throws Exception {
		testField("collectionList",Collection.class);
		testField("stringList2",String.class);
		testField("stringList3",String.class);
		testField("stringMap2", String.class);
	}

	private void testField(String key,Class<?> expectType) throws NoSuchFieldException {
		Field field = this.getClass().getField(key);
		Type gtype = field.getGenericType();
		Assert.assertEquals(expectType, ReflectUtil.getValueType(gtype));
	}

	@Test
	public void testGetMapKeyType() throws SecurityException,
			NoSuchFieldException {
		Field field = this.getClass().getField("stringMap2");
		Type gtype = field.getGenericType();
		Assert.assertEquals(Boolean.class, ReflectUtil.getKeyType(gtype));
	}


	@Test
	public void testMapSetter() throws SecurityException,
			NoSuchFieldException {
		final  Map<String, Integer> data = new HashMap<>();
		Object context = new Object(){
			public Map<String, Integer> getData() {
				return data;
			}
			
		};
		ExpressionImpl el = new ExpressionImpl("data.key1");
		Reference result = el.prepare(context);
		System.out.println(data);
		result.setValue(123);
		System.out.println(data);
		Assert.assertEquals((Object)123, data.get("key1"));
	}
	

}
