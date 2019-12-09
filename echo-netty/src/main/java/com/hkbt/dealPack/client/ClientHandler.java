package com.hkbt.dealPack.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

public class ClientHandler extends ChannelInboundHandlerAdapter {
    /**
     * 模拟粘包与拆包
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("client read");
        for(int i=0;i<500;i++){
            String bye = "【"+i+"】:"+"hello world" + System.getProperty("line.separator");
            ByteBuf byteBuf = Unpooled.buffer(bye.length());
            byteBuf.writeBytes(bye.getBytes());
            ctx.writeAndFlush(byteBuf);
        }
    }
}
