package ru.otus.h8.facevalue;

public class BundleOfBills {
    private Nominal nominal;
    private int billsCount;

    public BundleOfBills(Nominal nominal, int billsCount) {
        this.nominal = nominal;
        this.billsCount = billsCount;
    }

    public Nominal getNominal() {
        return nominal;
    }



    public void putFaceValues(int billsCount) {
        this.billsCount += billsCount;
    }

    public boolean getFaceValue() {
        if (isEmpty()) return false;
        billsCount -= 1;
        return true;
    }

    public int getFaceValues() {
        if (isEmpty()) return 0;
        int result = billsCount;
        billsCount = 0;
        return result;
    }

    public void putFaceValue() {
        putFaceValues(1);
    }

    public int getFaceValues(int num) {
        if (isEmpty()) return 0;

        if (billsCount <= num) {
            int result = billsCount;
            billsCount = 0;
            return result;
        } else {
            billsCount -= num;
            return num;
        }

    }

    public int getBillsCount() {
        return billsCount;
    }

    public boolean isEmpty() {

        return billsCount == 0;
    }

    public int getSumm() {
        return billsCount * nominal.asNominal();
    }

    @Override
    public String toString() {

        return nominal + "[" + billsCount + "]";
    }

    @Override
    protected BundleOfBills clone()
    {
        return new BundleOfBills(nominal, billsCount);
    }
}
