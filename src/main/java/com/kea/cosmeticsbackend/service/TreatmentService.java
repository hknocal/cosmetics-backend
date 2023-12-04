package com.kea.cosmeticsbackend.service;

import com.kea.cosmeticsbackend.dto.CreateTreatmentDTO;
import com.kea.cosmeticsbackend.model.Treatment;
import com.kea.cosmeticsbackend.repository.TreatmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TreatmentService {
    private TreatmentRepository treatmentRepository;

    public List<Treatment> getTreatmentByTreatmentId(int id){
        return treatmentRepository.findTreatmentByTreatmentId(id);
    }
    public List<Treatment> getTreatments(){
        return treatmentRepository.findAll();
    }

    public Treatment saveTreatment(CreateTreatmentDTO createTreatmentDTO) {
        Treatment treatment = convertDTOToEntity(createTreatmentDTO);
        return treatmentRepository.save(treatment);
    }

    // Hjælpefunktion til at konvertere DTO til Entity
    private Treatment convertDTOToEntity(CreateTreatmentDTO createTreatmentDTO) {
        Treatment treatment = new Treatment();
        treatment.setTreatmentType(createTreatmentDTO.getTreatmentType());
        treatment.setPrice(createTreatmentDTO.getPrice());
        treatment.setDuration(createTreatmentDTO.getDuration());
        treatment.setDiscount(createTreatmentDTO.getDiscount());

        return treatment;
    }

    public Treatment updateTreatment(int id, Treatment updatedTreatment){
        if (treatmentRepository.existsById(id)){
            updatedTreatment.setTreatmentId(id);
            return treatmentRepository.save(updatedTreatment);
        }else {
            return null;
        }
    }
    public void deleteTreatment(int id){
        treatmentRepository.deleteById(id);
    }
}
