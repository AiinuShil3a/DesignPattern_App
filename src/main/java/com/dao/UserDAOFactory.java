package com.dao;

public class UserDAOFactory {
    public static UserDAOInterface getUserDAO() {
        return new UserDAOImpl();
    }
}
