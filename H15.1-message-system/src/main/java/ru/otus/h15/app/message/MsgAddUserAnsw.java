package ru.otus.h15.app.message;

import ru.otus.h15.front.FrontendService;
import ru.otus.h15.app.MsgToFrontend;
import ru.otus.h15.dataset.UserDataSet;
import ru.otus.h15.messageSystem.Address;

public class MsgAddUserAnsw extends MsgToFrontend {
    private final UserDataSet user;
    private final int sessionId;

    public MsgAddUserAnsw(Address from, Address to, int sessionId, UserDataSet user) {
        super(from, to);
        this.user = user;
        this.sessionId = sessionId;
    }

    @Override
    public void exec(FrontendService frontendService) {

        frontendService.handleAddUserResponse(sessionId, user);
    }
}
