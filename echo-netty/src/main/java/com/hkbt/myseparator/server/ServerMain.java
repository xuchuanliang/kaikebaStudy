package com.hkbt.myseparator.server;

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
import io.netty.handler.codec.LineBasedFrameDecoder;
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
                            socketChannel.pipeline()
                                    .addLast(new FixedLengthFrameDecoder(100))//固定长度处理器，每一个数据包长度控制在100字节
                                    .addLast(new DelimiterBasedFrameDecoder(100,byteBuf))//粘包拆包处理器，自定义分隔符
                                    .addLast(new StringEncoder(CharsetUtil.UTF_8))//字符串编解码处理器，自动将字节码转成字符串
                                    .addLast(new StringDecoder(CharsetUtil.UTF_8))
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
