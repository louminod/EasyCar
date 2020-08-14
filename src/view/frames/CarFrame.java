package view.frames;

import entities.Customer;
import entities.Employee;
import entities.references.CarBrand;
import entities.references.CarColor;
import entities.references.CarType;
import model.controllers.CarFrameController;
import model.controllers.ReserveFrameController;
import view.CustomFrame;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Objects;

public class CarFrame extends JFrame implements CustomFrame {

    private JPanel panel;
    private JComboBox<CarColor> cmbColor;
    private JComboBox<CarType> cmbType;
    private JTextField txtModel;
    private JTextField txtKilometers;
    private JTextField txtSeats;
    private JComboBox<CarBrand> cmbBrand;
    private JTextField txtPower;
    private JTextField txtPrice;
    private JButton btnBack;
    private JButton btnReserve;
    private JButton btnValidate;

    private CarFrameController controller;

    public CarFrame(CarFrameController controller) {
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
        this.cmbColor = new JComboBox<CarColor>();
        for (CarColor carColor: Arrays.asList(CarColor.values())) {
            this.cmbColor.addItem(carColor);
        }
        this.cmbColor.setSelectedItem(this.controller.getCar().getColor());
        this.cmbType = new JComboBox<CarType>();
        for (CarType carType: Arrays.asList(CarType.values())) {
            this.cmbType.addItem(carType);
        }
        this.cmbType.setSelectedItem(this.controller.getCar().getType());
        this.txtModel = new JTextField(this.controller.getCar().getModel());
        this.txtKilometers = new JTextField(String.valueOf(this.controller.getCar().getKilometers()));
        this.txtSeats = new JTextField(String.valueOf(this.controller.getCar().getSeats()));
        this.cmbBrand = new JComboBox<CarBrand>();
        for (CarBrand carBrand: Arrays.asList(CarBrand.values())) {
            this.cmbBrand.addItem(carBrand);
        }
        this.cmbBrand.setSelectedItem(this.controller.getCar().getBrand());
        this.txtPower = new JTextField(String.valueOf(this.controller.getCar().getPower()));
        this.txtPrice = new JTextField(String.valueOf(this.controller.getCar().getPrice()));
        this.btnBack = new JButton("Go Back");
        this.btnReserve = new JButton("Reserve this car");
        this.btnValidate = new JButton("Validate changes");

        this.btnBack.addActionListener(event -> hideFrame());
        this.btnReserve.addActionListener(event -> {
            ReserveFrameController reserveFrameController = new ReserveFrameController(controller.getCar(), controller.getUser());
            ReserveFrame reserveFrame = new ReserveFrame(reserveFrameController);
            reserveFrame.buildFrame("Make a reservation", 600, 200);
            this.hideFrame();
            reserveFrame.showFrame();
        });
        this.btnValidate.addActionListener(event -> {
            this.controller.getCar().setBrand((CarBrand) this.cmbBrand.getSelectedItem());
            this.controller.getCar().setType((CarType) this.cmbType.getSelectedItem());
            this.controller.getCar().setModel(this.txtModel.getText());
            this.controller.getCar().setColor((CarColor) this.cmbColor.getSelectedItem());
            this.controller.getCar().setSeats(Integer.parseInt(this.txtSeats.getText()));
            this.controller.getCar().setPower(Integer.parseInt(this.txtPower.getText()));
            this.controller.getCar().setKilometers(Float.parseFloat(this.txtKilometers.getText()));
            this.controller.getCar().setPrice(Float.parseFloat(this.txtPrice.getText()));
            this.controller.updateCar();
            this.hideFrame();
        });

        if(Customer.class.equals(this.controller.getUser().getClass())){
            this.cmbBrand.setEnabled(false);
            this.cmbType.setEnabled(false);
            this.txtModel.setEnabled(false);
            this.cmbColor.setEnabled(false);
            this.txtSeats.setEnabled(false);
            this.txtPower.setEnabled(false);
            this.txtKilometers.setEnabled(false);
            this.txtPrice.setEnabled(false);
        }

        this.panel = new JPanel(new GridLayout(9, 1));
        this.panel.add(new JLabel("Brand:"));
        this.panel.add(this.cmbBrand);
        this.panel.add(new JLabel("Type:"));
        this.panel.add(this.cmbType);
        this.panel.add(new JLabel("Model:"));
        this.panel.add(this.txtModel);
        this.panel.add(new JLabel("Color:"));
        this.panel.add(this.cmbColor);
        this.panel.add(new JLabel("Number of seats:"));
        this.panel.add(this.txtSeats);
        this.panel.add(new JLabel("Power:"));
        this.panel.add(this.txtPower);
        this.panel.add(new JLabel("Kilometers:"));
        this.panel.add(this.txtKilometers);
        this.panel.add(new JLabel("Rent price:"));
        this.panel.add(this.txtPrice);
        this.panel.add(this.btnBack);
        if(Customer.class.equals(this.controller.getUser().getClass())){
            this.panel.add(this.btnReserve);
        } else if(Employee.class.equals(this.controller.getUser().getClass())) {
            this.panel.add(this.btnValidate);
        }

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
        CarFrame carFrame = (CarFrame) o;
        return Objects.equals(panel, carFrame.panel) &&
                Objects.equals(cmbColor, carFrame.cmbColor) &&
                Objects.equals(cmbType, carFrame.cmbType) &&
                Objects.equals(txtModel, carFrame.txtModel) &&
                Objects.equals(txtKilometers, carFrame.txtKilometers) &&
                Objects.equals(txtSeats, carFrame.txtSeats) &&
                Objects.equals(cmbBrand, carFrame.cmbBrand) &&
                Objects.equals(txtPower, carFrame.txtPower) &&
                Objects.equals(txtPrice, carFrame.txtPrice) &&
                Objects.equals(btnBack, carFrame.btnBack) &&
                Objects.equals(btnReserve, carFrame.btnReserve) &&
                Objects.equals(btnValidate, carFrame.btnValidate) &&
                Objects.equals(controller, carFrame.controller);
    }

    @Override
    public int hashCode() {
        return Objects.hash(panel, cmbColor, cmbType, txtModel, txtKilometers, txtSeats, cmbBrand, txtPower, txtPrice, btnBack, btnReserve, btnValidate, controller);
    }

    @Override
    public String toString() {
        return "CarFrame{" +
                "panel=" + panel +
                ", cmbColor=" + cmbColor +
                ", cmbType=" + cmbType +
                ", txtModel=" + txtModel +
                ", txtKilometers=" + txtKilometers +
                ", txtSeats=" + txtSeats +
                ", cmbBrand=" + cmbBrand +
                ", txtPower=" + txtPower +
                ", txtPrice=" + txtPrice +
                ", btnBack=" + btnBack +
                ", btnReserve=" + btnReserve +
                ", btnValidate=" + btnValidate +
                ", controller=" + controller +
                '}';
    }
}
