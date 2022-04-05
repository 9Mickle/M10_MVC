package com.epam.m10_mvc.service;

import com.epam.m10_mvc.dao.UserDAO;
import com.epam.m10_mvc.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final UserDAO userDAO;

    public int size() {
        return userDAO.size();
    }

    public List<User> findAll() {
        return userDAO.findAll();
    }

    public User findById(Long id) {
        return userDAO.findById(id);
    }

    public User findByEmail(String email) {
        return userDAO.findByEmail(email);
    }

    public User save(User user) {
        return userDAO.save(user);
    }

    public User update(User user) {
        return userDAO.update(user);
    }

    public User delete(Long id) {
        return userDAO.delete(id);
    }

    public void clear() {
        userDAO.clear();
    }
}
