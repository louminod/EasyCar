package model.controllers;

import data.tools.DatabaseManager;
import data.tools.UsersFileManager;
import entities.User;

public class ConnectFrameController {

    public ConnectFrameController() {
    }

    /**
     * Check if email and password belongs to an user in database.
     *
     * @param email    User email.
     * @param password User password.
     * @return User if exist, null instead.
     */
    public User connect(String email, String password) {
        return DatabaseManager.connectUser(email, password);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
