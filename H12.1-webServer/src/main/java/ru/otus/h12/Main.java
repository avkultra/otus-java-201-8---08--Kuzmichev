package ru.otus.h12;

import ru.otus.h12.database.DBService;
import ru.otus.h12.database.DBServiceImpl;
import ru.otus.h12.server.WebServer;

import  ru.otus.h12.dataset.*;

import java.util.ArrayList;
import java.util.Arrays;



public class Main {

    public static void main(String[] args) throws Exception {

        DBService dbService = new DBServiceImpl();

        System.out.println(dbService.getLocalStatus());

        UserDataSet user = new UserDataSet("USER_#1", 18,
                new ArrayList<>(Arrays.asList(new PhoneDataSet("7777777"))),
                new AddressDataSet("Nevskiy prospect"));

        user.addPhone(new PhoneDataSet("100501"));

        dbService.save(user);

        dbService.save(new UserDataSet("USER_#3", 72,
                new ArrayList<>(Arrays.asList(new PhoneDataSet("2222222"))),
                new AddressDataSet("Sennaya square")));
        WebServer server = WebServer.create(dbService);

        server.init();
        server.start();
    }
}
