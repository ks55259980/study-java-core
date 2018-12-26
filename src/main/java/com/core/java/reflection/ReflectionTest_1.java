package com.core.java.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Scanner;

/**
 * This program uses reflection to print all features of a class
 *
 * @author cpt copy from java core
 * @version 1.1 2018-12-15
 */
public class ReflectionTest_1 {

    public static void main(String[] args) {
        new Double(1);
        // read class name from command line args or user input
        String className;
        if (args.length > 0) {
            className = args[0];
        } else {
            Scanner in = new Scanner(System.in);
            System.out.println("Enter class name (e.g. java.util.Date): ");
            className = in.next();
        }

        // print class name and superClass name ( id != Object)
        try {
            Class<?> cl = Class.forName(className);
            printClassDetail(cl);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();

        }
        System.exit(0);

    }

    public static void printClassDetail(Class cl){
        Class<?> superclass = cl.getSuperclass();
        int modifiers = cl.getModifiers();
        String s = Modifier.toString(modifiers);
        System.out.print(s + " " + cl);

        //print super class
        if (superclass != null && superclass != Object.class) {
            System.out.println(" extends " + superclass.getName());
        }

        System.out.println("{");
        printConstructors(cl);
        System.out.println();
        printMethods(cl);
        System.out.println();
        printFields(cl);
        System.out.println("}");
    }

    /**
     * Print all constructors of a class
     *
     * @param cl a class
     */
    public static void printConstructors(Class cl) {
        //fetch all constructors
        Constructor[] constructors = cl.getDeclaredConstructors();
        for (Constructor constructor : constructors) {
            // These codes  ==  System.out.println(constructor);
            String name = constructor.getName();
            System.out.print("    ");
            String modifiers = Modifier.toString(constructor.getModifiers());
            if (modifiers.length() > 0) {
                System.out.print(modifiers + " ");
            }
            System.out.print(name + "(");

            //print parameter types
            Class[] parameterTypes = constructor.getParameterTypes();
            for (int j = 0; j < parameterTypes.length; j++) {
                if (j > 0) System.out.print(", ");
                System.out.print(parameterTypes[j].getName());
            }
            System.out.println(");");
        }

    }

    /**
     * Print all methods of a class
     * @param cl a class
     */
    public static void printMethods(Class cl) {
        Method[] methods = cl.getDeclaredMethods();
        for (Method method : methods) {
            // this code is better than these
            // System.out.println(method);
            System.out.print("    ");

            Class<?> returnType = method.getReturnType();
            String name = method.getName();

            //print modifiers , return type and method name
            String modifiers = Modifier.toString(method.getModifiers());
            if (modifiers.length() > 0) System.out.print(modifiers + " ");
            System.out.print(returnType.getName() + " " + name + "(");

            //print parameter types
            Class<?>[] parameterTypes = method.getParameterTypes();
            for (int j = 0; j < parameterTypes.length; j++) {
                if(j>0) System.out.print(", ");
                System.out.print(parameterTypes[j].getName());
            }
            System.out.println(");");

        }
    }

    /**
     * Print all fields of a class
     * @param cl a class
     */
    public static void printFields(Class cl){
        Field[] fields = cl.getDeclaredFields();
        for(Field field : fields){
//            System.out.println(field);
            Class<?> type = field.getType();
            String name = field.getName();
            String modifies = Modifier.toString(field.getModifiers());
            if(modifies.length()>0) System.out.print(modifies + " ");
            System.out.println(type.getName() + " " + name + ";");
        }
    }
}
