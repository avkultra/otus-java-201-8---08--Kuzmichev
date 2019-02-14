package ru.otus.h15.dataset;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user")
public class UserDataSet extends DataSet {

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private int age;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<PhoneDataSet> phones;

    @OneToOne(cascade = CascadeType.ALL)
    private AddressDataSet address;



    public UserDataSet(String name, int age, List<PhoneDataSet> phones, AddressDataSet address) {
        this.name = name;

        this.age = age;


        this.phones = phones;
        for (PhoneDataSet phone : phones){
            phone.setUser(this);
        }

        this.address = address;
    }

    public UserDataSet(String name, int age){
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

    public void addPhone(PhoneDataSet phone) {
        phones.add(phone);
        phone.setUser(this);
    }
    public void removePhone(PhoneDataSet phone){
            phones.remove(phone);
        }

    public AddressDataSet getAddress(){
            return address;
        }

    public void addAddress(AddressDataSet address) {
        this.address = address;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("User: \n");
        sb.append("  Id: ").append(getId()).append("\n");
        sb.append("  Name: ").append(name).append("\n");
        sb.append("  Address: ").append(address != null ? address.getStreet(): "").append("\n");
        for (PhoneDataSet pds : phones) {
            sb.append("  Phone: ").append(pds.getNumber()).append("\n");
        }
        return sb.toString();
    }
}