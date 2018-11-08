package ru.otus.h7;

public class Cell {
    //размер ячейки по умоланию
    public static final int DEFAULT_SIZE = 1000;
    //Размер ячейки
    private final int size;

    //Число банкнот
    private int billsCount;
    //Номинал
    private int faceValue;


    public Cell(int size, int billCount, int faceValue) {
        this.size = size;

        this.faceValue = faceValue;
        this.billsCount = billCount;
    }

    public Cell(int faceValue, int billCount) {
        this.size = DEFAULT_SIZE;

        this.faceValue = faceValue;
        this.billsCount = billCount;
    }

    public Cell(Cell cell) {
        this.size = cell.getSize();

        this.faceValue = cell.getFaceValue();
        this.billsCount = cell.getBillsCount();
    }

    public int getSize() {
        return size;
    }


    public void putFaceValue(int count) {
        this.billsCount += count;

        if (billsCount > size) {
            billsCount = size;
        }
    }

    public void setFaceValue(int FaceValue) {

        this.faceValue = faceValue;
    }

    public int getFaceValue() {

        return faceValue;
    }

    public int getBillsCount() {

        return billsCount;
    }

    public void setBillsCount(int billsCount) {
        this.billsCount = billsCount;
    }

    public boolean isBills() {
        return 0 < billsCount;
    }

    public void pullFaceValue() throws RuntimeException {
        if (billsCount > 0) {
            billsCount = billsCount - 1;
        } else {
            throw new RuntimeException("банкноты закончились");
        }
    }
}
