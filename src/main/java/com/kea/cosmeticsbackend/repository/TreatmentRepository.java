package com.kea.cosmeticsbackend.repository;

import com.kea.cosmeticsbackend.model.Treatment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface TreatmentRepository extends JpaRepository<Treatment, Integer> {
    List<Treatment> findTreatmentByTreatmentId(int id);
    Optional<Treatment> findByTreatmentType(String treatmentType);

}
