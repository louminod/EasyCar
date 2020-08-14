package data.tools;

import entities.Car;
import entities.Customer;
import entities.Reservation;
import entities.User;
import tools.Tool;

import java.util.ArrayList;
import java.util.List;

public abstract class DatabaseManager {

    private static CarsFileManager carsManager = new CarsFileManager();
    private static ReservationsFileManager reservationsManager = new ReservationsFileManager();
    private static UsersFileManager usersManager = new UsersFileManager();

    private DatabaseManager() {
    }

    // USERS

    /**
     * Get the database users list.
     *
     * @return A list of User.
     */
    public static List<User> getUsers() {
        return DatabaseManager.usersManager.getUsers();
    }

    /**
     * Write users to the database.
     *
     * @param users The users to write.
     */
    public static void writeUsers(List<User> users) {
        DatabaseManager.usersManager.writeUsers(users);
    }

    /**
     * Write (new) user to the database.
     *
     * @param user The user to write.
     */
    public static void writeUser(User user) {
        List<User> users = DatabaseManager.getUsers();
        users.add(user);
        DatabaseManager.writeUsers(users);
    }

    /**
     * Update an user in the database.
     *
     * @param user The user to update.
     */
    public static void updateUser(User user) {
        List<User> users = DatabaseManager.getUsers();
        List<User> usersToWrite = new ArrayList<>();

        users.forEach(u -> {
            if (!u.getUuid().toString().equals(user.getUuid().toString())) {
                usersToWrite.add(u);
            }
        });
        usersToWrite.add(user);
        DatabaseManager.writeUsers(usersToWrite);
    }

    /**
     * Delete an user in the database.
     *
     * @param user The user to delete.
     */
    public static void deleteUser(User user) {
        List<User> users = DatabaseManager.getUsers();
        List<User> usersToWrite = new ArrayList<>();

        users.forEach(u -> {
            if (!u.getUuid().toString().equals(user.getUuid().toString())) {
                usersToWrite.add(u);
            }
        });
        DatabaseManager.writeUsers(usersToWrite);
    }

    /**
     * Get Customer from uuid.
     *
     * @param customerUuid The customer uuid.
     * @return The Customer.
     */
    public static Customer getCustomerFromUuid(String customerUuid) {
        Customer customer = null;

        for (User user : DatabaseManager.getUsers()) {
            if (user.getClass() == Customer.class) {
                if (user.getUuid().toString().equals(customerUuid)) {
                    customer = (Customer) user;
                }
            }
        }

        return customer;
    }

    /**
     * Check if email and password belongs to an user in database.
     *
     * @param email    User email.
     * @param password User password.
     * @return User if exist, null instead.
     */
    public static User connectUser(String email, String password) {
        return DatabaseManager.usersManager.connect(email, password);
    }

    // RESERVATIONS

    /**
     * Get all the reservations from databse.
     *
     * @return The reservations.
     */
    public static List<Reservation> getReservations() {
        return DatabaseManager.reservationsManager.getReservations();
    }

    /**
     * Get all reservations for a specific customer.
     *
     * @param customer The customer.
     * @return The reservations.
     */
    public static List<Reservation> getCustomerReservations(Customer customer) {
        List<Reservation> reservations = new ArrayList<>();

        for (Reservation reservation : DatabaseManager.getReservations()) {
            if (reservation.getCustomer().getUuid().toString().equals(customer.getUuid().toString())) {
                reservations.add(reservation);
            }
        }

        return reservations;
    }

    /**
     * Write reservations in the database.
     *
     * @param reservations The reservations to write.
     */
    public static void writeReservations(List<Reservation> reservations) {
        DatabaseManager.reservationsManager.writeReservations(reservations);
    }

    /**
     * Write a single reservation in the database.
     *
     * @param reservation The reservation to write.
     */
    public static void writeReservation(Reservation reservation) {
        List<Reservation> reservations = DatabaseManager.getReservations();
        reservations.add(reservation);
        DatabaseManager.writeReservations(reservations);
    }

    /**
     * Update one reservation in the database.
     *
     * @param reservation The reservation to update.
     */
    public static void updateReservation(Reservation reservation) {
        List<Reservation> reservations = DatabaseManager.getReservations();
        List<Reservation> reservationsToWrite = new ArrayList<>();

        reservations.forEach(r -> {
            if (!r.getUuid().toString().equals(reservation.getUuid().toString())) {
                reservationsToWrite.add(r);
            }
        });
        reservationsToWrite.add(reservation);
        DatabaseManager.writeReservations(reservationsToWrite);
    }

    // CARS

    /**
     * Get all the cars from the database.
     *
     * @return The cars.
     */
    public static List<Car> getCars() {
        return DatabaseManager.carsManager.getCars();
    }

    /**
     * Write cars in the database.
     *
     * @param cars The cars to write.
     */
    public static void writeCars(List<Car> cars) {
        DatabaseManager.carsManager.writeCars(cars);
    }

    /**
     * Write a single car in the database.
     *
     * @param car The car to write.
     */
    public static void writeCar(Car car) {
        List<Car> cars = DatabaseManager.getCars();
        cars.add(car);
        DatabaseManager.writeCars(cars);
    }

    /**
     * Update a car in the database.
     *
     * @param car The car to update.
     */
    public static void updateCar(Car car) {
        List<Car> cars = DatabaseManager.getCars();
        List<Car> carsToWrite = new ArrayList<>();

        cars.forEach(c -> {
            if (!c.getUuid().toString().equals(car.getUuid().toString())) {
                carsToWrite.add(c);
            }
        });
        carsToWrite.add(car);
        DatabaseManager.writeCars(carsToWrite);
    }

    /**
     * Delete a car in the database.
     *
     * @param car The car to delete.
     */
    public static void deleteCar(Car car) {
        List<Car> cars = DatabaseManager.getCars();
        List<Car> carsToWrite = new ArrayList<>();

        cars.forEach(c -> {
            if (!c.getUuid().toString().equals(car.getUuid().toString())) {
                carsToWrite.add(c);
            }
        });
        DatabaseManager.writeCars(carsToWrite);
    }

    /**
     * Get a Car from an Uuid.
     *
     * @param carUuid The car uuid.
     * @return The Car.
     */
    public static Car getCarFromUuid(String carUuid) {
        Car car = null;

        for (Car c : DatabaseManager.getCars()) {
            if (c.getUuid().toString().equals(carUuid)) {
                car = c;
            }
        }

        return car;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
