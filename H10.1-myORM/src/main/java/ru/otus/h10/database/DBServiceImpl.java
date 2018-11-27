package ru.otus.h10.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.otus.h10.dataset.UserDataSet;
import ru.otus.h10.executor.Executor;

import ru.otus.h10.Helpers.DataSetHelper;
import ru.otus.h10.Helpers.QueryHelper;

import ru.otus.h10.dataset.DataSet;

import java.sql.ResultSetMetaData;

import java.lang.reflect.Field;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DBServiceImpl implements DBService {

    private static final String DROP_TABLE_USER = "DROP TABLE  IF EXISTS user";
    private static final String CREATE_TABLE_USER = "CREATE TABLE user (id BIGINT(20)  NOT NULL AUTO_INCREMENT, name VARCHAR(255), age INT(3), primary key (id))";

    private static final String INSERT_USER = "INSERT INTO user (name, age) VALUES(?,?)";

    private static final String SELECT_USER = "SELECT * FROM user WHERE id=%s";
    private static final String SELECT_USER_NAME = "SELECT name FROM user WHERE id=%s";
    private static final String SELECT_ALL_USERS = "SELECT * FROM user";
    private static final String SELECT_ALL_USERS_NAME = "SELECT name FROM user";

    private final Connection connection;

    private Logger log = LoggerFactory.getLogger(DBServiceImpl.class);

    public DBServiceImpl(Connection connection) {
        this.connection = connection;
    }

    private Connection getConnection() {
        return connection;
    }

    @Override
    public String getDBData() {
        try {
            return "Сторока соединения: " + getConnection().getMetaData().getURL() + "\n" +
                    "Имя БД: "     + getConnection().getMetaData().getDatabaseProductName() + "\n" +
                    "Версия: "  + getConnection().getMetaData().getDatabaseProductVersion() + "\n" +
                    "Драйвер: "      + getConnection().getMetaData().getDriverName();
        } catch (SQLException e) {
            log.error("Ошибка запроса о базе данных ", e);
            return e.getMessage();
        }
    }

    @Override
    public void makeTables() throws SQLException {
        Executor exec = new Executor(getConnection());
        exec.execUpdate(DROP_TABLE_USER);
        exec.execUpdate(CREATE_TABLE_USER);
        log.info("Таблица создана");
    }

    @Override
    public void addUsers(UserDataSet... users) throws SQLException {
        try {
            Executor exec = new Executor(getConnection());

            getConnection().setAutoCommit(false);

            exec.execUpdate(INSERT_USER, prepare -> {

                for (UserDataSet user : users) {
                    prepare.setString(1, user.getName());
                    prepare.setInt(2, user.getAge());
                    prepare.executeUpdate();
                    ResultSet rs = prepare.getGeneratedKeys();
                    if (rs.next()) {
                        user.setId(rs.getLong(1));
                    } else {
                        throw new RuntimeException("Не могу получить строку id");
                    }
                }
            });
            getConnection().commit();
        } catch (SQLException e){
            getConnection().rollback();
        } finally {
            getConnection().setAutoCommit(true);
        }
    }

    @Override
    public String getUserName(long id) throws SQLException {
        Executor exec = new Executor(getConnection());
        return exec.execQuery(String.format(SELECT_USER_NAME, id),
                result -> {
                    result.next();
                    String name = result.getString("name");
                    log.trace("User[{}] name: {}", id, name);
                    return name;
                });
    }

    @Override
    public List<String> getAllNames() throws SQLException {
        Executor exec = new Executor(getConnection());
        return exec.execQuery(SELECT_ALL_USERS_NAME, result -> {
            List<String> names = new ArrayList<>();

            while (!result.isLast()) {
                result.next();
                names.add(result.getString("name"));
            }
            return names;
        });
    }

    @Override
    public UserDataSet getUser(long id) throws SQLException {
        Executor exec = new Executor(getConnection());
        return exec.execQuery(String.format(SELECT_USER, id),
                result -> {
                    result.next();
                    UserDataSet user = new UserDataSet();
                    user.setName(result.getString("name"));
                    user.setAge(result.getInt("age"));
                    log.trace("User[{}] name: {}; age: {}", id, user.getName(), user.getAge());
                    return user;
                });
    }

    @Override
    public List<UserDataSet> getAllUsers() throws SQLException {
        Executor exec = new Executor(getConnection());

        return exec.execQuery(SELECT_ALL_USERS, result -> {
            List<UserDataSet> users = new ArrayList<>();

            while (!result.isLast()) {
                result.next();
                UserDataSet user = new UserDataSet();
                user.setId(result.getLong("id"));
                user.setName(result.getString("name"));
                user.setAge(result.getInt("age"));
                users.add(user);
            }
            return users;
        });
    }

    @Override
    public void dropTables() throws SQLException {
        Executor exec = new Executor(getConnection());

        exec.execUpdate(DROP_TABLE_USER);

        log.info("Удаление таблицы");
    }

    @Override
    public void close() throws Exception {
        connection.close();
        log.info("Соединение закрыто.");
    }

    //////////////////////////////////////////

    @Override
    public <T extends DataSet> void save(T entity) {
        String tableName = TablesEnum.getTableName(entity.getClass());
        if (tableName == null) {
            return;
        }

        Map<String, String> objectDataMap =
                DataSetHelper.getObjectData(entity);

        List<String> names = new ArrayList<>(objectDataMap.size());
        List<String> values = new ArrayList<>(objectDataMap.size());

        objectDataMap.forEach((name, value) -> {
            names.add(name);
            values.add(value);
        });
        Executor executor = new Executor(connection);
        final String query;
        if (DataSetHelper.isNewEntity(entity)) {
            query = QueryHelper.insertQuery(tableName,
                    names,
                    values);
            try {
                long id = executor.execInsert(query);
                entity.setId(id);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            query = QueryHelper.updateQuery(
                    tableName,
                    names,
                    values,
                    entity.getId());

            try {
                executor.execUpdate(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public <T extends DataSet> T load(long id, Class<T> clazz) throws SQLException {
        Executor executor = new Executor(connection);

        final String tableName = TablesEnum.getTableName(clazz);
        final String query = QueryHelper.selectQuery(tableName,
                UserDataSet.ID_NAME,
                String.valueOf(id));

        return executor.execQuery(query, resultSet -> {
            T instance = null;
            try {
                instance = clazz.getConstructor().newInstance();
                ResultSetMetaData metaData = resultSet.getMetaData();

                Map<String, Field> fieldsMap = DataSetHelper.getObjectFields(instance);

                while (resultSet.next()) {
                    for (int i = 1; i <= metaData.getColumnCount(); ++i) {
                        String name = metaData.getColumnName(i);
                        Field currentField = fieldsMap.get(name);
                        if (currentField == null) {
                            throw new RuntimeException("Field " + name + " not founded");
                        }
                        currentField.setAccessible(true);

                        Object value = getValue(
                                resultSet,
                                metaData.getColumnType(i),
                                i);

                        currentField.set(instance, value);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return instance;
        });
    }

    private Object getValue(ResultSet resultSet, int type, int index) {
        try {
            switch (type) {
                case 12:
                    return resultSet.getString(index);
                case 2:
                case -5:
                    return resultSet.getLong(index);
                case 5:
                case 4:
                    return resultSet.getInt(index);
                default:
                    throw new RuntimeException("ERROR TYPE " + type + " BY INDEX " + index);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private enum TablesEnum {
        USER_DATA_SET(UserDataSet.class, "user"),
        DEFAULT_DATA_SET(DataSet.class, null);

        private final Class clazz;
        private final String tableName;

        TablesEnum(Class clazz, String tableName) {
            this.clazz = clazz;
            this.tableName = tableName;
        }

        static String getTableName(Class clazz) {
            for (TablesEnum current : TablesEnum.values()) {
                if (current.clazz.equals(clazz)) {
                    return current.tableName;
                }
            }
            return null;
        }
    }

}
