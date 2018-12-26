package com.core.java.reflection;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * study proxy and code copy from below link
 * https://www.jianshu.com/p/28286f460f1e
 *
 * 在这个博客内 ,  testNull() 方法内空指针的原因我做了修改 , 和原博客不同
 * 一
 * proxy1.size();
 * 报空指针的原因是这个方法的返回值是Int型 , 而invocationHandler的invoke()返回的是null;
 * proxy1.toString();
 * 这种返回值是引用类型的方法 , 就不会报错 .
 * 二
 * 模拟JVM生成的代理类Proxy1 , 它的所有的方法应该调用invocationHandler的invoke()方法 ,
 * 但是传递的参数不应该是this , 这样是否会递归调用自己 . 我认为invocationHandler的invoke()方法调用的都是被代理的对象的method .
 *
 */
public class ProxyTest_7 {

    public static void main(String[] args) {
        //动态生成代理类
        //ClassLoader:  每一个Class就必须有一个类加载器加载进来的，比如每个人都有一个妈妈。既然需要JVM动态生成Java类，所以要为动态生成类的字节码指定类加载器
        //Class Interfaces: 动态生成的字节码实现了哪些接口
        Class cl = Proxy.getProxyClass(Collection.class.getClassLoader(), Collection.class);

        //打印代理类的构造方法
        getConstructors(cl);
        // com.sun.proxy.$Proxy0(java.lang.reflect.InvocationHandler )  --> 仅有一个构造方法

        //打印所有Method
        getMethods(cl);

        try {
            testNull();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //
        getMyInstance();

        //利用代理模拟AOP
        List target = new ArrayList<>();
        List proxyObject = (List) getProxy(target, new MyAdvice());
        boolean abc = proxyObject.add("abc");
        System.out.println(proxyObject.size());


        // Use ReflectionTest to print class
        // cl = Proxy.getProxyClass(null, new Class[]{Comparable.class});
        // ReflectionTest_1.printClassDetail(cl);

        /**
         * public final class com.sun.proxy.$Proxy1 extends java.lang.reflect.Proxy
         * {
         *     public com.sun.proxy.$Proxy1(java.lang.reflect.InvocationHandler);
         *
         *     public final boolean equals(java.lang.Object);
         *     public final java.lang.String toString();
         *     public final int hashCode();
         *     public final int compareTo(java.lang.Object);
         *
         * private static java.lang.reflect.Method m1;
         * private static java.lang.reflect.Method m3;
         * private static java.lang.reflect.Method m2;
         * private static java.lang.reflect.Method m0;
         * }
         */
    }


    /**
     * 打印生成类的构造函数
     */
    public static void getConstructors(Class classInstance) {

        //获取这个代理类的构造方法
        Constructor[] constructors = classInstance.getConstructors();

        System.out.println("---------------------beginning print Constructors-----------------");
        //遍历构造方法
        printExecutable(constructors);
        System.out.println("---------------------finished print Constructors-----------------");
    }

    /**
     * 打印类的每个方法
     */
    public static void getMethods(Class classInstance){

        //获取这个代理类声明的的方法
        Method[] methods = classInstance.getDeclaredMethods();
        System.out.println("---------------------beginning print Methods-----------------");
        //遍历构造方法
        printExecutable(methods);
        System.out.println("---------------------finished print Methods-----------------");
    }

    public static void printExecutable(Object o){
        Executable[] os = (Executable[])o;
        for (Executable e : os){
            //获取每个名称
            String name = e.getName();
            StringBuilder sb = new StringBuilder(name);
            sb.append("(");
            //获取每个构造方法的参数类型
            Class[] clazzTypes = e.getParameterTypes();
            for (Class clazzType : clazzTypes) {
                sb.append(clazzType.getName()).append(", ");
            }
            if(clazzTypes != null && clazzTypes.length != 0){
                sb.deleteCharAt(sb.length() - 2);
            }
            sb.append(")");
            System.out.println(sb.toString());
        }
    }

    /**
     * 创建动态类的实例对象及调用其方法
     * @throws Exception
     */
    public static void testNull() throws Exception{
        System.out.println("---------------------test invoke return Null-----------------");
        //通过打印构造方法，得到的动态代理类有一个InvocationHandler参数
        Class clazzProxy1 = Proxy.getProxyClass(Collection.class.getClassLoader(), Collection.class);
        //获取Constructor类
        Constructor constructor = clazzProxy1.getConstructor(InvocationHandler.class);
        //传递InvocationHandler参数,手动实现InvocationHander接口
        //返回的结果是Collection接口的对象
        Collection proxy1 = (Collection) constructor.newInstance(new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                return null;
            }
        });
        /**
         * 通过打印生成的对象发现结果为null 有两种种可能：
         * 第一种可能是对象为null
         * 第二种可能是对象的toString()方法为null
         */
        System.out.println(proxy1);
        //对象没有报空指针异常，所以对象的toString为null,可以得出结论，代理类对象的toString()方法被代理类重写了。
        System.out.println(proxy1.toString());
        //调用一个方法，运行成功，所以proxy1不为null
        proxy1.clear();

        //调用size方法出错，为什么出错呢？size方法返回值是int型 , null赋值给int会空指针
        proxy1.size();
    }

    public static void getMyInstance(){
        System.out.println("---------------------test normal proxy-----------------");
        //Proxy.newInstance方法直接创建出代理对象
        Collection proxy1 = (Collection) Proxy.newProxyInstance(
                Collection.class.getClassLoader(),
                new Class[]{Collection.class},
                new InvocationHandler() {
                    //方法外部指定目标
                    List target = new ArrayList<>();
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        //在调用代码之前加入系统功能代码
                        long startTime = System.currentTimeMillis();
                        //睡眠1秒钟
                        Thread.sleep(1000);
                        //目标方法
                        Object retVal = method.invoke(target, args);
                        //在调用代码之后加入系统功能代码
                        long endTime = System.currentTimeMillis();
                        System.out.println( method.getName() + "方法花费了:" + (endTime - startTime) + "毫秒");
                        return retVal;
                    }
                });

        proxy1.add("a");
        proxy1.add("b");
        proxy1.add("c");
        //3
        System.out.println(proxy1.size());
    }

    /**
     * 使用传递参数的方式灵活创建代理对象
     * @param target:目标对象
     * @param advice:系统功能对象
     * @return Proxy Object:代理对象
     */
    public static Object getProxy(final Object target,final Advice advice){

        //Proxy.newInstance方法直接创建出代理对象
        Object proxy3 = Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new InvocationHandler() {

                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                        advice.beforeMethod(method);

                        Object retVal = method.invoke(target, args);

                        advice.afterMethod(method);

                        return retVal;
                    }
                });
        return proxy3;
    }


}

interface Advice {

    void beforeMethod(Method method);

    void afterMethod(Method method);

}

class MyAdvice implements Advice {

    @Override
    public void beforeMethod(Method method) {
        System.out.println("在目标方法之   前   调用！");
    }

    @Override
    public void afterMethod(Method method) {
        System.out.println("在目标方法之   后   调用！");
    }

}

