package ru.otus.h15.database;

import ru.otus.h15.dataset.*;
import ru.otus.h15.dataset.UserDataSet;
import ru.otus.h15.messageSystem.Addressee;

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
