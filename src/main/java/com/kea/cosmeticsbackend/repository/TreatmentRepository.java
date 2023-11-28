package com.kea.cosmeticsbackend.repository;

import com.kea.cosmeticsbackend.model.Treatment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface TreatmentRepository extends JpaRepository<Treatment, Long> {

    List<Treatment> findTreatmentById(Long id);

    List<Treatment> findTreatmentByDiscount(int discount);

}