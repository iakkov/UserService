package ru.project.iakov.homework2.service;

import ru.project.iakov.homework2.User;
import ru.project.iakov.homework2.dao.UserDao;
import java.util.List;
import java.util.Optional;

public class UserService {
    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public void createUser(User user) {
        userDao.create(user);
    }
    public Optional<User> findById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException();
        } else return userDao.findById(id);
    }
    public List<User> findAll() {
        return userDao.findAll();
    }
    public void update(User user) {
        userDao.update(user);
    }
    public void delete(Long id) {
        userDao.delete(id);
    }
}