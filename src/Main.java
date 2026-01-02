public class Main {
    public static void main(String[] args) {
        RealEstateAgency agency = new RealEstateAgency("KazEstate");
        Realtor realtor = new Realtor("Arman");

        // Inheritance in action: Apartment is a Property
        Property p1 = new Apartment(1L, "Pavlodar", 120_000);
        Property p2 = new Property(2L, "Almaty", 150_000); // base class only
        Property p3 = new Apartment(3L, "Almaty", 95_000);

        agency.add(p1);
        agency.add(p2);
        agency.add(p3);

        // toString/equals/hashCode demonstrated
        System.out.println(agency);
        System.out.println(realtor);
        agency.all().forEach(System.out::println);
        System.out.println("p2 equals id=2? " + p2.equals(new Property(2L, "X", 0)));

        // filtering, searching, sorting
        System.out.println("Filter city=Almaty: " + agency.filterByCity("Almaty"));
        System.out.println("Search price 100k..160k: " + agency.searchByPrice(100_000, 160_000));
        System.out.println("Sort by price ASC: " + agency.sortByPriceAsc());

        // Polymorphism: commission varies by property type
        System.out.printf("Commission p1: %.2f%n", realtor.calculateCommission(p1)); // Apartment rate
        System.out.printf("Commission p2: %.2f%n", realtor.calculateCommission(p2)); // Base rate
    }
}


