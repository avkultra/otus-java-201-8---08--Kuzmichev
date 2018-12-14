package ru.otus.h11;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.h11.database.DBService;
import ru.otus.h11.dataset.PhoneDataSet;
import ru.otus.h11.dataset.UserDataSet;
import ru.otus.h11.dataset.AddressDataSet;
import ru.otus.h11.database.DBServiceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Main {

      public static void main(String[] args) throws Exception {


        try (DBService dbService = new DBServiceImpl()) {

            String status = dbService.getLocalStatus();

            System.out.println("Status: " + status);

            dbService.save(new UserDataSet("USER_#1", 18,
                    new ArrayList<>(Arrays.asList(new PhoneDataSet("7777777"))),
                                            new AddressDataSet("Nevskiy prospect")));
            dbService.save(new UserDataSet("USER_#2", 36,
                                            new ArrayList<>(Arrays.asList(new PhoneDataSet("5555555"))),
                                            new AddressDataSet("Voznesenskiy prospect")));

            UserDataSet user = new UserDataSet("USER_#3", 72,
                                                new ArrayList<>(Arrays.asList(new PhoneDataSet("2222222"))),
                                                new AddressDataSet("Sennaya square"));
            user.addPhone(new PhoneDataSet("1111111"));
            user.addPhone(new PhoneDataSet("6666666"));
            dbService.save(user);

            UserDataSet dataSet = dbService.read(1);
            System.out.println("UserDataSet id = 1: " + dataSet);

            dataSet = dbService.readByName("USER_#1");

            System.out.println("UserDataSet name= 'USER_#1: " + dataSet);

            printDataBase(dbService);

            dbService.delete(user);

            printDataBase(dbService);
        }
    }

    private static void printDataBase(DBService dbService) {
        System.out.println("Все пользователи");
        List<UserDataSet> userDataSets = dbService.readAllUsers();
        for (UserDataSet dataSet : userDataSets) {
            System.out.println(dataSet);
        }

        System.out.println("Все телефоны");
        List<PhoneDataSet> phoneDataSets = dbService.readAllPhones();
        for (PhoneDataSet dataSet : phoneDataSets) {
            System.out.println(dataSet);
        }

        System.out.println("Все адреса");
        List<AddressDataSet> addressDataSets = dbService.readAllAddresses();
        for (AddressDataSet dataSet : addressDataSets) {
            System.out.println(dataSet);;
        }
    }
}
