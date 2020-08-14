package model.controllers;

import data.tools.DatabaseManager;
import entities.Car;
import entities.Reservation;
import entities.references.ReservationStatus;

import java.util.ArrayList;
import java.util.List;

public class ReturnCarFrameController {

    public ReturnCarFrameController() {

    }

    /**
     * Get the reservations with the ReservationStatus ACCEPTED.
     *
     * @return The reservations.
     */
    public List<Reservation> getActiveReservations() {
        List<Reservation> reservations = new ArrayList<>();
        List<Reservation> allReservations = DatabaseManager.getReservations();

        for (Reservation reservation : allReservations) {
            if (reservation.getStatus() == ReservationStatus.ACCEPTED) {
                reservations.add(reservation);
            }
        }

        return reservations;
    }

    /**
     * Update a car in the database.
     *
     * @param car The car to update.
     */
    public void updateCar(Car car) {
        DatabaseManager.updateCar(car);
    }

    /**
     * Update one reservation in the database.
     *
     * @param reservation The reservation to update.
     */
    public void updateReservation(Reservation reservation) {
        DatabaseManager.updateReservation(reservation);
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
