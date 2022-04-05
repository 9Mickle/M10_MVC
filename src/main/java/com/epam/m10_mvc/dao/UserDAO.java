package com.epam.m10_mvc.dao;

import com.epam.m10_mvc.entity.User;
import com.epam.m10_mvc.storage.UserStorage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Слой доступа к хранилищу пользователей.
 */
@Component
@AllArgsConstructor
public class UserDAO {

    private final UserStorage userStorage;

    public int size() {
        return userStorage.getSizeStorage();
    }

    public List<User> findAll() {
        return userStorage.getAllUsers();
    }

    public User findById(Long id) {
        return userStorage.getUserById(id);
    }

    public User findByEmail(String email) {
        return userStorage.getUserByEmail(email);
    }

    public User save(User User) {
        return userStorage.saveUser(User);
    }

    public User update(User User) {
        return userStorage.updateUser(User);
    }

    public User delete(Long id) {
        return userStorage.deleteUser(id);
    }

    public void clear() {
        userStorage.deleteAllUsers();
    }
}
