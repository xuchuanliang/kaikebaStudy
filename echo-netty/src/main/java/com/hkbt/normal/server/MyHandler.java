package com.hkbt.normal.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

public class MyHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String content = "hello ,i am server";
        ByteBuf byteBuf = Unpooled.buffer(content.length());
        byteBuf.writeBytes(content.getBytes());
        ctx.writeAndFlush(byteBuf);
        System.out.println("server active");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try{
            ByteBuf byteBuf = (ByteBuf) msg;
            System.out.println("server get meesage:"+byteBuf.toString(CharsetUtil.UTF_8)+";");
            if("bye".equalsIgnoreCase(byteBuf.toString(CharsetUtil.UTF_8))){
                String bye = "bye";
                ByteBuf byteBuf1 = Unpooled.buffer(bye.length());
                byteBuf1.writeBytes(bye.getBytes());
                ctx.writeAndFlush(byteBuf1);
                ctx.close();
            }else {
                String content = "server get meesage:"+byteBuf.toString(CharsetUtil.UTF_8)+";";
                byteBuf = Unpooled.buffer(content.length());
                byteBuf.writeBytes(content.getBytes());
                ctx.writeAndFlush(byteBuf);
            }
        }finally {
            ReferenceCountUtil.release(msg);
        }
    }
}
