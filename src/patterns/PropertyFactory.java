package patterns;

import domain.Apartment;
import domain.Property;

public class PropertyFactory {
    public Property create(PropertyType type, long id, String city, double price) {
        if (type == PropertyType.APARTMENT) {
            return new Apartment(id, city, price);
        }
        return new Property(id, city, price);
    }
}
