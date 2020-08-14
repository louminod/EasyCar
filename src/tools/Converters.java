package tools;

import entities.references.*;

import java.awt.*;
import java.util.Arrays;

public abstract class Converters {
    private Converters() {
    }

    /**
     * Convert a String into a CarType.
     *
     * @param string The string to convert.
     * @return The CarType Object.
     */
    public static CarType stringToCarType(String string) {
        CarType result = null;
        for (CarType carType : Arrays.asList(CarType.values())) {
            if (carType.toString().equals(string)) {
                result = carType;
            }
        }
        return result;
    }

    /**
     * Convert a String into a CarBrand.
     *
     * @param string The string to convert.
     * @return The CarBrand Object.
     */
    public static CarBrand stringToCarBrand(String string) {
        CarBrand result = null;
        for (CarBrand carBrand : Arrays.asList(CarBrand.values())) {
            if (carBrand.toString().equals(string)) {
                result = carBrand;
            }
        }
        return result;
    }

    /**
     * Convert a String into a CarColor.
     *
     * @param string The string to convert.
     * @return The CarColor Object.
     */
    public static CarColor stringToCarColor(String string) {
        CarColor result = null;
        for (CarColor carColor : Arrays.asList(CarColor.values())) {
            if (carColor.toString().equals(string)) {
                result = carColor;
            }
        }
        return result;
    }

    /**
     * Convert a String into a ReservationStatus.
     *
     * @param string The string to convert.
     * @return The ReservationStatus Object.
     */
    public static ReservationStatus stringToReservationStatus(String string) {
        ReservationStatus result = null;
        for (ReservationStatus status : Arrays.asList(ReservationStatus.values())) {
            if (status.toString().equals(string)) {
                result = status;
            }
        }
        return result;
    }

    /**
     * Convert a String into a WorkPlace.
     *
     * @param string The string to convert.
     * @return The WorkPlace Object.
     */
    public static WorkPlace stringToWorkPlace(String string) {
        WorkPlace result = null;
        for (WorkPlace workPlace : Arrays.asList(WorkPlace.values())) {
            if (workPlace.toString().equals(string)) {
                result = workPlace;
            }
        }
        return result;
    }
}
