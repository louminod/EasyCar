package entities;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class Customer extends User {
    private String phoneNumber;
    private String address;
    private LocalDate birthDay;

    public Customer(UUID uuid, String name, String lastName, String email, String password, String phoneNumber, String address, LocalDate birthDay) {
        super(uuid, name, lastName, email, password);
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.birthDay = birthDay;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getBirthDay() {
        return birthDay;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(phoneNumber, customer.phoneNumber) &&
                Objects.equals(address, customer.address) &&
                Objects.equals(birthDay, customer.birthDay);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phoneNumber, address, birthDay);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", birthDay=" + birthDay +
                '}';
    }
}
