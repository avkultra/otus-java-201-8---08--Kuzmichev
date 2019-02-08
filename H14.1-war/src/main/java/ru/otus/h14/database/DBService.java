package ru.otus.h14.database;

import ru.otus.h14.dataset.*;
import ru.otus.h14.dataset.UserDataSet;

import java.util.List;

public interface DBService extends AutoCloseable {


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
