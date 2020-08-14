package data.tools;

import entities.Car;
import entities.references.CarBrand;
import entities.references.CarColor;
import entities.references.CarType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import tools.Converters;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class CarsFileManager {
    private final String FILE_PATH = "src/data/files/cars.json";

    protected CarsFileManager() {

    }

    /**
     * Get the database cars list.
     *
     * @return A list of Car.
     */
    protected List<Car> getCars() {
        List<Car> cars = new ArrayList<>();

        try (FileReader reader = new FileReader(FILE_PATH)) {

            JSONObject jsonObject = (JSONObject) new JSONParser().parse(reader);
            JSONArray jsonArray = (JSONArray) jsonObject.get("cars");

            jsonArray.forEach(car -> {
                cars.add(this.convertJSONObjectToCar((JSONObject) ((JSONObject) car).get("car")));
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

        return cars;
    }

    /**
     * Write cars to the database.
     *
     * @param cars The cars to write.
     */
    protected void writeCars(List<Car> cars) {
        try (FileWriter file = new FileWriter(FILE_PATH)) {
            JSONObject jsonObject = new JSONObject();
            JSONArray jsonArray = new JSONArray();

            cars.forEach(car -> {
                jsonArray.add(this.convertCarToJSONObject(car));
            });

            jsonObject.put("cars", jsonArray);

            file.write(jsonObject.toJSONString());
            file.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Convert a Json Object to a Car Object.
     *
     * @param jsonObject The Json Object to convert.
     * @return The Car Object converted.
     */
    private Car convertJSONObjectToCar(JSONObject jsonObject) {
        UUID uuid = UUID.fromString((String) jsonObject.get("uuid"));
        CarColor color = Converters.stringToCarColor((String) jsonObject.get("color"));
        CarType carType = Converters.stringToCarType((String) jsonObject.get("carType"));
        String model = (String) jsonObject.get("model");
        float kilometers = Float.parseFloat((String) jsonObject.get("kilometers"));
        int seats = Integer.parseInt((String) jsonObject.get("seats"));
        CarBrand carBrand = Converters.stringToCarBrand((String) jsonObject.get("carBrand"));
        int power = Integer.parseInt((String) jsonObject.get("power"));
        float price = Float.parseFloat((String) jsonObject.get("price"));
        boolean reserved = Boolean.parseBoolean((String) jsonObject.get("reserved"));

        return new Car(uuid, color, carType, model, kilometers, seats, carBrand, power, price, reserved);
    }

    /**
     * Convert a Car Object to a Json Object.
     *
     * @param car The Car Object to convert.
     * @return The Json Object converted.
     */
    private JSONObject convertCarToJSONObject(Car car) {
        JSONObject carDetails = new JSONObject();

        carDetails.put("uuid", car.getUuid().toString());
        carDetails.put("color", car.getColor().toString());
        carDetails.put("carType", car.getType().toString());
        carDetails.put("model", car.getModel());
        carDetails.put("kilometers", String.valueOf(car.getKilometers()));
        carDetails.put("seats", String.valueOf(car.getSeats()));
        carDetails.put("carBrand", car.getBrand().toString());
        carDetails.put("power", String.valueOf(car.getPower()));
        carDetails.put("price", String.valueOf(car.getPrice()));
        carDetails.put("reserved", String.valueOf(car.isReserved()));

        JSONObject customerJSONObject = new JSONObject();
        customerJSONObject.put("car", carDetails);

        return customerJSONObject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarsFileManager manager = (CarsFileManager) o;
        return Objects.equals(FILE_PATH, manager.FILE_PATH);
    }

    @Override
    public int hashCode() {
        return Objects.hash(FILE_PATH);
    }

    @Override
    public String toString() {
        return "CarsFileManager{" +
                "FILE_PATH='" + FILE_PATH + '\'' +
                '}';
    }
}
