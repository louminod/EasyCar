package view.frames;

import entities.references.PaymentMethods;
import entities.references.ReservationStatus;
import model.controllers.PaymentFrameController;
import tools.Tool;
import view.CustomFrame;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Objects;

public class PaymentFrame extends JFrame implements CustomFrame {

    private JPanel panel;
    private JComboBox<PaymentMethods> cmbPaymentMethods;

    private PaymentFrameController controller;

    public PaymentFrame(PaymentFrameController controller) {
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
        this.panel = new JPanel(new GridLayout(4, 2));
        this.add(panel, BorderLayout.CENTER);

        JButton btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(event -> hideFrame());
        JButton btnPay = new JButton("PAY");
        btnPay.addActionListener(event -> {
            this.controller.getReservation().setStatus(ReservationStatus.CREATED);
            this.controller.registerReservation();
            this.controller.getReservation().getCar().setReserved(true);
            this.controller.updateCar(this.controller.getReservation().getCar());
            Tool.generateBill(this.controller.getReservation());
            hideFrame();
        });

        this.cmbPaymentMethods = new JComboBox<>(PaymentMethods.values());

        this.panel.add(new JLabel("Total reservation cost:"));
        this.panel.add(new JLabel(String.format("%.2f $", this.controller.getReservation().getPrice())));
        this.panel.add(new JLabel(""));
        this.panel.add(new JLabel("There is only \"in store\" method for now."));
        this.panel.add(new JLabel("Choose your payment method:"));
        this.panel.add(this.cmbPaymentMethods);
        this.panel.add(btnCancel);
        this.panel.add(btnPay);
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
        PaymentFrame that = (PaymentFrame) o;
        return Objects.equals(panel, that.panel) &&
                Objects.equals(cmbPaymentMethods, that.cmbPaymentMethods) &&
                Objects.equals(controller, that.controller);
    }

    @Override
    public int hashCode() {
        return Objects.hash(panel, cmbPaymentMethods, controller);
    }

    @Override
    public String toString() {
        return "PaymentFrame{" +
                "panel=" + panel +
                ", cmbPaymentMethods=" + cmbPaymentMethods +
                ", controller=" + controller +
                '}';
    }
}
