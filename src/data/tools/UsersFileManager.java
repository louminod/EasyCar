package data.tools;

import entities.Administrator;
import entities.Customer;
import entities.Employee;
import entities.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import tools.Converters;
import tools.Tool;

import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class UsersFileManager {
    private final String FILE_PATH = "src/data/files/users.json";

    protected UsersFileManager() {

    }

    /**
     * Get the database users list.
     *
     * @return A list of User.
     */
    protected List<User> getUsers() {
        List<User> users = new ArrayList<>();

        try (FileReader reader = new FileReader(FILE_PATH)) {

            JSONObject jsonObject = (JSONObject) new JSONParser().parse(reader);
            JSONArray jsonArray = (JSONArray) jsonObject.get("users");

            jsonArray.forEach(user -> {
                String userType = (String) ((JSONObject) user).keySet().toArray()[0];
                switch (userType) {
                    case "customer":
                        users.add(this.convertJSONObjectToCustomer((JSONObject) ((JSONObject) user).get(userType)));
                        break;
                    case "employee":
                        users.add(this.convertJSONObjectToEmployee((JSONObject) ((JSONObject) user).get(userType)));
                        break;
                    case "administrator":
                        users.add(this.convertJSONObjectToAdministrator((JSONObject) ((JSONObject) user).get(userType)));
                        break;
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

        return users;
    }

    /**
     * Write users to the database.
     *
     * @param users The users to write.
     */
    protected void writeUsers(List<User> users) {
        try (FileWriter file = new FileWriter(FILE_PATH)) {
            JSONObject jsonObject = new JSONObject();
            JSONArray jsonArray = new JSONArray();

            users.forEach(user -> {
                if (Customer.class.equals(user.getClass())) {
                    jsonArray.add(this.convertCustomerToJSONObject((Customer) user));
                }
                if (Employee.class.equals(user.getClass())) {
                    jsonArray.add(this.convertEmployeeToJSONObject((Employee) user));
                }
                if (Administrator.class.equals(user.getClass())) {
                    jsonArray.add(this.convertAdministratorToJSONObject((Administrator) user));
                }
            });

            jsonObject.put("users", jsonArray);

            file.write(jsonObject.toJSONString());
            file.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Convert a Json Object to a Customer Object
     *
     * @param jsonObject The Json Object to convert.
     * @return The Customer Object converted.
     */
    private Customer convertJSONObjectToCustomer(JSONObject jsonObject) {
        return new Customer(UUID.fromString((String) jsonObject.get("uuid")), (String) jsonObject.get("name"), (String) jsonObject.get("lastName"), (String) jsonObject.get("email"), (String) jsonObject.get("password"), (String) jsonObject.get("phoneNumber"), (String) jsonObject.get("address"), LocalDate.parse((String) jsonObject.get("birthDay"), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }

    /**
     * Convert a Json Object to a Employee Object
     *
     * @param jsonObject The Json Object to convert.
     * @return The Employee Object converted.
     */
    private Employee convertJSONObjectToEmployee(JSONObject jsonObject) {
        return new Employee(UUID.fromString((String) jsonObject.get("uuid")), (String) jsonObject.get("name"), (String) jsonObject.get("lastName"), (String) jsonObject.get("email"), (String) jsonObject.get("password"), Converters.stringToWorkPlace((String) jsonObject.get("workPlace")));
    }

    /**
     * Convert a Json Object to a Administrator Object
     *
     * @param jsonObject The Json Object to convert.
     * @return The Administrator Object converted.
     */
    private Administrator convertJSONObjectToAdministrator(JSONObject jsonObject) {
        return new Administrator(UUID.fromString((String) jsonObject.get("uuid")), (String) jsonObject.get("name"), (String) jsonObject.get("lastName"), (String) jsonObject.get("email"), (String) jsonObject.get("password"));
    }

    /**
     * Convert a Customer Object to a JSON Object.
     *
     * @param customer The Customer object to convert.
     * @return The Json Object converted.
     */
    private JSONObject convertCustomerToJSONObject(Customer customer) {
        JSONObject customerDetails = new JSONObject();
        customerDetails.put("uuid", customer.getUuid().toString());
        customerDetails.put("name", customer.getName());
        customerDetails.put("lastName", customer.getLastName());
        customerDetails.put("email", customer.getEmail());
        customerDetails.put("password", customer.getPassword());
        customerDetails.put("phoneNumber", customer.getPhoneNumber());
        customerDetails.put("address", customer.getAddress());
        customerDetails.put("birthDay", customer.getBirthDay().toString());

        JSONObject customerJSONObject = new JSONObject();
        customerJSONObject.put("customer", customerDetails);

        return customerJSONObject;
    }

    /**
     * Convert a Employee Object to a JSON Object.
     *
     * @param employee The Employee object to convert.
     * @return The Json Object converted.
     */
    private JSONObject convertEmployeeToJSONObject(Employee employee) {
        JSONObject employeeDetails = new JSONObject();
        employeeDetails.put("uuid", employee.getUuid().toString());
        employeeDetails.put("name", employee.getName());
        employeeDetails.put("lastName", employee.getLastName());
        employeeDetails.put("email", employee.getEmail());
        employeeDetails.put("password", employee.getPassword());
        employeeDetails.put("workPlace", employee.getWorkPlace().toString());

        JSONObject employeeJSONObject = new JSONObject();
        employeeJSONObject.put("employee", employeeDetails);

        return employeeJSONObject;
    }

    /**
     * Convert a Administrator Object to a JSON Object.
     *
     * @param administrator The Administrator object to convert.
     * @return The Json Object converted.
     */
    private JSONObject convertAdministratorToJSONObject(Administrator administrator) {
        JSONObject administratorDetails = new JSONObject();
        administratorDetails.put("uuid", administrator.getUuid().toString());
        administratorDetails.put("name", administrator.getName());
        administratorDetails.put("lastName", administrator.getLastName());
        administratorDetails.put("email", administrator.getEmail());
        administratorDetails.put("password", administrator.getPassword());

        JSONObject administratorJSONObject = new JSONObject();
        administratorJSONObject.put("administrator", administratorDetails);

        return administratorJSONObject;
    }

    /**
     * Connect a user with his email and his password.
     *
     * @param email    The user email.
     * @param password The user password.
     * @return User is connected, null instead.
     */
    protected User connect(String email, String password) {
        User result = null;

        for (User user : this.getUsers()) {
            if (email.equals(user.getEmail()) && password.equals(user.getPassword())) {
                result = user;
            }
        }

        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsersFileManager manager = (UsersFileManager) o;
        return Objects.equals(FILE_PATH, manager.FILE_PATH);
    }

    @Override
    public int hashCode() {
        return Objects.hash(FILE_PATH);
    }

    @Override
    public String toString() {
        return "UsersFileManager{" +
                "FILE_PATH='" + FILE_PATH + '\'' +
                '}';
    }
}
