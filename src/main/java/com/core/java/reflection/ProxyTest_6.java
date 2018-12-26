package com.core.java.reflection;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Random;

/**
 * This program demonstrates the use of proxies
 *
 * @date 2018-12-22
 */
public class ProxyTest_6 {

    public static void main(String[] args) {

        Object[] elements = new Object[1000];

        Class<?> proxyClass = Proxy.getProxyClass(null, new Class[]{Comparable.class});
        System.out.println(proxyClass);

        //fill elements with proxies for the integers 1 - 1000
        for (int i = 0;i<elements.length;i++){
            Integer value = i +1;
            InvocationHandler handler = new TraceHandler(value);
            // proxy for value , 生成的实现了一定接口的实例 , 这个实例每调用一个方法都是通过invocationHandler的invoke来调用的
            Object proxy = Proxy.newProxyInstance(null, new Class[]{Comparable.class}, handler);
//            Comparable c = (Comparable)proxy;
//            c.compareTo(15);

//            对象 proxy 的类是 com.sun.proxy.$Proxy0 继承自 java.lang.reflect.Proxy
            System.out.println(new ObjectAnalyzer_3().toString(proxy));
            elements[i] = proxy;
        }

        //construct a random integer
        Integer key = new Random().nextInt(elements.length) + 1;

        //search for the key
        int result = Arrays.binarySearch(elements, key);

        //print match if found
        if(result >= 0) System.out.println(elements[result]);

        boolean equals = elements[result].equals(123);

    }

    /**
     * 如果key在数组中，则返回搜索值的索引；否则返回-1或者”-“(插入点)。插入点是索引键将要插入数组的那一点，即第一个大于该键的元素索引。
     */
    public void binarySearchApiTest(){
        int a[] = new int[] {1, 3, 4, 6, 8, 9};
        int x1 = Arrays.binarySearch(a, 5);
        int x2 = Arrays.binarySearch(a, 4);
        int x3 = Arrays.binarySearch(a, 0);
        int x4 = Arrays.binarySearch(a, 10);
        System.out.println("x1:" + x1 + ", x2:" + x2);
        System.out.println("x3:" + x3 + ", x4:" + x4);
    }
}

/**
 * An invocation handler that prints out the method name and parameters,
 * then invokes the original method
 * 所有动态代理类的方法调用，都会交由InvocationHandler接口实现类里的invoke()方法去处理。这是动态代理的关键所在。
 */
class TraceHandler implements InvocationHandler {

    private Object target;

    /**
     * Constructs a TraceHandler
     * @param t the implicit parameter of the method call
     */
    public TraceHandler(Object t) {
        this.target = t;
    }

    /**
     * Processes a method invocation on a proxy instance and returns the result.
     * This method will be invoked on an invocation handler when a method is invoked on a proxy instance that it is associated with.
     * 代理实例调用一个method, 然后返回result
     * ... 读不懂了
     *
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // print implicit argument
        System.out.print(target);
        //print method name
        System.out.print("." + method.getName() + "(");
        //print explicit arguments
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                System.out.print(args[i]);
                if (i < args.length - 1) System.out.print(",");
            }
        }
        System.out.println(")");

        //invoke actual method
        return method.invoke(target, args);
    }
}
