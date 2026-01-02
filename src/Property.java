import java.util.Objects;

public class Property {
    private final long id;       // encapsulation: private fields
    private String city;
    private double price;

    public Property(long id, String city, double price) {
        this.id = id;
        this.city = city;
        this.price = price;
    }

    // getters/setters
    public long getId() { return id; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

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
}

