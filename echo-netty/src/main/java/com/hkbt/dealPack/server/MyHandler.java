package com.hkbt.dealPack.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

public class MyHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        /**
         * 注意：客户端增加了粘包拆包处理器后，这里发送到客户端的消息中也需要增加系统换行符，否则会导致客户端接收不到数据，阻塞
         */
        String content = "hello ,i am server" + System.getProperty("line.separator");
        ByteBuf byteBuf = Unpooled.buffer(content.length());
        byteBuf.writeBytes(content.getBytes());
        ctx.writeAndFlush(byteBuf);
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
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println(byteBuf.toString(CharsetUtil.UTF_8));
        if("bye".equalsIgnoreCase(byteBuf.toString(CharsetUtil.UTF_8))){
            String bye = "bye";
            ByteBuf byteBuf1 = Unpooled.buffer(bye.length());
            byteBuf1.writeBytes(bye.getBytes());
            ctx.writeAndFlush(byteBuf1);
            ctx.close();
        }
    }

    public static void main(String[] args){
        System.getProperties().list(System.out);
    }
}
