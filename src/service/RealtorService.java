package service;

import dto.RealtorRecord;
import java.util.List;

public interface RealtorService {
    List<RealtorRecord> listRealtors();
    RealtorRecord getRealtor(int id);
    void createRealtor(RealtorRecord realtor);
    void updateRealtor(int id, RealtorRecord realtor);
    void deleteRealtor(int id);
}
