package entities;

import java.util.UUID;

public class Administrator extends User {
    public Administrator(UUID uuid, String name, String lastName, String email, String password) {
        super(uuid, name, lastName, email, password);
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
