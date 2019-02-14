package ru.otus.h15.app.message;

import ru.otus.h15.app.MsgToDB;
import ru.otus.h15.dataset.UserDataSet;
import ru.otus.h15.database.DBService;
import ru.otus.h15.messageSystem.Address;


public class MsgGetUserInfo extends MsgToDB {
    private final long userId;
    private final int sessionId;

    public MsgGetUserInfo(Address from, Address to, int sessionId, long userId) {
        super(from, to);
        this.userId = userId;
        this.sessionId = sessionId;
    }

    @Override
    public void exec(DBService dbService) {
        UserDataSet user = null;
        try {
            user = dbService.read(userId);
        } catch (Exception e){
            e.printStackTrace();
        }
        dbService.getMS().sendMessage(new MsgGetUserInfoAnsw(getTo(), getFrom(), sessionId, userId, user));
    }
}
