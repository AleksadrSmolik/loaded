package ru.saptex.Dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ru.saptex.Dao.Dao;
import ru.saptex.model.User;

import java.util.List;

public class UserDaoImpl implements Dao<User, Integer> {


    private final SessionFactory factory;

    public UserDaoImpl(SessionFactory factory) {
        this.factory = factory;

    }

    @Override
    public User findById(Integer id) {
        try (Session session = factory.openSession()) {
            return  session.get(User.class, id);
        }
    }

    @Override
    public List<User> findAll() {
        try(Session session = factory.openSession()) {
            return session.createQuery("User From", User.class).list();
        }

    }

    @Override
    public void save(User user) {
        try(Session session = factory.openSession()) {
            final Transaction transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        }
    }

    @Override
    public void update(User user) {
        try(Session session = factory.openSession()) {
            final  Transaction transaction = session.beginTransaction();
            session.update(user);
            transaction.commit();
        }

    }

    @Override
    public void delete(User user) {
        try(Session session = factory.openSession()) {
            final Transaction transaction = session.beginTransaction();
            session.delete(user);
            transaction.commit();
        }
    }
}
