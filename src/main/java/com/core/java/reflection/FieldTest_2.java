package com.core.java.reflection;

import com.core.java.entity.Employee;

import java.lang.reflect.Field;

public class FieldTest_2 {

    public static void main(String[] args) {
        Employee hacker = new Employee("Harry Hacker", 3500, 1991, 1, 1);
        Class<? extends Employee> aClass = hacker.getClass();
        System.out.println(aClass);
        try {
            //获取class对象内名为name的属性对象
            Field field = aClass.getDeclaredField("name");

            System.out.println(field);


            //获取employee对象内指定的field属性的值
            // java.lang.IllegalAccessException: Class com.core.java.reflection.FieldTest can not access a member of class com.core.java.entity.Employee with modifiers "private"
            //1  if the Field we access is "private" , IllegalAccessException will throw
            //2  setAccessible() -> A value of {@code true} indicates that the reflected object should suppress Java language access checking when it is used.
            field.setAccessible(true);
            String o = (String) field.get(hacker);
            System.out.println(o);

            //set field value
            field.set(hacker, "jack");
            System.out.println(hacker.getName());

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
