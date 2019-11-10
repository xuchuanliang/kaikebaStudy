package com.ant.common;

import java.util.concurrent.Flow;
import java.util.concurrent.TimeUnit;

/**
 * 定义订阅者
 */
public class SomeSubscriber implements Flow.Subscriber<Integer> {

    //声明订阅令牌，订阅令牌需要在发布者和订阅者之间传递消息
    private Flow.Subscription subscription;

    /**
     * 经过试验的知，该方法在生产者生产第一条消息前，调用了该方法
     * 通过debug
     * @param subscription
     */
    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        //设置订阅者首次向发布者（令牌）订阅消息的数量
        System.out.println("onSubscribe()方法被调用");
        subscription.request(10);
    }

    /**
     * 订阅者消费数据发生在该方法
     * 订阅者每接收一次消息数据，就会自动调用一次该方法
     * @param item
     */
    @Override
    public void onNext(Integer item) {
        System.out.println("当前订阅者正在消费的消息为:" + item);
        try {
            //模拟耗时操作
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //设置订阅者再次向发布者订阅更多的消息，即每消费一条数据，则向发布者订阅多条数据
        subscription.request(10);
        //当满足某个条件时取消订阅
//        if(true){
//            subscription.cancel();
//        }
    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
        //出现异常时，取消当前订阅者与发布者的订阅关系
        subscription.cancel();
    }

    /**
     * 当订阅者令牌，即subscription中的所有消息都已经消费完毕，则会调用该方法
     * 注：并不是生产者生产完数据会调用，则是消费者消费完毕才会调用
     */
    @Override
    public void onComplete() {
        System.out.println("订阅令牌中的所有消息都已经消费完毕");
    }
}
