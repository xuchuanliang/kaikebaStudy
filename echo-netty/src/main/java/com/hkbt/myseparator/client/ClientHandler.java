package com.hkbt.myseparator.client;

import com.hkbt.myseparator.InfoUtil;
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
            System.out.println("client read");
            for(int i=0;i<500;i++){
                String bye = "【"+i+"】:"+"hello world" + InfoUtil.SEPARATOR;
                ctx.writeAndFlush(bye);
            }
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
