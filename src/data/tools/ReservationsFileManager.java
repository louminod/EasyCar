package data.tools;

import entities.Car;
import entities.Customer;
import entities.Reservation;
import entities.references.ReservationStatus;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import tools.Converters;

import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class ReservationsFileManager {
    private final String FILE_PATH = "src/data/files/reservations.json";

    protected ReservationsFileManager() {

    }

    /**
     * Get the database reservations list.
     *
     * @return A list of Reservation.
     */
    protected List<Reservation> getReservations() {
        List<Reservation> reservations = new ArrayList<>();

        try (FileReader reader = new FileReader(FILE_PATH)) {

            JSONObject jsonObject = (JSONObject) new JSONParser().parse(reader);
            JSONArray jsonArray = (JSONArray) jsonObject.get("reservations");

            jsonArray.forEach(reservation -> {
                reservations.add(this.convertJSONObjectToReservation((JSONObject) ((JSONObject) reservation).get("reservation")));
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

        return reservations;
    }

    /**
     * Write reservations to the database.
     *
     * @param reservations The reservations to write.
     */
    protected void writeReservations(List<Reservation> reservations) {
        try (FileWriter file = new FileWriter(FILE_PATH)) {
            JSONObject jsonObject = new JSONObject();
            JSONArray jsonArray = new JSONArray();

            reservations.forEach(reservation -> {
                jsonArray.add(this.convertReservationToJSONObject(reservation));
            });

            jsonObject.put("reservations", jsonArray);

            file.write(jsonObject.toJSONString());
            file.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Convert a Json Object to a Reservation Object.
     *
     * @param jsonObject The Json Object to convert.
     * @return The Reservation Object converted.
     */
    private Reservation convertJSONObjectToReservation(JSONObject jsonObject) {
        UUID uuid = UUID.fromString((String) jsonObject.get("uuid"));
        LocalDateTime date = LocalDateTime.parse((String) jsonObject.get("date"));
        float price = Float.parseFloat((String) jsonObject.get("price"));
        Customer customer = DatabaseManager.getCustomerFromUuid((String) jsonObject.get("customerUuid"));
        Car car = DatabaseManager.getCarFromUuid((String) jsonObject.get("carUuid"));
        ReservationStatus status = Converters.stringToReservationStatus((String) jsonObject.get("status"));
        LocalDate startDate = LocalDate.parse((String) jsonObject.get("startDate"));
        LocalDate endDate = LocalDate.parse((String) jsonObject.get("endDate"));
        return new Reservation(uuid, date, price, customer, car, status, startDate, endDate);
    }

    /**
     * Convert a Reservation Object to a Json Object.
     *
     * @param reservation The Reservation Object to convert.
     * @return The Json Object converted.
     */
    private JSONObject convertReservationToJSONObject(Reservation reservation) {
        JSONObject reservationDetails = new JSONObject();

        reservationDetails.put("uuid", reservation.getUuid().toString());
        reservationDetails.put("date", reservation.getDate().toString());
        reservationDetails.put("price", String.valueOf(reservation.getPrice()));
        reservationDetails.put("customerUuid", reservation.getCustomer().getUuid().toString());
        reservationDetails.put("carUuid", reservation.getCar().getUuid().toString());
        reservationDetails.put("status", reservation.getStatus().toString());
        reservationDetails.put("startDate", reservation.getStartDate().toString());
        reservationDetails.put("endDate", reservation.getEndDate().toString());

        JSONObject reservationJSONObject = new JSONObject();
        reservationJSONObject.put("reservation", reservationDetails);

        return reservationJSONObject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReservationsFileManager that = (ReservationsFileManager) o;
        return Objects.equals(FILE_PATH, that.FILE_PATH);
    }

    @Override
    public int hashCode() {
        return Objects.hash(FILE_PATH);
    }

    @Override
    public String toString() {
        return "ReservationsFileManager{" +
                "FILE_PATH='" + FILE_PATH + '\'' +
                '}';
    }
}
