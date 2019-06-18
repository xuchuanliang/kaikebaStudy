package capter03;

import java.lang.reflect.Proxy;

public class Test {

    public static void main(String[] args){
        BaseMethods proxyInstance = (BaseMethods) Proxy.newProxyInstance(Person.class.getClassLoader(), new Class[]{BaseMethods.class}, new MyInvocation(new Person()));
        proxyInstance.eat();
        proxyInstance.wc();
    }
}
