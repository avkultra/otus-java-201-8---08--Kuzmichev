package ru.otus.h12.database;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import ru.otus.h12.dataset.UserDataSet;
import ru.otus.h12.dataset.PhoneDataSet;
import ru.otus.h12.dataset.AddressDataSet;
import ru.otus.h11.dao.UserDataSetDAO;
import ru.otus.h11.dao.AddressDataSetDAO;
import ru.otus.h11.dao.PhoneDataSetDAO;

import java.util.function.Consumer;
import java.util.function.Function;

import java.util.List;


public class DBServiceImpl implements DBService {

    private final SessionFactory sessionFactory;
    public DBServiceImpl() {

    Configuration config = new Configuration();

     config.addAnnotatedClass(UserDataSet.class);
     config.addAnnotatedClass(PhoneDataSet.class);
     config.addAnnotatedClass(AddressDataSet.class);

     config.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
     config.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
     config.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/db_example");
     config.setProperty("hibernate.connection.username", "tully");
     config.setProperty("hibernate.connection.password", "tully");
     config.setProperty("hibernate.show_sql", "true");
     config.setProperty("hibernate.hbm2ddl.auto", "create");
     config.setProperty("hibernate.connection.useSSL", "false");

     config.setProperty("hibernate.enable_lazy_load_no_trans", "true");

    sessionFactory = createSessionFactory(config);
}

    public DBServiceImpl(Configuration configuration) {
        sessionFactory = createSessionFactory(configuration);
    }

    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }
    @Override
    public String getLocalStatus() {
        return runInSession(session -> {
            return session.getTransaction().getStatus().name();
        });
    }

    @Override
    public void save(UserDataSet dataSet) {
        try (Session session = sessionFactory.openSession()) {
            UserDataSetDAO dao = new UserDataSetDAO(session);
            dao.save(dataSet);
        }
    }

    @Override
    public UserDataSet read(long id) {
        return runInSession(session -> {
            UserDataSetDAO dao = new UserDataSetDAO(session);
            return dao.read(id);
        });
    }

    @Override
    public UserDataSet readByName(String name) {
        return runInSession(session -> {
            UserDataSetDAO dao = new UserDataSetDAO(session);
            return dao.readByName(name);
        });
    }

    @Override
    public List<UserDataSet> readAllUsers() {
        return runInSession(session -> {
            UserDataSetDAO dao = new UserDataSetDAO(session);
            return dao.readAll();
        });
    }

    @Override
    public List<PhoneDataSet> readAllPhones() {
        return runInSession(session -> {
            PhoneDataSetDAO dao = new PhoneDataSetDAO(session);
            return dao.readAll();
        });
    }

    @Override
    public List<AddressDataSet> readAllAddresses() {
        return runInSession(session -> {
            AddressDataSetDAO dao = new AddressDataSetDAO(session);
            return dao.readAll();
        });
    }

    @Override
    public void delete(UserDataSet dataSet) {
        runInSession((session) -> {
            UserDataSetDAO dao = new UserDataSetDAO(session);
            dao.delete(dataSet);
        });
    }

    @Override
    public void delete(PhoneDataSet dataSet) {
        runInSession((session) -> {
            PhoneDataSetDAO dao = new PhoneDataSetDAO(session);
            dao.delete(dataSet);
        });
    }

    @Override
    public void delete(AddressDataSet dataSet) {
        runInSession((session) -> {
            AddressDataSetDAO dao = new AddressDataSetDAO(session);
            dao.delete(dataSet);
        });
    }

    @Override
    public void shutdown() {
        sessionFactory.close();
    }

    @Override
    public void close() throws Exception {
        shutdown();
    }

    public Long count() {
        return runInSession(session -> {
            UserDataSetDAO dao = new UserDataSetDAO(session);
            return dao.count();
        });
    }

    private <R> R runInSession(Function<Session, R> function) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            R result = function.apply(session);
            transaction.commit();
            return result;
        }
    }

    private void runInSession(Consumer<Session> consumer) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            consumer.accept(session);
            transaction.commit();
        }
    }
}
