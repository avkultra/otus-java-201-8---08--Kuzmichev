package ru.otus.h8.atm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.otus.h8.facevalue.SelectionModel;

import java.util.ArrayList;
import java.util.List;

public class ATMDepart {

    private static final int NUMBER_OF_ATMS = 10;
    private List<ATMModel> atmList = new ArrayList<>();

    private static final Logger log = LoggerFactory.getLogger(ATMDepart.class);

    public ATMDepart() {
        for (int i = 0; i < NUMBER_OF_ATMS; i++) {
            ATMModel atm = new ATMModel(new MinCountFaceValue());
            atm.load(ATMFactory.createAtmContent());
            atm.saveState();
            atmList.add(atm);
        }
        log.info("{} добавление ATM", atmList.size());
    }

    public int getATMsTotal() {
        if (atmList.isEmpty()) return 0;

        int total = 0;
        for (ATMModel atm : atmList) {
            total += atm.getTotal();
        }
        log.info("Всего ATM: {}", total);
        return total;
    }

    public void addATM(ATMModel atm) {
        atmList.add(atm);
        log.info("Добавление ATM: {}", atm);
    }

    public ATMModel getATM(String name) {
        for (ATMModel atm : atmList) {
            if (atm.getName().equalsIgnoreCase(name)) {
                return atm;
            }
        }
        return null;
    }

    public List<ATMModel> getATMs() {

        return atmList;
    }

    public void restoreOfATMs() {
        log.info("Востановление всех ATM");
        log.info("Баланс: {}", getATMsTotal());
        for (ATMModel atm : atmList) {
            restoreOfATM(atm);
        }
        log.info("Новый баланс: {}", getATMsTotal());
    }

    public void restoreOfATM(ATMModel atm) {
        int atmBalance = 0;
        SelectionModel sm = atm.empty();
        if (null != sm) {
            atm.restoreState();
            atmBalance = sm.getSum();
        }
        atm.load(ATMFactory.createAtmContent());
        log.info("{} Баланс: {}", atm.getName(), atmBalance);
    }
}
