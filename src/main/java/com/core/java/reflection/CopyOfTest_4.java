package com.core.java.reflection;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * This program demonstrates the use of reflection for manipulating arrays.
 *
 * @date 2018-12-22
 */
public class CopyOfTest_4 {

    public static void main(String[] args) {
//        Exception in thread "main" java.lang.ClassCastException:
//        [Ljava.lang.Object; cannot be cast to [Lcom.core.java.entity.Employee; at com.core.java.reflection.CopyOfTest_4.main
//        Employee[] employees = new Employee[5];
//        Employee[] objects = (Employee[])badCopyOf(employees, 10);

        int[] a = {1, 2, 3};
        a = (int[]) goodCopyOf(a, 10);
        System.out.println(Arrays.toString(a));

        String[] b = {"Tom", "Dick", "Harry"};
        b = (String[]) goodCopyOf(b, 10);
        System.out.println(Arrays.toString(b));

//        System.out.println("The following call will generate an exception.");
//        b = (String[]) badCopyOf(b, 10);

        //Test Array api
        Object o = Array.get(b, 1);
        System.out.println("获取数组指定位置的值：" + o);
        int anInt = Array.getInt(a, 0);
        System.out.println("获取指定类型数组指定位置的值 :" + anInt);
        Array.set(b, 4, "Chen");
        System.out.println("设置数组指定位置的值 : " + b[4]);
        Array.setInt(a, 4, 4);
        System.out.println("设置指定类型数组指定位置的值 : " + a[4]);

    }

    /**
     * a failure test to change Employee[] to Object[]
     * This method attempts to grow an array by allocating a new array and copying all elements.
     *
     * @param a         the array to grow
     * @param newLength the new length
     * @return a larger array that contains all elements of a .
     * However , the returned array has type Object[] , not same type as a
     */
    public static Object[] badCopyOf(Object[] a, int newLength) {
        Object[] newArray = new Object[newLength];
        System.arraycopy(a, 0, newArray, 0, Math.min(a.length, newLength));
        return newArray;
    }

    /**
     * 这个方法用于给任意类型数组扩容
     * 任意类型的数组可以转换为Object , 但不能转换为Object[] , 所有参数和返回值应该使用Object
     * This method grows an array by allocating a new array of the same type and copying all elements.
     *
     * @param a         the array to grow , this can be an object array or a primitive .
     * @param newLength
     * @return a larger array that contains all elements of a .
     */
    public static Object goodCopyOf(Object a, int newLength) {
        Class<?> aClass = a.getClass();
        if (!aClass.isArray()) return null;
        Class<?> componentType = aClass.getComponentType();
        int length = Array.getLength(a);
        Object newArray = Array.newInstance(componentType, newLength);
        System.arraycopy(a, 0, newArray, 0, Math.min(length, newLength));  //copy elements from a to newArray
        return newArray;
    }


}
