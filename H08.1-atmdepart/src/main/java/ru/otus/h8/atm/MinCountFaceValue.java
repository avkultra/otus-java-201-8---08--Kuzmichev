package ru.otus.h8.atm;

import ru.otus.h8.facevalue.SelectionModel;
import ru.otus.h8.facevalue.Nominal;

import java.util.List;

public class MinCountFaceValue implements IExclude {

    @Override
    public SelectionModel exclude(List<Cell> cells, int summExclude) {

        SelectionModel sm = new SelectionModel();

        int summleft = summExclude;

        for (Cell cell : cells) {
            Nominal nominal = cell.getNominal();
            while (0 < summleft && summleft >=  nominal.asNominal() && !cell.isEmpty()) {
                cell.pullFaceValue();
                sm.putFaceValue(nominal);
                summleft = summleft - nominal.asNominal();
            }
            if (0 == summleft) break;
        }

        if (0 < summleft) {
            return null;
        } else {
            return sm;
        }
    }
}
