package com.novacloud.data.formula.json;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.util.List;
public class JSONDecoder {

	public interface TypeTransformer<T> {
		 T create(Object source);

	}
	private static final Logger log = LoggerFactory.getLogger(JSONDecoder.class);
	private static final JSONDecoder decoder = new JSONDecoder(false);
	private static final JSONTransformer transformer = new JSONTransformer();
	private boolean strict = false;

	public JSONDecoder(boolean strict) {
		this.strict = strict;
	}

	@SuppressWarnings("unchecked")
	public static <T> T decode(String value) {
		return (T) decoder.decode(value, null);
	}

	public static TypeTransformer<?> addTransformer(TypeTransformer<?> factory){
		return transformer.addFactory(factory);
	}
	@SuppressWarnings("unchecked")
	public <T> T decode(String value, Type type) {
		try{
			Object result = new JSONTokenizer(value, strict).parse();
			if (type != null && type != Object.class) {
				result = transform(result, type);
			}
			return (T) result;
		}catch (RuntimeException e) {
			log.warn("json error:"+value,e);
			throw e;
		}
	}
	@SuppressWarnings("unchecked")
	public <T> List<T> decodeList(String value, Class<T> type) {
		try{
			List<Object> result = (List<Object>) new JSONTokenizer(value, strict).parse();
			if (type != null && type != Object.class) {
				for (int i = result.size()-1; i>=0; i--) {
					result.set(i, transform(result.get(i),type));
				}
			}
			return (List<T>) result;
		}catch (RuntimeException e) {
			log.warn("json error:"+value,e);
			throw e;
		}
	}
	@SuppressWarnings("unchecked")
	public <T> T transform(Object source,Type type){
		return (T)transformer.transform(source, type);
	}
}
