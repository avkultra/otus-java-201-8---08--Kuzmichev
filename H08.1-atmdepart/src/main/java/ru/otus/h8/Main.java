package ru.otus.h8;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import ru.otus.h8.atm.ATMModel;
import ru.otus.h8.atm.ATMDepart;
import ru.otus.h8.atm.ATMExcludeException;
import ru.otus.h8.facevalue.SelectionModel;
import ru.otus.h8.facevalue.Nominal;

import java.util.List;
import java.util.Scanner;

public class Main {

    //private static Logger log = LoggerFactory.getLogger(Main.class);

    @SuppressWarnings("EmptyCatchBlock")
    public static void main(String... args){

        ATMDepart depart = new ATMDepart();

        System.out.println("Банкоматы:");

        Scanner scanner = new Scanner(System.in).useDelimiter("\n");
        boolean finished = false;

        while(!finished) {
            System.out.println("Введите номер банкомата и сумму вклада");
            String input = scanner.next();
            String[] command = input.split(" ");

            if (1 == command.length) {
                switch (command[0]) {
                    case "e":
                        finished = true;
                        break;
                    case "h": {
                        System.out.println("Банкомат:");

                        System.out.println("Настройки: Число|p Число|s|e|h");

                        System.out.println("  Число    - сумма вклада - 50; 100 ...");

                        System.out.println("  p Номинал - внести в банкомат");

                        System.out.println("  s - Состояние банкомата");

                        System.out.println("  e - выход");

                        System.out.println("");
                    }
                    break;
                    case "s":
                        states(depart.getATMs());
                        break;
                    case "l":
                        list(depart.getATMs());
                        break;
                    case "r":
                        restoreOfATMs(depart);
                        break;
                    default:
                        System.out.println("Неправильный ввод. Введите 'h' чтоб получить справку");
                        break;
                }
            } else if (2 == command.length) {
                try {
                    int summ = Integer.valueOf(command[1]);

                    String atmName = command[0];
                    ATMModel atm = depart.getATM(atmName);

                        if (null == atm) {
                            System.out.println("Неверное имя банкомата: " +  atmName);
                        } else {
                            exclude(atm, summ);
                        }
                    } catch (NumberFormatException e) {
                    System.out.println("Неверная сумма");
                }

            } else if (3 == command.length) {
                try {
                    String comm = command[1];

                    if (!comm.equalsIgnoreCase("p")) {
                        System.out.println("Неправильный ввод. Введите 'h' чтоб получить справку");
                    }
                    String atmName = command[0];

                    ATMModel atm = depart.getATM(atmName);
                    if (null == atm) {

                        System.out.println("Неверное имя банкомата: " + atmName);

                    } else {

                        putFaceValue(atm, Nominal.fromString(command[2]));
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("Неверная сумма");
                }
            } else {
                System.out.println("Неправильный ввод. Введите 'h' чтоб получить справку");
            }
        }
        System.out.println("Конец");
    }

    private static void exclude(ATMModel atm, int sum) {
        System.out.println("Попытка получить сумму " +  sum + "из " +  atm.getName());
        try {
            SelectionModel sm = atm.exclude(sum);
            System.out.println("Выдано: " + sm.toString());
        } catch (ATMExcludeException e) {
            System.out.println("Запрошенная сумма не может быть выдана. " + e);
        } catch (Exception e) {
            System.out.println("Ошибка банкомата "+ e);
        }
    }

    private static void list(List<ATMModel> atmList) {
        System.out.println("Список банкоматов");

        if (atmList.isEmpty()) {
            System.out.println("Список пуст");
        }

        System.out.println("Банкоматы:");
        for (ATMModel atm : atmList) {
            System.out.println(atm.getName());
        }
    }

    private static void states(List<ATMModel> atmList) {
        System.out.println("Состояние банкоматов");

        for (ATMModel atm : atmList) {
            SelectionModel sm = atm.getState();
            System.out.println("Банкомат: " + atm.getName());
            if (sm.isEmpty()) {
                System.out.println("Банкомат " + atm.getName() );
            } else {
                System.out.println("В банкомате " + sm.toString());
            }
        }
    }

    private static void restoreOfATMs(ATMDepart depart) {

        System.out.println("Восcтановление банкоматов");

        depart.restoreOfATMs();

        System.out.println("Восстановно");
        states(depart.getATMs());
    }

    private static void putFaceValue(ATMModel atm, Nominal faceValue) {
        System.out.println("putFaceValue() " + atm + " " + faceValue);

        try {
            atm.putFaceValue(faceValue);

            System.out.println("Принято");
        } catch (IllegalStateException e) {

            System.out.println("Ошибка ввода купюры. "+ e);
        }
    }
}

