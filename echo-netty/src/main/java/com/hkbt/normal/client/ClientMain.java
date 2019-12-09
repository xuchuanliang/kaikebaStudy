package com.hkbt.normal.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class ClientMain {
    public static void main(String[] args){
        EventLoopGroup eventExecutors = new NioEventLoopGroup(1);
        try{
            Bootstrap bootstrap = new Bootstrap();
            ChannelFuture future = bootstrap.group(eventExecutors)
                    .channel(NioSocketChannel.class)
                    .handler(new ClientHandler())
                    .connect("localhost", 8899)
                    .sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
        } finally {
            eventExecutors.shutdownGracefully();
        }
    }
}
