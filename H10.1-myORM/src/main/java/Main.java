package ru.otus.h10;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.otus.h10.database.DBService;
import ru.otus.h10.database.DBServiceClass;
import ru.otus.h10.database.ConnectionHelper;

import java.util.List;
import java.util.Random;

import ru.otus.h10.dataset.DataSetUser;

public class Main {

    private static final int COUNT_USERS = 10;

    private static Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws Exception {

        try (DBService dbService = new DBServiceClass(ConnectionHelper.getConnection())) {

            log.info(dbService.getDBData());

            dbService.makeTables();

            dbService.addUsers(createListUsers(COUNT_USERS));

            long id = 5;
            log.info("Пользователь [{}] Имя: {}", id, dbService.getUserName(id));

            List<String> names = dbService.getAllNames();
            log.info("Все имена: {}", names.toString());

            List<DataSetUser> allUsers = dbService.getAllUsers();

            log.info("Все пользователи:");

            for (DataSetUser user : allUsers) {
                log.info("    {}", user.toString());
            }

            dbService.dropTables();
        }
    }

    private static DataSetUser[] createListUsers(final int countUsers) {
        DataSetUser[] users = new DataSetUser[countUsers];
        for (int i = 0; i < countUsers; ++i) {
            users[i] = createUser();
        }
        return users;
    }

    private static DataSetUser createUser() {
        Random rand = new Random();
        int index = rand.nextInt(10000);
        int age = 18 + rand.nextInt(90);
        return new DataSetUser("User#" + index, age);
    }
}
