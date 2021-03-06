package ru.otus.h16.dbserver.dataset;

import javax.persistence.*;

@Entity
@Table(name = "address")
public class AddressDataSet extends DataSet {

    @Column(name = "street")
    private String street;

    public AddressDataSet(){}

    public AddressDataSet(String street){
        this.street = street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreet() {
        return street;
    }

    @Override
    public String toString() {
        return "Address [id: " + getId() + "; street: " + street + "]";
    }
}
