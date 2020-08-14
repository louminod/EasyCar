package view.frames;

import entities.Customer;
import model.controllers.UserDataFrameController;
import view.CustomFrame;
import view.custom_widget.UserDataCustomerPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class UserDataFrame extends JFrame implements CustomFrame {

    private JPanel panel;


    private UserDataFrameController controller;

    public UserDataFrame(UserDataFrameController controller) {
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
        if (this.controller.getUser().getClass() == Customer.class) {
            Customer customer = (Customer) this.controller.getUser();
            UserDataCustomerPanel userDataCustomerPanel = new UserDataCustomerPanel(customer);
            this.panel = userDataCustomerPanel.buildPanel();
            JButton btnValidate = new JButton();
            btnValidate.setText("Validate");
            btnValidate.addActionListener(event -> {
                customer.setName(userDataCustomerPanel.getTxtNameValue());
                customer.setLastName(userDataCustomerPanel.getTxtLastNameValue());
                customer.setPhoneNumber(userDataCustomerPanel.getTxtPhoneNumberValue());
                customer.setAddress(userDataCustomerPanel.getTxtAddressValue());
                customer.setEmail(userDataCustomerPanel.getTxtEmailValue());
                customer.setPassword(userDataCustomerPanel.getTxtPasswordValue());
                controller.updateUser(customer);
                hideFrame();
            });
            panel.add(new JLabel());
            panel.add(btnValidate);

        }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDataFrame that = (UserDataFrame) o;
        return Objects.equals(panel, that.panel) &&
                Objects.equals(controller, that.controller);
    }

    @Override
    public int hashCode() {
        return Objects.hash(panel, controller);
    }

    @Override
    public String toString() {
        return "UserDataFrame{" +
                "panel=" + panel +
                ", controller=" + controller +
                '}';
    }
}
