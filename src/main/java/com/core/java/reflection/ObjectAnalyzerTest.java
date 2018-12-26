package com.core.java.reflection;

import com.core.java.entity.Employee;

import java.util.ArrayList;

/**
 * This program uses reflection to spy on object
 *
 * @date 20181221
 */
public class ObjectAnalyzerTest {

    public static void main(String[] args) {
        ArrayList<Object> squares = new ArrayList<>();
        squares.add(1);
        squares.add("string");
        squares.add(new Employee("java",3000,1992,5,1));
        squares.add(4);
        System.out.println(new ObjectAnalyzer_3().toString(squares));

        //        for (int i = 1; i <= 5; i++) {
//            squares.add(i * i);
//            System.out.println(new ObjectAnalyzer_3().toString(squares));
//        }

//        String[] test = new String[3];
//        System.out.println(new ObjectAnalyzer_3().toString(test));
//        test[0]="134";
//        System.out.println(new ObjectAnalyzer_3().toString(test));

//        int[] test1 = {1,2};
//        System.out.println(new ObjectAnalyzer_3().toString(test1));
    }

}
