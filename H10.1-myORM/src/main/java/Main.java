package ru.otus.h10;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.otus.h10.database.DBService;
import ru.otus.h10.database.DBServiceImpl;
import ru.otus.h10.database.ConnectionHelper;

import java.util.List;
import java.util.Random;

import ru.otus.h10.dataset.UserDataSet;

public class Main {

    private static final int COUNT_USERS = 10;

    private static Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws Exception {

        try (DBService dbService = new DBServiceImpl(ConnectionHelper.getConnection())) {

            DBServiceImpl dbImpl = (DBServiceImpl) dbService;
            dbImpl.setDataSetClass(UserDataSet.class);

            log.info(dbService.getDBData());

            dbService.makeTables();

            dbService.addUsers(createListUsers(COUNT_USERS));

            long id = 5;
            log.info("Пользователь [{}] Имя: {}", id, dbService.getUserName(id));

            List<String> names = dbService.getAllNames();
            log.info("Все имена: {}", names.toString());

            List<UserDataSet> allUsers = dbService.getAllUsers();

//            log.info("Все пользователи:");
//
//            for (UserDataSet user : allUsers) {
//                log.info("    {}", user.toString());
//            }

            UserDataSet userDataSet = new UserDataSet("Alex", 14);
            dbService.save(userDataSet);

            UserDataSet userDataSet2 = new UserDataSet("Pupkin", 18);
            dbService.save(userDataSet2);

            System.out.println("База: \n" + userDataSet.toString());

            UserDataSet userDataSetDb = dbService.load(1,  UserDataSet.class);

            System.out.println("Имя из базы \n" + userDataSetDb.toString());
            System.out.println("Значения введённое и извлечённое: ? " +
                    String.valueOf(userDataSet.equals(userDataSetDb)));

            dbService.dropTables();
        }
    }

    private static UserDataSet[] createListUsers(final int countUsers) {
        UserDataSet[] users = new UserDataSet[countUsers];
        for (int i = 0; i < countUsers; ++i) {
            users[i] = createUser();
        }
        return users;
    }

    private static UserDataSet createUser() {
        Random rand = new Random();
        int index = rand.nextInt(10000);
        int age = 18 + rand.nextInt(90);
        return new UserDataSet("User#" + index, age);
    }
}
