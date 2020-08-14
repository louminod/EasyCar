package model.controllers;

import data.tools.DatabaseManager;
import entities.Administrator;
import entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ManageUsersFrameController {

    private Administrator administrator;

    public ManageUsersFrameController(Administrator administrator) {
        this.administrator = administrator;
    }

    /**
     * Get the database users list.
     *
     * @return A list of User.
     */
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        List<User> usersFromDatabase = DatabaseManager.getUsers();
        usersFromDatabase.forEach(user -> {
            if (!user.getUuid().toString().equals(this.administrator.getUuid().toString())) {
                users.add(user);
            }
        });

        return users;
    }

    /**
     * Update an user in the database.
     *
     * @param user The user to update.
     */
    public void updateUser(User user) {
        DatabaseManager.updateUser(user);
    }

    /**
     * Delete an user in the database.
     *
     * @param user The user to delete.
     */
    public void deleteUser(User user) {
        DatabaseManager.deleteUser(user);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ManageUsersFrameController that = (ManageUsersFrameController) o;
        return Objects.equals(administrator, that.administrator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(administrator);
    }

    @Override
    public String toString() {
        return "ManageUsersFrameController{" +
                "administrator=" + administrator +
                '}';
    }
}
