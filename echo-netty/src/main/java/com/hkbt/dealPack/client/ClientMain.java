package com.hkbt.dealPack.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;

public class ClientMain {
    public static void main(String[] args){
        EventLoopGroup eventExecutors = new NioEventLoopGroup(1);
        try{
            Bootstrap bootstrap = new Bootstrap();
            ChannelFuture future = bootstrap.group(eventExecutors)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY,true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new LineBasedFrameDecoder(1024));
                            socketChannel.pipeline().addLast(new ClientHandler());
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
