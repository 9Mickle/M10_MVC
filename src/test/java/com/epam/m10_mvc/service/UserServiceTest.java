package com.epam.m10_mvc.service;

import com.epam.m10_mvc.entity.User;
import com.epam.m10_mvc.storage.UserStorage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private UserStorage userStorage;
    private User user;

    @BeforeEach
    public void setUp() {
        user = new User(1L, "Misha", "misha@mail.ru");
        userStorage.saveUser(user);
    }

    @AfterEach
    public void after() {
        userStorage.deleteUser(user.getId());
    }

    @Test
    void size() {
        assertEquals(1, userService.size());
    }

    @Test
    void findAll() {
        assertEquals(List.of(user), userService.findAll());
    }

    @Test
    void findById() {
        assertEquals(user, userService.findById(user.getId()));
    }

    @Test
    void findByEmail() {
        assertEquals(user, userService.findByEmail(user.getEmail()));
    }

    @Test
    void save() {
        User newUser = new User(2L, "User", "user@mail.ru");
        assertEquals(newUser, userService.save(newUser));
        userStorage.deleteUser(newUser.getId());

    }

    @Test
    void update() {
        String newName = "Mikhail";
        User updatedUser = new User(1L, newName, "misha@mail.ru");
        assertEquals(updatedUser, userService.update(updatedUser));
    }

    @Test
    void delete() {
        assertEquals(user, userService.delete(user.getId()));
    }

    @Test
    void clear() {
        userService.clear();
        assertEquals(0, userStorage.getSizeStorage());
    }
}