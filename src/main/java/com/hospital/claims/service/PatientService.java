package com.hospital.claims.service;

import com.hospital.claims.model.Patient;
import com.hospital.claims.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Optional<Patient> getPatientById(Long id) {
        return patientRepository.findById(id);
    }

    public Patient createPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    public void deletePatient(Long id) {
        patientRepository.deleteById(id);
    }

    public Patient updatePatient(Long id, Patient updated) {
        return patientRepository.findById(id).map(p -> {
            p.setName(updated.getName());
            p.setEmail(updated.getEmail());
            p.setInsuranceNumber(updated.getInsuranceNumber());
            return patientRepository.save(p);
        }).orElse(null);
    }
}
