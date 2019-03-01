package ru.otus.h16.messageserver.messageSystem;

public final class MessageJson extends Message {
    private final String message;

    public MessageJson(Address from, Address to, String msg) {
        super(from, to);
        this.message = msg;
    }

    public String getMessage() {
        return message;
    }

}
