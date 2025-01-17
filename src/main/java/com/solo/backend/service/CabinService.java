package com.solo.backend.service;

import com.solo.backend.model.Cabin;
import com.solo.backend.repository.CabinRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CabinService {

    private final CabinRepository cabinRepository;

    // Constructor injection
    public CabinService(CabinRepository cabinRepository) {
        this.cabinRepository = cabinRepository;
    }

    // Create a new cabin
    public Cabin createCabin(Cabin cabin) {
        return cabinRepository.save(cabin);
    }

    // Get cabin by ID
    public Optional<Cabin> getCabinById(Long id) {
        return cabinRepository.findById(id);
    }

    // List all cabins
    public List<Cabin> getAllCabins() {
        return cabinRepository.findAll();
    }

    // Update an existing cabin
    public Cabin updateCabin(Long id, Cabin cabin) {
        if (cabinRepository.existsById(id)) {
            cabin.setId(id);  // Ensure the ID is set on the cabin object
            return cabinRepository.save(cabin);
        }
        return null;
    }

    // Delete a cabin by ID
    public boolean deleteCabin(Long id) {
        if (cabinRepository.existsById(id)) {
            cabinRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
