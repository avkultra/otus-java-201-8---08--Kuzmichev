package ru.otus.h15.app.message;

import ru.otus.h15.app.MsgToDB;
import ru.otus.h15.dataset.UserDataSet;
import ru.otus.h15.database.DBService;
import ru.otus.h15.messageSystem.Address;

public class MsgAddUser extends MsgToDB {
    private final UserDataSet user;
    private final int sessionId;

    public MsgAddUser(Address from, Address to, int sessionId, UserDataSet user) {
        super(from, to);
        this.user = user;
        this.sessionId = sessionId;
    }

    @Override
    public void exec(DBService dbService) {
        dbService.save(user);
        dbService.getMS().sendMessage(new MsgAddUserAnsw(getTo(), getFrom(), sessionId, user));
    }
}

