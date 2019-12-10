package com.hkbt.objectEncode.server;

import com.hkbt.myseparator.InfoUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

public class ServerMain {
    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup boss = new NioEventLoopGroup(2);
        EventLoopGroup workder = new NioEventLoopGroup(4);
        try {
            ByteBuf byteBuf = Unpooled.copiedBuffer(InfoUtil.SEPARATOR.getBytes());
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            ChannelFuture future = serverBootstrap.group(boss, workder)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel
                                    .pipeline()
                                    .addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(this.getClass().getClassLoader())))
                                    .addLast(new ObjectEncoder())
                                    .addLast(new MyHandler());
                        }
                    })
                    .bind(8899)
                    .sync();
            future.channel().closeFuture().sync();
        } finally {
            boss.shutdownGracefully();
            workder.shutdownGracefully();
        }
    }
}
