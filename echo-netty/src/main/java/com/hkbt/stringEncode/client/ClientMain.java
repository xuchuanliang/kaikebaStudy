package com.hkbt.stringEncode.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

public class ClientMain {
    public static void main(String[] args) {
        EventLoopGroup eventExecutors = new NioEventLoopGroup(1);
        try {
            Bootstrap bootstrap = new Bootstrap();
            ChannelFuture future = bootstrap.group(eventExecutors)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel
                                    .pipeline()
                                    .addLast(new LineBasedFrameDecoder(1024))//粘包拆包处理器，自动根据系统换行符进行拆包
                                    .addLast(new StringEncoder(CharsetUtil.UTF_8))//字符串编解码处理器，自动将字节码转成字符串
                                    .addLast(new StringDecoder(CharsetUtil.UTF_8))
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
