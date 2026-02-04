package service;

import dto.RealtorRecord;
import exceptions.InvalidInputException;
import exceptions.NotFoundException;
import repository.RealtorRepository;
import util.ValidationRules;

import java.util.List;

public class DefaultRealtorService implements RealtorService, ValidationRules {
    private final RealtorRepository realtorRepository;

    public DefaultRealtorService(RealtorRepository realtorRepository) {
        this.realtorRepository = realtorRepository;
    }

    @Override
    public List<RealtorRecord> listRealtors() {
        return realtorRepository.listRealtors();
    }

    @Override
    public RealtorRecord getRealtor(int id) {
        if (id <= 0) {
            throw new InvalidInputException("Realtor id must be positive.");
        }
        RealtorRecord realtor = realtorRepository.getRealtorById(id);
        if (realtor == null) {
            throw new NotFoundException("Realtor not found.");
        }
        return realtor;
    }

    @Override
    public void createRealtor(RealtorRecord realtor) {
        validateRealtor(realtor);
        int result = realtorRepository.insertRealtor(realtor.getName());
        if (result <= 0) {
            throw new IllegalStateException("Failed to create realtor.");
        }
    }

    @Override
    public void updateRealtor(int id, RealtorRecord realtor) {
        if (id <= 0) {
            throw new InvalidInputException("Realtor id must be positive.");
        }
        validateRealtor(realtor);
        int result = realtorRepository.updateRealtor(id, realtor.getName());
        if (result <= 0) {
            throw new NotFoundException("Realtor not found.");
        }
    }

    @Override
    public void deleteRealtor(int id) {
        if (id <= 0) {
            throw new InvalidInputException("Realtor id must be positive.");
        }
        int result = realtorRepository.deleteRealtor(id);
        if (result <= 0) {
            throw new NotFoundException("Realtor not found.");
        }
    }

    private void validateRealtor(RealtorRecord realtor) {
        if (realtor == null) {
            throw new InvalidInputException("Realtor payload is required.");
        }
        requireNonBlank("Name", realtor.getName());
    }
}
