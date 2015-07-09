package com.novacloud.data.formula.impl;

import com.novacloud.data.formula.*;
import com.novacloud.data.formula.fn.ECMA262Impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

public class ExpressionFactoryImpl implements ExpressionFactory {
    private static volatile ExpressionFactoryImpl expressionFactory;
    private static final ValueStackImpl EMPTY_VS = new ValueStackImpl(Collections
            .emptyMap());
    protected final OperationStrategy strategy;
    protected Map<String, Integer> aliseMap = new HashMap<>();
    private int inc = 1;



    private boolean optimize = true;
    private static final Map<String, Invocable> cachedInvocableMap = new HashMap<>();

    public void setOptimize(boolean optimize) {
        this.optimize = optimize;
    }
    public static ExpressionFactoryImpl getInstance() {
        if (expressionFactory == null) {
            synchronized (ExpressionFactoryImpl.class) {
                if (expressionFactory == null) {
                    expressionFactory = new ExpressionFactoryImpl();
                    expressionFactory.aliseMap = Collections.emptyMap();
                    expressionFactory.getOperationStrategyImpl().customizable = false;
                }
            }
        }
        return expressionFactory;
    }

    public ExpressionFactoryImpl(OperationStrategy strategy) {
        this.strategy = strategy;
    }

    public OperationStrategy getStrategy() {
        return getOperationStrategyImpl();
    }

    public ExpressionFactoryImpl() {
        this.strategy = new OperationStrategyImpl(true);
        ECMA262Impl.setup(this);
    }

    public boolean isOptimize() {
        return optimize;
    }
    private OperationStrategyImpl getOperationStrategyImpl() {
        if (strategy instanceof OperationStrategyImpl) {
            return (OperationStrategyImpl) strategy;
        }
        throw new UnsupportedOperationException();
    }

    public void addVar(String var, Object value) {
        getOperationStrategyImpl().addVar(var, value);
    }
    public void addGlobalFn(String var, Method value,Category category) {
        getOperationStrategyImpl().addGlobalFn(var, value, category);
    }
    public void addMethod(Class<?> clazz, String name,
                          Invocable invocable) {
        getOperationStrategyImpl().addMethod(clazz, name, invocable);
    }
    public Map<String, Function> getGlobalFnMap(){
        return getOperationStrategyImpl().getGlobalFnMap();
    }
    public Map<String, Function> getGlobalFnMap(Locale locale){
        return getOperationStrategyImpl().getGlobalFnMap(locale);
    }
    private Invocable toInvocable(Object impl) {
        if (impl instanceof Method) {
            Method method = (Method) impl;
            if (Modifier.isPublic(method.getModifiers())
                    && Modifier.isStatic(method.getModifiers())) {
                impl = createProxy(method);
            }
        }
        if (impl instanceof Invocable) {
            return (Invocable) impl;
        }
        throw new IllegalArgumentException(
                "支持public static 格式的函数或者Invocable 对象");
    }

    public void addOperator(int sampleToken, String name, Object impl) {
        if (impl == null) {
            this.aliseMap.put(name, sampleToken);
        } else {
            sampleToken += (inc++ << ExpressionToken.POS_INC);
            this.aliseMap.put(name, sampleToken);
            getOperationStrategyImpl().addVar(sampleToken, toInvocable(impl));
        }
    }

    @SuppressWarnings("unchecked")
    public Object parse(String el) {
        ExpressionParser ep = new ExpressionParser(el);
        ep.setAliasMap(aliseMap);
        ExpressionToken tokens = ep.parseEL();
        if (isOptimize()) {
            tokens = ((TokenImpl) tokens).optimize(strategy, Collections.EMPTY_MAP);
        }
        return tokens;
    }

    @SuppressWarnings("unchecked")
    public Expression create(Object elo) {
        if (elo instanceof String) {
            return create(parse((String) elo));
        } else {
            ExpressionToken el;
            if (elo instanceof ExpressionToken) {
                el = (ExpressionToken) elo;
            } else {
                el = TokenImpl.toToken((List<Object>) elo);
            }
            return createExpression(el);

        }
    }

    protected Expression createExpression(ExpressionToken el) {
        if (isOptimize()) {
            Expression ressult = OptimizeExpressionImpl.create(el, this,
                    strategy);
            if (ressult != null) {
                return ressult;
            }
        }
        return new ExpressionImpl(el, this, strategy);
    }

    public static Invocable createProxy(final Method... methods) {
        for (Method method : methods) {
            try {
                method.setAccessible(true);
            } catch (Exception ignored) {
            }
        }
        MethodInvocable inv = new MethodInvocable();
        inv.methods = methods;
        return inv;
    }

    static Invocable getInvocable(final Class<?> clazz,
                                  final String name, int length) {
        String key = clazz.getName() + '.' + length + name.toUpperCase();
        Invocable result = cachedInvocableMap.get(key);
        if (result == null && !cachedInvocableMap.containsKey(key)) {
            ArrayList<Method> methods = new ArrayList<>();
            for (Method method : clazz.getMethods()) {
                if (method.getName().equalsIgnoreCase(name)
                        && (length < 0 || method.getParameterTypes().length == length)) {
                    methods.add(method);
                }
            }
            if (methods.size() > 0) {
                result = createProxy(methods
                        .toArray(new Method[methods.size()]));
                cachedInvocableMap.put(key, result);
            }
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    public <T> T wrapAsContext(Object context) {
        Map<String, Object> valueStack;
        if (context instanceof Map) {
            valueStack = (Map<String, Object>) context;
        } else if (context == null) {
            valueStack = EMPTY_VS;
        } else {
            valueStack = new ValueStackImpl(context);
        }
        return (T) valueStack;
    }
}

class MethodInvocable implements Invocable {
    Method[] methods;

    /**
     *
     * @param thiz
     * @param args 不支持java的可变参数类型或者数组类型参数
     * @return
     * @throws Exception
     */
    public Object invoke(Object thiz, Object... args) throws InvocationTargetException, IllegalAccessException {
        nextMethod:
        for (Method method : methods) {
            Class<?> clazzs[] = method.getParameterTypes();
            if (args.length < clazzs.length) {//to support  option  arg
                args = Arrays.copyOf(args, clazzs.length);
            }
            for (int i = 0; i < clazzs.length; i++) {
                Class<?> type = ReflectUtil.toWrapper(clazzs[i]);
                Object value = args[i];
                value = ECMA262Impl.ToValue(value, type);
                args[i] = value;
                if (value != null) {
                    if (!type.isInstance(value)) {
                        continue nextMethod;
                    }
                }
            }
            return method.invoke(thiz, args);
        }
        return null;
    }
}
