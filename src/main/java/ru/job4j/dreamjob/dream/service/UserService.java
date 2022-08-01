package ru.job4j.dreamjob.dream.service;

import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.dream.model.User;
import ru.job4j.dreamjob.dream.store.UserDBStore;

import java.util.List;

@Service
public class UserService {
    private final UserDBStore store;

    public UserService(UserDBStore store) {
        this.store = store;
    }

    public List<User> findAll() {
        return store.findAll();
    }

    public User findById(int id) {
        return store.findById(id);
    }

    public User add(User user) {
        return store.add(user);
    }

    public void update(User user) {
        store.update(user);
    }
}
