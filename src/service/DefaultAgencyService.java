package service;

import dto.AgencyRecord;
import exceptions.InvalidInputException;
import exceptions.NotFoundException;
import repository.AgencyRepository;
import util.ValidationRules;

import java.util.List;

public class DefaultAgencyService implements AgencyService, ValidationRules {
    private final AgencyRepository agencyRepository;

    public DefaultAgencyService(AgencyRepository agencyRepository) {
        this.agencyRepository = agencyRepository;
    }

    @Override
    public List<AgencyRecord> listAgencies() {
        return agencyRepository.listAgencies();
    }

    @Override
    public AgencyRecord getAgency(int id) {
        if (id <= 0) {
            throw new InvalidInputException("Agency id must be positive.");
        }
        AgencyRecord agency = agencyRepository.getAgencyById(id);
        if (agency == null) {
            throw new NotFoundException("Agency not found.");
        }
        return agency;
    }

    @Override
    public void createAgency(AgencyRecord agency) {
        validateAgency(agency);
        int result = agencyRepository.insertAgency(agency.getName(), agency.getAddress());
        if (result <= 0) {
            throw new IllegalStateException("Failed to create agency.");
        }
    }

    @Override
    public void updateAgency(int id, AgencyRecord agency) {
        if (id <= 0) {
            throw new InvalidInputException("Agency id must be positive.");
        }
        validateAgency(agency);
        int result = agencyRepository.updateAgency(id, agency.getName(), agency.getAddress());
        if (result <= 0) {
            throw new NotFoundException("Agency not found.");
        }
    }

    @Override
    public void deleteAgency(int id) {
        if (id <= 0) {
            throw new InvalidInputException("Agency id must be positive.");
        }
        int result = agencyRepository.deleteAgency(id);
        if (result <= 0) {
            throw new NotFoundException("Agency not found.");
        }
    }

    private void validateAgency(AgencyRecord agency) {
        if (agency == null) {
            throw new InvalidInputException("Agency payload is required.");
        }
        requireNonBlank("Name", agency.getName());
        requireNonBlank("Address", agency.getAddress());
    }
}
