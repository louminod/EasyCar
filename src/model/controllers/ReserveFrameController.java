package model.controllers;

import entities.Car;
import entities.User;

import java.util.Objects;

public class ReserveFrameController {
    private Car car;
    private User user;

    public ReserveFrameController(Car car, User user) {
        this.car = car;
        this.user = user;
    }

    public Car getCar() {
        return this.car;
    }

    public User getUser() {
        return this.user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReserveFrameController that = (ReserveFrameController) o;
        return Objects.equals(car, that.car) &&
                Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(car, user);
    }

    @Override
    public String toString() {
        return "ReserveFrameController{" +
                "car=" + car +
                ", user=" + user +
                '}';
    }
}
