package com.core.java.reflection;

import com.core.java.entity.Employee;

import java.lang.reflect.*;
import java.util.Collection;
import java.util.HashMap;

/**
 * code copy from below link
 * https://www.jianshu.com/p/28286f460f1e
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





        // Use ReflectionTest to print class
        // cl = Proxy.getProxyClass(null, new Class[]{Comparable.class});
        Class<?> superclass = cl.getSuperclass();
        int modifiers = cl.getModifiers();
        String s = Modifier.toString(modifiers);
        System.out.print(s + " " + cl);

        //print super class
        if (superclass != null && superclass != Object.class) {
            System.out.println(" extends " + superclass.getName());
        }

        System.out.println("{");
        ReflectionTest_1.printConstructors(cl);
        System.out.println();
        ReflectionTest_1.printMethods(cl);
        System.out.println();
        ReflectionTest_1.printFields(cl);
        System.out.println("}");

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
}
