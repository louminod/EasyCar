package model.controllers;

import data.tools.DatabaseManager;
import entities.Car;
import entities.Reservation;

import java.util.Objects;

public class PaymentFrameController {
    private Reservation reservation;

    public PaymentFrameController(Reservation reservation) {
        this.reservation = reservation;
    }

    public Reservation getReservation() {
        return this.reservation;
    }

    /**
     * Create a new reservation in the database.
     */
    public void registerReservation() {
        DatabaseManager.writeReservation(this.reservation);
    }

    public void updateCar(Car car) {
        DatabaseManager.updateCar(car);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentFrameController that = (PaymentFrameController) o;
        return Objects.equals(reservation, that.reservation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reservation);
    }

    @Override
    public String toString() {
        return "PaymentFrameController{" +
                "reservation=" + reservation +
                '}';
    }
}
