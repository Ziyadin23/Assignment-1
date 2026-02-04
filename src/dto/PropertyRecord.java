package dto;

import java.util.Objects;

public class PropertyRecord {
    private int id;
    private String city;
    private double price;

    public PropertyRecord() {}

    public PropertyRecord(int id, String city, double price) {
        this.id = id;
        this.city = city;
        this.price = price;
    }

    public PropertyRecord(String city, double price) {
        this.city = city;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getCity() {
        return city;
    }

    public double getPrice() {
        return price;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "PropertyRecord{id=" + id + ", city='" + city + "', price=" + price + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PropertyRecord)) return false;
        PropertyRecord that = (PropertyRecord) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
