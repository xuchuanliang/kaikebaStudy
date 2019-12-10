package com.hkbt.objectEncode.client;

import com.hkbt.myseparator.InfoUtil;
import com.hkbt.objectEncode.Person;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

public class ClientHandler extends ChannelInboundHandlerAdapter {
    /**
     * 模拟粘包与拆包
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try{
            Person person = (Person) msg;
            System.out.println(person);
            person.setName("【client】"+person.getName());
            ctx.writeAndFlush(person);
        }finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
