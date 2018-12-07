package ru.otus.h7;

import java.util.*;

public class MinCountFaceValue implements ExcludeStrategy {

    private List<Cell> lstCells;

    public MinCountFaceValue() {
    }

 //   @Override
//    public void init(List<Cell> lstCell) {
//        this.lstCells = lstCell;
//    }

    @Override
    public Map<Integer, Integer> exclude(int summExclude, List<Cell> inLstCells) {

        lstCells = inLstCells;

        Map<Integer, Integer> bundleOfBills = new TreeMap<Integer, Integer>();

        int summ = summExclude;

        for (Cell cell : lstCells) {
            int faceValue = cell.getFaceValue();
            while ( 0 < summ && faceValue <= summ && cell.isBills()) {
                Integer num = bundleOfBills.getOrDefault(faceValue, 0);
                bundleOfBills.put(faceValue, num + 1);
                summ = summ - faceValue;
                cell.getFaceValue();
            }
            if (0 == summ) break;
        }

        if (0 < summ) {
            return null;
        } else {
            return  bundleOfBills;
        }
    }
}