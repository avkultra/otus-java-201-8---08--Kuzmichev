package ru.otus.h14.helper;

import ru.otus.h14.cache.CacheEngine;
import ru.otus.h14.dataset.AddressDataSet;
import ru.otus.h14.dataset.PhoneDataSet;
import ru.otus.h14.dataset.UserDataSet;

import ru.otus.h14.database.DBService;
import ru.otus.h14.database.DBServiceImpl;

import java.util.ArrayList;
import java.util.Arrays;

public class DBHelper {
    public static DBService initialDB(CacheEngine cacheEngine){

        DBService dbService = new DBServiceImpl(cacheEngine);

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
