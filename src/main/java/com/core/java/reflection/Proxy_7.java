package com.core.java.reflection;

/**
 * 模拟JVM生成的代理类的内部大致结构
 * 并介绍之前使用的size()和add()方法
 * 因为实现了Connection接口，所以生成的代理类的方法就是Connection接口的方法
 */
public class Proxy_7 {

    //自定义的MyInvocationHandler接口接受三个对象
    //Proxy类唯一的构造器是有invocationHandler参数的构造器 , 那么Proxy理应有一个该invocationHandlegai的field
    MyInvocationHandler_7 handler;

    //代理类调用的所有方法都是通过invocationHandler的invoke()方法来实现的
    //那么proxy代理对象或者invocationHandler对象中有一个对象是持有被代理的对象的引用 , 例如这样持有Object
    //并通过method.invoke(object, args)这样的方式来实现业务
    Object object;

    //代理类的内部方法

    //每调用一下，invoke方法就执行一次
    Object size() throws Exception {
        //内部调用InvocationHandler的invoke方法，而InvocationHandler是一个接口，所以 handler.invoke()方法是调用了我们的实现类的invoke()方法。
        //proxy对象，size方法，参数null
        return handler.invoke(this.object, object.getClass().getMethod("size", null), null);
    }


    //每调用一下，invoke方法就执行一次
    Object add(Object args) throws Exception {
        //内部调用InvocationHandler的invoke方法
        //proxy对象，add方法，参数
        return handler.invoke(this.object, object.getClass().getMethod("add", Object.class), new Object[]{args});
    }

}
