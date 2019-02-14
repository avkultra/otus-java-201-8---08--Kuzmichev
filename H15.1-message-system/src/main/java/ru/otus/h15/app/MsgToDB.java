package ru.otus.h15.app;

import ru.otus.h15.database.DBService;
import ru.otus.h15.messageSystem.Address;
import ru.otus.h15.messageSystem.Addressee;
import ru.otus.h15.messageSystem.Message;

/**
 * Created by tully.
 */
public abstract class MsgToDB extends Message {
    public MsgToDB(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(Addressee addressee) {
        if (addressee instanceof DBService) {
            exec((DBService) addressee);
        }
    }

    public abstract void exec(DBService dbService);
}
