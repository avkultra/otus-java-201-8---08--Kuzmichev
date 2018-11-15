package ru.otus.h7;

import java.util.*;

public class AtmModel {

    private List<Cell> cells = new ArrayList<Cell>();
    private IExclude iExclude;

    public AtmModel() {
    }

    public AtmModel(List<Cell> cells, IExclude iExclude) {
        this.cells = cells;
        this.iExclude = iExclude;

        this.cells.sort((o1, o2) -> o2.getFaceValue() - o1.getFaceValue());

        iExclude.init(this.cells);
    }

    public void setIatm(IExclude iatm) {
        this.iExclude = iExclude;
    }

    public Map<Integer, Integer> exclude(int sumOfexclude) {
        if (cells.isEmpty() || null == iExclude) throw new IllegalStateException("Банкомат пуст");
        List<Cell> tmp = copy(cells);
        iExclude.init(cells);
        Map<Integer, Integer> bundleOfBills = iExclude.exclude(sumOfexclude);
        if (null == bundleOfBills) {
            cells = tmp;
            return null;
        } else {
            return bundleOfBills;
        }
    }

    public void fillAtm(Map<Integer, Integer> bundleOfBills) {
        for (Cell cell : cells) {
            if (bundleOfBills.containsKey(cell.getFaceValue())) {
                cell.putFaceValue(bundleOfBills.get(cell.getFaceValue()));
            }
        }
    }

    public Map<Integer, Integer> getState() {
        Map<Integer, Integer> atmCells = new TreeMap<Integer, Integer>();
        for (Cell cell : cells) {
            atmCells.put(cell.getFaceValue(), cell.getBillsCount());
        }
        return atmCells;
    }



    public void putFaceValue(int faceValue, int billCount) {
        for(Cell cell : cells) {
            if (cell.getFaceValue() == faceValue) {
                cell.putFaceValue(billCount);
                break;
            }
        }
    }

    public void putFaceValue(int faceValue) throws RuntimeException {
        for(Cell cell : cells) {
            if (cell.getFaceValue() == faceValue) {
                if (cell.getBillsCount() == cell.getSize()) {
                    throw new IllegalStateException("Банкомат полон");
                }
                cell.putFaceValue(1);
                break;
            }
        }
    }

    private List<Cell> copy(List<Cell> cells) {
        List<Cell> cls = new ArrayList<Cell>();
        for (Cell cell : cells) {
            cls.add(new Cell(cell));
        }
        return cls;
    }
}