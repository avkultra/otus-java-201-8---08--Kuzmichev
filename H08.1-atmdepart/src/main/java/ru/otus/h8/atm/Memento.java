package ru.otus.h8.atm;

import java.util.ArrayList;
import java.util.List;

public class Memento {
    private  final List<Cell> state;

    Memento(List<Cell> state) {
        if (null == state)
            throw new IllegalStateException("Error on Momento");
        this.state = new ArrayList<>(state.size());
        for (Cell c : state )
        {
           this.state.add(c.clone());
        }
    }

    List<Cell> getState() {
        List <Cell> state = new ArrayList<>(this.state.size());
        for(Cell c: state)
        {
            state.add(c.clone());
        }
        return state;
    }
}
