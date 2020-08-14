package model.controllers;

import data.tools.DatabaseManager;
import entities.User;
import entities.references.AccountType;

import java.util.Objects;

public class AddUserFrameController {
    private AccountType accountType;

    public AddUserFrameController(AccountType accountType) {
        this.accountType = accountType;
    }

    /**
     * Create an user in the database.
     *
     * @param user The user to create.
     */
    public void createUser(User user) {
        DatabaseManager.writeUser(user);
    }

    /**
     * Get the user account type.
     *
     * @return The AccountType.
     */
    public AccountType getAccountType() {
        return this.accountType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddUserFrameController that = (AddUserFrameController) o;
        return accountType == that.accountType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountType);
    }

    @Override
    public String toString() {
        return "AddUserFrameController{" +
                "accountType=" + accountType +
                '}';
    }
}
