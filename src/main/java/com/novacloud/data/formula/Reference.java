package com.novacloud.data.formula;



public interface Reference{
	/**
	 * 根据传入的变量上下文,设置表达式对应的属性值
	 * @see Reference#setValue(Object)
	 * @return
	 */
	 Object setValue(Object value);
	 Object getValue();
	 Reference next(Object key);
	 Class<?> getType();
	 Object getBase();
	 Object getName();
}