package com.core.java.number;

public class ETest {

    public static void main(String[] args) {


        //科学计数法 1.0 * 10^-5
        System.out.println(1.0e-5);

        //1.0 * 10^2
        System.out.println(1e2);

        //0x0.3p10是在C99中引入的十六进制浮点文本的示例。 p将基数与指数分离。
        //0x0.3位被称为有效位部分(整数与可选分数)，指数是被缩放的二的幂。
        //该特定值计算为十六进制中的0.3，或3 * 16^(-1) = (3/16) , 再乘以2^10(1024)，其给出3 * 1024/16 = 192
        System.out.println(0x0.3p10);

        // (1/16) * 2^2
        System.out.println(0x0.1p2);

        // 1 * 2^(-2)
        System.out.println(0x1p-2);

        // 2 * 2^(-3)
        System.out.println(0x2p-3);

        // 2 * 2^(-2)
        System.out.println(0x1.fffffp-2f);

        // 4.0 * 2^(-3)
        System.out.println(0x4.0p-3f);

        // 0.1 * 10^2
        System.out.println(0.1e2);

        //常数e=2.7
        System.out.println(Math.E);

        //3得2次方 , 参数和结果都是double类型
        System.out.println(Math.pow(3,2));

        //e为底数的log运算 log e(5)
        System.out.println(Math.log(5));

        //2为底数的log运算 log 2(5)
        System.out.println(Math.log(5)/Math.log(2));

        //10为底数
        System.out.println(Math.log10(10));

        //求4的平方根
        System.out.println(Math.sqrt(4));

        // 负数做余数运算
        System.out.println(-2%12);
        //取余数 , 使得得到的值总为正数
        //例如计算时钟指针的位置 , -2点其实为10点
        System.out.println(Math.floorMod(-2,12));

        // Math.sin(30) 这个方法的30不是度数
        System.out.println(Math.signum(30));

        //以e为底的对数运算
        System.out.println(Math.log(Math.E));

        //以10为底的对数运算
        System.out.println(Math.log10(10));

        // e^2
        System.out.println(Math.exp(2));

        System.out.println(Math.PI);
    }
}
