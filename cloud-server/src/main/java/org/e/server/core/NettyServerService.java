package org.e.server.core;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import org.e.server.core.handler.CommandInbountHandler;
import org.e.server.service.ServerService;

public class NettyServerService implements ServerService {

    private static final int SERVER_PORT = 8189;

    @Override
    public void startServer() {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workedGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workedGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<>() {
                        @Override
                        protected void initChannel(Channel channel)  {
                            channel.pipeline()
                                    .addLast(new ObjectEncoder())
                                    .addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(null)))
                                    .addLast(new CommandInbountHandler());
                        }
                    });
            ChannelFuture future = bootstrap.bind(SERVER_PORT).sync();
            System.out.println("Сервер запущен");
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            System.out.println("Сервер упал");
        } finally {
            bossGroup.shutdownGracefully();
            workedGroup.shutdownGracefully();
        }

    }
}
