package com.volka.eventdriven.core.event.handler;

import java.nio.channels.SelectionKey;

public interface EventHandler {

    void handleEvent(SelectionKey handle) throws Exception;
}
