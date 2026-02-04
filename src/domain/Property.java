package domain;

import java.util.Objects;

public class Property {
    private final long id;       // encapsulation: private fields
    private String city;
    private double price;

    public Property(long id, String city, double price) {
        this.id = id;
        setCity(city);
        setPrice(price);
    }

    // getters/setters
    public long getId() { return id; }
    public String getCity() { return city; }
    public void setCity(String city) {
        if (city == null || city.isBlank()) {
            throw new IllegalArgumentException("City cannot be blank.");
        }
        this.city = city;
    }
    public double getPrice() { return price; }
    public void setPrice(double price) {
        if (price <= 0) {
            throw new IllegalArgumentException("Price must be greater than 0.");
        }
        this.price = price;
    }

    // polymorphic behavior (subclasses can override)
    public double getCommissionRate() {
        return 0.03; // default 3%
    }

    // required overrides
    @Override
    public String toString() {
        return "Property{id=" + id + ", city='" + city + "', price=" + price + "}";
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Property)) return false;
        Property p = (Property) o;
        return id == p.id;
    }
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // Builder pattern
    public static Builder builder(long id) {
        return new Builder(id);
    }

    public static class Builder {
        private final long id;
        private String city;
        private double price;

        private Builder(long id) {
            this.id = id;
        }

        public Builder city(String city) {
            this.city = city;
            return this;
        }

        public Builder price(double price) {
            this.price = price;
            return this;
        }

        public Property build() {
            return new Property(id, city, price);
        }
    }
}
