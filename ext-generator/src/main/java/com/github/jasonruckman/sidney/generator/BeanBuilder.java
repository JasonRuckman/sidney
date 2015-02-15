package com.github.jasonruckman.sidney.generator;

import com.github.jasonruckman.sidney.core.Accessors;
import com.github.jasonruckman.sidney.core.Fields;
import com.github.jasonruckman.sidney.core.serde.DefaultInstanceFactory;
import com.github.jasonruckman.sidney.core.serde.InstanceFactory;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeanUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

public class BeanBuilder<T> {
  private static final Map<Class, Object> defaultReturnObjects = new HashMap<>();
  private final List<FieldAndStrategy> strategies = new ArrayList<>();
  private final InstanceFactory<T> factory;
  private final Stack<Method> methodStack;

  static {
    defaultReturnObjects.put(boolean.class, true);
    defaultReturnObjects.put(byte.class, 0);
    defaultReturnObjects.put(short.class, 0);
    defaultReturnObjects.put(char.class, 0);
    defaultReturnObjects.put(int.class, 0);
    defaultReturnObjects.put(long.class, 0);
    defaultReturnObjects.put(float.class, 0);
    defaultReturnObjects.put(double.class, 0);
  }

  private T stub;

  private BeanBuilder(T stub, InstanceFactory<T> factory, Stack<Method> methodStack) {
    this.stub = stub;
    this.factory = factory;
    this.methodStack = methodStack;
  }

  public static <R> BeanBuilder<R> stub(final Class<R> type) {
    InstanceFactory<R> fact = new DefaultInstanceFactory<>(type);
    R stub = fact.newInstance();
    ProxyFactory factory = new ProxyFactory(stub);
    final Stack<Method> m = new Stack<>();
    factory.addAdvice(new MethodInterceptor() {
      @Override
      public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        PropertyDescriptor descriptor = BeanUtils.findPropertyForMethod(methodInvocation.getMethod());
        if (descriptor == null) {
          throw new IllegalStateException(String.format("Method %s is not a getter or setter", methodInvocation.getMethod().getName()));
        }
        m.push(methodInvocation.getMethod());
        return defaultReturnObjects.get(methodInvocation.getMethod().getReturnType());
      }
    });
    return new BeanBuilder<>((R) factory.getProxy(), fact, m);
  }

  public T getStub() {
    return stub;
  }

  public <R> BeanBuilder<T> nullableField(R getter, Generator<R> generator, Nulls.NullableGenerator<R> nullableGenerator) {
    nullableGenerator.wrap(generator);
    return addStrategy(nullableGenerator);
  }

  public <R> BeanBuilder<T> field(R getter, final Generator<R> generator) {
    strategies.add(new FieldAndStrategy(accessor(methodStack.pop()),
        new Generator<R>() {
          @Override
          public R next() {
            return generator.next();
          }
        }));
    return this;
  }

  private BeanBuilder<T> addStrategy(Generator strategy) {
    strategies.add(new FieldAndStrategy(accessor(methodStack.pop()), strategy));
    return this;
  }

  public Generator<T> build() {
    return new Generator<T>() {
      @Override
      public T next() {
        T newStub = factory.newInstance();
        for (FieldAndStrategy fieldAndStrategy : strategies) {
          fieldAndStrategy.getAccessor().set(newStub, fieldAndStrategy.getStrategy().next());
        }
        return newStub;
      }
    };
  }

  private Accessors.FieldAccessor accessor(Method method) {
    PropertyDescriptor descriptor = BeanUtils.findPropertyForMethod(method);
    Class<?> declaringClass = method.getDeclaringClass();
    Field field = Fields.getFieldByName(declaringClass, descriptor.getName());
    return Accessors.newAccessor(field);
  }

  private static class FieldAndStrategy {
    private final Accessors.FieldAccessor accessor;
    private final Generator strategy;

    private FieldAndStrategy(Accessors.FieldAccessor accessor, Generator strategy) {
      this.accessor = accessor;
      this.strategy = strategy;
    }

    public Accessors.FieldAccessor getAccessor() {
      return accessor;
    }

    public Generator getStrategy() {
      return strategy;
    }
  }
}