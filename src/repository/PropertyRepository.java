package repository;

import dto.PropertyRecord;
import java.util.List;

public interface PropertyRepository {
    int insertProperty(String city, double price);
    PropertyRecord getPropertyById(int id);
    List<PropertyRecord> listProperties();
    int updateProperty(int id, String city, double price);
    int deleteProperty(int id);
}
