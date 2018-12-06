package ru.otus.h10.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.otus.h10.dataset.UserDataSet;
import ru.otus.h10.executor.Executor;


import ru.otus.h10.dataset.DataSet;



import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.HashMap;
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
    private Map<Class<? extends DataSet>, DBCache> dbCaches = new HashMap<>();

    private Logger log = LoggerFactory.getLogger(DBServiceImpl.class);

    public DBServiceImpl(Connection connection) {
        this.connection = connection;
    }

    private Connection getConnection() {
        return connection;
    }

    public void setDataSetClass(Class... clazz) throws SQLException {
        for (Class cl : clazz) {
            DBCache cache = DBCache.createCache(cl);
            if (null == cache) {
                throw new SQLException("ошибка инициализации класса " + cl.getSimpleName());
            }
            dbCaches.put(cl, cache);
        }
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
        Executor executor = new Executor(getConnection(), getCache(entity.getClass()));
        executor.save(entity);
        return;
    }

    @Override
    public <T extends DataSet> T load(long id, Class<T> clazz) throws SQLException {
        Executor executor = new Executor(getConnection(), getCache(clazz));
        T t = executor.load(id, clazz);
        return t;
    }


    protected DBCache getCache(Class clazz) {

        return dbCaches.get(clazz);
    }
}
