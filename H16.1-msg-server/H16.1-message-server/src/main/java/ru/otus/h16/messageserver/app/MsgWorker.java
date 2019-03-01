package ru.otus.h16.messageserver.app;

import ru.otus.h16.messageserver.channel.Blocks;
import ru.otus.h16.messageserver.messageSystem.Message;

import java.io.Closeable;

public interface MsgWorker extends Closeable {
    void send(Message msg);

    Message poll();

    @Blocks
    Message take() throws InterruptedException;

    void refund(Message msg);

    void close();
}
