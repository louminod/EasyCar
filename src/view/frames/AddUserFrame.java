package view.frames;

import entities.Administrator;
import entities.Customer;
import entities.Employee;
import entities.references.AccountType;
import entities.references.WorkPlace;
import model.controllers.AddUserFrameController;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import tools.Tool;
import view.CustomFrame;
import view.custom_widget.DateLabelFormatter;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Objects;
import java.util.Properties;
import java.util.UUID;

public class AddUserFrame extends JFrame implements CustomFrame {
    private JPanel panel;
    private JTextField txtName;
    private JTextField txtLastName;
    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JTextField txtPhoneNumber;
    private JTextField txtAddress;
    private UtilDateModel modelBirthDay;
    private JComboBox<WorkPlace> cmbWorkPlace;

    private AddUserFrameController controller;

    public AddUserFrame(AddUserFrameController controller) {
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
        this.txtName = new JTextField();
        this.txtLastName = new JTextField();
        this.txtEmail = new JTextField();
        this.txtPassword = new JPasswordField();

        switch (this.controller.getAccountType()) {
            case CUSTOMER:
                this.panel = new JPanel(new GridLayout(8, 2));
                break;
            case EMPLOYEE:
                this.panel = new JPanel(new GridLayout(6, 2));
                break;
            case ADMINISTRATOR:
                this.panel = new JPanel(new GridLayout(5, 2));
                break;
        }

        this.panel.add(new JLabel("Name:"));
        this.panel.add(this.txtName);
        this.panel.add(new JLabel("LastName:"));
        this.panel.add(this.txtLastName);
        this.panel.add(new JLabel("Email:"));
        this.panel.add(this.txtEmail);
        this.panel.add(new JLabel("Password:"));
        this.panel.add(this.txtPassword);

        switch (this.controller.getAccountType()) {
            case CUSTOMER:
                this.txtPhoneNumber = new JTextField();
                this.txtAddress = new JTextField();
                this.modelBirthDay = new UtilDateModel();
                this.modelBirthDay.setDate(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth());
                this.modelBirthDay.setSelected(true);
                Properties p = new Properties();
                p.put("text.today", "Today");
                p.put("text.month", "Month");
                p.put("text.year", "Year");
                JDatePanelImpl datePanelBirthDay = new JDatePanelImpl(this.modelBirthDay, p);
                JDatePickerImpl datePickerBirthDay = new JDatePickerImpl(datePanelBirthDay, new DateLabelFormatter());
                this.panel.add(new JLabel("Phone number:"));
                this.panel.add(this.txtPhoneNumber);
                this.panel.add(new JLabel("Address:"));
                this.panel.add(this.txtAddress);
                this.panel.add(new JLabel("BirthDay:"));
                this.panel.add(datePickerBirthDay);
                break;
            case EMPLOYEE:
                this.panel.add(new JLabel("WorkPlace:"));
                this.cmbWorkPlace = new JComboBox<>();
                for (WorkPlace workPlace : Arrays.asList(WorkPlace.values())) {
                    this.cmbWorkPlace.addItem(workPlace);
                }
                this.panel.add(this.cmbWorkPlace);
                break;
        }

        JButton btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(event -> {
            this.hideFrame();
        });
        JButton btnCreate = new JButton("Create");
        btnCreate.addActionListener(event -> {
            String name = this.txtName.getText();
            String lastName = this.txtLastName.getText();
            String email = this.txtEmail.getText();
            String password = this.txtPassword.getText();

            switch (this.controller.getAccountType()) {
                case CUSTOMER:
                    String phoneNumber = this.txtPhoneNumber.getText();
                    String address = this.txtAddress.getText();
                    LocalDate birthDay = this.modelBirthDay.getValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    Customer customer = new Customer(UUID.randomUUID(), name, lastName, email, password, phoneNumber, address, birthDay);
                    this.controller.createUser(customer);
                    break;
                case EMPLOYEE:
                    WorkPlace workPlace = (WorkPlace) this.cmbWorkPlace.getSelectedItem();
                    Employee employee = new Employee(UUID.randomUUID(), name, lastName, email, password, workPlace);
                    this.controller.createUser(employee);
                    break;
                case ADMINISTRATOR:
                    Administrator administrator = new Administrator(UUID.randomUUID(), name, lastName, email, password);
                    this.controller.createUser(administrator);
                    break;
            }
            this.hideFrame();
            JOptionPane.showMessageDialog(null, "Account has been created");
        });
        this.panel.add(btnCancel);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddUserFrame that = (AddUserFrame) o;
        return Objects.equals(panel, that.panel) &&
                Objects.equals(txtName, that.txtName) &&
                Objects.equals(txtLastName, that.txtLastName) &&
                Objects.equals(txtEmail, that.txtEmail) &&
                Objects.equals(txtPassword, that.txtPassword) &&
                Objects.equals(txtPhoneNumber, that.txtPhoneNumber) &&
                Objects.equals(txtAddress, that.txtAddress) &&
                Objects.equals(modelBirthDay, that.modelBirthDay) &&
                Objects.equals(cmbWorkPlace, that.cmbWorkPlace) &&
                Objects.equals(controller, that.controller);
    }

    @Override
    public int hashCode() {
        return Objects.hash(panel, txtName, txtLastName, txtEmail, txtPassword, txtPhoneNumber, txtAddress, modelBirthDay, cmbWorkPlace, controller);
    }

    @Override
    public String toString() {
        return "AddUserFrame{" +
                "panel=" + panel +
                ", txtName=" + txtName +
                ", txtLastName=" + txtLastName +
                ", txtEmail=" + txtEmail +
                ", txtPassword=" + txtPassword +
                ", txtPhoneNumber=" + txtPhoneNumber +
                ", txtAddress=" + txtAddress +
                ", modelBirthDay=" + modelBirthDay +
                ", cmbWorkPlace=" + cmbWorkPlace +
                ", controller=" + controller +
                '}';
    }
}
