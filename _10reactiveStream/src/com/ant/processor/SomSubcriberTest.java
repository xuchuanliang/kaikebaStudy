package com.ant.processor;

import java.util.Random;
import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.TimeUnit;

/**
 * 发布和消费是异步进行，并且有订阅令牌在发布者和订阅者之间进行消息传递，发布者和订阅令牌中包含一个有界数组，即发布者和订阅者令牌中的数组指向的是同一个数组，该数组初始长度是32，最大长度是256，该数组中存放着已经生产出来但是未被消费者
 * 消费的消息数据，当该数组的消息数量满了后，生产者的生产方法submit(item)将会阻塞，知道消费者消费了数据后才会继续进行生产
 * 由上得知：反应式流（Reactive Stream）为了解决在push和pull模型中，消费者和生产者之间消费能力和生产能力不均衡的情况，反应式流通过背压的思想进行协调工作。反应式流会在pull和push模型流处理机制之间进行动态转换，当发布者快，订阅者慢，
 * 他就使用pull模型；当发布者慢，订阅者快时，它使用push模型，**核心思想就是谁慢谁做主动**
 * 他就使用pull模型；当发布者慢，订阅者快时，它使用push模型，**核心思想就是谁慢谁做主动**
 */
public class SomSubcriberTest {
    public static void main(String[] args){
        //定义发布者，泛型是发布者发布的消息类型
        SubmissionPublisher<Integer> publisher = new SubmissionPublisher<>();
        //定义处理器
        SomeProcessor someProcessor = new SomeProcessor();
        //定义订阅者
        SomeSubscriber someSubscriber = new SomeSubscriber();
        //建立发布者与处理器的关系
        publisher.subscribe(someProcessor);
        //建立订阅者与处理器的关系
        someProcessor.subscribe(someSubscriber);
        //发布者生产数据，生产出300条消息
        //通过断点我们可以看到发布者最大缓存消息是256条，以一个32长度的数组存放队列
        for(int i=0;i<300;i++){
            //[0-100)的随机整数，作为消息
            int item = new Random().nextInt(100);
            System.out.println("生产出第" + i +"条消息："+item);
            //发布消息，发布者缓存满时submit()方法阻塞，因为发布者不具有无限缓冲区
            //通过debug调试我们可以看到，当消费比较慢时，发布者的发布会阻塞，消费一个、发布一个，
            // 因为发布者和订阅者令牌中均有一个有界数组存储消息，有界数组的长度是256，当有界队列满了之后，则会阻塞生产流程
            publisher.submit(item);
        }
        //关闭发布者
        publisher.close();
        try {
            //为了防止消息消息没有被消费完而主线程已关闭到情况发生
            TimeUnit.SECONDS.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
