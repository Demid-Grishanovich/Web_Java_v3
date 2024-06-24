package com.example.v3.utility;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyFactory {

    @SuppressWarnings("unchecked")
    public static <T> T createProxy(T target) {
        return (T) Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new DaoInvocationHandler<>(target)
        );
    }

    private static class DaoInvocationHandler<T> implements InvocationHandler {
        private final T target;

        public DaoInvocationHandler(T target) {
            this.target = target;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("Invoking method: " + method.getName());
            // You can add more proxy logic here if needed
            return method.invoke(target, args);
        }
    }
}
