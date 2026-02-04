package repository;

import dto.AgencyRecord;
import java.util.List;

public interface AgencyRepository {
    int insertAgency(String name, String address);
    AgencyRecord getAgencyById(int id);
    List<AgencyRecord> listAgencies();
    int updateAgency(int id, String name, String address);
    int deleteAgency(int id);
}
