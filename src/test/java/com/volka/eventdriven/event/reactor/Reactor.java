package com.volka.eventdriven.event.reactor;

import com.volka.eventdriven.event.handler.EventHandler;

import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Reactor
 *
 * @author volka
 */
public class Reactor {
    private Map<Integer, EventHandler> registerdHandler = new ConcurrentHashMap<>();
    private Selector demultiplexer;

    public Reactor() throws Exception {
        demultiplexer = Selector.open();
    }

    public Selector getDemultiplexer() {
        return demultiplexer;
    }

    public void registerEventHandler(int eventType, EventHandler eventHandler) {
        registerdHandler.put(eventType, eventHandler);
    }

    public void registerChannel(int eventType, SelectableChannel channel) throws Exception {
        channel.register(demultiplexer, eventType);
    }

    public void run() {
        try {
            while (true) {
                demultiplexer.select();

                Set<SelectionKey> readyHandleSet = demultiplexer.selectedKeys();
                Iterator<SelectionKey> handleIter = readyHandleSet.iterator();

                EventHandler handler = null;

                while (handleIter.hasNext()) {
                    SelectionKey handle = handleIter.next();

                    if (handle.isAcceptable()) {
                        handler = registerdHandler.get(SelectionKey.OP_ACCEPT);
                        handler.handleEvent(handle);
                    }

                    if (handle.isReadable()) {
                        handler = registerdHandler.get(SelectionKey.OP_READ);
                        handler.handleEvent(handle);
                        handleIter.remove();
                    }

                    if (handle.isWritable()) {
                        handler = registerdHandler.get(SelectionKey.OP_WRITE);
                        handler.handleEvent(handle);
                        handleIter.remove();
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("ERROR :: " + e.getLocalizedMessage());
        }
    }
}
