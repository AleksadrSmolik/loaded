package ru.saptex.Service;

import org.hibernate.SessionFactory;
import ru.saptex.Dao.Dao;
import ru.saptex.Dao.impl.UserDaoImpl;
import ru.saptex.model.User;

import java.util.List;

public class UserService {
    private final Dao<User, Integer> userDao;

    public UserService(SessionFactory factory) {
        this.userDao = new UserDaoImpl(factory);

    }

    public User findById(int id) {
        return userDao.findById(id);
    }

    public List<User> findAll() {
        return userDao.findAll();
    }

    public void save(User user) {
        userDao.save(user);
    }

    public void update(User user) {
        userDao.update(user);
    }

    public void delete(User user) {
        userDao.delete(user);
    }

}
