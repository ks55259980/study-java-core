package com.core.java.number;


public class CharTest {

    public static void main(String[] args) {

        //char 得取值范围\u0000 - \uffff
        System.out.println(0x0000);
        System.out.println('\u0000');

        System.out.println(0xffff);
        System.out.println('\uffff');

        // ™
        System.out.println("\u2122");
        System.out.println('\u2122');

        System.out.print("Hello\n");

        //Unicode转义序列会在解析代码之前得到处理 ,
        //"\u0022+\u0022"并不是一个" "+" "的字符串 ,
        //而\u0022会在解析前转换为"符号 , 这样就会得到  ""+"" , 也就是一个空串
        System.out.println("\u0000+\u0000");
        System.out.println('\u0022');
        System.out.println(""+"");
        System.out.println("\"+\"");
        System.out.println("\u0022+\u0022");

        System.out.println((char) 90);

        System.out.println('Z');

        System.out.println((int)'Z');

        System.out.println("Z".getBytes()[0]);

    }

    public strictfp void aa(){

    }
}
