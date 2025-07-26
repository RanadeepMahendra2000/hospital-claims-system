package com.hospital.claims.service;

import com.hospital.claims.model.Claim;
import com.hospital.claims.model.Patient;
import com.hospital.claims.repository.ClaimRepository;
import com.hospital.claims.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClaimService {

    @Autowired
    private ClaimRepository claimRepository;

    @Autowired
    private PatientRepository patientRepository; // ✅ Add this

    public List<Claim> getAllClaims() {
        return claimRepository.findAll();
    }

    public Optional<Claim> getClaimById(Long id) {
        return claimRepository.findById(id);
    }

    public Claim createClaim(Claim claim) {
        // ✅ Fetch full patient from DB using the ID provided in request
        Long patientId = claim.getPatient().getId();
        Patient fullPatient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        claim.setPatient(fullPatient); // ✅ Attach full patient

        // Business rule: auto-approve claims under 10,000
        if (claim.getAmount() <= 10000) {
            claim.setStatus("APPROVED");
        } else {
            claim.setStatus("PENDING");
        }

        return claimRepository.save(claim);
    }

    public void deleteClaim(Long id) {
        claimRepository.deleteById(id);
    }

    public Claim updateClaim(Long id, Claim updatedClaim) {
        return claimRepository.findById(id).map(claim -> {
            claim.setAmount(updatedClaim.getAmount());
            claim.setDateOfClaim(updatedClaim.getDateOfClaim());

            // ✅ Optional: handle updated patient if passed
            if (updatedClaim.getPatient() != null) {
                Long patientId = updatedClaim.getPatient().getId();
                Patient fullPatient = patientRepository.findById(patientId)
                        .orElseThrow(() -> new RuntimeException("Patient not found"));
                claim.setPatient(fullPatient);
            }

            // Re-evaluate business rule
            if (updatedClaim.getAmount() <= 10000) {
                claim.setStatus("APPROVED");
            } else {
                claim.setStatus("PENDING");
            }

            return claimRepository.save(claim);
        }).orElse(null);
    }
}
