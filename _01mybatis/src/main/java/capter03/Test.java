package capter03;

import java.lang.reflect.Proxy;

public class Test {

    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
        test2();
    }
    public static void test1(){
        BaseMethods proxyInstance = (BaseMethods) Proxy.newProxyInstance(Person.class.getClassLoader(), new Class[]{BaseMethods.class}, new MyInvocation(new Person()));
        proxyInstance.eat();
        proxyInstance.wc();
    }
    public static void test2() throws InstantiationException, IllegalAccessException {
        BaseMethods baseMethods = ProxyFactory.createProxy(Person.class);
        baseMethods.wc();
        baseMethods.eat();
    }
}
