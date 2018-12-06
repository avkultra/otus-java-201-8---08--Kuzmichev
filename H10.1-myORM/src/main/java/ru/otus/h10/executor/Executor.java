package ru.otus.h10.executor;

import ru.otus.h10.database.DBCache;
import ru.otus.h10.dataset.DataSet;

import ru.otus.h10.Helpers.ReflectionHelper;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;

public class Executor {

    private final Connection connection;
    private final DBCache cache;

    Connection getConnection()
    {
        return connection;
    }

    @FunctionalInterface
    public interface PrepareHandler {
        void handle(PreparedStatement statement) throws SQLException;
    }



    public Executor(Connection connection) {
        this.cache = null;
        this.connection = connection;
    }

    public Executor(Connection connection, DBCache cache) {
        this.connection = connection;
        this.cache = cache;
    }

    public void execQuery(String query, Handler handler) throws SQLException {
        try (Statement stm = connection.createStatement()) {
            stm.execute(query);
            ResultSet resset = stm.getResultSet();
            handler.handle(resset);
        }
    }

    public <T> T execQuery(String query, THandler<T> handler) throws SQLException {
        try(Statement stm = connection.createStatement()) {
            stm.execute(query);
            ResultSet resset = stm.getResultSet();
            return handler.handle(resset);
        }
    }

    private void prepareStatement(PreparedStatement statement, Object... parameters) throws SQLException {
        int paramLength = parameters.length;
        for (int i = 1; i <= paramLength; i++) {
            statement.setObject(i, parameters[i - 1]);
        }
    }

    public void execQuery(String query, Handler handler, Object... parameters) throws SQLException {
        try (PreparedStatement stm= connection.prepareStatement(query)) {
            prepareStatement(stm, parameters);
            stm.execute();
            ResultSet result = stm.getResultSet();
            handler.handle(result);
        }
    }


    public <T> T execQuery(String query, THandler<T> handler, Object... parameters) throws SQLException {
        try (PreparedStatement stm = connection.prepareStatement(query)) {
            prepareStatement(stm, parameters);
            stm.execute();
            ResultSet result = stm.getResultSet();
            return handler.handle(result);
        }
    }


    public int execUpdate(String update) throws SQLException {
        try (Statement stm = connection.createStatement()) {
            stm.execute(update);
            return stm.getUpdateCount();
        }
    }

    public void execUpdate(String update, PrepareHandler prepare) {
        try {
            PreparedStatement prep = getConnection().prepareStatement(update, Statement.RETURN_GENERATED_KEYS);
            prepare.handle(prep);
            prep.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public long execInsert(String query) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(query,
                Statement.RETURN_GENERATED_KEYS)) {
            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                }
                else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        }
    }

      public <T extends DataSet> void save(T user) {
        try {
            if (cache == null)
                throw new SQLException("Class is not supported");

            Field[] fields = cache.getFields();

            ArrayList<String> fieldsValues = new ArrayList<>();
            for (Field field : fields) {
                fieldsValues.add(ReflectionHelper.getValue(field, user));
            }

            String query = cache.getQueryInsert();

            execQuery(query,
                    result -> {
                    },
                    fieldsValues.toArray());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return;
    }

    public <T extends DataSet> T load(long id, Class<T> clazz) {
        try {
            if (null == cache)
                throw new SQLException("Class is not supported");

            String query = cache.getQuerySelect();

            return (T) execQuery(query,
                    result -> {
                        if (result.next())
                            return getDataSet(result, clazz);
                        else
                            return null;
                    },
                    id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private <T extends DataSet> T getDataSet(ResultSet res, Class<T> clazz) {
        try {
            Constructor<T> ctor = clazz.getDeclaredConstructor();
            Object object = ctor.newInstance();

            ResultSetMetaData resultSetMetaData = res.getMetaData();
            int columnCount = resultSetMetaData.getColumnCount();

            for (int i = 1; i <= columnCount; i++) {
                String column = resultSetMetaData.getColumnName(i);
                Method setter = ReflectionHelper.getSetter(clazz,  column, cache.getMethods());
                if (null != setter) {
                    setter.invoke(object, res.getObject(i));
                }
            }

            return (T) object;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void close() throws Exception {
        connection.close();
    }
}
