package ru.otus.h16.messageserver.runner;

import java.io.IOException;

/**
 * Created by tully.
 */
public interface ProcessRunner {
    void start(String command) throws IOException;

    void stop();

    String getOutput();
}
