package view.custom_widget;

import entities.Customer;
import org.jdatepicker.JDatePicker;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.util.Objects;
import java.util.Properties;
import java.util.ResourceBundle;

public class UserDataCustomerPanel extends JPanel {

    private JTextField txtName;
    private JTextField txtLastName;
    private JTextField txtPhoneNumber;
    private JTextField txtAddress;
    private JTextField txtEmail;
    private JPasswordField txtPassword;

    private Customer customer;

    public UserDataCustomerPanel(Customer customer) {
        this.customer = customer;
    }

    /**
     * Build a specific JPanel for the Customer class.
     *
     * @return The JPanel.
     */
    public JPanel buildPanel() {
        this.txtName = new JTextField(customer.getName());
        this.txtLastName = new JTextField(customer.getLastName());
        this.txtPhoneNumber = new JTextField(customer.getPhoneNumber());
        this.txtAddress = new JTextField(customer.getAddress());
        this.txtEmail = new JTextField(customer.getEmail());
        this.txtPassword = new JPasswordField(customer.getPassword());

        JPanel panel = new JPanel(new GridLayout(7, 2));
        panel.add(new JLabel("Name:"));
        panel.add(this.txtName);
        panel.add(new JLabel("LastName:"));
        panel.add(this.txtLastName);
        panel.add(new JLabel("Phone number:"));
        panel.add(this.txtPhoneNumber);
        panel.add(new JLabel("Address:"));
        panel.add(this.txtAddress);
        panel.add(new JLabel("Email:"));
        panel.add(this.txtEmail);
        panel.add(new JLabel("Password:"));
        panel.add(this.txtPassword);

        return panel;
    }

    public String getTxtNameValue() {
        return this.txtName.getText();
    }

    public String getTxtLastNameValue() {
        return this.txtLastName.getText();
    }

    public String getTxtPhoneNumberValue() {
        return this.txtPhoneNumber.getText();
    }

    public String getTxtAddressValue() {
        return this.txtAddress.getText();
    }

    public String getTxtEmailValue() {
        return this.txtEmail.getText();
    }

    public String getTxtPasswordValue() {
        return this.txtPassword.getText();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDataCustomerPanel that = (UserDataCustomerPanel) o;
        return Objects.equals(txtName, that.txtName) &&
                Objects.equals(txtLastName, that.txtLastName) &&
                Objects.equals(txtPhoneNumber, that.txtPhoneNumber) &&
                Objects.equals(txtAddress, that.txtAddress) &&
                Objects.equals(txtEmail, that.txtEmail) &&
                Objects.equals(txtPassword, that.txtPassword) &&
                Objects.equals(customer, that.customer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(txtName, txtLastName, txtPhoneNumber, txtAddress, txtEmail, txtPassword, customer);
    }

    @Override
    public String toString() {
        return "UserDataCustomerPanel{" +
                "txtName=" + txtName +
                ", txtLastName=" + txtLastName +
                ", txtPhoneNumber=" + txtPhoneNumber +
                ", txtAddress=" + txtAddress +
                ", txtEmail=" + txtEmail +
                ", txtPassword=" + txtPassword +
                ", customer=" + customer +
                '}';
    }
}
