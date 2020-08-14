package view.frames;

import entities.Car;
import entities.Reservation;
import entities.references.ReservationStatus;
import model.controllers.ReturnCarFrameController;
import tools.Tool;
import view.CustomFrame;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class ReturnCarFrame extends JFrame implements CustomFrame {

    private JPanel panel;
    private JComboBox<Reservation> cmbReservations;
    private JTextField txtKilometers;

    private ReturnCarFrameController controller;

    public ReturnCarFrame(ReturnCarFrameController controller) {
        this.controller = controller;
    }

    @Override
    public void buildFrame(String title, int width, int height) {
        this.setTitle(title);
        this.setSize(width, height);
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);

        this.buildComponents();
    }

    @Override
    public void buildComponents() {
        this.panel = new JPanel(new GridLayout(3, 2));
        this.cmbReservations = new JComboBox<>();
        for (Reservation reservation : this.controller.getActiveReservations()) {
            this.cmbReservations.addItem(reservation);
        }
        this.txtKilometers = new JTextField();
        JButton btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(event -> hideFrame());
        JButton btnValidate = new JButton("Validate");
        btnValidate.addActionListener(event -> {
            Reservation reservation = (Reservation) this.cmbReservations.getSelectedItem();
            Car car = reservation.getCar();
            car.setKilometers(Integer.parseInt(this.txtKilometers.getText()));
            car.setReserved(false);
            reservation.setStatus(ReservationStatus.ENDED);
            this.controller.updateCar(car);
            this.controller.updateReservation(reservation);
            this.hideFrame();
        });

        this.panel.add(new JLabel("Select reservation:"));
        this.panel.add(this.cmbReservations);
        this.panel.add(new JLabel("Car kilometers:"));
        this.panel.add(this.txtKilometers);
        this.panel.add(btnCancel);
        this.panel.add(btnValidate);
        this.add(panel, BorderLayout.CENTER);
    }

    @Override
    public void showFrame() {
        this.setVisible(true);
    }

    @Override
    public void hideFrame() {
        this.setVisible(false);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReturnCarFrame that = (ReturnCarFrame) o;
        return Objects.equals(panel, that.panel) &&
                Objects.equals(cmbReservations, that.cmbReservations) &&
                Objects.equals(txtKilometers, that.txtKilometers) &&
                Objects.equals(controller, that.controller);
    }

    @Override
    public int hashCode() {
        return Objects.hash(panel, cmbReservations, txtKilometers, controller);
    }

    @Override
    public String toString() {
        return "ReturnCarFrame{" +
                "panel=" + panel +
                ", cmbReservations=" + cmbReservations +
                ", txtKilometers=" + txtKilometers +
                ", controller=" + controller +
                '}';
    }
}
