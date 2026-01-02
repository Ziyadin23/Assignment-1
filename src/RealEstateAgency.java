import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

// Data abstraction: agency manages the data pool and exposes simple queries.
public class RealEstateAgency {
    private final String agencyName;
    private final List<Property> listings = new ArrayList<>();

    public RealEstateAgency(String agencyName) { this.agencyName = agencyName; }
    public String getAgencyName() { return agencyName; }

    // minimal data pool operations
    public void add(Property p) { listings.add(p); }
    public List<Property> all() { return new ArrayList<>(listings); }

    // filtering, searching, sorting (minimal set)
    public List<Property> filterByCity(String city) {
        return listings.stream()
                .filter(p -> p.getCity().equalsIgnoreCase(city))
                .collect(Collectors.toList());
    }
    public List<Property> searchByPrice(double min, double max) {
        return listings.stream()
                .filter(p -> p.getPrice() >= min && p.getPrice() <= max)
                .collect(Collectors.toList());
    }
    public List<Property> sortByPriceAsc() {
        return listings.stream()
                .sorted(Comparator.comparingDouble(Property::getPrice))
                .collect(Collectors.toList());
    }

    @Override
    public String toString() { return "Agency{name='" + agencyName + "', listings=" + listings.size() + "}"; }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RealEstateAgency)) return false;
        RealEstateAgency that = (RealEstateAgency) o;
        return Objects.equals(agencyName, that.agencyName);
    }
    @Override
    public int hashCode() { return Objects.hash(agencyName); }
}