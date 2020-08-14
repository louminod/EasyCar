package entities;

import entities.references.CarBrand;
import entities.references.CarColor;
import entities.references.CarType;

import java.awt.*;
import java.util.Objects;
import java.util.UUID;

public class Car {
    private UUID uuid;
    private CarColor color;
    private CarType type;
    private String model;
    private float kilometers;
    private int seats;
    private CarBrand brand;
    private int power;
    // The price is the price per day
    private float price;
    private boolean reserved;

    public Car(UUID uuid, CarColor color, CarType type, String model, float kilometers, int seats, CarBrand brand, int power, float price, boolean reserved) {
        this.uuid = uuid;
        this.color = color;
        this.type = type;
        this.model = model;
        this.kilometers = kilometers;
        this.seats = seats;
        this.brand = brand;
        this.power = power;
        this.price = price;
        this.reserved = reserved;
    }

    public UUID getUuid() {
        return uuid;
    }

    public CarColor getColor() {
        return color;
    }

    public void setColor(CarColor color) {
        this.color = color;
    }

    public CarType getType() {
        return type;
    }

    public void setType(CarType type) {
        this.type = type;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public float getKilometers() {
        return kilometers;
    }

    public void setKilometers(float kilometers) {
        this.kilometers = kilometers;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public CarBrand getBrand() {
        return brand;
    }

    public void setBrand(CarBrand brand) {
        this.brand = brand;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public boolean isReserved() {
        return reserved;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return Float.compare(car.kilometers, kilometers) == 0 &&
                seats == car.seats &&
                power == car.power &&
                Float.compare(car.price, price) == 0 &&
                reserved == car.reserved &&
                Objects.equals(uuid, car.uuid) &&
                Objects.equals(color, car.color) &&
                type == car.type &&
                Objects.equals(model, car.model) &&
                brand == car.brand;
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, color, type, model, kilometers, seats, brand, power, price, reserved);
    }

    @Override
    public String toString() {
        return "Car{" +
                "uuid=" + uuid +
                ", color=" + color +
                ", type=" + type +
                ", model='" + model + '\'' +
                ", kilometers=" + kilometers +
                ", seats=" + seats +
                ", brand=" + brand +
                ", power=" + power +
                ", price=" + price +
                ", reserved=" + reserved +
                '}';
    }

    public Object[] convertToObjectTable() {
        Object[] data = new Object[6];
        data[0] = this.brand;
        data[1] = this.type;
        data[2] = this.model;
        data[3] = this.color;
        data[4] = this.seats;
        data[5] = this.power;

        return data;
    }
}
