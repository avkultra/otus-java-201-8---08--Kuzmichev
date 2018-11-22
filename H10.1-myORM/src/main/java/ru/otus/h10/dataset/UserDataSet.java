package ru.otus.h10.dataset;

public class UserDataSet extends DataSet {

    private String name;
    private int age;

    public UserDataSet(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public UserDataSet() {
    }

    public void setName(String name) { this.name = name;
    }

    public void setAge(int age) { this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {

        return age;
    }

    @Override
    public String toString() {

        return "[" + getId() + ", " + getName() + ", " + getAge() + "]";
    }
}