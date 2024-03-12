package com.volka.eventdriven.core.event.reactor;

import com.volka.eventdriven.core.event.handler.AcceptEventHandler;
import com.volka.eventdriven.core.event.handler.ReadEventHandler;
import com.volka.eventdriven.core.event.handler.WriteEventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;

/**
 * Reactor 매니저
 *
 * @author volka
 */
@Slf4j
@Component
public class ReactorManager {

    private static final int SERVER_PORT = 8990;

    public void startReactor(int port) throws Exception {
        ServerSocketChannel server = ServerSocketChannel.open();
        server.socket().bind(new InetSocketAddress(port));
        server.configureBlocking(false);

        Reactor reactor = new Reactor();
        reactor.registerChannel(SelectionKey.OP_ACCEPT, server);

        reactor.registerEventHandler(SelectionKey.OP_ACCEPT, new AcceptEventHandler(reactor.getDemultiplexer()));
        reactor.registerEventHandler(SelectionKey.OP_READ, new ReadEventHandler(reactor.getDemultiplexer()));
        reactor.registerEventHandler(SelectionKey.OP_WRITE, new WriteEventHandler());
    }
}
