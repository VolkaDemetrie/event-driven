package com.volka.eventdriven.event.handler;

import java.nio.channels.SelectionKey;

public interface EventHandler {

    void handleEvent(SelectionKey handle) throws Exception;
}
