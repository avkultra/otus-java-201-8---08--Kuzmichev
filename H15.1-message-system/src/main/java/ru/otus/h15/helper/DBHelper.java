package ru.otus.h15.helper;

import ru.otus.h15.cache.CacheEngine;
import ru.otus.h15.dataset.AddressDataSet;
import ru.otus.h15.dataset.PhoneDataSet;
import ru.otus.h15.dataset.UserDataSet;

import ru.otus.h15.database.DBService;
import ru.otus.h15.database.DBServiceImpl;
import ru.otus.h15.app.MessageSystemContext;

import java.util.ArrayList;
import java.util.Arrays;

public class DBHelper {
    public static DBService initialDB(String address, MessageSystemContext context,CacheEngine cacheEngine){

        DBService dbService = new DBServiceImpl(address, context, cacheEngine);

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
