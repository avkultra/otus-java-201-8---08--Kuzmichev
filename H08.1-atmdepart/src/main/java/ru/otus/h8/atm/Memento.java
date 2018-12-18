package ru.otus.h8.atm;

import java.util.ArrayList;
import java.util.List;

public class Memento {
    private final List<Cell> state;

    Memento(List<Cell> state) {
        this.state = state;

    }

    List<Cell> getState() {
        return state;
    }
}
