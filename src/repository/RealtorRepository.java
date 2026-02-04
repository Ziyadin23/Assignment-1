package repository;

import dto.RealtorRecord;
import java.util.List;

public interface RealtorRepository {
    int insertRealtor(String name);
    RealtorRecord getRealtorById(int id);
    List<RealtorRecord> listRealtors();
    int updateRealtor(int id, String name);
    int deleteRealtor(int id);
}
