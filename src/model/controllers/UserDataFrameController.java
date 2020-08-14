package model.controllers;

import data.tools.DatabaseManager;
import entities.User;

import java.util.List;
import java.util.Objects;

public class UserDataFrameController {
    private User user;

    public UserDataFrameController(User user) {
        this.user = user;
    }

    public User getUser() {
        return this.user;
    }

    /**
     * Update an user in the database.
     *
     * @param user The user to update.
     */
    public void updateUser(User user) {
        DatabaseManager.updateUser(user);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDataFrameController that = (UserDataFrameController) o;
        return Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user);
    }

    @Override
    public String toString() {
        return "UserDataFrameController{" +
                "user=" + user +
                '}';
    }
}
