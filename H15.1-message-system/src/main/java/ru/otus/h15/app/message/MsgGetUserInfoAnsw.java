package ru.otus.h15.app.message;

import ru.otus.h15.front.FrontendService;
import ru.otus.h15.app.MsgToFrontend;
import ru.otus.h15.dataset.UserDataSet;
import ru.otus.h15.messageSystem.Address;

public class MsgGetUserInfoAnsw extends MsgToFrontend {
    private final UserDataSet user;
    private final long userId;

    private final int sessionId;

    public MsgGetUserInfoAnsw(Address from, Address to, int sessionId, long userId, UserDataSet user) {
        super(from, to);
        this.userId = userId;
        this.user = user;
        this.sessionId = sessionId;
    }

    @Override
    public void exec(FrontendService frontendService) {

        frontendService.handleGetUserInfoResponse(sessionId, user);
    }
}
