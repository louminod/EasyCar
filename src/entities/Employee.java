package entities;

import entities.references.WorkPlace;

import java.util.Objects;
import java.util.UUID;

public class Employee extends User {

    private WorkPlace workPlace;

    public Employee(UUID uuid, String name, String lastName, String email, String password, WorkPlace workPlace) {
        super(uuid, name, lastName, email, password);
        this.workPlace = workPlace;
    }

    public WorkPlace getWorkPlace() {
        return this.workPlace;
    }

    public void setWorkPlace(WorkPlace workPlace) {
        this.workPlace = workPlace;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return workPlace == employee.workPlace;
    }

    @Override
    public int hashCode() {
        return Objects.hash(workPlace);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "workPlace=" + workPlace +
                '}';
    }
}
