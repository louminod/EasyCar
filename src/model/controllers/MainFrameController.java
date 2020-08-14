package model.controllers;

import data.tools.DatabaseManager;
import entities.Car;
import entities.User;
import entities.references.AccountType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainFrameController {
    private AccountType accountType;
    private User user;

    public MainFrameController(AccountType accountType, User user) {
        this.accountType = accountType;
        this.user = user;
    }

    public AccountType getAccountType() {
        return this.accountType;
    }

    public User getUser() {
        return this.user;
    }

    /**
     * Get all the cars from the database.
     *
     * @return The cars.
     */
    public List<Car> getCars() {
        List<Car> cars = new ArrayList<>();

        for (Car car : DatabaseManager.getCars()) {
            if (!car.isReserved()) {
                cars.add(car);
            }
        }

        return cars;
    }

    /**
     * Delete a car in the database.
     *
     * @param car The car to delete.
     */
    public void deleteCar(Car car) {
        DatabaseManager.deleteCar(car);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MainFrameController that = (MainFrameController) o;
        return accountType == that.accountType &&
                Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountType, user);
    }

    @Override
    public String toString() {
        return "MainFrameController{" +
                "accountType=" + accountType +
                ", user=" + user +
                '}';
    }
}
