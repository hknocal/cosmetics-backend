package com.kea.cosmeticsbackend.service;

import com.kea.cosmeticsbackend.model.Treatment;
import com.kea.cosmeticsbackend.repository.TreatmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TreatmentService {
    private TreatmentRepository treatmentRepository;

    public List<Treatment> getTreatmentById(Long id){
        return treatmentRepository.findTreatmentById(id);
    }

    public List<Treatment> getTreatmentByDiscount(int discount){
        return treatmentRepository.findTreatmentByDiscount(discount);
    }

    public List<Treatment> getTreatments(){
        return treatmentRepository.findAll();
    }

    public Treatment saveTreatment(Treatment treatment){
        return treatmentRepository.save(treatment);
    }

    public Treatment updateTreatment(Long id, Treatment updatedTreatment){
        if (treatmentRepository.existsById(id)){
            updatedTreatment.setId(id);
            return treatmentRepository.save(updatedTreatment);
        }else {
            return null;
        }
    }

    public void deleteTreatment(Long id){
        treatmentRepository.deleteById(id);
    }
}
