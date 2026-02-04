package domain;

// Inheritance: Apartment extends Property and overrides behavior
public class Apartment extends Property {
    public Apartment(long id, String city, double price) {
        super(id, city, price);
    }

    @Override
    public double getCommissionRate() {
        return 0.025; // 2.5%, shows polymorphism
    }
}
