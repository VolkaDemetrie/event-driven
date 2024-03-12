package com.volka.eventdriven.core.event.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * 이벤트 승인 핸들러
 *
 * @author volka
 */
public class AcceptEventHandler implements EventHandler{

    private Selector demultiplexer;

    public AcceptEventHandler(Selector demultiplexer) {
        this.demultiplexer = demultiplexer;
    }

    public void handleEvent(SelectionKey handle) throws Exception {
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) handle.channel();
        SocketChannel socketChannel = serverSocketChannel.accept();

        if (socketChannel != null) {
            socketChannel.configureBlocking(false);
            socketChannel.register(demultiplexer, SelectionKey.OP_READ);
        }
    }
}
