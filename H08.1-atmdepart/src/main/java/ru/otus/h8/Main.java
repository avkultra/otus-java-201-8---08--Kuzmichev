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

    private static Logger log = LoggerFactory.getLogger(Main.class);

    @SuppressWarnings("EmptyCatchBlock")
    public static void main(String... args){

        ATMDepart depart = new ATMDepart();

        log.info("Банкоматы:");

        Scanner scanner = new Scanner(System.in).useDelimiter("\n");
        boolean finished = false;

        while(!finished) {
            log.info("Введите номер банкомата и сумму вклада");
            String input = scanner.next();
            String[] command = input.split(" ");

            if (1 == command.length) {
                switch (command[0]) {
                    case "e":
                        finished = true;
                        break;
                    case "h": {
                        log.info("Банкомат:");

                        log.info("Настройки: Число|p Число|s|e|h");

                        log.info("  Число    - сумма вклада - 50; 100 ...");

                        log.info("  p Номинал - внести в банкомат");

                        log.info("  s - Состояние банкомата");

                        log.info("  e - выход");

                        log.info("");
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
                        log.info("Неправильный ввод. Введите 'h' чтоб получить справку");
                        break;
                }
            } else if (2 == command.length) {
                try {
                    int summ = Integer.valueOf(command[1]);

                    String atmName = command[0];
                    ATMModel atm = depart.getATM(atmName);

                        if (null == atm) {
                            log.info("Неверное имя банкомата: {}", atmName);
                        } else {
                            exclude(atm, summ);
                        }
                    } catch (NumberFormatException e) {
                    log.warn("Неверная сумма");
                }

            } else if (3 == command.length) {
                try {
                    String comm = command[1];

                    if (!comm.equalsIgnoreCase("p")) {
                        log.info("Неправильный ввод. Введите 'h' чтоб получить справку");
                    }
                    String atmName = command[0];

                    ATMModel atm = depart.getATM(atmName);
                    if (null == atm) {

                        log.info("Неверное имя банкомата: {}", atmName);

                    } else {

                        putFaceValue(atm, Nominal.fromString(command[2]));
                    }
                } catch (IllegalArgumentException e) {
                    log.warn("Неверная сумма");
                }
            } else {
                log.info("Неправильный ввод. Введите 'h' чтоб получить справку");
            }
        }
        log.info("Конец");
    }

    private static void exclude(ATMModel atm, int sum) {
        log.trace("Попытка получить сумму {} из ()", sum, atm.getName());
        try {
            SelectionModel sm = atm.exclude(sum);
            log.info("Выдано: {}", sm.toString());
        } catch (ATMExcludeException e) {
              log.error("Запрошенная сумма не может быть выдана. ", e);
        } catch (Exception e) {
              log.error("Ошибка банкомата ", e);
        }
    }

    private static void list(List<ATMModel> atmList) {
        log.info("Список банкоматов");

        if (atmList.isEmpty()) {
            log.info("Список пуст");
        }

        log.info("Банкоматы:");
        for (ATMModel atm : atmList) {
            log.info(" {}", atm.getName());
        }
    }

    private static void states(List<ATMModel> atmList) {
        log.info("Состояние банкоматов");

        for (ATMModel atm : atmList) {
            SelectionModel sm = atm.getState();
            log.info("Банкомат: {}", atm.getName());
            if (sm.isEmpty()) {
                log.info("Банкомат {} пуст", atm.getName());
            } else {
                log.info("В банкомате {} пуст", sm.toString());
            }
        }
    }

    private static void restoreOfATMs(ATMDepart depart) {

        log.info("Восcтановление банкоматов");

        depart.restoreOfATMs();

        log.info("Восстановно");
        states(depart.getATMs());
    }

    private static void putFaceValue(ATMModel atm, Nominal faceValue) {
        log.trace("putFaceValue() {} {}", atm,  faceValue);

        try {
            atm.putFaceValue(faceValue);

            log.info("Принято");
        } catch (IllegalStateException e) {

            log.error("Ошибка ввода купюры. ", e);
        }
    }
}

