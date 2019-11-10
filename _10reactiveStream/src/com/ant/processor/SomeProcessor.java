package com.ant.processor;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.TimeUnit;

/**
 * 发布订阅消息处理器，在发布者和订阅者之间，作用相当于是消息装换，如发布者发布的是Integer类型消息，消费者消费的是String类型消息，那么可以使用消息处理器将Integer类型消息转成String类型
 * 消息处理器集成发布者接口和订阅者接口，即对于真正的发布者，则消息处理器是订阅者角色；对于真正的订阅者，则消息处理器是发布者角色
 * 此案例中是将真正发布者中的int数字中小于50的转成字符串给真正的订阅者，也就是该类相对于真正的订阅者是一个String类型的发布者，相对于真正的发布者是一个Integer类型的订阅者
 */
public class SomeProcessor extends SubmissionPublisher<String> implements Flow.Processor<Integer,String> {
    private Flow.Subscription subscription;
    //该方法是发布者调用，本类承担的角色是订阅者
    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        System.out.println("调用了处理器的onSubscribe()方法");
        this.subscription = subscription;
        subscription.request(10);
    }

    /**
     * 此处将作为订阅者收到的数据转换成要求的数据，并作为发布者将数据发布给真正的订阅者
     * @param item
     */
    @Override
    public void onNext(Integer item) {
        //进行消息装换，将小于50的转成字符串传递给订阅者
        System.out.println("处理器开始处理数据："+item);
        if(item < 50){
            this.submit("转成数据:"+item);
        }
        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
        subscription.cancel();
    }

    @Override
    public void onComplete() {
        System.out.println("处理器已经将订阅者令牌中的数据处理完毕");
    }
}
