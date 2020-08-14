package entities;

import entities.references.ReservationStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.UUID;

public class Reservation {
    private UUID uuid;
    private LocalDateTime date;
    private float price;
    private Customer customer;
    private Car car;
    private ReservationStatus status;
    private LocalDate startDate;
    private LocalDate endDate;

    public Reservation(UUID uuid, LocalDateTime date, float price, Customer customer, Car car, ReservationStatus status, LocalDate startDate, LocalDate endDate) {
        this.uuid = uuid;
        this.date = date;
        this.price = price;
        this.customer = customer;
        this.car = car;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public UUID getUuid() {
        return uuid;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Object[] convertToObjectTable() {
        Object[] data = new Object[6];
        data[0] = this.date.format(DateTimeFormatter.ISO_LOCAL_DATE);
        data[1] = this.price;
        data[2] = String.format("%s %s %s", this.car.getType(), this.car.getBrand(), this.car.getModel());
        data[3] = this.status;
        data[4] = this.startDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
        data[5] = this.endDate.format(DateTimeFormatter.ISO_LOCAL_DATE);

        return data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return Float.compare(that.price, price) == 0 &&
                Objects.equals(uuid, that.uuid) &&
                Objects.equals(date, that.date) &&
                Objects.equals(customer, that.customer) &&
                Objects.equals(car, that.car) &&
                status == that.status &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(endDate, that.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, date, price, customer, car, status, startDate, endDate);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("date of reservation: %s | ", this.date.format(DateTimeFormatter.BASIC_ISO_DATE)));
        sb.append(String.format("customer: %s %s | ", this.customer.getLastName().toUpperCase(), this.customer.getName()));
        sb.append(String.format("car: %s %s %s | ", this.car.getType(), this.car.getBrand(), this.car.getModel()));
        sb.append(String.format("start: %s | ", this.startDate.format(DateTimeFormatter.BASIC_ISO_DATE)));
        sb.append(String.format("end: %s", this.endDate.format(DateTimeFormatter.BASIC_ISO_DATE)));

        return sb.toString();
    }

    public String data() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Reservation %s\n", this.uuid.toString()));
        sb.append(String.format("* Date -> %s\n", this.date.format(DateTimeFormatter.BASIC_ISO_DATE)));
        sb.append(String.format("* Price -> %.2f\n", this.price));
        sb.append(String.format("* Customer last name -> %s\n", this.customer.getLastName()));
        sb.append(String.format("* Car -> %s %s %s\n", this.car.getType(), this.car.getBrand(), this.car.getModel()));
        sb.append(String.format("* Reservation status -> %s\n", this.status));
        sb.append(String.format("* Start date -> %s\n", this.startDate.format(DateTimeFormatter.BASIC_ISO_DATE)));
        sb.append(String.format("* End date -> %s\n", this.endDate.format(DateTimeFormatter.BASIC_ISO_DATE)));
        return sb.toString();
    }
}
