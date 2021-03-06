package ru.otus.h16.dbserver.dao;

import org.hibernate.Session;
import ru.otus.h16.dbserver.dataset.AddressDataSet;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class AddressDataSetDAO {
    private Session session;

    public AddressDataSetDAO(Session session) {
        this.session = session;
    }

    public void save(AddressDataSet dataSet) {
        session.save(dataSet);
    }

    public AddressDataSet read(long id) {
        return session.load(AddressDataSet.class, id);
    }

    public List<AddressDataSet> readAll() {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<AddressDataSet> criteria = builder.createQuery(AddressDataSet.class);
        criteria.from(AddressDataSet.class);
        return session.createQuery(criteria).list();
    }

    public void delete(AddressDataSet dataSet) {
        session.delete(dataSet);
    }
}
