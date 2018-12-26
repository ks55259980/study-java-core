package com.core.java.reflection;

import com.core.java.entity.Employee;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * This program shows how to invoke methods through reflection.
 *
 * @date 2018-12-22
 */
public class MethodTableTest_5 {

    public static void main(String[] args) throws NoSuchMethodException {


        try {
//            Method[] declaredMethods = Employee.class.getDeclaredMethods();
//            for (Method method : declaredMethods){
//                System.out.println(method);
//            }

            Method getName = Employee.class.getMethod("getName");
            System.out.println(getName);
            Employee jack = new Employee("jack", 3500, 1992, 01, 1);
            Object o = getName.invoke(jack);
            System.out.println(o);

            Method setSalary = Employee.class.getMethod("setSalary", double.class);
            System.out.println(setSalary);
            Object invoke = setSalary.invoke(jack, 5000);
            System.out.println(invoke);
            System.out.println(jack.getSalary());

            Method sqrt = Math.class.getMethod("sqrt", double.class);
            Object invoke1 = sqrt.invoke(null, 3);
            System.out.println(invoke1.getClass());
            System.out.println(invoke1);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        // get method pointers to the square and sqrt methods
        Method square = MethodTableTest_5.class.getMethod("square", double.class);
        Method sqrt = Math.class.getMethod("sqrt", double.class);

        //print tables of x- and y-values
        printTable(1, 100, 10, square);
        printTable(1, 100, 10, sqrt);
    }

    /**
     * Return the square of a number
     *
     * @param x
     * @return
     */
    public static double square(double x) {
        return x * x;
    }

    /**
     * Print a table with x- and y- values for a method
     *
     * @param from   the lower bound for the x-values
     * @param to     the upper bound for the x-values
     * @param n      the number of rows in the table
     * @param method a method with a double parameter and double return value
     */
    public static void printTable(double from, double to, int n, Method method) {
        // print out the method as table header
        System.out.println(method);

        double dx = (to - from) / (n - 1);
        for (double x = from; x <= to; x += dx) {
            try {
                double y = (Double) method.invoke(null, x);
                System.out.printf("%10.4f | %10.4f%n", x, y);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }

    }
}
