package com.dao;

import com.model.User;

public interface UserDAOInterface {
    User getUserByUsername(String username);
    void addUser(User user);
    boolean isUsernameTaken(String username);
}
