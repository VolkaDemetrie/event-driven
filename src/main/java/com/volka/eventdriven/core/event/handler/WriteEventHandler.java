package com.volka.eventdriven.core.event.handler;

import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

/**
 * 쓰기 핸들러
 *
 * @author volka
 */
@Slf4j
public class WriteEventHandler implements EventHandler {
    @Override
    public void handleEvent(SelectionKey handle) throws Exception {
        log.info("====Write Event Handler====");

        SocketChannel socketChannel = (SocketChannel) handle.channel();
        ByteBuffer inputBuffer = (ByteBuffer) handle.attachment();
        socketChannel.write(inputBuffer);
        socketChannel.close();
    }
}
