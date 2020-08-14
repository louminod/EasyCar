package view.frames;

import entities.User;
import entities.references.AccountType;
import model.controllers.AddUserFrameController;
import model.controllers.ConnectFrameController;
import model.controllers.MainFrameController;
import tools.Tool;
import view.CustomFrame;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class ConnectFrame extends JFrame implements CustomFrame {

    private JPanel panel;
    private JLabel labelmail, labelPassword, labelMessage;
    private JTextField inputMail;
    private JPasswordField inputPassword;
    private JButton btnLogin;

    private ConnectFrameController controller;

    public ConnectFrame(ConnectFrameController controller) {
        this.controller = controller;
    }

    @Override
    public void buildFrame(String title, int width, int height) {
        this.setTitle(title);
        this.setSize(width, height);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.buildComponents();
    }

    @Override
    public void buildComponents() {
        this.labelmail = new JLabel();
        this.labelmail.setText("Email :");
        this.inputMail = new JTextField();

        // Password
        this.labelPassword = new JLabel();
        this.labelPassword.setText("Password :");
        this.inputPassword = new JPasswordField();

        // Submit

        this.btnLogin = new JButton("CONNECT");

        this.panel = new JPanel(new GridLayout(4, 1));

        this.panel.add(labelmail);
        this.panel.add(inputMail);
        this.panel.add(labelPassword);
        this.panel.add(inputPassword);

        this.labelMessage = new JLabel();
        this.panel.add(labelMessage);
        this.panel.add(btnLogin);

        this.btnLogin.addActionListener(event -> {
            String email = this.inputMail.getText();
            String password = this.inputPassword.getText();

            User userConnected = this.controller.connect(email, password);

            if (userConnected != null) {
                MainFrameController mainFrameController = new MainFrameController(Tool.classToAccountType(userConnected.getClass()), userConnected);
                MainFrame mainFrame = new MainFrame(mainFrameController);
                mainFrame.buildFrame("Easy Car", 1000, 600);

                this.hideFrame();
                mainFrame.showFrame();

            } else {
                this.labelMessage.setText("Connection error !");
            }

        });

        JButton btnAddCustomer = new JButton("I'm a new customer");
        btnAddCustomer.addActionListener(e -> {
            AddUserFrameController addUserFrameController = new AddUserFrameController(AccountType.CUSTOMER);
            AddUserFrame addUserFrame = new AddUserFrame(addUserFrameController);
            addUserFrame.buildFrame("New customer registration", 600, 400);
            addUserFrame.showFrame();
        });
        this.panel.add(new JLabel(""));
        this.panel.add(btnAddCustomer);

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
        ConnectFrame that = (ConnectFrame) o;
        return Objects.equals(panel, that.panel) &&
                Objects.equals(labelmail, that.labelmail) &&
                Objects.equals(labelPassword, that.labelPassword) &&
                Objects.equals(labelMessage, that.labelMessage) &&
                Objects.equals(inputMail, that.inputMail) &&
                Objects.equals(inputPassword, that.inputPassword) &&
                Objects.equals(btnLogin, that.btnLogin) &&
                Objects.equals(controller, that.controller);
    }

    @Override
    public int hashCode() {
        return Objects.hash(panel, labelmail, labelPassword, labelMessage, inputMail, inputPassword, btnLogin, controller);
    }

    @Override
    public String toString() {
        return "ConnectFrame{" +
                "panel=" + panel +
                ", labelmail=" + labelmail +
                ", labelPassword=" + labelPassword +
                ", labelMessage=" + labelMessage +
                ", inputMail=" + inputMail +
                ", inputPassword=" + inputPassword +
                ", btnLogin=" + btnLogin +
                ", controller=" + controller +
                '}';
    }
}
