package ru.otus.h15.app;

import ru.otus.h15.front.FrontendService;
import ru.otus.h15.messageSystem.Address;
import ru.otus.h15.messageSystem.Addressee;
import ru.otus.h15.messageSystem.Message;

/**
 * Created by tully.
 */
public abstract class MsgToFrontend extends Message {
    public MsgToFrontend(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(Addressee addressee) {
        if (addressee instanceof FrontendService) {
            exec((FrontendService) addressee);
        } else {
            //todo error!
        }
    }

    public abstract void exec(FrontendService frontendService);
}