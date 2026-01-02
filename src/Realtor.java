import java.util.Objects;

public class Realtor {
    private String name;

    public Realtor(String name) { this.name = name; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double calculateCommission(Property p) {
        return p.getPrice() * p.getCommissionRate();
    }

    @Override
    public String toString() { return "Realtor{name='" + name + "'}"; }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Realtor)) return false;
        Realtor r = (Realtor) o;
        return Objects.equals(name, r.name);
    }
    @Override
    public int hashCode() { return Objects.hash(name); }
}
