package service;

import dto.PropertyRecord;
import java.util.List;

public interface PropertyService {
    List<PropertyRecord> listProperties();
    PropertyRecord getProperty(int id);
    void createProperty(PropertyRecord property);
    void updateProperty(int id, PropertyRecord property);
    void deleteProperty(int id);
}
