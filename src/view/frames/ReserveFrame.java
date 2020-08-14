package view.frames;

import entities.Car;
import entities.Customer;
import entities.Reservation;
import entities.references.ReservationStatus;
import model.controllers.PaymentFrameController;
import model.controllers.ReserveFrameController;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import view.CustomFrame;
import view.custom_widget.DateLabelFormatter;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;
import java.util.Properties;
import java.util.UUID;

public class ReserveFrame extends JFrame implements CustomFrame {

    private JPanel panel;
    private UtilDateModel modelEnd;
    private UtilDateModel modelStart;
    private float price = 0;

    private ReserveFrameController controller;

    public ReserveFrame(ReserveFrameController controller) {
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

        this.modelStart = new UtilDateModel();
        this.modelStart.setDate(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth());
        this.modelStart.setSelected(true);
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanelStart = new JDatePanelImpl(this.modelStart, p);
        JDatePickerImpl datePickerStart = new JDatePickerImpl(datePanelStart, new DateLabelFormatter());

        this.modelEnd = new UtilDateModel();
        this.modelEnd.setDate(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth() + 1);
        this.modelEnd.setSelected(true);
        JDatePanelImpl datePanelEnd = new JDatePanelImpl(this.modelEnd, p);
        JDatePickerImpl datePickerEnd = new JDatePickerImpl(datePanelEnd, new DateLabelFormatter());

        JButton btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(event -> hideFrame());
        JButton btnPay = new JButton("Make payment");
        btnPay.addActionListener(event -> {
            UUID uuid = UUID.randomUUID();
            LocalDateTime date = LocalDateTime.now();
            LocalDate localDateStart = this.modelStart.getValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate localDateEnd = this.modelEnd.getValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            float price = controller.getCar().getPrice() + (localDateEnd.getDayOfYear() - localDateStart.getDayOfYear());
            Customer customer = (Customer) controller.getUser();
            Car car = controller.getCar();
            ReservationStatus status = ReservationStatus.WAIT_FOR_PAYMENT;
            Reservation reservation = new Reservation(uuid, date, price, customer, car, status, localDateStart, localDateEnd);
            PaymentFrameController paymentFrameController = new PaymentFrameController(reservation);
            PaymentFrame paymentFrame = new PaymentFrame(paymentFrameController);
            paymentFrame.buildFrame("Payment",600,200);
            hideFrame();
            paymentFrame.showFrame();
        });

        this.panel.add(new JLabel("Car selected:"));
        this.panel.add(new JLabel(String.format("%s %s - %s", this.controller.getCar().getBrand(), this.controller.getCar().getType(), this.controller.getCar().getModel())));
        this.panel.add(new JLabel("- Rent start date -"));
        this.panel.add(new JLabel("- Rent end date -"));
        this.panel.add(datePickerStart);
        this.panel.add(datePickerEnd);
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
        ReserveFrame that = (ReserveFrame) o;
        return Float.compare(that.price, price) == 0 &&
                Objects.equals(panel, that.panel) &&
                Objects.equals(modelEnd, that.modelEnd) &&
                Objects.equals(modelStart, that.modelStart) &&
                Objects.equals(controller, that.controller);
    }

    @Override
    public int hashCode() {
        return Objects.hash(panel, modelEnd, modelStart, price, controller);
    }

    @Override
    public String toString() {
        return "ReserveFrame{" +
                "panel=" + panel +
                ", modelEnd=" + modelEnd +
                ", modelStart=" + modelStart +
                ", price=" + price +
                ", controller=" + controller +
                '}';
    }
}
