package ru.otus.h8.facevalue;

public enum Nominal {

    V_5000(5000),
    V_2000(2000),
    V_1000(1000),
    V_500(500),
    V_100(100),
    V_50(50);

    private int nominal;

    Nominal(int noml) {

        nominal = noml;
    }

    public int asNominal() {

        return nominal;
    }

    public static Nominal fromString(String strNominal) throws NominalException {

        switch (strNominal) {
            case "5000":
                return V_5000;
            case "2000":
                return V_2000;
            case "1000":
                return V_1000;
            case "500":
                return V_500;
            case "100":
                return V_100;
            case "50":
                return V_50;
            default:
                throw new NominalException();
        }
    }
}
