package ru.otus.H031;

import java.util.*;

public class MyArrayList<T> implements List<T> {
    private int size = 0;
    private int start_capasity = 7;
    private Object[] array;

    public MyArrayList() {

        this.array = new Object[start_capasity];
    }

    public MyArrayList(int capacity) {
        if (capacity > 0) {
            this.array = new Object[capacity];
        } else if (capacity == 0) {
            this.array = new Object[];
        } else {
            throw new IllegalArgumentException("Wrong capacity: "+
                    capacity);
        }
    }

    public int size() {
        return this.size;
    }

    public boolean isEmpty()
    {
       return (0 == size());
    }


}
