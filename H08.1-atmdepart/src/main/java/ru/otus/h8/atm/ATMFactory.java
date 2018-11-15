package ru.otus.h8.atm;
import ru.otus.h8.facevalue.Nominal;
import ru.otus.h8.facevalue.SelectionModel;

public class ATMFactory {

    private static final Nominal[] DEFAULT_NOMINALS = new Nominal[] {
            Nominal.V_5000,
            Nominal.V_2000,
            Nominal.V_1000,
            Nominal.V_500,
            Nominal.V_100,
            Nominal.V_50
    };

    private static final int DEFAULT_COUNT_FACEVALUES = 1000;

    private ATMFactory() {}

    public static SelectionModel createAtmContent(Nominal[] nominals, int billCount) {
        SelectionModel sm = new SelectionModel();
        for(int i = 0; i < nominals.length; i++) {
            sm.putFaceValues(nominals[i], billCount);
        }
        return sm;
    }

    public static SelectionModel createAtmContent() {
        return createAtmContent(DEFAULT_NOMINALS, DEFAULT_COUNT_FACEVALUES);
    }

    public static SelectionModel createAtmContent(int billCount) {
        return createAtmContent(DEFAULT_NOMINALS, billCount);
    }
}