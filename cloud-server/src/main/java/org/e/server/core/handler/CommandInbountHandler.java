package org.e.server.core.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.e.domain.Command;
import org.e.server.factory.Factory;
import org.e.server.service.CommandDictionaryService;

public class CommandInbountHandler extends SimpleChannelInboundHandler<Command> {

    private CommandDictionaryService dictionaryService;

    public CommandInbountHandler() {
        this.dictionaryService = Factory.getCommandDictionaryService();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Command command) {
        String result = dictionaryService.processCommand(command);
        ctx.writeAndFlush(result);
    }

}
