package com.hkbt.objectEncode.server;

import com.hkbt.myseparator.InfoUtil;
import com.hkbt.objectEncode.Person;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

public class MyHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(new Person("snail",20,"man",2.2));
        System.out.println("server active");
    }

    /**
     * 当客户端循环多次发送数据时，服务端会出现粘包问题，结果如下
     【82】:hello world
     【83】:hello world
     【84】:hello world
     【85】:hello world
     【86】:hello world
     【87】:hello world
     【88】:hello world
     【89】:hello world
     【90】:hello world【91】:hello world
     【92】:hello world
     【93】:hello world【94】:hello world【95】:hello world【9
     6】:hello world【97】:hello world【98】:hello world【99】:hello world【100】:hello world【101】:hello world【102】:
     hello world【103】:hello world【104】:hello world【105】:hello world【106】:hello world
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try{
            Person person = (Person)msg;
            System.out.println(person);
            person.setName("【server】"+person.getName());
//            ctx.writeAndFlush(person);
        }finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    public static void main(String[] args){
        System.getProperties().list(System.out);
    }
}
