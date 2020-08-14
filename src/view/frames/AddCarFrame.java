package view.frames;

import entities.Car;
import entities.references.CarBrand;
import entities.references.CarColor;
import entities.references.CarType;
import model.controllers.AddCarFrameController;
import view.CustomFrame;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.UUID;

public class AddCarFrame extends JFrame implements CustomFrame {

    private JPanel panel;
    private JComboBox<CarColor> cmbColor;
    private JComboBox<CarType> cmbType;
    private JComboBox<CarBrand> cmbBrand;
    private JTextField txtModel;
    private JTextField txtKilometers;
    private JTextField txtSeats;
    private JTextField txtPower;
    private JTextField txtPrice;

    private AddCarFrameController controller;

    public AddCarFrame(AddCarFrameController controller) {
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
        this.panel = new JPanel(new GridLayout(9, 2));

        this.cmbColor = new JComboBox<>();
        for (CarColor carColor : Arrays.asList(CarColor.values())) {
            this.cmbColor.addItem(carColor);
        }
        this.cmbType = new JComboBox<>();
        for (CarType carType : Arrays.asList(CarType.values())) {
            this.cmbType.addItem(carType);
        }
        this.cmbBrand = new JComboBox<>();
        for (CarBrand carBrand : Arrays.asList(CarBrand.values())) {
            this.cmbBrand.addItem(carBrand);
        }

        this.txtModel = new JTextField();
        this.txtKilometers = new JTextField();
        this.txtSeats = new JTextField();
        this.txtPower = new JTextField();
        this.txtPrice = new JTextField();

        JButton btnBack = new JButton("Back");
        btnBack.addActionListener(e -> {
            this.hideFrame();
        });
        JButton btnCreate = new JButton("Create");
        btnCreate.addActionListener(e -> {
            CarColor carColor = (CarColor) this.cmbColor.getSelectedItem();
            CarType carType = (CarType) this.cmbType.getSelectedItem();
            CarBrand carBrand = (CarBrand) this.cmbBrand.getSelectedItem();
            String carModel = this.txtModel.getText();
            float carKilometers = Float.parseFloat(this.txtKilometers.getText());
            int carSeats = Integer.parseInt(this.txtSeats.getText());
            int carPower = Integer.parseInt(this.txtPower.getText());
            float carPrice = Float.parseFloat(this.txtPrice.getText());
            Car car = new Car(UUID.randomUUID(), carColor, carType, carModel, carKilometers, carSeats, carBrand, carPower, carPrice, false);
            this.controller.createCar(car);
            this.hideFrame();
            JOptionPane.showMessageDialog(null, "Car has been created");
        });

        this.panel.add(new JLabel("Color:"));
        this.panel.add(this.cmbColor);
        this.panel.add(new JLabel("Type:"));
        this.panel.add(this.cmbType);
        this.panel.add(new JLabel("Brand:"));
        this.panel.add(this.cmbBrand);
        this.panel.add(new JLabel("Model:"));
        this.panel.add(this.txtModel);
        this.panel.add(new JLabel("Kilometers:"));
        this.panel.add(this.txtKilometers);
        this.panel.add(new JLabel("Seats:"));
        this.panel.add(this.txtSeats);
        this.panel.add(new JLabel("Power:"));
        this.panel.add(this.txtPower);
        this.panel.add(new JLabel("Price:"));
        this.panel.add(this.txtPrice);
        this.panel.add(btnBack);
        this.panel.add(btnCreate);

        this.add(this.panel, BorderLayout.CENTER);
    }

    @Override
    public void showFrame() {
        this.setVisible(true);
    }


    @Override
    public void hideFrame() {
        this.setVisible(false);
    }
}
