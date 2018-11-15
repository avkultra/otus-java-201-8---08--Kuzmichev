package ru.otus.h8.atm;

import ru.otus.h8.facevalue.BundleOfBills;
import ru.otus.h8.facevalue.Nominal;

public class Cell extends BundleOfBills {

    public static final int DEFAULT_SIZE = 1000;
    private final int size;

    public Cell(Nominal nominal) {
        super(nominal, 0);
        this.size = DEFAULT_SIZE;
    }

    public Cell(Nominal nominal, int size) {
        super(nominal, 0);
        this.size = size;
    }

    private Cell(Nominal nominal, int size, int numberOfBanknotes) {
        super(nominal, numberOfBanknotes);
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public int loadFaceValues(int numLoad) {
        int numInCell = getBillsCount();
        if (numInCell + numLoad > size) {
            int loaded = size - (numInCell + numLoad);
            putFaceValues(loaded);
            return loaded;
        } else {
            putFaceValues(numLoad);
            return numLoad;
        }
    }

    public void pullFaceValue() throws RuntimeException {
        if (!getFaceValue()) {
            throw new RuntimeException("Закончились деньги");
        }
    }

    @Override
    protected Cell clone() {
        return new Cell(getNominal(), size, getBillsCount());
    }
}
