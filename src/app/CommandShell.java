package app;

import domain.Apartment;
import domain.Property;
import domain.RealEstateAgency;
import domain.Realtor;
import patterns.PropertyFactory;
import patterns.PropertyType;

import java.util.List;

public class CommandShell {
    public void run() {
        System.out.println("=== Real Estate Agency Demo ===");

        PropertyFactory factory = new PropertyFactory();
        Property home = Property.builder(1)
                .city("Dubai")
                .price(450_000)
                .build();
        Property apartment = factory.create(PropertyType.APARTMENT, 2, "Abu Dhabi", 320_000);

        Realtor realtor = new Realtor("Sara");
        System.out.printf("Commission (home): %.2f%n", realtor.calculateCommission(home));
        System.out.printf("Commission (apartment): %.2f%n", realtor.calculateCommission(apartment));

        RealEstateAgency agency = new RealEstateAgency("Skyline Realty");
        agency.add(home);
        agency.add(apartment);
        agency.add(new Apartment(3, "Dubai", 280_000));

        List<Property> dubaiListings = agency.filterByCity("Dubai");
        List<Property> midRange = agency.searchByPrice(250_000, 400_000);
        List<Property> sorted = agency.sortByPriceAsc();

        System.out.println("Dubai listings: " + dubaiListings);
        System.out.println("Mid-range listings: " + midRange);
        System.out.println("Sorted listings: " + sorted);
    }
}
