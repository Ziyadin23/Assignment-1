package service;

import dto.AgencyRecord;
import java.util.List;

public interface AgencyService {
    List<AgencyRecord> listAgencies();
    AgencyRecord getAgency(int id);
    void createAgency(AgencyRecord agency);
    void updateAgency(int id, AgencyRecord agency);
    void deleteAgency(int id);
}
