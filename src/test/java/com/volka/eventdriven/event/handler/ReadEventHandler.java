package com.volka.eventdriven.event.handler;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * 읽기 핸들러
 *
 * @author volka
 */
public class ReadEventHandler implements EventHandler {

    private Selector demultiplexer;
    private ByteBuffer inputBuffer = ByteBuffer.allocate(2048);

    public ReadEventHandler(Selector demultiplexer) {
        this.demultiplexer = demultiplexer;
    }


    @Override
    public void handleEvent(SelectionKey handle) throws Exception {

        System.out.println("====Read Event Handler====");

        SocketChannel socketChannel = (SocketChannel) handle.channel();

        socketChannel.read(inputBuffer);

        inputBuffer.flip();

        byte[] buffer = new byte[inputBuffer.limit()];
        inputBuffer.get(buffer);

        System.out.println("Received message :: " + new String(buffer));

        inputBuffer.flip();

        socketChannel.register(demultiplexer, SelectionKey.OP_WRITE, inputBuffer);
    }
}
