package ru.otus.h10.database;

import ru.otus.h10.dataset.*;

import java.sql.SQLException;
import java.util.List;

public interface DBService extends AutoCloseable {

    String getDBData();

    void makeTables() throws SQLException;

    void addUsers(UserDataSet... users) throws SQLException;

    UserDataSet getUser(long id) throws SQLException;

    String getUserName(long id) throws SQLException;

    List<UserDataSet> getAllUsers() throws SQLException;

    List<String> getAllNames() throws SQLException;

    void dropTables() throws SQLException;


    <T extends DataSet> void save(T entity);

    <T extends DataSet> T load(long id, Class<T> clazz) throws SQLException;
}
