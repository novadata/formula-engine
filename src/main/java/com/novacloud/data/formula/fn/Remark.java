package com.novacloud.data.formula.fn;

import java.lang.annotation.*;

/**
 * 用于标识出Method Prototype
 * 
 * @author zhaoxy
 * 
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Remark {
	String value() default "";
}