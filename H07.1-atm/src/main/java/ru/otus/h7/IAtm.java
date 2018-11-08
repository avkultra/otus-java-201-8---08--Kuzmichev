package ru.otus.h7;

import java.util.List;
import java.util.Map;

public interface IAtm {
    void init(List<Cell> lstCell);
    Map<Integer, Integer> exclude(int sumExclude);
}
