package model.controllers;

import data.tools.DatabaseManager;
import entities.Customer;
import entities.Employee;
import entities.Reservation;
import entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ReservationsFrameController {

    private User user;

    public ReservationsFrameController(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    /**
     * Get the user reservations.
     *
     * @return The reservations.
     */
    public List<Reservation> getUserReservations() {
        List<Reservation> reservations = new ArrayList<>();
        if (Customer.class.equals(this.user.getClass())) {
            reservations = DatabaseManager.getCustomerReservations((Customer) this.user);
        }
        if (Employee.class.equals(this.user.getClass())) {
            reservations = DatabaseManager.getReservations();
        }
        return reservations;
    }

    /**
     * Update a reservation in the database.
     *
     * @param reservation The reservation to update.
     */
    public void updateReservation(Reservation reservation) {
        DatabaseManager.updateReservation(reservation);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReservationsFrameController that = (ReservationsFrameController) o;
        return Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user);
    }

    @Override
    public String toString() {
        return "ReservationsFrameController{" +
                "user=" + user +
                '}';
    }
}
