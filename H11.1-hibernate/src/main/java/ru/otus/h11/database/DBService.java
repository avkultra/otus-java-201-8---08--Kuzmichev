package ru.otus.h11.database;

import ru.otus.h11.dataset.*;

import java.sql.SQLException;
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

    void shutdown();
}
