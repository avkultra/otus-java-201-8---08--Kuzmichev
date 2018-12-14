package ru.otus.h12.dataset;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "phone")
public class PhoneDataSet extends DataSet {

    @Column(name = "number")
    private String number;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserDataSet user;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<PhoneDataSet> phones = new ArrayList<>();


    public PhoneDataSet(){}

    public PhoneDataSet(String number){
        this.number = number;
    }

    public PhoneDataSet(String number, UserDataSet user, List<PhoneDataSet> phones){
        this.number = number;
        this.user = user;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public UserDataSet getUser() {
        return user;
    }

    public void setUser(UserDataSet user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Phone [id: " + getId() + "; number: " + number + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof PhoneDataSet)) return false;
        return getId() == ((PhoneDataSet) obj).getId();
    }

}