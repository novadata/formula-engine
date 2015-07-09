package com.novacloud.data.formula.impl;

import com.novacloud.data.formula.Function;
import org.junit.Test;

import java.util.Locale;
import java.util.Map;

public class OperationStrategyImplTest {

    @Test
    public void testGetGlobalFnMap() throws Exception {

    }

    @Test
    public void testGetGlobalFnMapLocale() throws Exception {
        ExpressionFactoryImpl factory = new ExpressionFactoryImpl();
        Map<String, Function> globalFnMap = factory.getGlobalFnMap(Locale.forLanguageTag("zh"));

    }
}