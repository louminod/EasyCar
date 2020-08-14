package application;

import data.tools.CarsFileManager;
import data.tools.DatabaseManager;
import entities.*;
import entities.references.*;
import tools.Tool;

import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DataLoader {
    public static void main(String[] args) {
        // Use this class to load a dataSet

        // USERS
        List<User> users = new ArrayList<>();
        User user1 = new Customer(UUID.randomUUID(), "Jhon", "Doe", "jhon@gmail.com", "azerty", "1234567890", "12 rwd Alamina", LocalDate.now());
        User user2 = new Employee(UUID.randomUUID(), "Bob", "Dylan", "bob@gmail.com", "azerty", WorkPlace.EMPLOYEE);
        User user3 = new Administrator(UUID.randomUUID(), "Bryan", "Smith", "bryan@gmail.com", "azerty");

        users.add(user1);
        users.add(user2);
        users.add(user3);
        DatabaseManager.writeUsers(users);

        // CARS
        List<Car> cars = new ArrayList<>();
        Car car1 = new Car(UUID.randomUUID(), CarColor.BLUE, CarType.SPORT, "208", 12, 5, CarBrand.RENAUD, 150, 125, false);
        Car car2 = new Car(UUID.randomUUID(), CarColor.CLEAR_SKY, CarType.SUV, "805", 11234, 5, CarBrand.PEUGEOT, 90, 90, false);
        Car car3 = new Car(UUID.randomUUID(), CarColor.LEMON_YELLOW, CarType.LUXURY, "Zoe", 5342, 7, CarBrand.RENAUD, 110, 85, false);
        cars.add(car1);
        cars.add(car2);
        cars.add(car3);
        DatabaseManager.writeCars(cars);

        // RESERVATIONS
        List<Reservation> reservations = new ArrayList<>();
        Reservation reservation1 = new Reservation(UUID.randomUUID(), LocalDateTime.now(), 175, (Customer) user1, car1, ReservationStatus.CREATED, LocalDate.now(), LocalDate.now());
        reservations.add(reservation1);
        DatabaseManager.writeReservations(reservations);

        System.out.println("- Data loaded -");
        for (User user : users) {
            System.out.println(String.format("%s login: email -> %s | password -> %s", Tool.classToAccountType(user.getClass()), user.getEmail(), user.getPassword()));
        }
    }
}
