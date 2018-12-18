package ru.otus.h8.atm;

import java.util.List;
import ru.otus.h8.facevalue.SelectionModel;

public interface ExcludeStrategy {
    SelectionModel exclude(List<Cell> cellList, int sumExclude);
}