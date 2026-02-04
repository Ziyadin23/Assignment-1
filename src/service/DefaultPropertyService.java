package service;

import dto.PropertyRecord;
import exceptions.InvalidInputException;
import exceptions.NotFoundException;
import repository.PropertyRepository;
import util.ValidationRules;

import java.util.List;

public class DefaultPropertyService implements PropertyService, ValidationRules {
    private final PropertyRepository propertyRepository;

    public DefaultPropertyService(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    @Override
    public List<PropertyRecord> listProperties() {
        return propertyRepository.listProperties();
    }

    @Override
    public PropertyRecord getProperty(int id) {
        if (id <= 0) {
            throw new InvalidInputException("Property id must be positive.");
        }
        PropertyRecord property = propertyRepository.getPropertyById(id);
        if (property == null) {
            throw new NotFoundException("Property not found.");
        }
        return property;
    }

    @Override
    public void createProperty(PropertyRecord property) {
        validateProperty(property);
        int result = propertyRepository.insertProperty(property.getCity(), property.getPrice());
        if (result <= 0) {
            throw new IllegalStateException("Failed to create property.");
        }
    }

    @Override
    public void updateProperty(int id, PropertyRecord property) {
        if (id <= 0) {
            throw new InvalidInputException("Property id must be positive.");
        }
        validateProperty(property);
        int result = propertyRepository.updateProperty(id, property.getCity(), property.getPrice());
        if (result <= 0) {
            throw new NotFoundException("Property not found.");
        }
    }

    @Override
    public void deleteProperty(int id) {
        if (id <= 0) {
            throw new InvalidInputException("Property id must be positive.");
        }
        int result = propertyRepository.deleteProperty(id);
        if (result <= 0) {
            throw new NotFoundException("Property not found.");
        }
    }

    private void validateProperty(PropertyRecord property) {
        if (property == null) {
            throw new InvalidInputException("Property payload is required.");
        }
        requireNonBlank("City", property.getCity());
        ValidationRules.requirePositive("Price", property.getPrice());
    }
}
