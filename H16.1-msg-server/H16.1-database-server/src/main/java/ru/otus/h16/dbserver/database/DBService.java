package ru.otus.h16.dbserver.database;

import ru.otus.h16.dbserver.dataset.*;
import ru.otus.h16.dbserver.dataset.UserDataSet;
import ru.otus.h16.messageserver.messageSystem.Addressee;

import java.util.List;

public interface DBService extends AutoCloseable, Addressee {


    String getLocalStatus();

    void save(UserDataSet dataSet);

    UserDataSet read(long id);

    UserDataSet readByName(String name);

    List<UserDataSet> readAllUsers();

    List<PhoneDataSet> readAllPhones();

    List<AddressDataSet> readAllAddresses();

    void delete(UserDataSet dataSet);

    void delete(PhoneDataSet dataSet);

    void delete(AddressDataSet dataSet);

    Long count();

    void shutdown();
}
