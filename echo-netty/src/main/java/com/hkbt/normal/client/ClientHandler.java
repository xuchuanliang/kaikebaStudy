package com.hkbt.normal.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

public class ClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try{
            ByteBuf byteBuf = (ByteBuf) msg;
            System.out.println("client get message from server:"+byteBuf.toString(CharsetUtil.UTF_8));
            String bye = "bye";
            byteBuf = Unpooled.buffer(bye.length());
            byteBuf.writeBytes(bye.getBytes());
            ctx.writeAndFlush(byteBuf);
        }finally {
            ReferenceCountUtil.release(msg);
        }
    }
}
