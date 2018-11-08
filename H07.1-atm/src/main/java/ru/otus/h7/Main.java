package ru.otus.h7;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

    private static Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String... args) throws Exception {

        atmModel atm = new atmModel(fillAtmFaceValue(), new MinCountFaceValue());

        log.info("Банкомат:");
        Scanner inputline = new Scanner(System.in).useDelimiter("\n");

        boolean finished = false;

        while(!finished) {

            log.info("Веведите сумму вклада:");

            String line = inputline.next();
            try {

                int summ = Integer.valueOf(line);

                log.trace("Сумма: {}", summ);

                exclude(atm, summ);

                log.info("");

            } catch (NumberFormatException e) {

                String[] command = line.split(" ");

                if ( 3 < command.length || 0 == command.length) {

                    log.info("Неправильный ввод. введите 'h' чтоб получить справку");

                }
                switch (command[0]) {
                    case "s": {
                        state(atm);
                    }
                        break;
                    case "p": {
                        putFaceValue(atm, command[1]);
                    }
                        break;
                    case "e": {
                        finished = true;
                    }
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
                    default:
                        log.info("Неправильный ввод. Введите 'h' чтоб получить справку");
                        break;

                }
            }

        }
        log.info("Конец");
    }

    private static List<Cell> fillAtmFaceValue() {
        List<Cell> cells = new ArrayList<Cell>();

        cells.add(new Cell(5000, 1000));
        cells.add(new Cell(2000, 1000));
        cells.add(new Cell(1000, 100));
        cells.add(new Cell(100, 100));
        cells.add(new Cell(50, 100));

        return cells;
    }

    private static void state(atmModel atm) {
        log.trace("Состояние банкомата:");

        Map<Integer, Integer> bundleOfBills = atm.getState();

        if (bundleOfBills.isEmpty()) {
            log.info("Банкомат пуст");
        } else {
            bundle(bundleOfBills);
        }
    }

    private static void putFaceValue(atmModel atm, String faceValue) {
        try {
            int fv = Integer.valueOf(faceValue);
            atm.putFaceValue(fv);
            log.info("Успешно");
        } catch (NumberFormatException e) {
            log.error("Не правельный номинал внесения.");
        } catch (RuntimeException e) {
            log.error("Ошибка ввода банкноты. " + e.getMessage());
        }
    }

    private static void bundle(Map<Integer, Integer> bundleOfBills) {

        StringBuilder sb = new StringBuilder();

        for (Map.Entry<Integer, Integer> entry : bundleOfBills.entrySet()) {

            int faceValue = entry.getKey();
            int count = entry.getValue();

            sb.append(faceValue).append("[").append(count).append("] ");
        }
        log.info(sb.toString());
    }

    private static void exclude(atmModel atm, int summ) {
        log.trace("Попытка получить сумму{} ...", summ);
        try {
            Map<Integer, Integer> bundleOfBills = atm.exclude(summ);
            if (null == bundleOfBills) {
                log.info("Невозможно выдать запрошенную сумму");
            } else {
                bundle(bundleOfBills);
            }
        } catch (Exception e) {
            log.error("Ошибка внесения вклада. " + e.getMessage());
        }
    }
}
