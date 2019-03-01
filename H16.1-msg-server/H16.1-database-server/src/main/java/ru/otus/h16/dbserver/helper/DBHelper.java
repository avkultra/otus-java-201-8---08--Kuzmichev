package ru.otus.h16.dbserver.database;

import ru.otus.h16.dbserver.cache.CacheEngine;
import ru.otus.h16.dbserver.dataset.AddressDataSet;
import ru.otus.h16.dbserver.dataset.PhoneDataSet;
import ru.otus.h16.dbserver.dataset.UserDataSet;

import ru.otus.h16.dbserver.database.DBService;
import ru.otus.h16.dbserver.database.DBServiceImpl;
//import ru.otus.h16.dbserver.app.MessageSystemContext;

import java.util.ArrayList;
import java.util.Arrays;

public class DBHelper {
    public static DBService initialDB(String address, CacheEngine cacheEngine){

        DBService dbService = new DBServiceImpl(address, cacheEngine);

        System.out.println(dbService.getLocalStatus());

        UserDataSet user = new UserDataSet("USER_#1", 18,
                new ArrayList<>(Arrays.asList(new PhoneDataSet("7777777"))),
                new AddressDataSet("Nevskiy prospect"));

        user.addPhone(new PhoneDataSet("100501"));

        dbService.save(user);

        dbService.save(new UserDataSet("USER_#3", 72,
                new ArrayList<>(Arrays.asList(new PhoneDataSet("2222222"))),
                new AddressDataSet("Sennaya square")));

        return dbService;
    }
}
