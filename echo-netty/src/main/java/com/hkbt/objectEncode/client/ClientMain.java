package com.hkbt.objectEncode.client;

import com.hkbt.myseparator.InfoUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

public class ClientMain {
    public static void main(String[] args) {
        EventLoopGroup eventExecutors = new NioEventLoopGroup(1);
        try {
            ByteBuf byteBuf = Unpooled.copiedBuffer(InfoUtil.SEPARATOR.getBytes());
            Bootstrap bootstrap = new Bootstrap();
            ChannelFuture future = bootstrap.group(eventExecutors)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel
                                    .pipeline()
                                    .addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(this.getClass().getClassLoader())))
                                    .addLast(new ObjectEncoder())
                                    .addLast(new ClientHandler());
                        }
                    })
                    .connect("localhost", 8899)
                    .sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
        } finally {
            eventExecutors.shutdownGracefully();
        }
    }
}
