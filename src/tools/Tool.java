package tools;

import entities.Administrator;
import entities.Customer;
import entities.Employee;
import entities.Reservation;
import entities.references.AccountType;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class Tool {
    private Tool() {

    }

    /**
     * Get the AccountType of an user class.
     *
     * @param c The user class.
     * @return The AccountType.
     */
    public static AccountType classToAccountType(Class c) {
        AccountType result = null;
        if (Customer.class.equals(c)) {
            result = AccountType.CUSTOMER;
        }
        if (Employee.class.equals(c)) {
            result = AccountType.EMPLOYEE;
        }
        if (Administrator.class.equals(c)) {
            result = AccountType.ADMINISTRATOR;
        }

        return result;
    }

    /**
     * Generate and write the reservation bill into the user folder.
     *
     * @param reservation The reservation to make the bill.
     */
    public static void generateBill(Reservation reservation) {
        LocalDate date = LocalDate.now();
        File billPath = new File(String.format("%s/easy_car_bill_%s_%s.txt", System.getProperty("user.home"), date.toString(), reservation.getUuid()));

        try {
            if (!billPath.exists()) {
                billPath.createNewFile();
                FileWriter writer = new FileWriter(billPath);
                BufferedWriter bWriter = new BufferedWriter(writer);
                bWriter.write("- EasyCar's Bill -\n\n\n\n");
                bWriter.write("Bill created the " + "" + LocalDateTime.now().format(DateTimeFormatter.BASIC_ISO_DATE) + "\n");
                bWriter.write(reservation.data() + "\n\n\n");
                bWriter.write("==> The price is : " + reservation.getPrice() + "$\n\n\n\n\n\n");
                bWriter.write("You can pay directly at the office by creditCard or cash.\n\n\n\n\n");
                bWriter.write("Thanks you to have choosing EasyCar.\n");
                bWriter.write("We wish you to have a nice journey with EasyCar.\n\n\n\n\n\n");
                bWriter.write("EasyCar, the journey with confidence !\n");

                bWriter.close();
                writer.close();

                JOptionPane.showMessageDialog(null, "The bill has been save into your documents !");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
