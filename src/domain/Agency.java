package domain;

import java.util.Objects;

public class Agency {
    private int id;
    private String name;
    private String address;

    public Agency() {}

    public Agency(int id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public Agency(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    @Override
    public String toString() {
        return "Agency{id=" + id + ", name='" + name + "', address='" + address + "'}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Agency)) return false;
        Agency agency = (Agency) o;
        return id == agency.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
