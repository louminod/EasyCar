package model.controllers;

import data.tools.DatabaseManager;
import entities.Car;

public class AddCarFrameController {

    public AddCarFrameController() {
    }

    /**
     * Create an car in the database.
     *
     * @param car The car to create.
     */
    public void createCar(Car car) {
        DatabaseManager.writeCar(car);
    }
}
