package com.volka.eventdriven.core.event.handler;

import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * 읽기 핸들러
 *
 * @author volka
 */
@Slf4j
public class ReadEventHandler implements EventHandler {

    private Selector demultiplexer;
    private ByteBuffer inputBuffer = ByteBuffer.allocate(2048);

    public ReadEventHandler(Selector demultiplexer) {
        this.demultiplexer = demultiplexer;
    }


    @Override
    public void handleEvent(SelectionKey handle) throws Exception {
        log.info("====Read Event Handler====");

        SocketChannel socketChannel = (SocketChannel) handle.channel();

        socketChannel.read(inputBuffer);

        inputBuffer.flip();

        byte[] buffer = new byte[inputBuffer.limit()];
        inputBuffer.get(buffer);

        log.info("Received message :: {}", new String(buffer));

        inputBuffer.flip();

        socketChannel.register(demultiplexer, SelectionKey.OP_WRITE, inputBuffer);
    }
}
